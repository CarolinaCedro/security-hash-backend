package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.Chave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChaveRepository extends JpaRepository<Chave, String> {


}
