package br.com.lassulfi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.lassulfi.adapter.GraphQLErrorAdapter;
import br.com.lassulfi.model.Author;
import br.com.lassulfi.model.Book;
import br.com.lassulfi.repository.AuthorRepository;
import br.com.lassulfi.repository.BookRepository;
import br.com.lassulfi.resolver.BookResolver;
import br.com.lassulfi.resolver.Mutation;
import br.com.lassulfi.resolver.Query;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;

@SpringBootApplication
public class SpringbootGraphqlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootGraphqlDemoApplication.class, args);
	}

	@Bean
	public BookResolver authorResolver(AuthorRepository authorRepository) {
		return new BookResolver(authorRepository);
	}
	
	@Bean
	public Query query(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Query(bookRepository, authorRepository);
	}
	
	@Bean
	public Mutation mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		return new Mutation(bookRepository, authorRepository);
	}
	
	//TODO: Change this method to a Springboot config class
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {

			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream().filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream().filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new).collect(Collectors.toList());
				
				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}
	
	public CommandLineRunner demo(AuthorRepository authorRepository, BookRepository bookRepository) {
		return (args) -> {
			Author author = new Author("Herbert", "Schildt");
			authorRepository.save(author);
			
			bookRepository.save(new Book("Java: A Beginner's Guide, Sixth Edition", "0071809252", 728, author));
		};
	}
}
