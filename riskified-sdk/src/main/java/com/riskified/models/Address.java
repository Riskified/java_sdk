package com.riskified.models;

import com.riskified.validations.*;

public class Address implements IValidated {
    private String firstName;
    private String lastName;
    private String city;
    private String phone;
	private Boolean verifiedPhone;
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

    public Address() {
    }

    public void validate(Validation validationType) throws FieldBadFormatException {
        if (validationType == Validation.ALL) {
            Validate.notNullOrEmpty(this, this.firstName, "First Name");
            Validate.notNullOrEmpty(this, this.lastName, "Last Name");
            Validate.notNullOrEmpty(this, this.address1, "Address1");
            Validate.notNullOrEmpty(this, this.country, "Country");
            Validate.notNullOrEmpty(this, this.city, "City");
            Validate.notNullOrEmpty(this, this.phone, "Phone");
        }

        if (this.countryCode != null) {
            Validate.countryCode(this, this.countryCode, "Country Code");
        }
        if (this.provinceCode != null) {
            Validate.provinceCode(this, this.provinceCode, "Province Code");
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

    public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
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

    public void setAddress1(String address1) {
        this.address1 = address1;
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

	public Boolean getVerifiedPhone() {
		return verifiedPhone;
	}

	public void setVerifiedPhone(Boolean verifiedPhone) {
		this.verifiedPhone = verifiedPhone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
