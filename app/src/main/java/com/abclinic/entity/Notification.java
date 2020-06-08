package com.abclinic.entity;

import androidx.annotation.Nullable;

import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "sender",
        "receiver",
        "payloadId",
        "message",
        "type",
        "isRead",
        "createdAt"
})
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private long id;
    @JsonProperty("sender")
    private transient UserInfo sender;
    @JsonProperty("receiver")
    private transient UserInfo receiver;
    @JsonProperty("payloadId")
    private long payloadId;
    @JsonProperty("message")
    private String message;
    @JsonProperty("type")
    private int type;
    @JsonProperty("isRead")
    private boolean isRead;
    @JsonProperty("createdAt")
    private List<Integer> createdAt = null;

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("sender")
    public UserInfo getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("isRead")
    public Boolean isRead() {
        return isRead;
    }

    @JsonProperty("isRead")
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return DateTimeUtils.parseDateTime(createdAt);
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(List<Integer> createdAt) {
        this.createdAt = createdAt;
    }

    public UserInfo getReceiver() {
        return receiver;
    }

    public void setReceiver(UserInfo receiver) {
        this.receiver = receiver;
    }

    public long getPayloadId() {
        return payloadId;
    }

    public void setPayloadId(long payloadId) {
        this.payloadId = payloadId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    @JsonIgnore
    public String getActivity() {
        try {
            return message.substring(message.indexOf("đã"));
        } catch (IndexOutOfBoundsException e) {
            return message;
        }
    }

    @JsonIgnore
    public ISaveable getData() {
        return new SaveableImpl(payloadId, type, getCreatedAt(), id);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Notification) {
            return this.id == ((Notification) obj).getId() && this.isRead == ((Notification) obj).isRead;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(String.valueOf(this.id + (isRead ? 1 : 0)));
    }
}