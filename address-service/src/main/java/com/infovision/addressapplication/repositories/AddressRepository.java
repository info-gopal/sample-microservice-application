package com.infovision.addressapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.infovision.addressapplication.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	Address findAddressByEmpId(Long empid);

}
