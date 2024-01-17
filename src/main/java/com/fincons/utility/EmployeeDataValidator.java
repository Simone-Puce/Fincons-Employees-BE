package com.fincons.utility;
import com.fincons.dto.EmployeeDTO;
import com.fincons.dto.ErrorDetailDTO;
import com.fincons.enums.ErrorCode;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeDataValidator {
    private EmployeeDataValidator() {

    }

    public static List<ErrorDetailDTO> isValidEmployee(EmployeeDTO employeeToValidate) {
        List<ErrorDetailDTO> validationResultList = new ArrayList<>();


        //controllo sui campi fondamentali: Nome,Cognome ed Email
        if ( (!isValidEmail(employeeToValidate.getEmail())) && (!isValidNameSurname(employeeToValidate.getFirstName())) && (!isValidNameSurname(employeeToValidate.getLastName())) ) {
            //se tutti e tre i campi non sono validi, aggiungi l'errore bloccante di riga interamente errata
            validationResultList.add(new ErrorDetailDTO(employeeToValidate.getRowNum(), "Name,Surname,Email", ErrorCode.INVALID_ROW));
            return validationResultList;
        }

        //Validazione dell'email
        if (!isValidEmail(employeeToValidate.getEmail())) {
            validationResultList.add(new ErrorDetailDTO(employeeToValidate.getRowNum(), "EmailId", ErrorCode.INVALID_EMAIL));

        }
        //Validazione nome
        if (!isValidNameSurname(employeeToValidate.getFirstName())) {
            validationResultList.add(new ErrorDetailDTO(employeeToValidate.getRowNum(), "Name", ErrorCode.INVALID_NAME_SURNAME));
        }

        //Validazione cognome
        if (!isValidNameSurname(employeeToValidate.getLastName())) {
            validationResultList.add(new ErrorDetailDTO(employeeToValidate.getRowNum(), "Surname", ErrorCode.INVALID_NAME_SURNAME));
        }

        //Validazione genere
        if (!isValidGenre(employeeToValidate.getGender())) {
            validationResultList.add(new ErrorDetailDTO(employeeToValidate.getRowNum(), "Genere", ErrorCode.INVALID_GENRE));
        }

        return validationResultList;
    }

    private static boolean isValidEmail(String email) {
        // Implementazione della validazione dell'email (ad esempio, utilizzando espressioni regolari)
        // Ritorna true se l'email è valida, altrimenti false

        if (!StringUtils.hasText(email)) {
            return false;
        }

        final  String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (Pattern.matches(regex, email) && email.length() > 3 && email.length() <= 60) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isValidNameSurname(String nameSurname) {
        if (!StringUtils.hasText(nameSurname)) {
            return false;
        }

        final String regex = "^[A-Za-z\\s]+$";

        if (Pattern.matches(regex, nameSurname) && nameSurname.length() >= 2 && nameSurname.length() <= 40) {
            return true;
        } else {
            return false;
        }

    }


    private static boolean isValidGenre(String genre) {
        if (!StringUtils.hasText(genre)) {
            return false;
        }

        if (genre.equalsIgnoreCase("M") || genre.equalsIgnoreCase("F") || genre.equalsIgnoreCase("O")) {
            return true;
        } else {
            return false;
        }

    }


    public static boolean isValidDate(String date) {

        // Implementazione della validazione della data
        // Ritorna true se la data è valida, altrimenti false

        try{
            if (!StringUtils.hasText(date)) {
                return false;
            }

            final  String regex = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

            if (Pattern.matches(regex, date)) {
                return true;
            } else {
                return false;
            }
        }catch (NumberFormatException e){
            return false;
        }


    }
    public static boolean isValidDateExcelFile(String date) {

        // Implementazione della validazione della data
        // Ritorna true se la data è valida, altrimenti false


        try{
            double doubleDate = Double.parseDouble(date);
            LocalDate processData = DateUtil.getLocalDateTime(doubleDate).toLocalDate();

            if (!StringUtils.hasText(processData.toString())) {
                return false;
            }

            final  String regex = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

            if (Pattern.matches(regex, processData.toString())) {
                return true;
            } else {
                return false;
            }
        }catch (NumberFormatException e){
            return false;
        }


    }

}
