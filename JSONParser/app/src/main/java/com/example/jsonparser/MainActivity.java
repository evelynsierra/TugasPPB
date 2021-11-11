package com.example.jsonparser;

import androidx.appcompat.app.AppCompatActivity;

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
    ArrayList <HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lv);
        new GetContacts().execute();
    }
    private class GetContacts extends AsyncTask <Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            //make a request
            String url = "http://api.androidhive.info/contacts/"; String jsonStr = sh.makeServiceCall(url);
            Log.e("Main", "Response from url : " + jsonStr);

            if (jsonStr != null ){
                try {
                    JSONObject jsonObj = new JSONObject("contacts");
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    for (int i = 0; i < contacts.length(); i++){
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name"); String email = c.getString("email");
                        String address = c.getString("address"); String gender = c.getString("gender");

                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile"); String home = phone.getString("home");
                        String office = phone.getString("office");

                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("id", id); contact.put("name", name);
                        contact.put("email", email); contact.put("mobile", mobile);

                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e("Main", "Json parsing error : " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else {
                Log.e("Main", "Couldn't get Json from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();

                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.list_item, new String[] {"id", "name", "email", "mobile"},
                    new int[] {R.id.id, R.id.name, R.id.email, R.id.mobile});
            lv.setAdapter(adapter);
        }
    }
}


