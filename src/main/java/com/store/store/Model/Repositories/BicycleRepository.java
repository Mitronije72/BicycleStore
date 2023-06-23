package com.store.store.Model.Repositories;

import com.store.store.Model.Entities.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle, Integer> {

}

