package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewsActivity extends AppCompatActivity {

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Intent it = getIntent();
        Bundle b = it.getExtras();
        final String username = b.getString("username");
        final String placename = b.getString("placename");
        TextView tvUsername = (TextView) findViewById(R.id.idUsername);
        tvUsername.setText(username);
        TextView tvPlaceName = (TextView) findViewById(R.id.tv_rev);
        tvPlaceName.setText(placename);
        RatingBar rs = (RatingBar) findViewById(R.id.rs_rev);
        rs.setRating(0);
        Button btnWriteReview = (Button) findViewById(R.id.rev_btn);
        btnWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addReview(username, placename);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnViewReviews = (Button) findViewById(R.id.revViewBtn);
        btnViewReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailReview = new Intent(getApplicationContext(), ReviewDetailsActivity.class);
                detailReview.putExtra("placeName", placename);
                startActivity(detailReview);
            }
        });

        Button cancelReview = (Button) findViewById(R.id.revCancelBtn);
        cancelReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    emptyReviewFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnChart = (Button) findViewById(R.id.chartBtn);
        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chart = new Intent(getApplicationContext(), ChartActivity.class);
                chart.putExtra("whereCondition", placename);
                startActivity(chart);
            }
        });
    }

    public void addReview(String username, String placeName) throws Exception {
        String rUser = username;
        String pName = placeName;
        EditText etRev = (EditText) findViewById(R.id.et_rev);
        String rText = etRev.getText().toString();
        RatingBar rb = (RatingBar) findViewById(R.id.rs_rev);
        int rRating = (int) rb.getRating();
        if (rText.equals("") && rRating == 0) {
            Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
        } else if (rRating == 0) {
            Toast.makeText(getApplicationContext(), "Not Rated", Toast.LENGTH_SHORT).show();
        } else if (rText.equals("")) {
            Toast.makeText(getApplicationContext(), "Empty Review Field", Toast.LENGTH_SHORT).show();
        } else {
            Review r = new Review();
            r.setUsername(rUser);
            r.setReviewText(rText);
            r.setRating(rRating);
            r.setPlaceName(pName);
            dbHelper.writeReview(r);
            Toast.makeText(getApplicationContext(), "Review Added", Toast.LENGTH_SHORT).show();
            etRev.setText("");
            rb.setRating(0);
        }
    }

    public void emptyReviewFields() throws Exception {
        EditText tv = (EditText) findViewById(R.id.et_rev);
        RatingBar rb = (RatingBar) findViewById(R.id.rs_rev);
        tv.setText("");
        rb.setRating(0);
    }
}