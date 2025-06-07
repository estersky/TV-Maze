package com.example.restapi_p7_sitiaisyah.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restapi_p7_sitiaisyah.R;
import com.example.restapi_p7_sitiaisyah.data.database.ShowEntity;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {
    private final List<ShowEntity> showList;

    public ShowAdapter(List<ShowEntity> showList) {
        this.showList = showList != null ? showList : new ArrayList<>();
    }
    public void setShows(List<ShowEntity> newShowList) {
        showList.clear(); // Clear existing data
        showList.addAll(newShowList); // Add new data
        notifyDataSetChanged(); // Notify adapter for the change
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        ShowEntity show = showList.get(position);
        holder.tvShowName.setText(show.getName());

        // Set Gambar
        Glide.with(holder.itemView.getContext())
                .load(show.getImageUrl())
                .into(holder.ivShowImage);

        // Set Rating (Cek jika null)
        String ratingText = (show.getRating() != null) ? "Rating: " + show.getRating() : "Rating: N/A";
        holder.tvShowRating.setText(ratingText);

        // Set Status
        holder.tvShowStatus.setText("Status: " + show.getStatus());

        // Set Tahun Rilis (Cek jika null)
        String premieredText = (show.getPremiered() != null) ? "Tahun Rilis: " + show.getPremiered() : "Tahun Rilis: N/A";
        holder.tvShowPremiered.setText(premieredText);
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

        public static class ShowViewHolder extends RecyclerView.ViewHolder {
        TextView tvShowName, tvShowRating, tvShowStatus, tvShowPremiered;
        ImageView ivShowImage;

        public ShowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShowName = itemView.findViewById(R.id.show_title);
            ivShowImage = itemView.findViewById(R.id.show_image);
            tvShowRating = itemView.findViewById(R.id.show_rating);
            tvShowStatus = itemView.findViewById(R.id.show_status);
            tvShowPremiered = itemView.findViewById(R.id.show_premiered);
        }
    }
}
