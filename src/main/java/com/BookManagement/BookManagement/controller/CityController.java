package com.BookManagement.BookManagement.controller;

import com.BookManagement.BookManagement.apiResponse.ApiResponse;
import com.BookManagement.BookManagement.entity.City;
import com.BookManagement.BookManagement.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/city")
@CrossOrigin(origins = "http://localhost:4200/", allowCredentials = "true")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping("/getCities")
    public ResponseEntity<ApiResponse<List<City>>> getAllCities(){
        try{
            List<City> city = cityService.getAllCities();
            if(city.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ApiResponse<>( "No City Found", null));
            }
            return ResponseEntity.ok(
                    new ApiResponse<>("City Fetch Successfully", city));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error while Fetching city data", null));
        }
    }
}
