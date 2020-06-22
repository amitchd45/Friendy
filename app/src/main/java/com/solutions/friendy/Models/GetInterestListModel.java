package com.solutions.friendy.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInterestListModel
{
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

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
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private List<Type> type = null;

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

        public List<Type> getType() {
            return type;
        }

        public void setType(List<Type> type) {
            this.type = type;
        }

        public class Type {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("interestId")
            @Expose
            private String interestId;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("selectStatus")
            @Expose
            private String selectStatus;

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

            public String getSelectStatus() {
                return selectStatus;
            }

            public void setSelectStatus(String selectStatus) {
                this.selectStatus = selectStatus;
            }

        }
    }

}
