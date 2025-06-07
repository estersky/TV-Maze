package com.example.restapi_p7_sitiaisyah.data.network;

import com.example.restapi_p7_sitiaisyah.model.ShowResponse;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TvMazeApi {
    @GET("search/shows?q=batman")
    Call<List<ShowResponse>> getShows();
}
