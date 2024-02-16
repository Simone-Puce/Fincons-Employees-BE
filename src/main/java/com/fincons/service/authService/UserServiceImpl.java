package com.fincons.service.authService;

import com.fincons.dto.RoleDTO;
import com.fincons.dto.UserDTO;
import com.fincons.entity.Role;
import com.fincons.entity.User;
import com.fincons.exception.EmailException;
import com.fincons.exception.PasswordException;
import com.fincons.exception.ResourceNotFoundException;
import com.fincons.exception.RoleException;
import com.fincons.jwt.JwtTokenProvider;
import com.fincons.jwt.LoginDto;
import com.fincons.mapper.UserAndRoleMapper;
import com.fincons.utility.PasswordValidator;
import com.fincons.utility.RoleValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fincons.repository.RoleRepository;
import com.fincons.repository.UserRepository;
import com.fincons.utility.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
public class UserServiceImpl  implements UserService{


    public UserServiceImpl(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepo;
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAndRoleMapper userAndRoleMapper;

    private JwtTokenProvider jwtTokenProvider;

    @Value("${admin.password}")
    private String passwordAdmin;

    @Override
    public User registerNewUser(UserDTO userDTO, String passwordForAdmin) throws EmailException, PasswordException {

        String emailDto = userDTO.getEmail().toLowerCase().replace(" ", "");

        if (emailDto.isEmpty() || !EmailValidator.isValidEmail(emailDto) || userRepo.existsByEmail(emailDto)) {
            LOG.warn("Invalid or existing email!!");
            throw new EmailException(EmailException.emailInvalidOrExist());
        }
            User userToSave = userAndRoleMapper.dtoToUser(userDTO);
            Role role;

            if(!PasswordValidator.isValidPassword(userDTO.getPassword())){
                LOG.warn("Password dos not respect Regex!!");
                throw new PasswordException( PasswordException.passwordDoesNotRespectRegexException());
            }
            if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {
                role = roleToAssign("ROLE_ADMIN");
            } else {
                role = roleToAssign("ROLE_USER");
            }
            userToSave.setRoles(List.of(role));
            userToSave.setGeneratedPassword(false);
            User userSaved = userRepo.save(userToSave);

            return userSaved;
    }

    @Override
    public String login(LoginDto loginDto) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.generateToken(authentication);
    }


    @Override
    public List<User> findAllUsers() {
        return  userRepo.findAll();
    }

    @Override
    public void deleteUserByEmail(String email) throws EmailException, RoleException {
        User userToRemove = userRepo.findByEmail(email);
        if(userToRemove==null){
            throw new EmailException(EmailException.emailInvalidOrExist());
        }

        List<User> usersWithRoleAdmin = userRepo.findAll()
                .stream()
                .filter(user -> user.getRoles()
                        .stream()
                        .anyMatch(role -> role.getName().equals("ROLE_ADMIN")))
                .toList();

        if(usersWithRoleAdmin.size() < 2 ){
            throw new RoleException("Can't modify the only admin remained!");
        }
        userRepo.delete(userToRemove);
    }

    @Override
    public User updateUser(String email, UserDTO userModified, String passwordForAdmin, String currentPassword) throws EmailException, PasswordException, RoleException {

        //Se email esiste
        // verifico che l'email sia valida
        if (email.isEmpty() || !EmailValidator.isValidEmail(email) || !userRepo.existsByEmail(email) ) {
            throw new EmailException(EmailException.emailInvalidOrExist());
        }

        //Se passa i controlli lo assegno a userFound
        User userFound = userRepo.findByEmail(email);

        //Modificare i campi se valorizzati
        if (userModified.getFirstName() != null) {
            userFound.setFirstName(userModified.getFirstName());
        }

        if (userModified.getLastName() != null) {
            userFound.setLastName(userModified.getLastName());
        }

        // password valorizzata e matcha con la password dell'utente  ?
        if (currentPassword != null && !passwordEncoder.matches(currentPassword, userFound.getPassword()) ) {
            throw new PasswordException("The password entered does not match the user's password");
        }

		// Se non è stata inserita la current password e l'utente iserisce la nuova password genera l'eccezione
		if(currentPassword == null && userModified.getPassword() != null){
			throw new PasswordException("The user must enter the current password of user before update it");
		}


        if(!PasswordValidator.isValidPassword(userModified.getPassword())){
            throw new PasswordException("The new password does not respect regex!");
        }

        // password per l'amministratore valorizzata ?, verifica e aggiorna il ruolo
        if (passwordForAdmin != null && passwordForAdmin.equals(passwordAdmin)) {

            if (RoleValidator.isValidRole(String.valueOf(userModified.getRoles().get(0)))) {
                throw new RoleException(RoleException.roleDoesNotRespectRegex());
            }

            // Se il nome del ruolo dell'user da modificare è "ROLE_ADMIN" e Il nome del nuovo ruolo è diverso da admin controllo che ci sia un altro admin
            if(userFound.getRoles().get(0).getName().equals("ROLE_ADMIN") && !userModified.getRoles().get(0).getName().equals("ROLE_ADMIN")){

                //count numbers of admins
                List<User> usersWithRoleAdmin = userRepo.findAll()
                        .stream()
                        .filter(user -> user.getRoles()
                                .stream()
                                .anyMatch(role -> role.getName().equals("ROLE_ADMIN")))
                        .toList();

                if(usersWithRoleAdmin.size() < 2 ){
                    throw new RoleException("Can't modify the only admin remained!");
                }

                userFound.setRoles(List.of(roleToAssign("ROLE_ADMIN")));

            }else{

                // se ruolo diverso da admin, se esiste nella role repository prendilo e assegnalo, se non esiste crealo nel db e poi settalo
               userFound.setRoles(List.of(roleToAssign(userModified.getRoles().get(0).getName())));

            }

        }

        userFound.setPassword(passwordEncoder.encode(userModified.getPassword()));

        return userRepo.save(userFound);

    }


    @Override
    public User getUserDtoByEmail(String email) {
        if(userRepo.findByEmail(email)==null){
            throw new ResourceNotFoundException("User with this email doesn't exist");
        }
        return userRepo.findByEmail(email);
    }

    public Role roleToAssign(String nomeRuolo) {
        Role role = roleRepo.findByName(nomeRuolo);
        if (role == null) {
            Role newRole = new Role();
            newRole.setName(nomeRuolo);
            role = roleRepo.save(newRole);
        }
        return role;
    }




    //METODO PER AGGIUNGERE UN NUOVO USER IN SEGUITO ALLA LETTURA DI UN FILE.
    public User addNewUser(User user) throws RuntimeException {

        String emailUser = user.getEmail().toLowerCase().replace(" ", "");
        // Controllo se l'indirizzo email è valido e non esiste un utente registrato con la mail specificata
    //    if (!emailUser.isEmpty() && EmailValidator.isValidEmail(emailUser) && !userRepo.existsByEmail(emailUser)) {
            Role role;
            role = roleToAssign("ROLE_USER");
            user.setRoles(List.of(role));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
    //    } else{
    //        return false;
    //    }
    }


}
