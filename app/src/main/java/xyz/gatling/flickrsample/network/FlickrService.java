package xyz.gatling.flickrsample.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import xyz.gatling.flickrsample.model.FlickrPhotoResponse;

/**
 * Created by gimmi on 7/22/2016.
 */

public interface FlickrService {

    @GET("rest/")
    Call<FlickrPhotoResponse> searchFor(@QueryMap Map<String, String> query);

}
