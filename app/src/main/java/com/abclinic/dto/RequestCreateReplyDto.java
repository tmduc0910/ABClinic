package com.abclinic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestCreateReplyDto {
    @JsonProperty("inquiry_id")
    private long inquiryId;
    private String reply;

    public RequestCreateReplyDto(long inquiryId, String reply) {
        this.inquiryId = inquiryId;
        this.reply = reply;
    }

    public long getInquiryId() {
        return inquiryId;
    }

    public String getReply() {
        return reply;
    }
}
