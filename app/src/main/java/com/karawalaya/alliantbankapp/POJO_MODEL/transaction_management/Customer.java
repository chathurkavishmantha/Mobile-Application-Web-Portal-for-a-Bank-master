package com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management;

import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.OnlineUser;

import java.io.Serializable;

public class Customer implements Serializable {
    private String customerId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String addressStreet;
    private String addressCity;
    private String addressProvince;
    private int addressZip;
    private String contactNumber;
    private String email;

    private Account account;
    private OnlineUser onlineUser;

    public Customer() {}

    public Customer(String customerId, String firstName, String lastName, int age, String gender, String addressStreet, String addressCity,
                    String addressProvince, int addressZip, String contactNumber, String email) {
        this.setCustomerId(customerId);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAge(age);
        this.setGender(gender);
        this.setAddressStreet(addressStreet);
        this.setAddressCity(addressCity);
        this.setAddressProvince(addressProvince);
        this.setAddressZip(addressZip);
        this.setContactNumber(contactNumber);
        this.setEmail(email);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public int getAddressZip() {
        return addressZip;
    }

    public void setAddressZip(int addressZip) {
        this.addressZip = addressZip;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OnlineUser getOnlineUser() {
        return onlineUser;
    }

    public void setOnlineUser(OnlineUser onlineUser) {
        this.onlineUser = onlineUser;
    }
}
