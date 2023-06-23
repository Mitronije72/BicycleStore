package com.store.store.Services;

import com.store.store.Model.Entities.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    List<Brand> getAllBrands();

    Brand getBrandById(int id);

    Brand saveBrand(Brand brand);

    void deleteBrand(int id);

    Optional<Brand> getBrandByName(String brandName);

    Brand createBrand(Brand Brand);


    List<Brand> getBrandsWithMultipleBicycles(long minBicycles);

}

