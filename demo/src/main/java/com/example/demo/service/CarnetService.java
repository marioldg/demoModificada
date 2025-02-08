package com.example.demo.service;

import com.example.demo.clasesBase.Carnet;
import com.example.demo.repository.CarnetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarnetService {

    @Autowired
    private CarnetRepository carnetRepository;

    public Carnet guardarCarnet(Carnet carnet) {
        return carnetRepository.save(carnet);
    }

    public Carnet buscarCarnetPorId(Long id) {
        return carnetRepository.findById(id).orElse(null);
    }
}
