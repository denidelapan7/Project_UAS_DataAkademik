package com.example.uas.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.uas.R;
import com.example.uas.model.dataDosenModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.Holder> {
    private ArrayList<dataDosenModel> dataModel;
    private Activity activity;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ProgressDialog progressDialog;
    public adapter(Activity activity, ArrayList<dataDosenModel> dataModel)
        this.dataModel = dataModel;
        this.activity = activity;
    @NonNull
    @Override
    public adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterdata,parent,false);
        Holder holder= new Holder(v);
        return holder
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.Holder holder, int position) {
        dataDosenModel model=dataModel.get(position);
        holder.nama.setText(model.getNama());
        holder .nid.setText(model.getNid());

        viewBinderHelper.bind(holder.swipeRevealLayout, model.nid);
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(activity)
                        .setTitle("Konfirmasi Hapus")
                        .setMessage("Data akan dihapus, lanjutkan ?")
                        .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.hapusData();
                                dataModel.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());

                            }
                        })
                        .setPositiveButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        holder.model = model;
    }

    @Override
    public int getItemCount() {

        return dataModel.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeRevealLayout;
        private LinearLayout layoutDelete;
        TextView nid, nama;
        dataDosenModel model;
        public  Holder(@NonNull View v) {
            super(v);
            nid = v.findViewById(R.id.nid);
            nama = v.findViewById(R.id.nama);

            swipeRevealLayout = v.findViewById(R.id.swipereveallayout);
            layoutDelete = v.findViewById(R.id.layoutDelete);
        }
        private  void hapusData(){
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("loading...");
            progressDialog.show();
            AndroidNetworking.post("http://192.168.1.25/rest/hapus.php")
                    .addBodyParameter("nid", model.getNid())
                    .addBodyParameter("action", "hapusDosen")

                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        private int text;

                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            try {
                                if (response.getString("status").equals("berhasil"))  {
                                    Toast.makeText(activity(), "Data berhasil dihapus", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }else{
                                    Toast.makeText(activity(), "Data gagal dihapus", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }catch (JSONException e){
                                Toast.makeText(activity(), "Data gagal dihapus" + e, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            Toast.makeText(activity(), "Data gagal dihapus" + error, Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
}
