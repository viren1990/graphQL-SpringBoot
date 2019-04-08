package io.viren.graphqldemo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
	
	@Id
	@GeneratedValue
	@Column(name = "PRICE_ID")
	private long id;
	
	@Column(name = "PRICE_VALUE")
	private double value;
	
	@ManyToOne(optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID" , nullable = false)
	private Product product;

}
