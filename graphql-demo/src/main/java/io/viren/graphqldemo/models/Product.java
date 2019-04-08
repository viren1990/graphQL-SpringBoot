package io.viren.graphqldemo.models;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products" ,uniqueConstraints = {@UniqueConstraint(name = "UK_PRODUCT_CODE" , columnNames = {"PRODUCT_CODE"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue
	@Column(name="PRODUCT_ID")
	private  long id;
	
	@Column(name = "PRODUCT_CODE")
	@Nonnull
	private String code;
	
	@Column(name = "PRODUCT_DESCRIPTION")
	private String description;
	
	@OneToMany(mappedBy="product"  , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Price> prices;

}
