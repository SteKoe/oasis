package de.stekoe.idss.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.Document;

public interface DocumentRepository extends CrudRepository<Document, String> {
}
