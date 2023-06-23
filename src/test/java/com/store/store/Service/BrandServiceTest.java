package com.store.store.Service;

import com.store.store.AbstractTest;
import com.store.store.Model.Entities.Brand;
import com.store.store.Model.Repositories.BrandRepository;
import com.store.store.Services.BrandService;
import com.store.store.Services.Impl.BrandServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
public class BrandServiceTest extends AbstractTest {

    BrandService brandService;
    BrandRepository brandRepository;

    List<Brand> brands = new ArrayList<>();

    @Before
    public void setup(){

        brandRepository = mock(BrandRepository.class);
        brandService = new BrandServiceImpl(brandRepository);

        brands = new ArrayList<>();
        Brand brand1 = new Brand();
        brand1.setName("testBrand1");
        brands.add(brand1);
        Brand brand2 = new Brand();
        brand2.setName("testBrand2");
        brands.add(brand2);
        Mockito.when(brandRepository.findAll()).thenReturn(brands);

    }

    @Test
    public void getAllBrands_Success(){

        List<Brand> expectedBrands = brandService.getAllBrands();
        List<Brand> ServiceReasaultBrands = brandService.getAllBrands();

        assertEquals(brands,expectedBrands);

    }

    public void getBrandById_ExistingId_ReturnsBrand() {

        int brandId = 1;
        Brand expectedBrand = new Brand();
        expectedBrand.setId(brandId);
        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.of(expectedBrand));
        Brand resultBrand = brandService.getBrandById(brandId);
        assertEquals(expectedBrand, resultBrand);
    }

    @Test
    public void getBrandById_NonExistingId_ReturnsNull() {
          int brandId = 1;
        Mockito.when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act
        Brand resultBrand = brandService.getBrandById(brandId);

        // Assert
        assertNull(resultBrand);
    }

    @Test
    public void saveBrand_ValidBrand_ReturnsSavedBrand() {
        // Arrange
        Brand brandToSave = new Brand();
        brandToSave.setName("Test Brand");
        Mockito.when(brandRepository.save(brandToSave)).thenReturn(brandToSave);

        // Act
        Brand savedBrand = brandService.saveBrand(brandToSave);

        // Assert
        assertEquals(brandToSave, savedBrand);
    }

    @Test
    public void deleteBrand_ExistingId_DeletesBrand() {
        // Arrange
        int brandId = 1;

        // Act
        brandService.deleteBrand(brandId);

        // Assert
        Mockito.verify(brandRepository, Mockito.times(1)).deleteById(brandId);
    }

    @Test
    public void getBrandByName_ExistingName_ReturnsBrandOptional() {
        // Arrange
        String brandName = "Test Brand";
        Brand expectedBrand = new Brand();
        expectedBrand.setName(brandName);
        Mockito.when(brandRepository.findByName(brandName)).thenReturn(Optional.of(expectedBrand));

        // Act
        Optional<Brand> resultBrand = brandService.getBrandByName(brandName);

        // Assert
        assertTrue(resultBrand.isPresent());
        assertEquals(expectedBrand, resultBrand.get());
    }

    @Test
    public void getBrandByName_NonExistingName_ReturnsEmptyOptional() {
        // Arrange
        String brandName = "Non-existing Brand";
        Mockito.when(brandRepository.findByName(brandName)).thenReturn(Optional.empty());

        // Act
        Optional<Brand> resultBrand = brandService.getBrandByName(brandName);

        // Assert
        assertFalse(resultBrand.isPresent());
    }

    @Test
    public void createBrand_ValidBrand_ReturnsSavedBrand() {
        // Arrange
        Brand brandToCreate = new Brand();
        brandToCreate.setName("Test Brand");
        Mockito.when(brandRepository.save(brandToCreate)).thenReturn(brandToCreate);

        // Act
        Brand createdBrand = brandService.createBrand(brandToCreate);

        // Assert
        assertEquals(brandToCreate, createdBrand);
    }

    @Test
    public void getBrandsWithMultipleBicycles_MinBicycles_ReturnsBrandsWithMinBicycles() {
        // Arrange
        long minBicycles = 5;
        List<Brand> expectedBrands = new ArrayList<>();
        Brand brand1 = new Brand();
        brand1.setName("Brand 1");
        expectedBrands.add(brand1);
        Brand brand2 = new Brand();
        brand2.setName("Brand 2");
        expectedBrands.add(brand2);
        Mockito.when(brandRepository.findBrandsWithMinBicycles(minBicycles)).thenReturn(expectedBrands);

        // Act
        List<Brand> resultBrands = brandService.getBrandsWithMultipleBicycles(minBicycles);

        // Assert
        assertEquals(expectedBrands, resultBrands);
    }
}
