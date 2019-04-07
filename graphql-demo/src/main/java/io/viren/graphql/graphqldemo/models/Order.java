package io.viren.graphql.graphqldemo.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "ORDER_ID")
	private long id;
	
	@OneToMany(mappedBy = "order" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<OrderEntry> entries;
	
	@ManyToOne (optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID" , nullable = false)
	private User user;

}
