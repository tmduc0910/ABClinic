package com.abclinic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "fields"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthIndex implements Parcelable {
    public static final Creator<HealthIndex> CREATOR = new Creator<HealthIndex>() {
        @Override
        public HealthIndex createFromParcel(Parcel source) {
            return new HealthIndex(source);
        }

        @Override
        public HealthIndex[] newArray(int size) {
            return new HealthIndex[size];
        }
    };
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("fields")
    private List<HealthIndexField> fields = null;


    public HealthIndex() {
    }

    protected HealthIndex(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();
        this.fields = in.createTypedArrayList(HealthIndexField.CREATOR);
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public List<HealthIndexField> getFields() {
        return fields;
    }

    public void setFields(List<HealthIndexField> fields) {
        this.fields = fields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeTypedList(this.fields);
    }
}