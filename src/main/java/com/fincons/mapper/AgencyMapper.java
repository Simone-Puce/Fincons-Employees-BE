package com.fincons.mapper;

import com.fincons.entity.Agency;
import com.fincons.model.AgencyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AgencyMapper {
    public Agency mapAgencyDtoToAgency(AgencyDto agencyDto) {
        return new Agency(agencyDto.getName(), agencyDto.getAddress());
    }

    public AgencyDto mapAgencyToAgencyDto(Agency agency) {
        return new AgencyDto(agency.getId(), agency.getName(), agency.getAddress());
    }

    public List<AgencyDto> mapAgencyListToAgencyDtoList(List<Agency> agencyList) {
        return agencyList.stream()
                .map(this::mapAgencyToAgencyDto)
                .collect(Collectors.toList());
    }
}
