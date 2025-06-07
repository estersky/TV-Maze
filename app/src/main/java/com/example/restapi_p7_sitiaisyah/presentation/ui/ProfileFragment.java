package com.example.restapi_p7_sitiaisyah.presentation.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderDao;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderEntity;
import com.example.restapi_p7_sitiaisyah.data.database.AppDatabase;
import com.example.restapi_p7_sitiaisyah.presentation.adapter.Reminder;
import com.example.restapi_p7_sitiaisyah.presentation.adapter.ReminderAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_ADD_REMINDER = 100;

    private RecyclerView rvPengingatFilm;
    private Button btnTambahPengingat;
    private ReminderAdapter reminderAdapter;
    private List<Reminder> reminderList;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        View rootLayout = view.findViewById(R.id.root_layout);
        rootLayout.setBackgroundColor(getThemeColorFromPreferences());

        rvPengingatFilm = view.findViewById(R.id.rvPengingatFilm);
        btnTambahPengingat = view.findViewById(R.id.btnTambahPengingat);
        rvPengingatFilm.setLayoutManager(new LinearLayoutManager(getContext()));

        reminderList = new ArrayList<>();

        // Ambil data dari database Room
        AppDatabase db = Room.databaseBuilder(requireContext(),
                AppDatabase.class, "app_database").allowMainThreadQueries().build();

        ReminderDao reminderDao = db.reminderDao();
        List<ReminderEntity> reminderEntities = reminderDao.getAllReminders();

        // Konversi ReminderEntity ke Reminder (untuk adapter)
        for (ReminderEntity entity : reminderEntities) {
            reminderList.add(new Reminder(
                    entity.getTitle(),
                    entity.getDate(),
                    entity.getTime(),
                    entity.getImageUri()
            ));
        }

        // Set adapter
        reminderAdapter = new ReminderAdapter(getContext(), reminderList);
        rvPengingatFilm.setAdapter(reminderAdapter);

        btnTambahPengingat.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AddReminderActivity.class);
            startActivityForResult(intent, REQUEST_ADD_REMINDER);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_REMINDER && resultCode == Activity.RESULT_OK && data != null) {
            String title = data.getStringExtra("title");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String imageUri = data.getStringExtra("imageUri");

            Reminder newReminder = new Reminder(title, date, time, imageUri);
            reminderList.add(newReminder);
            reminderAdapter.notifyItemInserted(reminderList.size() - 1);
        }
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
