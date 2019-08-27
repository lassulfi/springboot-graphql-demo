package br.com.lassulfi.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;

import br.com.lassulfi.model.Book;

public class BookResolver implements GraphQLResolver<Book> {
	
	private AuthorRepository authorRepository;
	
	public BookResolver(AuthorRepository authorRespository) {
		this.authorRepository = authorRespository;
	}
	
	public Author getAuthor(Book book) {
		return authorRepository.getOne(book.getAuthor().getId());
	}
}
