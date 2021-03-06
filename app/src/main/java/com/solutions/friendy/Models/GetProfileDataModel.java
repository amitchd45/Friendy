package com.solutions.friendy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfileDataModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public class Details {

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
        @SerializedName("industryTitle")
        @Expose
        private String industryTitle;
        @SerializedName("personalityTitle")
        @Expose
        private List<PersonalityTitle> personalityTitle = null;
        @SerializedName("interestTitle")
        @Expose
        private List<InterestTitle> interestTitle = null;
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

        public String getIndustryTitle() {
            return industryTitle;
        }

        public void setIndustryTitle(String industryTitle) {
            this.industryTitle = industryTitle;
        }

        public List<PersonalityTitle> getPersonalityTitle() {
            return personalityTitle;
        }

        public void setPersonalityTitle(List<PersonalityTitle> personalityTitle) {
            this.personalityTitle = personalityTitle;
        }

        public List<InterestTitle> getInterestTitle() {
            return interestTitle;
        }

        public void setInterestTitle(List<InterestTitle> interestTitle) {
            this.interestTitle = interestTitle;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public class InterestTitle {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("interestType")
            @Expose
            private List<InterestType> interestType = null;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<InterestType> getInterestType() {
                return interestType;
            }

            public void setInterestType(List<InterestType> interestType) {
                this.interestType = interestType;
            }

            public class InterestType {

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("interestId")
                @Expose
                private String interestId;
                @SerializedName("title")
                @Expose
                private String title;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getInterestId() {
                    return interestId;
                }

                public void setInterestId(String interestId) {
                    this.interestId = interestId;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

            }

        }

        public class PersonalityTitle {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("text")
            @Expose
            private String text;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

        }


    }

}
