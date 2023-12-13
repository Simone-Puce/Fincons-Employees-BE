package com.fincons.service;


import com.fincons.entity.Agency;
import com.fincons.mapper.AgencyMapper;
import com.fincons.model.AgencyDto;
import com.fincons.repository.AgencyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyServiceImpl implements AgencyServiceApi {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private AgencyMapper agencyMapper;

    @Override
    public List<AgencyDto> getAllAgency() {
        List<Agency> agency = agencyRepository.findAll();
        return agencyMapper.mapAgencyListToAgencyDtoList(agency);
    }

    @Override
    public Optional<AgencyDto> getAgencyById(Long id) {
        Optional<Agency> optionalAgency = agencyRepository.findById(id);
        return optionalAgency.map(agencyMapper::mapAgencyToAgencyDto);
    }


    @Override
    public Optional<AgencyDto> createAgency(AgencyDto agencyDto) {
        Agency agency = agencyMapper.mapAgencyDtoToAgency(agencyDto);
        Agency savedAgency = agencyRepository.save(agency);
        return Optional.ofNullable(agencyMapper.mapAgencyToAgencyDto(savedAgency));
    }

    @Override
    public Optional<AgencyDto> updateAgency(Long id, AgencyDto agencyDto) {
        Optional<Agency> optionalAzienda = agencyRepository.findById(id);
        return optionalAzienda.map(existingAgency -> {
            BeanUtils.copyProperties(agencyDto, existingAgency, "id");
            Agency updatedAgency = agencyRepository.save(existingAgency);
            return agencyMapper.mapAgencyToAgencyDto(updatedAgency);
        });
    }

    @Override
    public void deleteAgency(Long id) {
        Optional<Agency> optionalAzienda = agencyRepository.findById(id);
        optionalAzienda.ifPresent(agencyRepository::delete);
    }

}

