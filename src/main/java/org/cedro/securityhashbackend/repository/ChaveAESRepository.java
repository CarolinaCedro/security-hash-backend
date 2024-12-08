package org.cedro.securityhashbackend.repository;

import org.cedro.securityhashbackend.model.ChaveAES;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveAESRepository extends JpaRepository<ChaveAES, String> {

}
