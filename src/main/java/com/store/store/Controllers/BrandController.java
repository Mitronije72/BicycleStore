package com.store.store.Controllers;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Brand;
import com.store.store.Services.BicycleService;
import com.store.store.Services.BrandService;
import com.store.store.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(
            ModelMapper modelMapper,
                           BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/{count}")
    public ResponseEntity<List<Brand>> getAllBrandsWithBicycleCountMorethan(@PathVariable long count) {
        List<Brand> brands = brandService.getBrandsWithMultipleBicycles(count);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

}
