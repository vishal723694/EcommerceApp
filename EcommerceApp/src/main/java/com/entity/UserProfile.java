package com.entity;

/**
 * Entity representing extended user profile details.
 */
public class UserProfile {
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String city;
    private String zipCode;
    private String avatarUrl;
    private String bio;

    public UserProfile() {
    }

    public UserProfile(String email, String fullName, String phone, String address, String city, String zipCode, String avatarUrl, String bio) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
// Refactored domain annotations
