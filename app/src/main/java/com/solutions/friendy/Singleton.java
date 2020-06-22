package com.solutions.friendy;


import com.solutions.friendy.Models.GetSwipeItemsModelClass;

import java.util.List;

public class Singleton {

    List<GetSwipeItemsModelClass.Detail> filteredUser;

    private String Uq_id;
    private String Social_id;
    private String LoginType;
    private String CheckType;
    private String Fb_image;

    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFb_email() {
        return fb_email;
    }

    public void setFb_email(String fb_email) {
        this.fb_email = fb_email;
    }

    private String fb_email;

    public String getSocial_id() {
        return Social_id;
    }

    public void setSocial_id(String social_id) {
        Social_id = social_id;
    }

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    public String getCheckType() {
        return CheckType;
    }

    public void setCheckType(String checkType) {
        CheckType = checkType;
    }

    public String getFb_image() {
        return Fb_image;
    }

    public void setFb_image(String fb_image) {
        Fb_image = fb_image;
    }

    public String getUq_id() {
        return Uq_id;
    }

    public void setUq_id(String uq_id) {
        Uq_id = uq_id;
    }

    String filterActive = "";

    public List<GetSwipeItemsModelClass.Detail> getFilteredUser() {
        return filteredUser;
    }

    public void setFilteredUser(List<GetSwipeItemsModelClass.Detail> filteredUser) {
        this.filteredUser = filteredUser;
    }

    public String getFilterActive() {
        return filterActive;
    }

    public void setFilterActive(String filterActive) {
        this.filterActive = filterActive;
    }

    private String phone;
    private String otp;
    private String id;
    private String name;
    private String dob;
    private String gender;
    private String password;
    private String checkUserExist;
    private String showUser_number;

    //Todo set user data

    private String user_name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    private Integer age;
    private String about;
    private String industry;
    private String company;
    private String hometown;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String note;

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    private String user_image;
    private String user_image1;
    private String user_image2;
    private String user_image3;
    private String user_image4;
    private String user_image5;

    public String getUser_image1() {
        return user_image1;
    }

    public void setUser_image1(String user_image1) {
        this.user_image1 = user_image1;
    }

    public String getUser_image2() {
        return user_image2;
    }

    public void setUser_image2(String user_image2) {
        this.user_image2 = user_image2;
    }

    public String getUser_image3() {
        return user_image3;
    }

    public void setUser_image3(String user_image3) {
        this.user_image3 = user_image3;
    }

    public String getUser_image4() {
        return user_image4;
    }

    public void setUser_image4(String user_image4) {
        this.user_image4 = user_image4;
    }

    public String getUser_image5() {
        return user_image5;
    }

    public void setUser_image5(String user_image5) {
        this.user_image5 = user_image5;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    //TODO this is for image path stored

    private String path_image;
    private String path_image1;
    private String path_image2;
    private String path_image3;
    private String path_image4;
    private String path_image5;

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public String getPath_image1() {
        return path_image1;
    }

    public void setPath_image1(String path_image1) {
        this.path_image1 = path_image1;
    }

    public String getPath_image2() {
        return path_image2;
    }

    public void setPath_image2(String path_image2) {
        this.path_image2 = path_image2;
    }

    public String getPath_image3() {
        return path_image3;
    }

    public void setPath_image3(String path_image3) {
        this.path_image3 = path_image3;
    }

    public String getPath_image4() {
        return path_image4;
    }

    public void setPath_image4(String path_image4) {
        this.path_image4 = path_image4;
    }

    public String getPath_image5() {
        return path_image5;
    }

    public void setPath_image5(String path_image5) {
        this.path_image5 = path_image5;
    }

    public String getShowUser_number() {
        return showUser_number;
    }

    public void setShowUser_number(String showUser_number) {
        this.showUser_number = showUser_number;
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone;
    }

    private String newPhone;

    public String getNewOtpForget() {
        return newOtpForget;
    }

    public void setNewOtpForget(String newOtpForget) {
        this.newOtpForget = newOtpForget;
    }

    private String newOtpForget;

    public String getResetCheck() {
        return resetCheck;
    }

    public void setResetCheck(String resetCheck) {
        this.resetCheck = resetCheck;
    }

    private String resetCheck;

    public String getCheckUserExist() {
        return checkUserExist;
    }

    public void setCheckUserExist(String checkUserExist) {
        this.checkUserExist = checkUserExist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    //Todo Friend profile data

    private String f_name;

    public Integer getF_age() {
        return f_age;
    }

    public void setF_age(Integer f_age) {
        this.f_age = f_age;
    }

    private Integer f_age;
    private String f_about;
    private String f_industry;
    private String f_company;
    private String f_hometown;

    public String getF_note() {
        return f_note;
    }

    public void setF_note(String f_note) {
        this.f_note = f_note;
    }

    private String f_note;

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }


    public String getF_about() {
        return f_about;
    }

    public void setF_about(String f_about) {
        this.f_about = f_about;
    }

    public String getF_industry() {
        return f_industry;
    }

    public void setF_industry(String f_industry) {
        this.f_industry = f_industry;
    }

    public String getF_company() {
        return f_company;
    }

    public void setF_company(String f_company) {
        this.f_company = f_company;
    }

    public String getF_hometown() {
        return f_hometown;
    }

    public void setF_hometown(String f_hometown) {
        this.f_hometown = f_hometown;
    }
}
