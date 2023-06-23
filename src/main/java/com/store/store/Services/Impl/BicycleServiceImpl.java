package com.store.store.Services.Impl;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Category;
import com.store.store.Model.Repositories.BicycleRepository;
import com.store.store.Services.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BicycleServiceImpl implements BicycleService {

    private final BicycleRepository bicycleRepository;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository) {
        this.bicycleRepository = bicycleRepository;
    }

    @Override
    public List<Bicycle> getAllBicycles() {
        List<Bicycle> bicycles = bicycleRepository.findAll();
        return bicycles;
    }

    @Override
    public Bicycle getBicycleById(int id) {
        return bicycleRepository.findById(id).orElse(null);
    }

    @Override
    public Bicycle saveBicycle(Bicycle bicycle) {
        return bicycleRepository.save(bicycle);
    }

    @Override
    public Boolean deleteBicycle(int id) {
        if (bicycleRepository.existsById(id)) {
            bicycleRepository.deleteById(id);
            return true; // Deletion successful
        } else {
            return false; // Category with the given ID does not exist
        }
    }

    @Override
    public Bicycle createBicycle(Bicycle bicycle) {

            // Perform any necessary validation or business logic checks
            // For example, you can check if the category already exists

            // Call the CategoryRepository to save the category
            return bicycleRepository.save(bicycle);
        }

    @Override
    public Bicycle updateBicycle(Integer id, Bicycle bicycle) {

        // Check if the category with the given id exists in the database
        Bicycle existingbicycle = bicycleRepository.findById(id).orElse(null);
        if (existingbicycle == null) {
            return null; // Category not found, return null or throw an exception
        }

        // Update the existing category with the new data
        existingbicycle.setName(bicycle.getName());
        existingbicycle.setBrand(bicycle.getBrand());
        existingbicycle.setCategory(bicycle.getCategory());
        existingbicycle.setPrice(bicycle.getPrice());

        // Save the updated category
        return bicycleRepository.save(existingbicycle);
    }
}


