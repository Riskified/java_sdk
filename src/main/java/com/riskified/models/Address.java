package main.java.com.riskified.models;

public class Address {
  public String firstName;
  public String lastName;
  public String city;
  public String phone;
  public String country;
  public String countryCode;
  public String name;
  public String company;
  public String address1;
  public String address2;
  public String province;
  public String provinceCode;
  public String zip;
  public Float latitude;
  public Float longitude;
  
  public Address(String firstName, String lastName, String city, String phone, String country, String countryCode){
    this.firstName = firstName;
    this.lastName = lastName;
    this.city = city;
    this.phone = phone;
    this.country = country;
    this.countryCode = countryCode;
  }
}
