package com.example.restapi_p7_sitiaisyah.presentation.activity;

import static com.example.restapi_p7_sitiaisyah.R.id.navigation_setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restapi_p7_sitiaisyah.LoginActivity;
import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.presentation.ui.ProfileFragment;
import com.example.restapi_p7_sitiaisyah.presentation.ui.KontenFragment;
import com.example.restapi_p7_sitiaisyah.presentation.ui.HomeFragment;
import com.example.restapi_p7_sitiaisyah.presentation.ui.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Jika belum login maka arahkan ke LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        setContentView(R.layout.activity_main);

        // ✅ Tambahan untuk izin notifikasi Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        100);
            }
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        loadFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int id = item.getItemId();

            if (id == R.id.navigation_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.navigation_konten) {
                fragment = new KontenFragment();
            } else if (id == R.id.navigation_favorite) {
                fragment = new ProfileFragment();
            } else if (id == R.id.navigation_setting) {
                fragment = new SettingsFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
            return true;
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
