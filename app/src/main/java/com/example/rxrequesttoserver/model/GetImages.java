package com.example.rxrequesttoserver.model;

import com.google.gson.annotations.SerializedName;

public class GetImages {
    @SerializedName("data")
    public GetImagesDetail getImagesDetail;

    public GetImagesDetail getGetImagesDetail() {
        return getImagesDetail;
    }

    public void setGetImagesDetail(GetImagesDetail getImagesDetail) {
        this.getImagesDetail = getImagesDetail;
    }
}
