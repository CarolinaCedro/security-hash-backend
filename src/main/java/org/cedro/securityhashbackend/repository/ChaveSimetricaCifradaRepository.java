package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.ChaveSimetricaCifrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveSimetricaCifradaRepository extends JpaRepository<ChaveSimetricaCifrada, String> {

}
