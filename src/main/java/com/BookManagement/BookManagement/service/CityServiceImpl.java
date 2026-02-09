package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.City;
import com.BookManagement.BookManagement.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    @Autowired
    private CityRepository cityRepo;

    @Override
    public List<City> getAllCities() {
        return cityRepo.findByIsActiveTrue();
    }
}
