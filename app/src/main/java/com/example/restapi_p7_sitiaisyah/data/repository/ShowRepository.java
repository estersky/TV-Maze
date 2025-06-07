package com.example.restapi_p7_sitiaisyah.data.repository;

import androidx.lifecycle.LiveData;
import com.example.restapi_p7_sitiaisyah.data.database.ShowDao;
import com.example.restapi_p7_sitiaisyah.data.database.ShowEntity;
import com.example.restapi_p7_sitiaisyah.model.Show;

import java.util.List;

public class ShowRepository {
    private final ShowDao showDao;

    public ShowRepository(ShowDao showDao) {
        this.showDao = showDao;
    }

    public LiveData<List<ShowEntity>> searchShows(String query) {
        return showDao.searchShows("%" + query + "%");
    }

    public LiveData<List<ShowEntity>> getAllShows() {
        return showDao.getAllShows();
    }

}
