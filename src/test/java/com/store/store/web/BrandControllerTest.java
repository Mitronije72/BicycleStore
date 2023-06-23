package com.store.store.web;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.store.store.Configuration.SpringSecurityWebAuxTestConfig;
import com.store.store.AbstractTest;
import com.store.store.Controllers.BrandController;
import com.store.store.Model.Entities.Brand;
import com.store.store.Services.BrandService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@Import(SpringSecurityWebAuxTestConfig.class)
public class BrandControllerTest extends AbstractTest {


    MockMvc mvc;

    @InjectMocks
    BrandController brandController;
    @Mock
    BrandService brandService;
    List<Brand> brands;


    @Before
    public void setup(){
        brands = new ArrayList<>();

        Brand brand1 = new Brand();
        brand1.setName("Brand1");

        Brand brand2 = new Brand();
        brand2.setName("Brand2");

        brands.add(brand1);
        brands.add(brand2);

        mvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }


    @Test
 //   @WithUserDetails("USER")
    public void getAllBrands_Should_Work()throws Exception{
        Mockito.when(brandService.getAllBrands()).thenReturn(brands);
        String uri = "/brands";
       // String inputJson = mapToJson(brands);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        //      .content("asdasdasd")
                ).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    //   @WithUserDetails("USER")
    public void getAllBrandsWithBicycleCountMoreThan_Should_ReturnBrands() throws Exception {
        Mockito.when(brandService.getBrandsWithMultipleBicycles(Mockito.anyLong())).thenReturn(brands);
        int count = 1;
        String uri = "/brands/{count}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, count)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verify the response status code
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        // Verify the response body contains the expected list of brands
        String responseBody = mvcResult.getResponse().getContentAsString();

        List<Brand> responseBrands = mapFromJson(responseBody, new TypeReference<List<Brand>>(){});
        assertEquals(brands.size(), responseBrands.size());
        assertEquals(brands.get(0).getName(), responseBrands.get(0).getName());
        assertEquals(brands.get(1).getName(), responseBrands.get(1).getName());
    }

    protected <T> T mapFromJson(String json, TypeReference<T> typeReference)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, typeReference);
    }


    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


}
