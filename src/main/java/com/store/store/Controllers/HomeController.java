package com.store.store.Controllers;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Services.BicycleService;
import com.store.store.Services.Impl.BicycleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController

public class HomeController {
    BicycleService bicycleService;

    public HomeController(BicycleService hobbyService) {
        this.bicycleService = hobbyService;
    }


    @GetMapping("/home")
   public String home () {

        return"home!";
    }

    @GetMapping(value = "/all")
    public List<Bicycle> getAllBicycles() {
        return this.bicycleService.getAllBicycles();
    }

}

