package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.DoEntDocListItem;
import com.eijs.creditscore.pojo.EntDocListRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDocList extends AppCompatActivity {

    ViewDialog progressDialoge;
    ConstraintLayout sv ;
    RecyclerView doctorslist;
    ImageView norecord;
    public List<DoEntDocListItem> drlst = new ArrayList<>();
    String eanme,selecode,selnetid,selposition;
    TextView ttldoc,empname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_doc_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        eanme = getIntent().getStringExtra("ename");
        selecode = getIntent().getStringExtra("selecode");
        selnetid = getIntent().getStringExtra("selnetid");
        selposition = getIntent().getStringExtra("position");
        progressDialoge=new ViewDialog(HistoryDocList.this);
        sv = findViewById(R.id.sv);
        doctorslist = findViewById(R.id.doclist);
        norecord = findViewById(R.id.norecord);
        ttldoc = findViewById(R.id.ttldoc);
        empname = findViewById(R.id.empname);
        empname.setText("PSR NAME : "+eanme.toUpperCase());
        setDocLstAdapter();
        callApi();
    }

    public void setDocLstAdapter(){
        doctorslist.setNestedScrollingEnabled(true);
        doctorslist.setLayoutManager(new LinearLayoutManager(HistoryDocList.this));
        doctorslist.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(HistoryDocList.this).inflate(R.layout.hist_doc_list_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final DoEntDocListItem model = drlst.get(i);
                myHolder.drname.setText(model.getDrcd()+" - "+model.getDrname().toUpperCase());
                myHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HistoryDocList.this,DetailedHistory.class);
                        intent.putExtra("drname", model.getDrname());
                        intent.putExtra("selcntcd", model.getCntcd());
                        intent.putExtra("selecode", selecode);
                        intent.putExtra("selnetid", model.getNetid());
                        intent.putExtra("position", Integer.toString(i));
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HistoryDocList.this, R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                    }
                });
                myHolder.itemView.setTag(i);
                myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HistoryDocList.this,DetailedHistory.class);
                        intent.putExtra("drname", model.getDrname());
                        intent.putExtra("selcntcd", model.getCntcd());
                        intent.putExtra("selecode", selecode);
                        intent.putExtra("selnetid", model.getNetid());
                        intent.putExtra("position", Integer.toString(i));
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HistoryDocList.this, R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
                        startActivity(intent,bndlanimation);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return drlst.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView drname;
                ImageButton view;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    drname = itemView.findViewById(R.id.drname);
                    view = itemView.findViewById(R.id.view);
                }
            } }
        );
    }

    private void callApi() {
        progressDialoge.show();

        retrofit2.Call<EntDocListRes> call1 = RetrofitClient
                .getInstance().getApi().hisDocList(selecode,selnetid,Global.date,Global.yr,Global.mth);
        call1.enqueue(new Callback<EntDocListRes>() {
            @Override
            public void onResponse(retrofit2.Call<EntDocListRes> call1, Response<EntDocListRes> response) {
                EntDocListRes res = response.body();
                progressDialoge.dismiss();
                drlst = res.getDoEntDocList();
                ttldoc.setText("DOCTORS COUNT : "+drlst.size());
                if(drlst.size()>0) {
                    norecord.setVisibility(View.GONE);
                    doctorslist.setVisibility(View.VISIBLE);
                    doctorslist.getAdapter().notifyDataSetChanged();
                }else{
                    doctorslist.setVisibility(View.GONE);
                    norecord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<EntDocListRes> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to process ! Check internet connection !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Re-try", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                callApi();
                            }
                        });
                snackbar.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        } return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        HistoryDocList.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
