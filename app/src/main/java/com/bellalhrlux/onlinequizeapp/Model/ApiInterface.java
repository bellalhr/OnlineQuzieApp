package com.bellalhrlux.onlinequizeapp.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
// https://opentdb.com/api.php?amount=20&category=9&type=multiple
public interface ApiInterface {
    @GET("api.php?amount=30&category=9&type=multiple")
    Call<Question> getQustionApi();
}
