package com.example.demo.service;

import com.example.demo.clasesBase.Entrenador;
import com.example.demo.clasesBase.Torneo;
import com.example.demo.repository.EntrenadorRepository;
import com.example.demo.repository.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntrenadorService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;
    
    @Autowired
    private TorneoRepository torneoRepository;

    @Transactional
    public Entrenador guardarEntrenador(Entrenador entrenador) {
        // Sincroniza las relaciones bidireccionales
        if (entrenador.getTorneos() != null) {
            for (Torneo torneo : entrenador.getTorneos()) {
                torneo.agregarEntrenador(entrenador);
            }
        }

        // Guarda el entrenador
        return entrenadorRepository.save(entrenador);
    }
    
    
    public Entrenador buscarEntrenadorPorId(Long id) {
        return entrenadorRepository.findById(id).orElse(null);
    }
}
