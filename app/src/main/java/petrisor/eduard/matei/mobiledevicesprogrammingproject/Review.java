package petrisor.eduard.matei.mobiledevicesprogrammingproject;

public class Review {
    private String username;
    private String reviewText;
    private int rating;
    private String placeName;

    public Review(String username, String reviewText, int rating, String pname) {
        this.username = username;
        this.reviewText = reviewText;
        this.rating = rating;
        this.placeName = pname;
    }

    public Review() {
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return getUsername() + "," + getReviewText() + "," + getRating() + "," + getPlaceName() + ";";
    }
}
