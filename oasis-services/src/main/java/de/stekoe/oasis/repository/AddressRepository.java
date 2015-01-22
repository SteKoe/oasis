package de.stekoe.oasis.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.Address;

public interface AddressRepository extends CrudRepository<Address, String> {
}
