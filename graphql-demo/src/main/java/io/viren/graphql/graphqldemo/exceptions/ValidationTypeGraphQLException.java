package io.viren.graphql.graphqldemo.exceptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public abstract class ValidationTypeGraphQLException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = -436526660472720699L;
	
	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.ValidationError;
	}
	
	@JsonIgnore
	@Override
	public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
