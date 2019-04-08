package io.viren.graphqldemo.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
	
	private String line1;
	private String line2;
	private String town;
	private String country;
	private String pincode;
	private String mobile;

}
