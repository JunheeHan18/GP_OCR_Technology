package data;

import com.google.gson.annotations.SerializedName;

public class SigninData {
    @SerializedName("memberId")
    String memberId;

    @SerializedName("memberPwd")
    String memberPwd;

    public SigninData(String memberId, String memberPwd) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
    }
}

