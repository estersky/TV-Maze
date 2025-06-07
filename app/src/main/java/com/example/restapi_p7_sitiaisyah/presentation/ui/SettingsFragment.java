package com.example.restapi_p7_sitiaisyah.presentation.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.restapi_p7_sitiaisyah.R;

public class SettingsFragment extends Fragment {

    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_THEME = "theme_color";

    private RadioGroup rgTheme;
    private RadioButton rbDefault, rbNavy, rbDark, rbUngu, rbPink;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgTheme = view.findViewById(R.id.rgTheme);
        rbDefault = view.findViewById(R.id.rbDefault);
        rbNavy = view.findViewById(R.id.rbNavy);
        rbDark = view.findViewById(R.id.rbDark);
        rbUngu = view.findViewById(R.id.rbUngu);
        rbPink = view.findViewById(R.id.rbPink);

        // Set background root layout sesuai tema
        View rootLayout = view.findViewById(R.id.root_layout);
        rootLayout.setBackgroundColor(getThemeColorFromPreferences());

        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String selectedTheme = prefs.getString(KEY_THEME, "navy");

        switch (selectedTheme) {
            case "black":
                rbDark.setChecked(true);
                break;
            case "white":
                rbDefault.setChecked(true);
                break;
            case "ungu":
                rbUngu.setChecked(true);
                break;
            case "pink":
                rbPink.setChecked(true);
                break;
            case "navy":
            default:
                rbNavy.setChecked(true);
                break;
        }

        rgTheme.setOnCheckedChangeListener((group, checkedId) -> {
            String theme = "navy";

            if (checkedId == R.id.rbNavy) {
                theme = "navy";
            } else if (checkedId == R.id.rbDark) {
                theme = "black";
            } else if (checkedId == R.id.rbUngu) {
                theme = "ungu";
            } else if (checkedId == R.id.rbPink) {
                theme = "pink";
            } else if (checkedId == R.id.rbDefault) {
                theme = "white";
            }

            prefs.edit().putString(KEY_THEME, theme).apply();

            rootLayout.setBackgroundColor(getThemeColorFromTheme(theme));

            requireActivity().recreate();
        });
    }

    private int getThemeColorFromPreferences() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String theme = prefs.getString(KEY_THEME, "navy");
        return getThemeColorFromTheme(theme);
    }

    private int getThemeColorFromTheme(String theme) {
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
