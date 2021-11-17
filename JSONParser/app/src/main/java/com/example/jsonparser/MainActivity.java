package com.example.jsonparser;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    ArrayList<HashMap<String, String>> gempaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gempaList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        new GetContacts().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,
                    "Json Data is downloading", Toast.LENGTH_LONG).show();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://data.bmkg.go.id/DataMKG/TEWS/gempadirasakan.json";
            String jsonStr = sh.makeServiceCall(url);
            Log.e("Main", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONObject infoGempa = jsonObj.getJSONObject("Infogempa");
                    JSONArray menggempa = infoGempa.getJSONArray("gempa");
                    // looping through All Contacts
                    for (int i = 0; i < menggempa.length(); i++) {
                        JSONObject c = menggempa.getJSONObject(i);
                        String tanggal = c.getString("Tanggal");
                        String jam = c.getString("Jam");
                        String date_time = c.getString("DateTime");
                        String coord = c.getString("Coordinates");
                        String lintang = c.getString("Lintang");
                        String bujur = c.getString("Bujur");
                        String magnitude = c.getString("Magnitude");
                        String kedalaman = c.getString("Kedalaman");
                        String wilayah = c.getString("Wilayah");
                        String dirasakan = c.getString("Dirasakan");
                        // tmp hash map for single contact
                        HashMap<String, String> h_gempa = new HashMap<>();
                        // adding each child node to HashMap key => value
                        h_gempa.put("Tanggal", "Tanggal: " + tanggal);
                        h_gempa.put("Jam", "Jam: " + jam);
                        h_gempa.put("DateTime", "Tanggal dan Waktu: " + date_time);
                        h_gempa.put("Koordinat", "Koordinat" + coord);
                        h_gempa.put("Lintang", "Lintang: " + lintang);
                        h_gempa.put("Bujur", "Bujur" + bujur);
                        h_gempa.put("Magnitude", "Magnitude: " + magnitude);
                        h_gempa.put("Kedalaman", "Kedalaman: " + kedalaman);
                        h_gempa.put("Wilayah", "Wilayah: " + wilayah);
                        h_gempa.put("Dirasakan", "Dirasakan di: " + dirasakan);
                        // adding contact to contact list
                        gempaList.add(h_gempa);
                    }}
                catch (final JSONException e) {
                    Log.e("Main", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }});
                }
            } else {
                Log.e("Main", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }});
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, gempaList,
                    R.layout.list_item, new String[]{"Tanggal","Jam","DateTime","Coordinates","Lintang","Bujur","Magnitude","Kedalaman","Wilayah","Dirasakan"},
                    new int[]{R.id.tanggal,R.id.jam,R.id.date_time, R.id.coord, R.id.lintang, R.id.bujur, R.id.magnitude, R.id.kedalaman, R.id.wilayah, R.id.dirasakan});
            lv.setAdapter(adapter);
        }}
}


