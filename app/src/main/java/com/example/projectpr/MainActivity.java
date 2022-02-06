package com.example.projectpr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private ListView list_view_launcher;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        //memunculkan back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view_launcher = findViewById(R.id.list_view_launcher);
        list_view_launcher.setOnItemClickListener(this);

        //method ambil data JSON
        getJSON();
    }

    private void getJSON()
    {

        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Mengsmbil Data",
                        "Harap Menunggu",false,false);
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetReponse(Konfigurasi.URL_GET_DETAIL, Konfigurasi.URL_GET_ALL);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.d("DATA_JSON:",JSON_STRING);

                //menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData()
    {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

        try
        {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            Log.d("DATA_JSON", JSON_STRING);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject object = result.getJSONObject(i);
                String id = object.getString(Konfigurasi.TAG_JSON_ID);
                String name = object.getString(Konfigurasi.TAG_JSON_NAME);
                String saldo = object.getString(Konfigurasi.TAG_JSON_SALDO);

                HashMap<String, String> nasbah = new HashMap<>();
                nasbah.put(Konfigurasi.TAG_JSON_ID, id);
                nasbah.put(Konfigurasi.TAG_JSON_NAME, name);
                nasbah.put(Konfigurasi.TAG_JSON_SALDO, saldo);

                //ubah format json menjadi array list
                list.add(nasbah);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(getApplicationContext(), list,
                R.layout.activity_list_item,
                new String[]{Konfigurasi.TAG_JSON_ID, Konfigurasi.TAG_JSON_NAME, Konfigurasi.TAG_JSON_SALDO},
                new int[]{R.id.txt_id, R.id.txt_name, R.id.txt_saldo});

        list_view_launcher.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l)
    {
        //ketika salah satu list dipilih
        //detail: ID, Name, Desg, Salary
        Intent myIntent = new Intent(MainActivity.this,
                LihatDetailDataActivity.class);
        HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        onBackPressed();
        return true;
    }
}