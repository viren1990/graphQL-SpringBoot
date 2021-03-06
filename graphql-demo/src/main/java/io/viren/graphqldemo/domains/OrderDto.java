package io.viren.graphqldemo.domains; 

import java.util.List; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
	
	private List<OrderEntryDto> orderEntries;
	
	private UserDto user;
	
	private long id;

}
