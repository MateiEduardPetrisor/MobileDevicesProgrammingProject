package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    public static String USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent mainMenu = getIntent();
        Bundle mainMenuBundle = mainMenu.getExtras();
        USERNAME = mainMenuBundle.getString("user");
        Toast.makeText(getApplicationContext(), "Logged in as " + USERNAME, Toast.LENGTH_SHORT).show();
        Button btn = (Button) findViewById(R.id.btnPlaces);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Spinner sp = (Spinner) findViewById(R.id.Spinner);
                    String typeFullName = sp.getSelectedItem().toString();
                    String typeAbbreviation = convertDataFromSpinner(typeFullName);
                    Intent Places = new Intent(getApplicationContext(), PlacesActivity.class);
                    Places.putExtra("typeAbbreviation", typeAbbreviation);
                    Places.putExtra("typeFullName", typeFullName);
                    Places.putExtra("username", USERNAME);
                    startActivity(Places);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String convertDataFromSpinner(String dataType) throws Exception {
        if (dataType.equals("Bars")) {
            dataType = "B";
        } else if (dataType.equals("Restaurants")) {
            dataType = "R";
        } else if (dataType.equals("Shopping Centers")) {
            dataType = "SC";
        } else if (dataType.equals("Museums")) {
            dataType = "M";
        } else if (dataType.equals("Hotels")) {
            dataType = "H";
        } else if (dataType.equals("Points Of Interest")) {
            dataType = "POI";
        } else {
            Toast.makeText(getApplicationContext(), "Other Type", Toast.LENGTH_LONG).show();
        }
        return dataType;
    }
}