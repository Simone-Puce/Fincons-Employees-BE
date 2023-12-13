package com.fincons.service;


import com.fincons.model.AgencyDto;

import java.util.List;
import java.util.Optional;

public interface AgencyServiceApi {

    List<AgencyDto> getAllAgency();

    Optional<AgencyDto> getAgencyById(Long id);

    Optional<AgencyDto> createAgency(AgencyDto agencyDto);

    Optional<AgencyDto> updateAgency(Long id, AgencyDto agencyDto);

    void deleteAgency(Long id);
}
