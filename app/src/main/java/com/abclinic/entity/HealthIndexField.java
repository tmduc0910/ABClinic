package com.abclinic.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthIndexField implements Parcelable {
    public static final Creator<HealthIndexField> CREATOR = new Creator<HealthIndexField>() {
        @Override
        public HealthIndexField createFromParcel(Parcel source) {
            return new HealthIndexField(source);
        }

        @Override
        public HealthIndexField[] newArray(int size) {
            return new HealthIndexField[size];
        }
    };

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;

    public HealthIndexField() {
    }

    public HealthIndexField(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
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

}
