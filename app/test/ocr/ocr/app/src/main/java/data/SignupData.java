package data;

import com.google.gson.annotations.SerializedName;

public class SignupData {
    @SerializedName("memberId")
    private String memberId;

    @SerializedName("memberPwd")
    private String memberPwd;

    public SignupData(String memberId, String memberPwd) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
    }
}
