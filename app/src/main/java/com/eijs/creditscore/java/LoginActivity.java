package com.eijs.creditscore.java;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eijs.creditscore.R;
import com.eijs.creditscore.api.RetrofitClient;
import com.eijs.creditscore.others.Global;
import com.eijs.creditscore.others.ViewDialog;
import com.eijs.creditscore.pojo.DefaultResponse;
import com.eijs.creditscore.pojo.IsUpdateReqRes;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    public EditText uid,pass,date;
    public Button login;
    DatePickerDialog datePickerDialog;
    ViewDialog progress;
    String currentversion;
    RelativeLayout rl;
    boolean allgranted = false;
    public static String web_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        getSupportActionBar().hide();
        new Global();
        setContentView(R.layout.activity_login);
        progress = new ViewDialog(this);
        checkforUpgrade();
        rl = findViewById(R.id.rl);
        uid = findViewById(R.id.uid);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        date = findViewById(R.id.wdate);
        String cdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date.setText(cdate);
        date.setFocusable(false);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(LoginActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                //date.setText(String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + year);
                                date.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dologin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestStoragePermission();
    }

    private void checkforUpgrade() {

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentversion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        progress.show();
        Call<IsUpdateReqRes> call = RetrofitClient
                .getInstance().getApi().IsUpdateRequired();
        call.enqueue(new Callback<IsUpdateReqRes>() {

            @Override
            public void onResponse(Call<IsUpdateReqRes> call, Response<IsUpdateReqRes> response) {
                IsUpdateReqRes isUpReqRes = response.body();
                progress.dismiss();
                double newverison = Double.parseDouble(isUpReqRes.getVersion());
                double verison = Double.parseDouble(currentversion);
                final String link = isUpReqRes.getLink();
                if (verison < newverison) {
                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.updatereqpopup);
                        MaterialButton button = dialog.findViewById(R.id.update);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(link));
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                startActivity(intent, bndlanimation);
                            }
                        });
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        dialog.show();
                        dialog.getWindow().setAttributes(lp);
                }
            }

            @Override
            public void onFailure(Call<IsUpdateReqRes> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(LoginActivity.this, "Due to slow internet app unable to check update !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dologin() {

            if(allgranted){

                final String lid = uid.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String dt = date.getText().toString().trim();

                if (lid.isEmpty()) {
                    uid.setError("Login ID is required");
                    uid.requestFocus();
                    return;
                }

                if (lid.length() < 5) {
                    uid.setError("Enter a valid login ID");
                    uid.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    pass.setError("Password is required");
                    pass.requestFocus();
                    return;
                }

                if(dt.isEmpty()){
                    date.setError("Login date is required");
                    date.requestFocus();
                    return;
                }

                web_url = RetrofitClient.BASE_URL+"hitJSfile.php?password="+password;
                WebViewFragment webViewFragment = new WebViewFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentcont,webViewFragment).commit();
                progress.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(Global.password == null){
                            progress.dismiss();
                            notEncryptAlert();
                        }else {
                            Call<DefaultResponse> call = RetrofitClient
                                    .getInstance().getApi().Login(lid, Global.password, date.getText().toString().trim());
                            call.enqueue(new Callback<DefaultResponse>() {

                                @Override
                                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                    DefaultResponse dResponse = response.body();

                                    progress.dismiss();

                                    if (!dResponse.isError()) {

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        Global.ecode = uid.getText().toString().trim();
                                        Global.date = date.getText().toString().trim();
                                        String[] dateslt = Global.date.split("-");
                                        Global.yr = dateslt[0];
                                        Global.mth = dateslt[1];
                                        Global.day = dateslt[2];
                                        String[] msgsplt = dResponse.getErrormsg().split("~");
                                        Global.netid = msgsplt[0];
                                        Global.hname = msgsplt[1];
                                        Global.ename = msgsplt[2];
                                        Global.ecode = msgsplt[3];
                                        Global.emplevel = msgsplt[4];
                                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                                        startActivity(intent, bndlanimation);
                                        finish();
                                    } else {
                                        Snackbar snackbar = Snackbar.make(rl, dResponse.getErrormsg(), Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DefaultResponse> call, Throwable t) {
                                    progress.dismiss();
                                    if (t instanceof IOException) {
                                        Snackbar snackbar = Snackbar.make(rl, "Internet Issue ! Failed to process your request !", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    } else {
                                        Snackbar snackbar = Snackbar.make(rl, "Data Conversion Issue ! Contact to admin", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                            });
                        }
                    }
                }, 2000);

            }else{
                requestStoragePermission();
            }

    }

    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            allgranted = true;
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
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

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    public void notEncryptAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Warning !");
        builder.setMessage("Because of slow internet connection your password encryption failed ! \nClick on LOGIN button again.");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        dialog.show();
    }
}
