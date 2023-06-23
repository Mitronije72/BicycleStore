package com.store.store.web;


import com.fasterxml.jackson.databind.ObjectMapper;
//import com.store.store.Configuration.SpringSecurityWebAuxTestConfig;
import com.store.store.AbstractTest;
import com.store.store.Controllers.BicycleController;
import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Brand;
import com.store.store.Model.Entities.Category;
import com.store.store.Model.Entities.dto.BicycleDto;
import com.store.store.Services.BicycleService;
import com.store.store.Services.BrandService;
import com.store.store.Services.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Import(SpringSecurityWebAuxTestConfig.class)
public class BicycleControllerTest extends AbstractTest {

    private MockMvc mockMvc;


    @Mock
    private BicycleService bicycleService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BrandService brandService;

    @Mock
    ModelMapper modelMapper;


    List<Bicycle> bicycles = new ArrayList<>();

    @InjectMocks
    private BicycleController bicycleController;
    @Before
    public void setup() {
        bicycles = new ArrayList<Bicycle>();

        Bicycle bicycle1 = new Bicycle();
        bicycle1.setId(1);
        bicycle1.setName("Bicycle 1");

        Bicycle bicycle2 = new Bicycle();
        bicycle2.setId(2);
        bicycle2.setName("Bicycle 2");

        bicycles.add(bicycle1);
        bicycles.add(bicycle2);

        Category category = new Category();
        category.setId(1);
        category.setName("Category 1");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Brand 1");

        mockMvc = MockMvcBuilders.standaloneSetup(bicycleController).build();
    }

    @Test
    public void getAllBicycles_Should_ReturnBicycles() throws Exception {
        when(bicycleService.getAllBicycles()).thenReturn(bicycles);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bicycles")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String responseBody = mvcResult.getResponse().getContentAsString();
        Bicycle[] responseBicycles = new ObjectMapper().readValue(responseBody, Bicycle[].class);
        assertEquals(bicycles.size(), responseBicycles.length);
        assertEquals(bicycles.get(0).getName(), responseBicycles[0].getName());
        assertEquals(bicycles.get(1).getName(), responseBicycles[1].getName());
    }

    @Test
    public void getBicycleById_ExistingId_Should_ReturnBicycle() throws Exception {
        int id = 1;
        Bicycle bicycle = new Bicycle();
        bicycle.setId(id);
        bicycle.setName("Bicycle 1");

        when(bicycleService.getBicycleById(id)).thenReturn(bicycle);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bicycles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String responseBody = mvcResult.getResponse().getContentAsString();
        Bicycle responseBicycle = new ObjectMapper().readValue(responseBody, Bicycle.class);
        assertEquals(bicycle.getId(), responseBicycle.getId());
        assertEquals(bicycle.getName(), responseBicycle.getName());
    }

    @Test
    public void getBicyclesByBrand_ExistingBrand_Should_ReturnBicycles() throws Exception {
        String brandName = "Brand 1";
        Brand brand = new Brand();
        brand.setName(brandName);
        brand.setBicycles(bicycles);

        when(brandService.getBrandByName(brandName)).thenReturn(Optional.of(brand));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bicycles/brand/{name}", brandName)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String responseBody = mvcResult.getResponse().getContentAsString();
        Bicycle[] responseBicycles = new ObjectMapper().readValue(responseBody, Bicycle[].class);
        assertEquals(bicycles.size(), responseBicycles.length);
        assertEquals(bicycles.get(0).getName(), responseBicycles[0].getName());
        assertEquals(bicycles.get(1).getName(), responseBicycles[1].getName());
    }


    @Test
    public void updateBicycle_ExistingIdAndValidBicycleInfo_Should_ReturnUpdatedBicycle() throws Exception {
        int id = 1;
        BicycleDto bicycleDto = new BicycleDto();
        bicycleDto.setName("Updated Bicycle");
        bicycleDto.setCategoryName("test");
        bicycleDto.setBrandName("test");

        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("Brand 1");

        Category category = new Category();
        category.setId(1);
        category.setName(bicycleDto.getCategoryName());

        when(brandService.getBrandByName(bicycleDto.getBrandName())).thenReturn(Optional.of(brand));
        when(categoryService.getCategoryByName(bicycleDto.getCategoryName())).thenReturn(Optional.of(category));

        Bicycle updatedBicycle = new Bicycle();
        updatedBicycle.setId(id);
        updatedBicycle.setName("Updated Bicycle");
        updatedBicycle.setBrand(brand);

        when(bicycleService.updateBicycle(Mockito.eq(id), Mockito.any(Bicycle.class))).thenReturn(updatedBicycle);
        when(bicycleService.getBicycleById(id)).thenReturn(updatedBicycle);

        ModelMapper originalModelMapper = new ModelMapper();

        when(modelMapper.map(Mockito.any(), Mockito.eq(Bicycle.class))).thenAnswer(invocation -> {
            Object source = invocation.getArgument(0);
            return originalModelMapper.map(source, Bicycle.class);
        });
        String requestBody = new ObjectMapper().writeValueAsString(bicycleDto);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/bicycles/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String responseBody = mvcResult.getResponse().getContentAsString();
        Bicycle responseBicycle = new ObjectMapper().readValue(responseBody, Bicycle.class);
        assertEquals(updatedBicycle.getId(), responseBicycle.getId());
        assertEquals(updatedBicycle.getName(), responseBicycle.getName());
    }


    @Test
    public void deleteBicycle_ExistingId_Should_ReturnNoContent() throws Exception {
        int id = 1;

        when(bicycleService.deleteBicycle(id)).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/bicycles/{id}", id))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.NO_CONTENT.value(), status);
    }

}
