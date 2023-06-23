package com.store.store.Services;

import com.store.store.Model.Entities.Bicycle;

import java.util.List;

public interface BicycleService {

    List<Bicycle> getAllBicycles();

    Bicycle getBicycleById(int id);

    Bicycle saveBicycle(Bicycle bicycle);

    Boolean deleteBicycle(int id);

    Bicycle createBicycle(Bicycle bicycle);

    Bicycle updateBicycle(Integer id, Bicycle bicycle);
}

