package com.example.demo.controller;

import com.example.demo.dto.MeasurementDTO;
import com.example.demo.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class MeasurementController {

    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping("/measurements/{districtId}")
    public List<MeasurementDTO> readMeasurements(@PathVariable int districtId, Principal principal, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int pageSize) {
        return measurementService.readMeasurements(districtId, page, pageSize, principal);
    }

    @PostMapping("/measurement/{districtId}")
    public void saveMeasurement(@PathVariable int districtId, Principal principal, @RequestBody MeasurementDTO measurementDTO) {
         measurementService.saveMeasurement(districtId, measurementDTO, principal);
    }
}
