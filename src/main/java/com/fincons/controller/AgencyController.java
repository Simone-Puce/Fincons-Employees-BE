package com.fincons.controller;


import com.fincons.model.AgencyDto;
import com.fincons.service.AgencyServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {

    @Autowired
    private final AgencyServiceApi agencyServiceApi;

    @Autowired
    public AgencyController(AgencyServiceApi agencyServiceApi) {
        this.agencyServiceApi = agencyServiceApi;
    }

    @GetMapping
    public List<AgencyDto> getAllAgency() {
        return agencyServiceApi.getAllAgency();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgencyDto> getAgencyById(@PathVariable Long id) {
        Optional<AgencyDto> azienda = agencyServiceApi.getAgencyById(id);
        return azienda.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AgencyDto> createAgency(@RequestBody AgencyDto agencyDto) {
        Optional<AgencyDto> createdAgency = agencyServiceApi.createAgency(agencyDto);
        return createdAgency.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgencyDto> updateAgency(@PathVariable Long id, @RequestBody AgencyDto agencyDto) {
        Optional<AgencyDto> updatedAgency = agencyServiceApi.updateAgency(id, agencyDto);
        return updatedAgency.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyServiceApi.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }
}
