package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.eijs.creditscore.R;

public class SplashActivity extends AppCompatActivity {

    private long splashtime = 900, ms = 0;
    private boolean splashactive = true, pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);


        if(!isOnline()){
            successDilogue();
        }else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    goToNextScreen();
                }
            }, 900);

        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void goToNextScreen() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SplashActivity.this, R.anim.trans_left_in,R.anim.trans_left_out).toBundle();
        startActivity(intent,bndlanimation);
        finish();
    }

    public void successDilogue() {
        final Dialog dialog = new Dialog(SplashActivity.this);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.no_internet_dialog);
        Button button = dialog.findViewById(R.id.btnsucces);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                recreate();
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
