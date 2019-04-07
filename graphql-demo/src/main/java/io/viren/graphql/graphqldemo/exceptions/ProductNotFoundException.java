package io.viren.graphql.graphqldemo.exceptions;

import java.util.Collections;
import java.util.Map;

public class ProductNotFoundException extends ValidationTypeGraphQLException  {

	private static final long serialVersionUID = 5786958345541628670L;
	private String productCode;
	
	public ProductNotFoundException(final String productCode) {
		this.productCode = productCode;
	}
	
	
	
	@Override
	public String getMessage() {
		return String.format("Product with id %s couldn't be found.", this.productCode);
	}
	
	
	@Override
	public Map<String, Object> getExtensions() {
		return Collections.singletonMap("productId", this.productCode);
	}
	
}

