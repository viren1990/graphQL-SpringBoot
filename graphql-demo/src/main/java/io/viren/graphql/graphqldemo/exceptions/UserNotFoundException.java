package io.viren.graphql.graphqldemo.exceptions;

import java.util.Collections;
import java.util.Map;

public class UserNotFoundException extends ValidationTypeGraphQLException {

	private static final long serialVersionUID = -1504740038834525272L;

	private String uid;

	public UserNotFoundException(final String uid) {
		this.uid = uid;
	}

	@Override
	public String getMessage() {
		return String.format("User with uid %s couldn't be found.", this.uid);
	}

	@Override
	public Map<String, Object> getExtensions() {
		return Collections.singletonMap("uid", this.uid);
	}

}
