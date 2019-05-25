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

            @Expose
            @SerializedName("id")
            private Long id;

            @Expose
            @SerializedName("rating")
            private Double rating;

            @Expose
            @SerializedName("text")
            private String text;

            @Expose
            @SerializedName("my_comment")
            private boolean myComment;

            @Expose
            @SerializedName("negative_votes")
            private int negativeVotes;

            @Expose
            @SerializedName("positive_votes")
            private int positive_votes;

            @Expose
            @SerializedName("my_vote")
            private String myVote;


            public Comment(Long id, Double rating, String text, boolean myComment, int negativeVotes, int positive_votes, String myVote) {
                this.id = id;
                this.rating = rating;
                this.text = text;
                this.myComment = myComment;
                this.negativeVotes = negativeVotes;
                this.positive_votes = positive_votes;
                this.myVote = myVote;
            }

            public Comment() {
            }


            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Double getRating() {
                return rating;
            }

            public void setRating(Double rating) {
                this.rating = rating;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public boolean isMyComment() {
                return myComment;
            }

            public void setMyComment(boolean myComment) {
                this.myComment = myComment;
            }

            public int getNegativeVotes() {
                return negativeVotes;
            }

            public void setNegativeVotes(int negativeVotes) {
                this.negativeVotes = negativeVotes;
            }

            public int getPositive_votes() {
                return positive_votes;
            }

            public void setPositive_votes(int positive_votes) {
                this.positive_votes = positive_votes;
            }

            public String getMyVote() {
                return myVote;
            }

            public void setMyVote(String myVote) {
                this.myVote = myVote;
            }
        }
    }


}
