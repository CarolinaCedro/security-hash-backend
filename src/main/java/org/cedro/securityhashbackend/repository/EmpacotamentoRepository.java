package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.Empacotamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public interface EmpacotamentoRepository extends JpaRepository<Empacotamento, String> {

}
