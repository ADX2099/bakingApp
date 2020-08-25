package com.adx2099.bakingapp.models;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Steps extends BaseObservable implements Parcelable {
    @Expose
    @SerializedName("id")
    public int stepId;
    @Expose
    @SerializedName("shortDescription")
    public String shortDescription;
    @Expose
    @SerializedName("description")
    public String description;
    @Expose
    @SerializedName("videoURL")
    public String videoURL;
    @Expose
    @SerializedName("thumbnailURL")
    public String thumbnailURL;

    @Expose
    @SerializedName("step")
    public String stepNumber;




    public Steps(){

    }

    protected Steps(Parcel in) {
        stepId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
        stepNumber = in.readString();

    }

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel in) {
            return new Steps(in);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
        dest.writeString(stepNumber);

    }
}
