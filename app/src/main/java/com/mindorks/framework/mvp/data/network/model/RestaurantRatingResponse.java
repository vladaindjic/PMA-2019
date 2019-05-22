package com.mindorks.framework.mvp.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantRatingResponse {

    @Expose
    @SerializedName("http_status_code")
    private int httpStatusCode;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private RestaurantRating data;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RestaurantRating getData() {
        return data;
    }

    public void setData(RestaurantRating data) {
        this.data = data;
    }

    public static class RestaurantRating {

        @Expose
        @SerializedName("rating")
        private Double rating;
        @Expose
        @SerializedName("rated")
        private int rated;
        @Expose
        @SerializedName("comments")
        private List<Comment> comments;

        public RestaurantRating() {
        }

        public RestaurantRating(Double rating, int rated, List<Comment> comments) {
            this.rating = rating;
            this.rated = rated;
            this.comments = comments;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public int getRated() {
            return rated;
        }

        public void setRated(int rated) {
            this.rated = rated;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

        public static class Comment {
            private String text;
            // TODO mica: dodati da li je korisnik da like ili dislike
            // dodati i broj lajkova i dislajkova

            public Comment(String text) {
                this.text = text;
            }

            public Comment() {
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }
    }


}
