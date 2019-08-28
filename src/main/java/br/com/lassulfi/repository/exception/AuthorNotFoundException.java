package br.com.lassulfi.repository.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AuthorNotFoundException extends RuntimeException implements GraphQLError {

	private Map<String, Object> extensions = new HashMap<String, Object>();
	
	public AuthorNotFoundException(String message, Long invalidAuthorId) {
		super(message);
		extensions.put("invalidBookid", invalidAuthorId);
	}
	
	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.DataFetchingException;
	}

	@Override
	public Map<String, Object> getExtensions() {
		return extensions;
	}

}
