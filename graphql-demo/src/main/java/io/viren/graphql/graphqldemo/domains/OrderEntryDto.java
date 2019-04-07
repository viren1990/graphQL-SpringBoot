package io.viren.graphql.graphqldemo.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntryDto {
	
	private ProductDto product;
	
	private long id;

}