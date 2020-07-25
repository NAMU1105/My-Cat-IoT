package com.example.team_cat_iot2020.retrofit;

public class Data {

    //회원가입 결과를 받을때 사용
    private String email;
    private String password;
    private String name;
    //응답결과만 받을때 사용
    private String response;
    //비밀번호 찾기 결과를 받을떄 사용
    private String code;
    private  boolean booleanresponse;

    public boolean isBooleanresponse() {
        return booleanresponse;
    }

    public void setBooleanresponse(boolean booleanresponse) {
        this.booleanresponse = booleanresponse;
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
