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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADDRESS")
@Entity
public class Address {
	
	@Id
	@GeneratedValue
	@Column(name = "ADDRESS_ID")
	private long id;
	
	private String line1;
	private String line2;
	private String town;
	private String pincode;
	private String country;
	private String mobile;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

}
