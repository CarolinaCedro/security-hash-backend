package org.cedro.securityhashbackend.repository;


import org.cedro.securityhashbackend.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, String> {

}
