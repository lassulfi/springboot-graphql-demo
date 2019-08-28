package br.com.lassulfi.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lassulfi.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {

}
