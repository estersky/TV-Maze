package com.example.restapi_p7_sitiaisyah.presentation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;

import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.presentation.adapter.ShowAdapter;
import com.example.restapi_p7_sitiaisyah.presentation.viewmodel.ShowViewModel;

import java.util.ArrayList;

public class KontenFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ShowAdapter showAdapter;
    private ShowViewModel showViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        LinearLayout rootLayout = view.findViewById(R.id.root_layout);
        rootLayout.setBackgroundColor(getThemeColorFromPreferences());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.searchRecyclerView);

        showAdapter = new ShowAdapter(new ArrayList<>());
        recyclerView.setAdapter(showAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 kolom

        showViewModel = new ViewModelProvider(this).get(ShowViewModel.class);

        showViewModel.getShowsFromDatabase().observe(getViewLifecycleOwner(), shows -> {
            showAdapter.setShows(shows);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchShows(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchShows(newText);
                return true;
            }
        });
    }

    private void searchShows(String query) {
        showViewModel.searchShowsInDatabase(query).observe(getViewLifecycleOwner(), shows -> {
            showAdapter.setShows(shows);
        });
    }

    private int getThemeColorFromPreferences() {
        SharedPreferences prefs = requireContext().getSharedPreferences("app_settings", Context.MODE_PRIVATE);
        String theme = prefs.getString("theme_color", "navy"); // default navy
        switch (theme) {
            case "black":
                return ContextCompat.getColor(requireContext(), R.color.black);
            case "white":
                return ContextCompat.getColor(requireContext(), R.color.white);
            case "pink":
                return ContextCompat.getColor(requireContext(), R.color.pink);
            case "ungu":
                return ContextCompat.getColor(requireContext(), R.color.ungu);
            case "navy":
            default:
                return ContextCompat.getColor(requireContext(), R.color.navy);
        }
    }
}
