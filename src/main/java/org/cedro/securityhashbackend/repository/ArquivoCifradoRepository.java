package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.ArquivoCifrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoCifradoRepository extends JpaRepository<ArquivoCifrado, String> {

}
