package com.example.restapi_p7_sitiaisyah.presentation.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.restapi_p7_sitiaisyah.data.database.AppDatabase;
import com.example.restapi_p7_sitiaisyah.data.database.ShowDao;
import com.example.restapi_p7_sitiaisyah.data.database.ShowEntity;
import com.example.restapi_p7_sitiaisyah.data.network.TvMazeApi;
import com.example.restapi_p7_sitiaisyah.data.repository.ShowRepository;
import com.example.restapi_p7_sitiaisyah.model.ShowResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowViewModel extends AndroidViewModel {
    private final AppDatabase db;
    private final LiveData<List<ShowEntity>> shows;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // Thread untuk database
    private final ShowRepository repository;

    public ShowViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDatabase(application);
        shows = db.showDao().getAllShows();

        ShowDao dao = AppDatabase.getDatabase(application).showDao();
        repository = new ShowRepository(dao);
    }

    public LiveData<List<ShowEntity>> searchShows(String keyword) {
        return repository.searchShows(keyword);
    }
    // Metode untuk mengambil data dari API
    public LiveData<List<ShowEntity>> getShows() {
        return shows;
    }
    // Metode untuk mengambil data dari API
    public void fetchShows(TvMazeApi api) {
        api.getShows().enqueue(new Callback<List<ShowResponse>>() {
            @Override
            public void onResponse(Call<List<ShowResponse>> call, Response<List<ShowResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ShowEntity> showEntities = new ArrayList<>();
                    // Konversi ShowResponse ke ShowEntity
                    for (ShowResponse showResponse : response.body()) {
                        showEntities.add(new ShowEntity(
                                showResponse.getShow().getName(),
                                showResponse.getShow().getImage() != null ? showResponse.getShow().getImage().getMedium() : null,
                                showResponse.getShow().getRating() != null ? showResponse.getShow().getRating().getAverage() : null,
                                showResponse.getShow().getStatus(),
                                showResponse.getShow().getPremiered()
                        ));
                    }

                    // Simpan ke database di thread terpisah
                    executorService.execute(() -> db.showDao().insertShows(showEntities));
                }
            }
            // Handle error jika ada
            @Override
            public void onFailure(Call<List<ShowResponse>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public LiveData<List<ShowEntity>> searchShowsInDatabase(String query) {
        return repository.searchShows(query);
    }
    public LiveData<List<ShowEntity>> getShowsFromDatabase() {
        return repository.getAllShows();
    }

}
