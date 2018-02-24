package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class ReviewDetailsActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_details);

        try {
            Intent it = getIntent();
            Bundle b = it.getExtras();
            String CONDITION = b.getString("placeName");
            ListView lvDetailReview = (ListView) findViewById(R.id.lvReviewsDetails);
            ArrayList<Review> listOfReviews = getReviews2(CONDITION);
            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getApplicationContext(), R.layout.detail_review, listOfReviews);
            lvDetailReview.setAdapter(reviewsAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Review> getReviews2(String CONDITION) throws Exception {
        ArrayList<Review> reviews = dbHelper.getReviewsFromDatabase(CONDITION);
        return reviews;
    }
}