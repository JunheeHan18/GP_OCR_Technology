package network;

import java.util.List;

import data.BookmarkData;
import data.BookmarkResponse;
import data.SigninData;
import data.SigninResponse;
import data.SignupData;
import data.SignupResponse;
import data.TransTextData;
import data.TransTextResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/login")
    Call<SigninResponse> memberSignin(@Body SigninData data);

    @POST("/user/join")
    Call<SignupResponse> memberSignup(@Body SignupData data);
    @POST("/user/text/save")
    Call<TransTextResponse> transTextSave(@Body TransTextData data);
//    bookmark추가코드
   @POST("/user/bookmark")
    Call<BookmarkResponse[]> getSaveContent(@Body BookmarkData data);
    @GET("/user/bookmark")
    Call<BookmarkResponse> getSaveContent(@Field("event") String event);


}