package com.example.restapi_p7_sitiaisyah;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.restapi_p7_sitiaisyah.presentation.activity.MainActivity;
import com.example.restapi_p7_sitiaisyah.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // memeriksa apakah user sudah login
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // jika sudah login maka langsung ke Home
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        switchFragment(new LoginFragment());
    }

    public void switchToRegister() {
        switchFragment(new RegisterFragment());
    }

    public void switchToLogin() {
        switchFragment(new LoginFragment());
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
