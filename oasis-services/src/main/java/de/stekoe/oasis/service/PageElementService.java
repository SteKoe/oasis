package de.stekoe.oasis.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.repository.PageElementRepository;

@Service
@Transactional(readOnly = true)
public abstract class PageElementService {

    @Inject
    PageElementRepository pageElementRepository;
}
