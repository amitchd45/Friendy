package com.solutions.friendy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetSwipeItemsModelClass {
    @SerializedName("countSuperLikeUser")
    @Expose
    private String countSuperLikeUser;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public String getCountSuperLikeUser() {
        return countSuperLikeUser;
    }

    public void setCountSuperLikeUser(String countSuperLikeUser) {
        this.countSuperLikeUser = countSuperLikeUser;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public class Detail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("social_id")
        @Expose
        private Object socialId;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("phoneVerifyStatus")
        @Expose
        private String phoneVerifyStatus;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private Object email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("image1")
        @Expose
        private String image1;
        @SerializedName("image2")
        @Expose
        private String image2;
        @SerializedName("image3")
        @Expose
        private String image3;
        @SerializedName("image4")
        @Expose
        private String image4;
        @SerializedName("image5")
        @Expose
        private String image5;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("industry")
        @Expose
        private String industry;
        @SerializedName("company")
        @Expose
        private String company;
        @SerializedName("home_town")
        @Expose
        private String homeTown;
        @SerializedName("my_personality")
        @Expose
        private String myPersonality;
        @SerializedName("my_interests")
        @Expose
        private String myInterests;
        @SerializedName("my_note")
        @Expose
        private String myNote;
        @SerializedName("device_type")
        @Expose
        private String deviceType;
        @SerializedName("reg_id")
        @Expose
        private String regId;
        @SerializedName("login_type")
        @Expose
        private String loginType;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("age")
        @Expose
        private Integer age;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getSocialId() {
            return socialId;
        }

        public void setSocialId(Object socialId) {
            this.socialId = socialId;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getPhoneVerifyStatus() {
            return phoneVerifyStatus;
        }

        public void setPhoneVerifyStatus(String phoneVerifyStatus) {
            this.phoneVerifyStatus = phoneVerifyStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }

        public String getImage3() {
            return image3;
        }

        public void setImage3(String image3) {
            this.image3 = image3;
        }

        public String getImage4() {
            return image4;
        }

        public void setImage4(String image4) {
            this.image4 = image4;
        }

        public String getImage5() {
            return image5;
        }

        public void setImage5(String image5) {
            this.image5 = image5;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getHomeTown() {
            return homeTown;
        }

        public void setHomeTown(String homeTown) {
            this.homeTown = homeTown;
        }

        public String getMyPersonality() {
            return myPersonality;
        }

        public void setMyPersonality(String myPersonality) {
            this.myPersonality = myPersonality;
        }

        public String getMyInterests() {
            return myInterests;
        }

        public void setMyInterests(String myInterests) {
            this.myInterests = myInterests;
        }

        public String getMyNote() {
            return myNote;
        }

        public void setMyNote(String myNote) {
            this.myNote = myNote;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getRegId() {
            return regId;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

    }

}
