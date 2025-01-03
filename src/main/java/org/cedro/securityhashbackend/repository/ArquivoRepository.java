package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, String> {

}
