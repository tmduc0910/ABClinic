package com.abclinic.entity;

import com.abclinic.constant.PayloadStatus;
import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "patient",
        "albumId",
        "replies",
        "medicalRecords",
        "dietRecords",
        "content",
        "type",
        "status",
        "date",
        "createdAt",
        "updatedAt"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inquiry {

    @JsonProperty("id")
    private long id;
    @JsonProperty("albumId")
    private String albumId;
    @JsonProperty("patient")
    private UserInfo patient;
    @JsonProperty("replies")
    private List<Reply> replies;
    @JsonProperty("medicalRecords")
    private List<Record> medicalRecords;
    @JsonProperty("dietRecords")
    private List<Record> dietRecords;
    @JsonProperty("content")
    private String content;
    @JsonProperty("type")
    private int type;
    @JsonProperty("status")
    private int status;
    @JsonProperty("date")
    private List<Integer> date;
    @JsonProperty("createdAt")
    private List<Integer> createdAt = null;
    @JsonProperty("updatedAt")
    private List<Integer> updatedAt = null;

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    @JsonProperty("patient")
    public UserInfo getPatient() {
        return patient;
    }

    @JsonProperty("patient")
    public void setPatient(UserInfo patient) {
        this.patient = patient;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Record> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<Record> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<Record> getDietRecords() {
        return dietRecords;
    }

    public void setDietRecords(List<Record> dietRecords) {
        this.dietRecords = dietRecords;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonIgnore
    public PayloadStatus getStatusValue() throws Throwable {
        return PayloadStatus.getStatus(status);
    }

    @JsonProperty("date")
    public LocalDateTime getDate() {
        return DateTimeUtils.parseDateTime(date);
    }

    @JsonProperty("date")
    public void setDate(List<Integer> date) {
        this.date = date;
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
    public List<Integer> getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(List<Integer> updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Inquiry sort() {
        medicalRecords = this.getMedicalRecords()
                .stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .collect(Collectors.toList());
        dietRecords = this.getDietRecords()
                .stream()
                .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
                .collect(Collectors.toList());
        return this;
    }
}