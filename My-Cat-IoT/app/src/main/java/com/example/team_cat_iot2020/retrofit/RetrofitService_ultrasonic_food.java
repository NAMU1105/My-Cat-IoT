package com.example.team_cat_iot2020.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService_ultrasonic_food {

//사료높이 측정

    String URL = "http://192.168.43.143";

    /**
     * GET 방식, URL/posts/{userId} 호출.
     * Data Type의 JSON을 통신을 통해 받음.
     * @Path("userId") String id : id 로 들어간 STring 값을, 첫 줄에 말한
     * {userId}에 넘겨줌.
     * userId에 1이 들어가면
     * "http://jsonplaceholder.typicode.com/posts/1" 이 최종 호출 주소.
     * @param userId 요청에 필요한 userId
     * @return Data 객체를 JSON 형태로 반환.
     */

    @GET("/posts/{userId}")
    Call<Data> getData(@Path("userId") String userId);

    //비밀번호 찾기에서 변경할때 사용.

    @POST("/")
    Call<ultrawave_result> valuePost();




    /**
     * POST 방식, 주소는 위들과 같음.
     * @FieldMap HashMap<String, Object> param :
     * Field 형식을 통해 넘겨주는 값들이 여러 개일 때 FieldMap을 사용함.
     * Retrofit에서는 Map 보다는 HashMap 권장.
     * @FormUrlEncoded Field 형식 사용 시 Form이 Encoding 되어야 하기 때문에 사용하는 어노테이션
     * Field 형식은 POST 방식에서만 사용가능.
//     * @param param 요청에 필요한 값들.
     * @return Data 객체를 JSON 형태로 반환.
     */
//    @FormUrlEncoded
    @POST("/register.php")
    Call<Data> postData();

}
