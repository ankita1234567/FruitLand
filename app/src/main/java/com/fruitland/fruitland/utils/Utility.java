package com.fruitland.fruitland.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {


    private static ProgressDialog mProgressDialog;
    private static Dialog mDialog;

    public static void showToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

   /* public static void showCustomAlert(Context _context) {

        // Create layout inflator object to inflate toast.xml file
        LayoutInflater inflater = LayoutInflater.from(_context);
        // Call toast.xml file for toast layout
        View toastRoot = inflater.inflate(R.layout.toast, null);
        Toast toast = new Toast(_context);
        // Set layout to toast
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();

    }
*/
    public static boolean isNetworkAvailable(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static boolean emailCheck(String email) {
        try {
            Pattern pattern = Pattern.compile("^([a-z0-9\\+_\\-]+)(\\.[a-z0-9\\+_\\-]+)*@([a-z0-9\\-]+\\.)+[a-z]{2,6}$");
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception ex) {
            return false;
        }
    }
    public static boolean passwordCheck(String password) {
        try {
            if (password.length()>=6 && password.length()<=12)
                return true;
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    public static boolean confirmpasswordCheck(String confirmpassword,String password) {
        try {
            if (confirmpassword.equals(password))
                return true;
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
    public static void showSimpleProgressDialog(Context context) {
        showSimpleProgressDialog(context, null, "Loading...", false);
    }
    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void ChangeNavTopColor(Activity con) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = con.getWindow();
            try {
                //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //	window.setStatusBarColor(con.getResources().getColor(R.color.colorPrimary));
            } catch (Exception e) {
                // upgrade your SDK and ADT :D
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static boolean is_blank(EditText edt_temp){
        if(edt_temp!=null){
            if(edt_temp.getText().toString().trim().length()==0)
                return true;
            return false;
        }
        return false;
    }
    public static void display_error(EditText edt_temp,String error){
        if(error!=null && edt_temp!=null){
            edt_temp.setError(error);
            edt_temp.requestFocus();
        }
    }
    public static void keyboard_down_outside_touch(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }

            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                keyboard_down_outside_touch(innerView,activity);
            }
        }
    }

}
