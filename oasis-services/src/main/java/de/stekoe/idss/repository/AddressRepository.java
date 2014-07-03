package de.stekoe.idss.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
}
