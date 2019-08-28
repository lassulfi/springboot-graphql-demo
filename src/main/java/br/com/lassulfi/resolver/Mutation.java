package br.com.lassulfi.resolver;

import java.util.Optional;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import br.com.lassulfi.model.Author;
import br.com.lassulfi.model.Book;
import br.com.lassulfi.repository.AuthorRepository;
import br.com.lassulfi.repository.BookRepository;
import br.com.lassulfi.repository.exception.BookNotFoundException;

public class Mutation implements GraphQLMutationResolver {

	private BookRepository bookRepository;
	private AuthorRepository authorRepository;
	
	public Mutation(BookRepository bookRepository, AuthorRepository authorRepository) {

		this.bookRepository = bookRepository;
		this.authorRepository = authorRepository;
	}
	
	public Author newAuthor(String firstName, String lastName) {
		Author author = new Author();
		author.setFirstName(firstName);
		author.setLastName(lastName);
		
		this.authorRepository.save(author);
		
		return author;		
	}
	
	public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
		Book book = new Book();
		book.setAuthor(new Author(authorId));
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setPageCount(pageCount != null ? pageCount : 0);
		
		this.bookRepository.save(book);
		
		return book;
	}
	
	public boolean deleteBook(Long id) {
		this.bookRepository.deleteById(id);
		
		return true;
	}
	
	public Book updateBookPageCount(Integer pageCount, Long id) {
		Optional<Book> obj = bookRepository.findById(id);
		
		if(!obj.isPresent()) return null;
		
		Book book = obj.orElseThrow(() -> new BookNotFoundException("The book to be updated was not found", id));
		book.setPageCount(pageCount);
		
		this.bookRepository.save(book);
		
		
		return book;
	}
}
