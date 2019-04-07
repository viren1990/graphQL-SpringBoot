package io.viren.graphql.graphqldemo.domains;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class PriceDto {
	
	@NotEmpty
	private Double value;
	
	private String unit;

}
