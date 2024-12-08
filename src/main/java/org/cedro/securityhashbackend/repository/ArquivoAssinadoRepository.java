package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.ArquivoAssinado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoAssinadoRepository extends JpaRepository<ArquivoAssinado, String> {

}
