package com.example.demo.controller;

import com.example.demo.domain.District;
import com.example.demo.domain.Measurement;
import com.example.demo.repository.CityRepository;
import com.example.demo.repository.DistrictRepository;
import com.example.demo.repository.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MeasurementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    MeasurementRepository measurementRepository;


    @Test
    @WithMockUser(value = "foo")
    public void non_existing_principal_get_access_denied_when_reading_measurements() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/measurements/{districtId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(value = "vienna-principal")
    public void city_principal_can_read_districts_measurements() throws Exception {

        District favoriten = districtRepository.findById(1).get();

        Measurement measurement1 = new Measurement();
        measurement1.setDistrict(favoriten);
        measurement1.setCreated(LocalDateTime.parse("2021-11-20T20:21:48.156759"));
        measurement1.setCo2level(12.1);
        measurementRepository.save(measurement1);


        mockMvc.perform( MockMvcRequestBuilders
                        .get("/measurements/{districtId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"co2level\":12.1,\"timestamp\":\"2021-11-20T20:21:48.156759\",\"districtId\":1,\"cityId\":1}]"));
        ;

    }

    @Test
    @WithMockUser(value = "vienna-principal")
    public void wrong_city_principal_get_access_denied_when_reading_measurements() throws Exception {

        District gracia = districtRepository.findById(3).get();

        Measurement measurement1 = new Measurement();
        measurement1.setDistrict(gracia);
        measurement1.setCreated(LocalDateTime.now());
        measurement1.setCo2level(12.1);
        measurementRepository.save(measurement1);


        mockMvc.perform( MockMvcRequestBuilders
                        .get("/measurements/{districtId}", 3)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(value = "SENSOR")
    public void sensor_can_save_measurement() throws Exception {

        District favoriten = districtRepository.findById(1).get();

        Measurement measurement1 = new Measurement();
        measurement1.setDistrict(favoriten);
        measurement1.setCreated(LocalDateTime.now());
        measurement1.setCo2level(12.1);

        assertTrue(measurementRepository.findByCo2level(12.1).isEmpty());

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/measurement/{districtId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content("{\"co2level\":12.1,\"timestamp\":\"2021-11-20T20:21:48.156759\",\"districtId\":1}"))

                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(measurementRepository.findByCo2level(12.1).isPresent());

    }

}