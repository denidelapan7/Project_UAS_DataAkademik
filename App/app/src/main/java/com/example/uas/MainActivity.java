package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import static android.app.ProgressDialog.show;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private EditText nid, nama, telepon;
    String txnNid;
    String txtNama;
    String txtTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nid = findViewById(R.id.nid);
        nama = findViewById(R.id.nama);
        telepon = findViewById(R.id.telepon);
    }

    public void SimpanData(View view) {
    }
    private void AksiSimpan(){
        txnNid = nid.getText().toString();
        txtNama = nama.getText().toString();
        txtTelepon = telepon.getText().toString();

        if (txnNid.equals("") || txtNama.equals("") || txtTelepon.equals(""))
        {
            Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_LONG).show();
        }else
        {
            AndroidNetworking.post("http://192.168.1.25/rest/add.php")
                    .addBodyParameter("nid", "txnNid")
                    .addBodyParameter("nama", "txtNama")
                    .addBodyParameter("telepon", "txtTelepon")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        private int text;

                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                if (response.getString("status").equals("data berhasil disimpan"))  {
                                    Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Data gagal disimpan", Toast.LENGTH_LONG).show();
                                }
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), "Data gagal disimpan" + e, Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Toast.makeText(getApplicationContext(), "Data gagal disimpan" + error, Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    public void tampilData(View view) {
        Intent intent = new Intent(MainActivity.this, read_all_dosen.class);
        startActivity(intent);
    }
}