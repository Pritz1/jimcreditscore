package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.EmplistItem;
import com.eijs.creditscore.pojo.ListOfEmpRes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class Frag_scentry extends Fragment {

    View view;
    RecyclerView rv_entry;
    ViewDialog progressDialog;
    List<EmplistItem> emplist = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_scentry, container, false);
        progressDialog=new ViewDialog(getActivity());
        rv_entry = view.findViewById(R.id.rv_entry);
        rv_entry.setNestedScrollingEnabled(false);
        rv_entry.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_entry.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(getActivity()).inflate(R.layout.entry_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final EmplistItem model = emplist.get(i);
                myHolder.name.setText("NAME : " + model.getEname().toUpperCase());
                myHolder.hqname.setText("HQ NAME : " + model.getHname().toUpperCase());
                myHolder.itemView.setTag(i);
                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),EntDocListActivity.class);
                        intent.putExtra("ename", model.getEname());
                        intent.putExtra("selecode", model.getEcode());
                        intent.putExtra("selnetid", model.getNetid());
                        intent.putExtra("position", Integer.toString(i));
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return emplist.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView name,hqname;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    name = itemView.findViewById(R.id.name);
                    hqname = itemView.findViewById(R.id.hqname);
                }
            } }
        );

        getEmplist();
        return view;
    }

    private void getEmplist() {
        //data variables call
        progressDialog.show();
        Call<ListOfEmpRes> call = RetrofitClient
                .getInstance().getApi().EmpList(Global.ecode,Global.emplevel,Global.yr,Global.mth);
        call.enqueue(new Callback<ListOfEmpRes>() {
            @Override
            public void onResponse(Call<ListOfEmpRes> call, retrofit2.Response<ListOfEmpRes> response) {
                ListOfEmpRes res = response.body();
                progressDialog.dismiss();
                emplist = res.getEmplist();
                rv_entry.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ListOfEmpRes> call, Throwable t) {
                progressDialog.dismiss();
                if (t instanceof IOException) {
                    Snackbar snackbar = Snackbar.make(view, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }
}
