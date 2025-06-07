package com.example.restapi_p7_sitiaisyah.presentation.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.data.database.AppDatabase;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderDao;
import com.example.restapi_p7_sitiaisyah.data.database.ReminderEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.restapi_p7_sitiaisyah.receiver.ReminderReceiver;

public class AddReminderActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_PERMISSION = 100;

    private EditText etTitle, etDate, etTime;
    private ImageView ivPreview, ivPickImage;
    private Button btnSave;

    private Uri selectedImageUri;
    private Uri imageUri;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        etTitle = findViewById(R.id.etTitle);
        etDate = findViewById(R.id.etDate);
        etTime = findViewById(R.id.etTime);
        ivPreview = findViewById(R.id.ivPreview);
        ivPickImage = findViewById(R.id.ivPickImage);
        btnSave = findViewById(R.id.btnSave);

        calendar = Calendar.getInstance();

        etDate.setOnClickListener(v -> showDatePicker());
        etTime.setOnClickListener(v -> showTimePicker());
        ivPickImage.setOnClickListener(v -> showImageSourceDialog());
        btnSave.setOnClickListener(v -> saveReminder());
    }

    private void showDatePicker() {
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            etDate.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            etTime.setText(sdf.format(calendar.getTime()));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void showImageSourceDialog() {
        String[] options = {"Kamera", "Galeri"};
        new AlertDialog.Builder(this)
                .setTitle("Pilih Gambar")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        if (checkAndRequestPermissions()) {
                            openCamera();
                        }
                    } else {
                        openGallery();
                    }
                })
                .show();
    }

    private boolean checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Izin kamera diperlukan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                selectedImageUri = data.getData();
                ivPreview.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                selectedImageUri = imageUri;
                ivPreview.setImageURI(selectedImageUri);
            }
        }
    }

    private void saveReminder() {
        String title = etTitle.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String time = etTime.getText().toString().trim();
        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : "";

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
            return;
        }

        ReminderEntity reminder = new ReminderEntity();
        reminder.setTitle(title);
        reminder.setDate(date);
        reminder.setTime(time);
        reminder.setImageUri(imageUriString);

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        AsyncTask.execute(() -> {
            db.reminderDao().insertReminder(reminder);
        });

        setReminder(title, calendar);

        Toast.makeText(this, "Reminder berhasil disimpan", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("title", title);
        resultIntent.putExtra("date", date);
        resultIntent.putExtra("time", time);
        resultIntent.putExtra("imageUri", imageUriString);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void setReminder(String title, Calendar calendar) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("title", title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                title.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }


}
