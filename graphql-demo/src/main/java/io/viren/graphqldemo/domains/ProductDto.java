package io.viren.graphqldemo.domains;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
	
	@NotEmpty
	private String code;
	private String description;
	private List<PriceDto> prices;
	
	
	
	
}
