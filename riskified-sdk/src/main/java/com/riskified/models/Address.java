package com.riskified.models;

import com.riskified.validations.FieldBadFormatException;
import com.riskified.validations.IValidated;
import com.riskified.validations.Validate;
import com.riskified.validations.Validation;

public class Address implements IValidated {
    private String firstName;
    private String lastName;
    private String city;
    private String phone;
    private String country;
    private String countryCode;
    private String name;
    private String company;
    private String address1;
    private String address2;
    private String province;
    private String provinceCode;
    private String zip;
    private Float latitude;
    private Float longitude;

    public Address(String firstName, String lastName, String address1, String city, String phone, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.phone = phone;
        this.country = country;
        this.address1 = address1;
    }

    public void validate(Validation validationType)
			throws FieldBadFormatException {
		
    	if(validationType == Validation.all) {
	    	Validate.stringNotNullOrEmpty(this, this.firstName, "First Name");
	    	Validate.stringNotNullOrEmpty(this, this.lastName, "last Name");
	    	Validate.stringNotNullOrEmpty(this, this.address1, "Address1");
	    	Validate.stringNotNullOrEmpty(this, this.country, "Country");
	    	Validate.stringNotNullOrEmpty(this, this.city, "City");
	    	Validate.stringNotNullOrEmpty(this, this.phone, "Phone");
    	}
    	
    	if(this.countryCode != null) {
    		Validate.countryCodeWellFormed(this, this.countryCode, "Country Code");
    		Validate.provinceCodeWellFormed(this, this.provinceCode, "Province Code");
    	}
		
	}
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

	
}
