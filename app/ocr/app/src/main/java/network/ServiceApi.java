package network;

import data.SigninData;
import data.SigninResponse;
import data.SignupData;
import data.SignupResponse;
import data.TransTextData;
import data.TransTextResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<SigninResponse> memberSignin(@Body SigninData data);

    @POST("/user/join")
    Call<SignupResponse> memberSignup(@Body SignupData data);
    @POST("/user/text/save")
    Call<TransTextResponse> transTextSave(@Body TransTextData data);
}