package xyz.gatling.flickrsample.network;

import android.support.v4.util.ArrayMap;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gimmi on 7/22/2016.
 */

public class FlickrApi {

    private static FlickrService flickr;

    public static void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        flickr = retrofit.create(FlickrService.class);
    }

    public static FlickrService getFlickr() {
        return flickr;
    }

    private static Map<String, String> baseQueryMap = new ArrayMap<>();
    static {
        baseQueryMap.put("api_key", "643309d5b94cdee5d931c311994f6094");
        baseQueryMap.put("format", "json");
        baseQueryMap.put("nojsoncallback", "1");
//        baseQueryMap.put("per_page", "10");
    }

    public static String ENDPOINT_PHOTO_SEARCH = "flickr.photos.search";
    public static Map<String, String> getCleanMap(){
        return baseQueryMap;
    }

}
