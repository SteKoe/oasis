package de.stekoe.oasis.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.Document;

public interface DocumentRepository extends CrudRepository<Document, String> {
}
