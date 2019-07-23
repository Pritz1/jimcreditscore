package com.eijs.creditscore.java;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.eijs.creditscore.others.Global;

public class WebAppInterface {

    private Context context;

    public WebAppInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void showToast(String message){
        //Toast.makeText(context,message,Toast.LENGTH_LONG).show();
        Global.password = message;
    }
}
