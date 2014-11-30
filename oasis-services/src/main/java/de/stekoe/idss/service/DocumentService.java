package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.repository.DocumentRepository;

@Service
@Transactional
public class DocumentService {
    @Inject
    private DocumentRepository documentRepository;

    public void delete(String id) {
        documentRepository.delete(id);
    }

    public Document findOne(String s) {
        return documentRepository.findOne(s);
    }

    public void save(Document document) {
        documentRepository.save(document);
    }
}
