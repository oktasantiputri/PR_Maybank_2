package com.example.projectpr;

public class Konfigurasi
{
    //URL dimana web API berada
    public static final String URL_GET_ALL = "http://192.168.100.4/nasabah/tampilSemuaNsb.php";
    public static final String URL_GET_DETAIL = "http://192.168.100.4/nasabah/tampilNsb.php?id=";

    //key and value JSON yang muncul di browser
    public static final String KEY_NASABAH_ID ="id";
    public static final String KEY_NASABAH_NAMA = "name";
    public static final String KEY_NASABAH_PEKERJAAN = "pekerjaan";
    public static final String KEY_NASABAH_SALDO = "saldo";
    public static final String KEY_NASABAH_LOKASI = "lokasi";

    //flag JSON
    public static final String TAG_JSON_ARRAY ="result";
    public static final String TAG_JSON_ID ="id";
    public static final String TAG_JSON_NAME ="name";
    public static final String TAG_JSON_PEKERJAAN ="pekerjaan";
    public static final String TAG_JSON_SALDO ="saldo";
    public static final String TAG_JSON_LOKASI ="lokasi";


    //variabel ID Pegawai
    public static final String PGW_ID = "emp_id";
}
