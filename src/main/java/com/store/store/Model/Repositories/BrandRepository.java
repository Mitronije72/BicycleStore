package com.store.store.Model.Repositories;
import com.store.store.Model.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Optional<Brand> findByName(String brandName);
    @Query("SELECT b FROM Brand b WHERE (SELECT COUNT(*) FROM b.bicycles)  >= :minBicycles")
//    @Query("SELECT b FROM Brand b JOIN FETCH b.bicycles WHERE size(b.bicycles) >= :minBicycles ORDER BY size(b.bicycles) DESC")
    List<Brand> findBrandsWithMinBicycles(@Param("minBicycles") long minBicycles);
}