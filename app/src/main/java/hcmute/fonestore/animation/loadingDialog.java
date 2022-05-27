package hcmute.fonestore.animation;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import hcmute.fonestore.R;

public class loadingDialog {

    Activity activity;
    AlertDialog dialog;

    public loadingDialog(Activity myActivity) {
        activity = myActivity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_layout, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
