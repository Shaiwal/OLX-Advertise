package com.test.olx.advertise.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value="Advertise Model", description = "Advertise Model")
public class Advertisement {
	@ApiModelProperty(value = "Id", dataType = "int")
	private int id;
	@ApiModelProperty(value = "Title of Advertisement", dataType = "String")
	private String title;
	@ApiModelProperty(value = "Price", dataType = "int")
	private int price;
	@ApiModelProperty(value = "Category", dataType = "String")
	private int category;
	@ApiModelProperty(value = "Description", dataType = "String")
	private String description;
	@ApiModelProperty(value = "Creation Date", dataType = "Date")
	private Date createdDate;
	@ApiModelProperty(value = "Last Modified date", dataType = "Date")
	private Date modifiedDate;
	@ApiModelProperty(value = "Status of Advertisement", dataType = "String")
	private String status;
	@ApiModelProperty(value = "Username of Ad owner", dataType = "String")
	private String username;
	@ApiModelProperty(value = "Posted by whom", dataType = "String")
	private String postedBy;
	
	public Advertisement(int id, String title, String description, int price, int category, Date createdDate,
			Date modifiedDate, String status, String username, String postedBy) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.status = status;
		this.username = username;
		this.postedBy = postedBy;
	}
}
