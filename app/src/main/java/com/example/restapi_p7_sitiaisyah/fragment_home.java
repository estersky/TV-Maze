package com.example.restapi_p7_sitiaisyah;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class fragment_home extends Fragment {

    private FirebaseAuth auth;

    public fragment_home() {
    }

    public static fragment_home newInstance(String param1, String param2) {
        fragment_home fragment = new fragment_home();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Logout diklik", Toast.LENGTH_SHORT).show();
            logoutUser();
        });


        return view;
    }

//    private void logoutUser() {
//        // Logout dari Firebase
//        auth.signOut();
//
//        // Logout dari Google
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(
//                requireActivity(),
//                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//        );
//
//        googleSignInClient.signOut().addOnCompleteListener(task -> {
//            // Redirect ke LoginActivity dan clear activity stack
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        });
//    }
    private void logoutUser() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

}
