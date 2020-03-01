package data;

import com.google.gson.annotations.SerializedName;

public class SigninResponse {
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("memberId")
    private String memberId;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMemberId() {
        return memberId;
    }
}
