package com.infovision.addressapplication.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addId;
	private String addStreet;
	private String addVillage;
	private String addTown;
	private String addDist;
	@NonNull
	private String addState;
	@NonNull
	private Integer addPincode;
	private Long empId;
	

}
