package com.example.demo.service;

import com.example.demo.domain.District;
import com.example.demo.domain.Measurement;
import com.example.demo.dto.MeasurementDTO;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final DistrictRepository districtRepository;


    public List<MeasurementDTO> readMeasurements(int districtId, int page, int size, Principal principal) {
        validateAccess(districtId, principal);


        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findAllByDistrictId(districtId, pageable).stream()
                .map(m -> new MeasurementDTO(m.getCo2level(), m.getCreated(), m.getDistrict().getId(),m.getDistrict().getCity().getId()))
                .collect(Collectors.toList());
    }

    public void saveMeasurement(int districtId, MeasurementDTO measurementDTO, Principal principal) {

        if (! Objects.equals(principal.getName(), "SENSOR")) {
            throw new AccessDeniedException(String.format("Principal {} does not have permission to write measurements of district with id: {}", principal.getName(), districtId));
        }

        Optional<District> district = districtRepository.findById(measurementDTO.getDistrictId());

        if (district.isPresent()) {
            Measurement measurement = new Measurement();
            measurement.setCreated(measurementDTO.getTimestamp());
            measurement.setCo2level(measurementDTO.getCo2level());
            measurement.setDistrict(district.get());
            measurementRepository.save(measurement);
        }


    }

    private void validateAccess(int districtId, Principal principal) {
        int count = districtRepository.countDistrictsByPrincipal(districtId, principal.getName());

        if (count == 0) {
            throw new AccessDeniedException(String.format("Principal {} does not have permission to view measurements of district with id: {}", principal.getName(), districtId));
        }
    }



}
