package io.viren.graphql.graphqldemo.domains;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private long id;
	private String uid;
	private String name;
	private int age;
	
	private List<OrderDto> orders;
	
	private List<AddressDto> addresses;
	
	
	
}
