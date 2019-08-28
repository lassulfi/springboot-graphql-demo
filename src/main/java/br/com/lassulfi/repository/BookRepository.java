package br.com.lassulfi.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.lassulfi.model.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

}
