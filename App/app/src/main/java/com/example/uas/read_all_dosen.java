package com.example.uas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.uas.adapter.adapter;
import com.example.uas.model.dataDosenModel;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class read_all_dosen extends AppCompatActivity {
    private final  static  String STATUS = "Data Dosen";
    public ArrayList<dataDosenModel> dataModel;
    public RecyclerView recyclerView;
    public RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_all_dosen);

        getSupportActionBar().setTitle("Data Dosen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recy);
        recyclerView.setHasFixedSize(true);
        AndroidNetworking.initialize(getApplicationContext());

        laodData();

        layoutManager = new LinearLayoutManager(read_all_dosen.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new adapter(this,dataModel);
        recyclerView.setAdapter(adapter);


    }

    private void laodData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("loading...");
        progressDialog.show();

        AndroidNetworking.post("http://192.168.1.25/rest/tampil.php")
                .addBodyParameter("action", "tampil_dosen")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        try {
                            JsonArray jsonArray = response.getJSONArray("DataDosen");
                            for (int i = 0; i < ((JSONArray) jsonArray).length(); i ++){
                                JSONObject data = jsonArray.getAsJsonObject(i);
                                dataDosenModel item = new dataDosenModel(
                                        data.getString("nid"),
                                        data.getString("nama"),
                                        data.getString("telepon")
                                ):

                                dataModel.add(item);
                                progressDialog.dismiss();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(getApplicationContext(), "Kesalahan Kode" + error, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                });
    }
}