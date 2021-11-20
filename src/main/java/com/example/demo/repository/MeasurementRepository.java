package com.example.demo.repository;

import com.example.demo.domain.Measurement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends PagingAndSortingRepository<Measurement,Integer> {

    List<Measurement> findAllByDistrictId(int districtId, Pageable pageable);

    Optional<Measurement> findByCo2level(double co2level);
}
