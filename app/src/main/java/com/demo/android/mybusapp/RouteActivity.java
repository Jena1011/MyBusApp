package com.demo.android.mybusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

public class RouteActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        listView = findViewById(R.id.listView);


        new MyAsyncTask().execute();
    }



//    記得去設定 <uses-permission android:name="android.permission.INTERNET"/>
    private String fetchData() {
        try {
            URL url = new URL("https://tcgbusfs.blob.core.windows.net/blobbus/GetRoute.gz");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            is = new GZIPInputStream(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] cache = new byte[1024];
            int total = 0, n;
            while ((n = is.read(cache)) > 0) {
                System.out.println("total=" + total + ", n=" + n);
                baos.write(cache, 0, n);
            }
            cache = baos.toByteArray();
            return new String(cache);
        } catch (Exception e) {
            return "Failed!!";
        }
    }



    ArrayList<HashMap<String,Object>> routeList = new ArrayList<>();
    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return fetchData();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("jena", result);

            try {
                JSONObject jobj = new JSONObject(result);
                JSONArray jsonArray = jobj.getJSONArray("BusInfo");
                for (int i=0; i< jsonArray.length(); i++){
                    JSONObject e = (JSONObject)jsonArray.get(i);
                    String nameZh = e.getString("nameZh");
                    String departureZh = e.getString("departureZh");
                    String destinationZh = e.getString("destinationZh");
                    int routeId = e.getInt("Id");
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("nameZh",nameZh);
                    map.put("departureZh",departureZh);
                    map.put("destinationZh",destinationZh);
                    map.put("routeId",routeId);
                    routeList.add(map);
                }

                SimpleAdapter adapter = new SimpleAdapter(
                        RouteActivity.this,
                        routeList,
                        R.layout.item_view,
                        new String[]{"nameZh","departureZh","destinationZh"},
                        new int[]{R.id.tvNameZh,R.id.tvDepart,R.id.tvDest}
                        );

                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}