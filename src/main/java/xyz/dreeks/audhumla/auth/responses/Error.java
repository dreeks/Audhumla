package xyz.dreeks.audhumla.auth.responses;

import com.google.gson.annotations.SerializedName;

public class Error extends Throwable {

    @SerializedName("error")
    public String error;

    @SerializedName("errorMessage")
    public String errorMessage;

    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return this.error + ": " + this.errorMessage;
    }
}
