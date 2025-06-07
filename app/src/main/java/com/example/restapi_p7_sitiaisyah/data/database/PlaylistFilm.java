package com.example.restapi_p7_sitiaisyah.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_film")
public class PlaylistFilm {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String judul;
    private String tanggal; // Simpan dalam format "dd-MM-yyyy"
    private String waktu;   // Simpan dalam format "HH:mm"

    // Constructor
    public PlaylistFilm(String judul, String tanggal, String waktu) {
        this.judul = judul;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
}

