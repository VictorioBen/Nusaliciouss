package com.workspace.adminpanels.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.workspace.adminpanels.R;

public class WaitingDialog {
    Activity activity;
    AlertDialog dialog;

    public WaitingDialog(Activity myActivity){
        activity = myActivity;
    }
    @SuppressLint("InflateParams")
    void startWaitingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.waiting_dialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    void dismissWaiting(){
        dialog.dismiss();
    }
}
