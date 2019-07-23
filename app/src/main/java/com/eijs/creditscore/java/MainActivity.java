package com.eijs.creditscore.java;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.eijs.creditscore.R;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    Fragment fragment = null;
    ConstraintLayout container;
    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    mTextMessage.setText(R.string.account);
                    return true;
                case R.id.navigation_score:
                    mTextMessage.setText(R.string.score);
                    return true;
                case R.id.navigation_history:
                    mTextMessage.setText(R.string.history);
                    return true;
                case R.id.navigation_logout:
                    logoutAlert();
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //mTextMessage = (TextView) findViewById(R.id.message);
        container = findViewById(R.id.container);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragment = new Frag_Account();
        loadFragment(fragment);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.navigation_account:
                        fragment = new Frag_Account();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_score:
                        fragment = new Frag_scentry();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_history:
                        fragment = new Frag_history();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_logout:
                        logoutAlert();
                        return true;
                }
                return loadFragment(fragment);
            }

        });

        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_account:
                        fragment = new Frag_Account();
                        return true;
                    case R.id.navigation_score:
                        fragment = new Frag_scentry();
                        return true;
                    case R.id.navigation_history:
                        fragment = new Frag_history();
                        return true;
                    case R.id.navigation_logout:
                        logoutAlert();
                        return true;
                }
                return false;
            }
        };
    }

    public void logoutAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("LOGOUT ?");
        builder.setMessage("Are you sure wants to Logout ?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(MainActivity.this, R.anim.trans_right_in,R.anim.trans_right_out).toBundle();
                        startActivity(intent,bndlanimation);
                        finish();
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

    private boolean loadFragment(Fragment fragment) {
        //switching fragment


        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
