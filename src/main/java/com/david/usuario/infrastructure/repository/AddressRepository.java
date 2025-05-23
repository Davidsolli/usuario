package com.david.usuario.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository<Address> extends JpaRepository<Address, Long> {
}
