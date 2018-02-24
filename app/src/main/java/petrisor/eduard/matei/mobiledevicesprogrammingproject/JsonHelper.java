package petrisor.eduard.matei.mobiledevicesprogrammingproject;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper extends AsyncTask<String, Void, List<Places>> {
    @Override
    protected List<Places> doInBackground(String... strings) {
        List<Places> placesList = new ArrayList<Places>();
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream is = con.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuilder builder = new StringBuilder();
            String linie = null;
            while ((linie = reader.readLine()) != null) {
                builder.append(linie);
            }

            JSONObject object = new JSONObject(builder.toString());
            JSONObject json = object.getJSONObject("Places");
            JSONArray jsonArray[] = new JSONArray[6];
            jsonArray[0] = json.getJSONArray("Restaurants");
            jsonArray[1] = json.getJSONArray("Bars");
            jsonArray[2] = json.getJSONArray("Museums");
            jsonArray[3] = json.getJSONArray("Shopping-Centers");
            jsonArray[4] = json.getJSONArray("Hotels");
            jsonArray[5] = json.getJSONArray("Points-Of-Interest");

            for (int i = 0; i < jsonArray.length; i++) {
                for (int j = 0; j < jsonArray[i].length(); j++) {
                    JSONObject el = jsonArray[i].getJSONObject(j);
                    Places p = new Places(el.getString("-type"), el.getString("-name"), el.getDouble("-lat"), el.getDouble("-long"));
                    placesList.add(p);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placesList;
    }
}