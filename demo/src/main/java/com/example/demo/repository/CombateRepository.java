package com.example.demo.repository;

import com.example.demo.clasesBase.Combate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombateRepository extends JpaRepository<Combate, Long> {



}
