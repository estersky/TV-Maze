package com.example.restapi_p7_sitiaisyah.presentation.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import com.example.restapi_p7_sitiaisyah.LoginActivity;
import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.presentation.adapter.ShowAdapter;
import com.example.restapi_p7_sitiaisyah.data.network.TvMazeApi;
import com.example.restapi_p7_sitiaisyah.presentation.viewmodel.ShowViewModel;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private ShowViewModel showViewModel;
    private RecyclerView recyclerView;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayout rootLayout = view.findViewById(R.id.root_layout);
        rootLayout.setBackgroundColor(getThemeColorFromPreferences());

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(requireContext(), "Logout berhasil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        showViewModel = new ViewModelProvider(this).get(ShowViewModel.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.tvmaze.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TvMazeApi api = retrofit.create(TvMazeApi.class);

        showViewModel.fetchShows(api);

        showViewModel.getShows().observe(getViewLifecycleOwner(), shows -> {
            ShowAdapter adapter = new ShowAdapter(shows);
            recyclerView.setAdapter(adapter);
        });

        return view;
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
