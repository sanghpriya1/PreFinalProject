package com.roiim.paysafe.dto;

import com.roiim.paysafe.customer.CustomerDOB;

public class CreateNewPaySafeCustomerResponseDTO {
	private String id;

	private String merchantCustomerId;

	private String locale;

	private String firstName;

	private String middleName;

	private String lastName;

	private CustomerDOB dateOfBirth;

	private String email;

	private String phone;

	private String ip;

	private String gender;

	private String nationality;

	private String cellPhone;

	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCustomerId() {
		return merchantCustomerId;
	}

	public void setMerchantCustomerId(String merchantCustomerId) {
		this.merchantCustomerId = merchantCustomerId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CustomerDOB getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(CustomerDOB dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CreateNewPaySafeCustomerResponseDTO [id=" + id + ", merchantCustomerId=" + merchantCustomerId
				+ ", locale=" + locale + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", dateOfBirth=" + dateOfBirth + ", email=" + email + ", phone=" + phone + ", ip=" + ip
				+ ", gender=" + gender + ", nationality=" + nationality + ", cellPhone=" + cellPhone + ", status="
				+ status + "]";
	}
    


}
