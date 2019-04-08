package io.viren.graphqldemo.exceptions;

import java.util.Collections;
import java.util.Map;

public class OrderNotFoundException extends ValidationTypeGraphQLException{
	
	private static final long serialVersionUID = 8152299246331074455L;

	private String orderCode;
	
	public OrderNotFoundException(final String orderCode) {
		this.orderCode = orderCode;
	}
	

	@Override
	public String getMessage() {
		return String.format("Order with id %s couldn't be found.", this.orderCode);
	}
	
	
	@Override
	public Map<String, Object> getExtensions() {
		return Collections.singletonMap("orderId", this.orderCode);
	}
	
	


}
