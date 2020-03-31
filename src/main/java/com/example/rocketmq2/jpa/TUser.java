package com.example.rocketmq2.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "usertable")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6867298175769751390L;

	@Id
	private String name;
	
	private Integer money;


}
