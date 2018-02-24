package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity extends AppCompatActivity {

    public List<Review> lstRev = new ArrayList<Review>();
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent chartIntent = getIntent();
            Bundle chartBundle = chartIntent.getExtras();
            String Condition = chartBundle.getString("whereCondition");
            lstRev = dbHelper.getReviewsFromDatabase(Condition);
            setContentView(new BarChart(getApplicationContext(), (ArrayList<Review>) lstRev));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
