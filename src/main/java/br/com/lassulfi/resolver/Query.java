package br.com.lassulfi.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import br.com.lassulfi.model.Author;
import br.com.lassulfi.model.Book;
import br.com.lassulfi.repository.AuthorRepository;
import br.com.lassulfi.repository.BookRepository;

public class Query implements GraphQLQueryResolver {
	
	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	
	public Query(BookRepository bookRepository, AuthorRepository authorRepository) {
		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}
	
	public Iterable<Book> findAllBooks() {
		return bookRepository.findAll();
	}
	
	public Iterable<Author> findAllAuthors() {
		return authorRepository.findAll();
	}
	
	public long countBooks() {
		return bookRepository.count();
	}
	
	public long countAuthors() {
		return authorRepository.count();
	}
}
