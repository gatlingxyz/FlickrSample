package xyz.gatling.flickrsample.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.gatling.flickrsample.R;
import xyz.gatling.flickrsample.adapters.FlickrAdapter;
import xyz.gatling.flickrsample.model.FlickrErrorImage;
import xyz.gatling.flickrsample.model.FlickrPhotoResponse;
import xyz.gatling.flickrsample.model.Photo;
import xyz.gatling.flickrsample.network.FlickrApi;

public class MainActivity extends Activity {

    private final String KEY_RESPONSE = "key_response";
    private final String KEY_REQUEST = "key_request";

    @BindView(R.id.search)
    SearchView searchView;
    @BindView(R.id.flicker_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ContentLoadingProgressBar progress;

    private FlickrAdapter adapter;
    private InputMethodManager inputMethodManager;
    private String latestRequest;
    private FlickrPhotoResponse latestResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FlickrApi.init();
        progress.hide();

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        searchView.setQueryHint("Pokemon, Emrakul, Android, Chocolate...");
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int rotation = wm.getDefaultDisplay().getRotation();
        boolean isPortrait = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setRecycleChildrenOnDetach(true);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        adapter = new FlickrAdapter(
                progress.getIndeterminateDrawable(),
                metrics.widthPixels,
                metrics.heightPixels,
                isPortrait
        );
        recyclerView.setAdapter(adapter);

        searchView.setIconifiedByDefault(false);

        if(savedInstanceState != null){
            latestResponse = savedInstanceState.getParcelable(KEY_RESPONSE);
            if(latestResponse != null){
                adapter.updateItems(latestResponse.getPhotos().getPhoto());
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_RESPONSE, latestResponse);
        outState.putString(KEY_REQUEST, latestRequest);
        super.onSaveInstanceState(outState);
    }

    private void search(String query){
        Map<String, String> queryMap = new android.support.v4.util.ArrayMap<>();
        queryMap.put("method", FlickrApi.ENDPOINT_PHOTO_SEARCH);
        queryMap.putAll(FlickrApi.getCleanMap());
        queryMap.put("text", query);

        latestRequest = query;

        adapter.clearItems();
        progress.show();
        FlickrApi.getFlickr().searchFor(queryMap).enqueue(new Callback<FlickrPhotoResponse>() {
            @Override
            public void onResponse(Call<FlickrPhotoResponse> call, Response<FlickrPhotoResponse> response) {
                try {
                    latestResponse = response.body();
                    List<Photo> photos = latestResponse.getPhotos().getPhoto();
                    if (photos.isEmpty()) {
                        photos.add(new FlickrErrorImage(R.drawable.no_results));
                    }
                    adapter.updateItems(photos);
                    progress.hide();
                }
                catch (NullPointerException e){
                    onFailure(call, e);
                }
            }

            @Override
            public void onFailure(Call<FlickrPhotoResponse> call, Throwable t) {
                t.printStackTrace();
                List<Photo> errorPhotos = new ArrayList<Photo>();
                errorPhotos.add(new FlickrErrorImage(R.drawable.blue_screen));
                adapter.updateItems(errorPhotos);
                progress.hide();
            }
        });
    }


}
