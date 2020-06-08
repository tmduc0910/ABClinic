package com.abclinic.entity;

import android.os.Parcel;

import androidx.annotation.Nullable;

import com.abclinic.constant.NotificationType;
import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "user",
        "content",
        "createdAt",
        "updatedAt"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reply implements ISaveable {
    public static final Creator<Reply> CREATOR = new Creator<Reply>() {
        @Override
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        @Override
        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

    @JsonProperty("id")
    private long id;
    @JsonProperty("user")
    private UserInfo user;
    @JsonProperty("content")
    private String content;
    @JsonProperty("createdAt")
    private List<Integer> createdAt = null;
    @JsonProperty("updatedAt")
    private List<Integer> updatedAt = null;

    public Reply() {
    }

    public Reply(Parcel in) {
        id = in.readLong();
        in.readInt();
        createdAt = DateTimeUtils.toList(in.readString());
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("user")
    public UserInfo getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(UserInfo user) {
        this.user = user;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return DateTimeUtils.parseDateTime(createdAt);
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(List<Integer> createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updatedAt")
    public LocalDateTime getUpdatedAt() {
        return DateTimeUtils.parseDateTime(updatedAt);
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(List<Integer> updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Reply)
            return this.id == ((Reply) obj).getId();
        return false;
    }

    @JsonIgnore
    @Override
    public int getDataType() {
        return NotificationType.REPLY.getValue();
    }
}
