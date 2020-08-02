package com.abclinic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "id",
        "chainId",
        "type",
        "prevInquiry",
        "nextInquiry"
})
public class Chain implements Parcelable {
    public static final Parcelable.Creator<Chain> CREATOR = new Parcelable.Creator<Chain>() {
        @Override
        public Chain createFromParcel(Parcel source) {
            return new Chain(source);
        }

        @Override
        public Chain[] newArray(int size) {
            return new Chain[size];
        }
    };
    @JsonProperty("id")
    private long id;
    @JsonProperty("chainId")
    private long chainId;
    @JsonProperty("type")
    private int type;
    @JsonProperty("prevInquiry")
    private Inquiry prevInquiry;
    @JsonProperty("nextInquiry")
    private Inquiry nextInquiry;

    public Chain() {
    }

    public Chain(long id, long chainId, int type, Inquiry prevInquiry, Inquiry nextInquiry) {
        this.id = id;
        this.chainId = chainId;
        this.type = type;
        this.prevInquiry = prevInquiry;
        this.nextInquiry = nextInquiry;
    }

    protected Chain(Parcel in) {
        this.id = in.readLong();
        this.chainId = in.readLong();
        this.type = in.readInt();
        this.prevInquiry = in.readParcelable(Inquiry.class.getClassLoader());
        this.nextInquiry = in.readParcelable(Inquiry.class.getClassLoader());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChainId() {
        return chainId;
    }

    public void setChainId(long chainId) {
        this.chainId = chainId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Inquiry getPrevInquiry() {
        return prevInquiry;
    }

    public void setPrevInquiry(Inquiry prevInquiry) {
        this.prevInquiry = prevInquiry;
    }

    public Inquiry getNextInquiry() {
        return nextInquiry;
    }

    public void setNextInquiry(Inquiry nextInquiry) {
        this.nextInquiry = nextInquiry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.chainId);
        dest.writeInt(this.type);
        dest.writeParcelable(this.prevInquiry, flags);
        dest.writeParcelable(this.nextInquiry, flags);
    }
}
