package com.example.restapi_p7_sitiaisyah.presentation.adapter;

public class Reminder {
    private String judul;
    private String tanggal;  // format "yyyy-MM-dd"
    private String waktu;    // format "HH:mm"
    private String gambarUri; // Uri string gambar

    public Reminder(String judul, String tanggal, String waktu, String gambarUri) {
        this.judul = judul;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.gambarUri = gambarUri;
    }

    public String getJudul() {
        return judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getGambarUri() {
        return gambarUri;
    }
}
