package com.test.olx.advertise.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "Advertisement")
public class AdvertisementEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="title")
	private String title;
	@Column(name="price")
	private int price;
	@Column(name="category")
	private int category;
	@Column(name="description")
	private String description;
	@Column(name="created_date")
	private Date createdDate;
	@Column(name="modified_date")
	private Date modifiedDate;
	@Column(name="status")
	private String status;
	@Column(name="username")
	private String username;
	
	public AdvertisementEntity(String title, String description, int price, int category, Date createdDate,
			Date modifiedDate, String status, String username) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.username = username;
	}
	
}
