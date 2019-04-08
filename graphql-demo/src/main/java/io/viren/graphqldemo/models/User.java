package io.viren.graphqldemo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private long id;
	
	@Column(name = "USER_UID",unique = true)
	private String uid;
	
	
	private String name;
	
	private int age;
	
	@OneToMany(mappedBy ="user" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<Order> orders;
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	private List<Address> addresses;
}
