package com.customer;

public class Customer {
	private String first_name;
	private String last_name;
	private String street;
	private String address;
	private String city;
	private String state;
	private String email;
	private String phone;

	public Customer(String first_name, String last_name, String street, String address, String city, String state,
			String email, String phone) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.street = street;
		this.address = address;
		this.city = city;
		this.state = state;
		this.email = email;
		this.phone = phone;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getStreet() {
		return street;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
}
