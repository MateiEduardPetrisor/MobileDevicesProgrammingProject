package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.InputStream;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        try {
            String about = readFile("About.txt");
            TextView tv = findViewById(R.id.tvAbout);
            tv.setText(about);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFile(String file) throws Exception {
        String text = "";
        InputStream inputStream = getAssets().open(file);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        text = new String(buffer);
        return text;
    }
}
