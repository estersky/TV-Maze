package com.example.restapi_p7_sitiaisyah.presentation.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restapi_p7_sitiaisyah.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private Context context;
    private List<Reminder> reminderList;

    public ReminderAdapter(Context context, List<Reminder> reminderList) {
        this.context = context;
        this.reminderList = reminderList;
    }

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);

        holder.tvJudulFilm.setText(reminder.getJudul());

        // Format jadwal
        String jadwalLengkap = formatJadwal(reminder.getTanggal(), reminder.getWaktu());
        holder.tvJadwalNonton.setText(jadwalLengkap);

        // Set gambar dari Uri
        if (reminder.getGambarUri() != null && !reminder.getGambarUri().isEmpty()) {
            Uri uri = Uri.parse(reminder.getGambarUri());
            holder.imgFilm.setImageURI(uri);
        } else {
            holder.imgFilm.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFilm;
        TextView tvJudulFilm, tvJadwalNonton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFilm = itemView.findViewById(R.id.imgFilm);
            tvJudulFilm = itemView.findViewById(R.id.tvJudulFilm);
            tvJadwalNonton = itemView.findViewById(R.id.tvJadwalNonton);
        }
    }

    private String formatJadwal(String tanggal, String waktu) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat inputTimeFormat = new SimpleDateFormat("HH:mm");
            Date date = inputDateFormat.parse(tanggal);
            Date time = inputTimeFormat.parse(waktu);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
            return outputFormat.format(date) + ", " + waktu;
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal + ", " + waktu;
        }
    }
}
