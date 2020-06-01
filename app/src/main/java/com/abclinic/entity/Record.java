
package com.abclinic.entity;

import androidx.annotation.Nullable;

import com.abclinic.utils.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "inquiry",
        "doctor",
        "recordType",
        "status",
        "note",
        "prescription",
        "createdAt",
        "disease",
        "diagnose"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {

    @JsonProperty("id")
    private long id;
    @JsonProperty("inquiry")
    private Inquiry inquiry;
    @JsonProperty("status")
    private int status;
    @JsonProperty("doctor")
    private UserInfo doctor;
    @JsonProperty("recordType")
    private int type;
    @JsonProperty("note")
    private String note;
    @JsonProperty("prescription")
    private String prescription;
    @JsonProperty("createdAt")
    private List<Integer> createdAt;
    @JsonProperty("disease")
    private Disease disease;
    @JsonProperty("diagnose")
    private String diagnose;

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public UserInfo getDoctor() {
        return doctor;
    }

    public void setDoctor(UserInfo doctor) {
        this.doctor = doctor;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(int status) {
        this.status = status;
    }

    @JsonProperty("note")
    public String getNote() {
        return note;
    }

    @JsonProperty("note")
    public void setNote(String note) {
        this.note = note;
    }

    @JsonProperty("prescription")
    public String getPrescription() {
        return prescription;
    }

    @JsonProperty("prescription")
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    @JsonProperty("createdAt")
    public LocalDateTime getCreatedAt() {
        return DateTimeUtils.parseDateTime(createdAt);
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(List<Integer> createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("disease")
    public Disease getDisease() {
        return disease;
    }

    @JsonProperty("disease")
    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    @JsonProperty("diagnose")
    public String getDiagnose() {
        return diagnose;
    }

    @JsonProperty("diagnose")
    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Record)
            return this.id == ((Record) obj).getId();
        return false;
    }
}