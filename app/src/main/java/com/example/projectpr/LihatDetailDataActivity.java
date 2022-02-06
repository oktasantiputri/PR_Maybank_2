package com.example.projectpr;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LihatDetailDataActivity extends AppCompatActivity {
    EditText edit_id, edit_name, edit_pekerjaan, edit_saldo, edit_lokasi;
    String id;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Data Pegawai");

        edit_id = findViewById(R.id.edit_id);
        edit_name = findViewById(R.id.edit_name);
        edit_pekerjaan = findViewById(R.id.edit_pekerjaan);
        edit_saldo = findViewById(R.id.edit_saldo);
        edit_lokasi = findViewById(R.id.edit_lokasi);

        //menerima intent dr class LihatDataActivity
        Intent recieveIntent = getIntent();
        id = recieveIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id.setText(id);

        //ambil JSON
        getJSON();
    }

    private void getJSON() {
        // bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetailDataActivity.this,
                        "Mengambil Data", "Harap menunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, id);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json)
    {
        try
        {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);
            String nama = object.getString(Konfigurasi.TAG_JSON_NAME);
            String jabatan = object.getString(Konfigurasi.TAG_JSON_PEKERJAAN);
            String gaji = object.getString(Konfigurasi.TAG_JSON_SALDO);
            String lokasi = object.getString(Konfigurasi.TAG_JSON_LOKASI);

            edit_name.setText(nama);
            edit_pekerjaan.setText(jabatan);
            edit_saldo.setText(gaji);
            edit_lokasi.setText(lokasi);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}