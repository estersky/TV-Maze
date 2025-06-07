package com.example.restapi_p7_sitiaisyah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.restapi_p7_sitiaisyah.domain.utils.AuthValidator;
import com.example.restapi_p7_sitiaisyah.presentation.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private EditText editEmail, editPassword;
    private Button buttonRegister;
    private TextView textLogin;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        editEmail = view.findViewById(R.id.editEmail);
        editPassword = view.findViewById(R.id.editPassword);
        buttonRegister = view.findViewById(R.id.buttonRegister);
        textLogin = view.findViewById(R.id.textLogin);
        auth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(v -> registerUser());
        textLogin.setOnClickListener(v -> ((LoginActivity) getActivity()).switchToLogin());

        return view;
    }

    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (!AuthValidator.isEmailValid(email)) {
            Toast.makeText(getActivity(), "Email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AuthValidator.isPasswordValid(password)) {
            Toast.makeText(getActivity(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Registrasi berhasil. Silakan login.", Toast.LENGTH_SHORT).show();
                        ((LoginActivity) getActivity()).switchToLogin();
                    } else {
                        Toast.makeText(getActivity(), "Registrasi gagal: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
