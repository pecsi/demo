package com.example.demo.repository;

import com.example.demo.domain.District;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends CrudRepository<District,Integer> {

    @Query(
            value = "SELECT count(*) FROM district d JOIN city c WHERE d.city_id = c.id AND d.id = ?1 AND c.principal = ?2",
            nativeQuery = true)
    int countDistrictsByPrincipal(int districtId, String principal);

}
