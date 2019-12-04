package com.example.dell.devakademi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AnaSayfa extends AppCompatActivity {

    private String TAG = AnaSayfa.class.getSimpleName();
    ArrayList<Ilan> ilanlist_array;

    private ListView ilanListView;
    private ImageButton nextButton, backButton;

    CustomAdapter adapter;
    static int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        ilanlist_array = new ArrayList<>();
        ilanListView = findViewById(R.id.ilanListView);

        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        new GetDataTask().execute("https://devakademi.sahibinden.com/api/auctions?offset=" + offset + "&size=10");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilanlist_array.clear();
                offset+=10;
                new GetDataTask().execute("https://devakademi.sahibinden.com/api/auctions?offset=" + offset+ "&size=10");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offset > 0) {
                    ilanlist_array.clear();
                    offset-=10;
                    new GetDataTask().execute("https://devakademi.sahibinden.com/api/auctions?offset=" + offset+ "&size=10");
                }
            }
        });

    }

    private void updateView(int index){
        View v = ilanListView.getChildAt(index -
                ilanListView.getFirstVisiblePosition());

        if(v == null)
            return;
    }

    class GetDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            HttpHandler httpHandler = new HttpHandler();

            String jsonStr = httpHandler.makeServiceCall("https://devakademi.sahibinden.com/api/auctions?offset=" + offset + "&size=10");
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                JSONObject jsonResponse;
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Log.e(TAG, "jsArray: " + jsonArray.get(i).toString());
                        jsonResponse = jsonArray.getJSONObject(i);
                        String id = jsonResponse.getString("id");
                        String title = jsonResponse.getString("title");
                        String description = jsonResponse.getString("description");
                        String city = jsonResponse.getString("city");
                        String town = jsonResponse.getString("town");

                        ilanlist_array.add(new Ilan(id, title, description, city, town));

                        //t2.setText(id);
                        Log.e(TAG, "id: " + id +", "+title+", "+description+", "+city+", "+town);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            final Ilan haber = new Ilan();
            adapter = new CustomAdapter(getApplicationContext(), R.layout.list_item, ilanlist_array);
            ilanListView.setAdapter(adapter);

            /*ilanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(getApplicationContext(), IlanDetay.class);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("İlan", ilanlist_array.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });*/
        }

    }
}

