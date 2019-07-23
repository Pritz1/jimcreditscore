package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.DefaultResponse;
import com.eijs.creditscore.pojo.DetHistOfDocRes;
import com.eijs.creditscore.pojo.DoEntDocListItem;
import com.eijs.creditscore.pojo.EntDocListRes;
import com.eijs.creditscore.pojo.FullHistoryItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedHistory extends AppCompatActivity {

    ViewDialog progressDialoge;
    ConstraintLayout sv ;
    RecyclerView histlist;
    ImageView norecord;
    public List<FullHistoryItem> hislst = new ArrayList<>();
    String selecode,seldrname,selcntcd,selnetid,selposition;
    TextView ttlrec,drname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        seldrname = getIntent().getStringExtra("drname");
        selecode = getIntent().getStringExtra("selecode");
        selcntcd = getIntent().getStringExtra("selcntcd");
        selnetid = getIntent().getStringExtra("selnetid");
        selposition = getIntent().getStringExtra("position");
        progressDialoge=new ViewDialog(DetailedHistory.this);
        sv = findViewById(R.id.sv);
        histlist = findViewById(R.id.hislst);
        norecord = findViewById(R.id.norecord);
        ttlrec = findViewById(R.id.ttlrec);
        drname = findViewById(R.id.docname);
        drname.setText("DOCTOR NAME : "+seldrname.toUpperCase());
        setHisLstAdapter();
        callApi();
    }

    public void setHisLstAdapter(){
        histlist.setNestedScrollingEnabled(true);
        histlist.setLayoutManager(new LinearLayoutManager(DetailedHistory.this));
        histlist.setAdapter(new RecyclerView.Adapter() {

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(DetailedHistory.this).inflate(R.layout.detail_history_adapter, viewGroup,false);
                Holder holder=new Holder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                final Holder myHolder= (Holder) viewHolder;
                final FullHistoryItem model = hislst.get(i);
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                String inputDateStr=model.getEntdate();
                Date date = null;
                try {
                    date = inputFormat.parse(inputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputDateStr = outputFormat.format(date);
                myHolder.edate.setText("Date : "+outputDateStr);
                myHolder.ds.setText(model.getDailyscore());
                myHolder.ms.setText(model.getMthscore());
                myHolder.editentry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call fetch api
                        dialogSubmit(DetailedHistory.this,model.getDailyscore(),model.getMthscore(),model.getEcode(),Global.ecode,model.getCntcd(),model.getNetid(),model.getMth(),model.getYr(),model.getEntdate(),i);
                    }
                });
                myHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailedHistory.this);
                        builder.setCancelable(true);
                        builder.setTitle("DELETE ?");
                        builder.setMessage("Are you sure wants to delete ?");
                        builder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        performDelete(model.getEcode(),Global.ecode,model.getCntcd(),model.getNetid(),model.getMth(),model.getYr(),model.getEntdate(),i);
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        });

                        final AlertDialog dialog = builder.create();
                        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface arg0) {
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
                            }
                        });
                        dialog.show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return hislst.size();
            }
            class Holder extends RecyclerView.ViewHolder {
                TextView edate,ds,ms;
                ImageButton editentry,delete;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    edate = itemView.findViewById(R.id.date);
                    ds = itemView.findViewById(R.id.dailyscore);
                    ms = itemView.findViewById(R.id.monthlyscore);
                    editentry = itemView.findViewById(R.id.editentry);
                    delete = itemView.findViewById(R.id.delete);
                }
            } }
        );
    }

    private void callApi() {
        progressDialoge.show();

        retrofit2.Call<DetHistOfDocRes> call1 = RetrofitClient
                .getInstance().getApi().getFullHistOfDoc(selecode, selnetid, selcntcd, Global.date,Global.yr,Global.mth);
        call1.enqueue(new Callback<DetHistOfDocRes>() {
            @Override
            public void onResponse(retrofit2.Call<DetHistOfDocRes> call1, Response<DetHistOfDocRes> response) {
                DetHistOfDocRes res = response.body();
                progressDialoge.dismiss();
                hislst.clear();
                hislst = res.getFullHistory();
                ttlrec.setText("SHOWING LATEST "+hislst.size()+" RECORDS");
                if(hislst.size()>0) {
                    norecord.setVisibility(View.GONE);
                    histlist.setVisibility(View.VISIBLE);
                    histlist.getAdapter().notifyDataSetChanged();
                }else{
                    histlist.setVisibility(View.GONE);
                    norecord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DetHistOfDocRes> call1, Throwable t) {
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

    private void performDelete(final String ecode, final String logecode, final String cntcd, final String netid, final String mth, final String yr, final String wrkdate, final int position) {
        progressDialoge.show();
        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().deleteEntry(ecode,logecode,cntcd,netid,mth,yr,wrkdate);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                if(!res.isError()){
                    Toast.makeText(DetailedHistory.this, res.getErrormsg(),Toast.LENGTH_LONG).show();
                    recreate();
                }else{
                    Toast.makeText(DetailedHistory.this, res.getErrormsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to process ! Check internet connection !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Re-try", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                performDelete(ecode,logecode,cntcd,netid,mth,yr,wrkdate,position);
                            }
                        });
                snackbar.show();
            }
        });
    }

    public void dialogSubmit(final Context context, final String DS, final String MS, final String ecode, final String logecode, final String cntcd, final String netid, final String mth, final String yr, final String wrkdate, final int position) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.score_entry_popup);
        CardView buttonNo = dialog.findViewById(R.id.no);
        CardView buttonYes = dialog.findViewById(R.id.yes);
        final EditText ds = dialog.findViewById(R.id.score);
        if(DS != null && !DS.equalsIgnoreCase("") && !DS.equalsIgnoreCase("0")){
            ds.setText(DS);
        }
        final EditText ms = dialog.findViewById(R.id.mthscore);
        if(MS != null && !MS.equalsIgnoreCase("") && !MS.equalsIgnoreCase("0")){
            ms.setText(MS);
        }
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f1 = true,f2= true;
                if(ds.getText().toString().isEmpty()){
                    ds.setError("Enter a daily score");
                    ds.requestFocus();
                    f1 = false;
                }
                if(ms.getText().toString().isEmpty()){
                    ms.setError("Enter a monthly score");
                    ms.requestFocus();
                    f2 = false;
                }
                if(f1 && f2) {
                    //todo call submit api
                    if(ds.getText().toString().equalsIgnoreCase(DS) && ms.getText().toString().equalsIgnoreCase(MS)){
                        Toast.makeText(context,"Values not changed !",Toast.LENGTH_LONG).show();
                    }else{
                        performAddUpdate(ds.getText().toString(),ms.getText().toString(),ecode,logecode,cntcd,netid,mth,yr,wrkdate,position);
                    }
                    dialog.dismiss();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void performAddUpdate(final String ds, final String ms, final String ecode, final String logecode, final String cntcd, final String netid, final String mth, final String yr, final String wrkdate, final int position) {

        progressDialoge.show();
        retrofit2.Call<DefaultResponse> call1 = RetrofitClient
                .getInstance().getApi().addScore(ecode,logecode,ds,ms,cntcd,netid,mth,yr,wrkdate);
        call1.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(retrofit2.Call<DefaultResponse> call1, Response<DefaultResponse> response) {
                DefaultResponse res = response.body();
                progressDialoge.dismiss();
                if(!res.isError()){
                    Toast.makeText(DetailedHistory.this, res.getErrormsg(),Toast.LENGTH_LONG).show();
                    recreate();
                }else{
                    Toast.makeText(DetailedHistory.this, res.getErrormsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call1, Throwable t) {
                progressDialoge.dismiss();
                Snackbar snackbar = Snackbar.make(sv, "Failed to process ! Check internet connection !", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Re-try", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                performAddUpdate(ds,ms,ecode,logecode,cntcd,netid,mth,yr,wrkdate,position);
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
        DetailedHistory.this.overridePendingTransition(R.anim.trans_right_in,R.anim.trans_right_out);
    }
}
