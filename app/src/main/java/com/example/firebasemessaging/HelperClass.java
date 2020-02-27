package com.example.firebasemessaging;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class HelperClass {
    static ProgressDialog pDialog;
    public static final int REQUEST_PERMISSION_CODE = 1002;
    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;
    public static void displayProgressDialog(Context context, String msg) {
        pDialog = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage(msg);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }
    public static void hideProgressDialog() {
        pDialog.dismiss();
    }
    public static void makeAlert(Context context, String title, String message){
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AppCompatAlertDialogStyle);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(context.getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            builder.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkCameraPermissions(Context context) {
        int writeExternalResults = ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE");
        int checkSelfPermission = ContextCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE");
        int checkCameraPermission = ContextCompat.checkSelfPermission(context, "android.permission.CAMERA");
        return writeExternalResults == 0 && checkCameraPermission == 0 && checkSelfPermission == 0 ;
    }

    public static void startEditing(EditText inputEditText, Context context) {

        inputEditText.requestFocus();
        inputEditText.setFocusableInTouchMode(true);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(inputEditText, InputMethodManager.SHOW_FORCED);
    }
    public static void stopEditing(EditText stopEdittext, Context contxt) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                contxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(stopEdittext.getWindowToken(), 0);
    }
}
