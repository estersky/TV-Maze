package com.example.restapi_p7_sitiaisyah.model;

public class Show {
    private String name;
    private Image image;
    private Rating rating;
    private String status;
    private String premiered;

    public String getName() {
        return name;
    }

    public Image getImage() {
        return image;
    }

    public Rating getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getPremiered() {
        return premiered;
    }

    // Inner class untuk rating
    public static class Rating {
        private Double average;

        public Double getAverage() {
            return average;
        }
    }
}
