package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Address;
import de.stekoe.idss.repository.AddressRepository;

@Service
@Transactional(readOnly = true)
public class AddressService {

    @Inject
    private AddressRepository addressRepository;

    public Address findOne(String id) {
        return addressRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        addressRepository.delete(id);
    }
}
