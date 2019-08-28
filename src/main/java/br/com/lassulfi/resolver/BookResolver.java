package br.com.lassulfi.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.com.lassulfi.model.Author;
import br.com.lassulfi.model.Book;
import br.com.lassulfi.repository.AuthorRepository;
import br.com.lassulfi.repository.exception.AuthorNotFoundException;

public class BookResolver implements GraphQLResolver<Book> {

	private AuthorRepository authorRepository;

	public BookResolver(AuthorRepository authorRespository) {
		this.authorRepository = authorRespository;
	}

	public Author getAuthor(Book book) throws Exception {
		return authorRepository.findById(book.getAuthor().getId())
				.orElseThrow(() -> new AuthorNotFoundException("The author to the corresponding book was not found",
						book.getAuthor().getId()));
	}
}
