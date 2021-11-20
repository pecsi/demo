package com.example.demo.repository;

import com.example.demo.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City,Integer> {


}
