package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

    public List<Places> lstPlaces = new ArrayList<Places>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        Intent placesIntent = getIntent();
        Bundle placesBundle = placesIntent.getExtras();
        final String typeFullName = placesBundle.getString("typeFullName");
        final String typeAbbreviation = placesBundle.getString("typeAbbreviation");
        TextView tv = findViewById(R.id.placesTXTView);
        tv.setText(typeFullName);

        JsonHelper json = new JsonHelper() {
            @Override
            protected void onPostExecute(List<Places> placeList) {
                for (int i = 0; i < placeList.size(); i++) {
                    lstPlaces.add(placeList.get(i));
                }
                final ArrayList<String> names = new ArrayList<String>();
                for (Places p : placeList) {
                    if (p.getType().matches(typeAbbreviation)) {
                        names.add(p.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                ListView lv = findViewById(R.id.lv_places);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() //define the evnet user press on a place from the list to write a review
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent review = new Intent(getApplicationContext(), ReviewsActivity.class);
                        review.putExtra("username", MainMenuActivity.USERNAME);
                        review.putExtra("placename", names.get(i).toString());
                        startActivity(review);
                    }
                });
                lv.setAdapter(adapter);
            }
        };
        json.execute("https://raw.githubusercontent.com/MateiEduardPetrisor/MobileDevicesProgrammingProject/master/Places.json");

        final Button map = findViewById(R.id.btnViewOnMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Map = new Intent(getApplicationContext(), MapsActivity.class);
                Map.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) lstPlaces);
                Map.putExtra("typeAbbreviation", typeAbbreviation);
                startActivity(Map);
            }
        });
    }
}