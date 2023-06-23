package com.store.store.Controllers;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Brand;
import com.store.store.Model.Entities.Category;
import com.store.store.Model.Entities.dto.BicycleDto;
import com.store.store.Services.BicycleService;
import com.store.store.Services.BrandService;
import com.store.store.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bicycles")
public class BicycleController {

    private final BicycleService bicycleService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    private final BrandService brandService;

    @Autowired
    public BicycleController(BicycleService bicycleService, CategoryService categoryService, ModelMapper modelMapper, BrandService brandService) {
        this.bicycleService = bicycleService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Bicycle>> getAllBicycles() {
        List<Bicycle> bicycles = bicycleService.getAllBicycles();
        return new ResponseEntity<>(bicycles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bicycle> getBicycleById(@PathVariable Integer id) {
        Bicycle bicycle = bicycleService.getBicycleById(id);
        if (bicycle != null) {
            return new ResponseEntity<>(bicycle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/brand/{name}")
    public ResponseEntity<List<Bicycle>> getBicyclesByBrand(@PathVariable String name) {
        Brand brand = brandService.getBrandByName(name).orElse(null);
        if (brand != null) {
            return new ResponseEntity<>(brand.getBicycles(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Bicycle> createBicycle(@RequestBody BicycleDto bicycleInfo) {
        Bicycle bicycle = modelMapper.map(bicycleInfo,Bicycle.class);

        Category category = this.categoryService.getCategoryByName(bicycleInfo.getCategoryName())
                .orElse(this.categoryService.getCategoryByName("uncategorized")
                .orElse(null));

        Brand brand = this.brandService.getBrandByName(bicycleInfo.getBrandName())
                .orElse(null);

//if brand doesn'texist crate a new one

        if(brand == null){
            Brand newBrand = new Brand();
            newBrand.setName(bicycleInfo.getBrandName());
            this.brandService.createBrand(newBrand);

// if brand doesn't get created it wil break hire and return null EX handling neaded

            brand = brandService.getBrandByName(bicycleInfo.getBrandName()).orElse(null);
        }

        bicycle.setCategory(category);
        bicycle.setBrand(brand);

        Bicycle createdBicycle = bicycleService.createBicycle(bicycle);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBicycle);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Bicycle> updateBicycle(@PathVariable Integer id, @RequestBody BicycleDto bicycleInfo) {
        Bicycle bicycle = modelMapper.map(bicycleInfo, Bicycle.class);

        Category category = this.categoryService.getCategoryByName(bicycleInfo.getCategoryName())
                .orElse(this.categoryService.getCategoryByName("uncategorized")
                        .orElse(null));

        Brand brand = this.brandService.getBrandByName(bicycleInfo.getBrandName())
                .orElse(null);

//if brand doesn't exist crate a new one
        if(brand == null){
            Brand newBrand = new Brand();
            newBrand.setName(bicycleInfo.getBrandName());
            this.brandService.createBrand(newBrand);
// if brand doesn't get created it wil break hire and return null EX handling neaded
            brand = brandService.getBrandByName(bicycleInfo.getBrandName()).orElse(null);
        }

        bicycle.setCategory(category);
        bicycle.setBrand(brand);

//remember  the what used to be the name of the brand
        String oldBrandName = bicycleService.getBicycleById(id).getBrand().getName();

        Bicycle updatedBicycle = bicycleService.updateBicycle(id, bicycle);
// if after saving the bicycle name of the brand has changed, and old brand has no bicycles, remove brand from DB
        Brand oldBrand = brandService.getBrandByName(oldBrandName).orElse(null);
        if(oldBrand != null){
            String newBrandName = bicycleService.getBicycleById(id).getBrand().getName();
            List<Bicycle> bajsevi = oldBrand.getBicycles();

            if(bajsevi.isEmpty()){
                brandService.deleteBrand(oldBrand.getId());
            }
        }

        if (updatedBicycle != null) {
            return new ResponseEntity<>(updatedBicycle, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBicycle(@PathVariable Integer id) {
        boolean deleted = bicycleService.deleteBicycle(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

