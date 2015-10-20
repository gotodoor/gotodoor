package com.go2door.common;






import com.go2door.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;




public class MyProgressDialog extends Dialog {

	public static MyProgressDialog show(Context context, CharSequence title,
	        CharSequence message) {
		Log.e("status:  ","dialog class");
	    return show(context, title, message, false);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate) {
		Log.e("status:  ","dialog class");
	    return show(context, title, message, indeterminate, false, null);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate, boolean cancelable) {
		Log.e("status:  ","dialog class");
	    return show(context, title, message, indeterminate, cancelable, null);
	}

	public static MyProgressDialog show(Context context, CharSequence title,
	        CharSequence message, boolean indeterminate,
	        boolean cancelable, OnCancelListener cancelListener) {
		Log.e("status:  ","dialog class");
	    MyProgressDialog dialog = new MyProgressDialog(context);
	    dialog.setTitle(title);
	   
	    dialog.setCancelable(cancelable);
	    dialog.setOnCancelListener(cancelListener);
	    /* The next line will add the ProgressBar to the dialog. */
	    dialog.addContentView(new ProgressBar(context), new LayoutParams(60,60));

	    
	    LayoutInflater inflator = ((Activity) context).getLayoutInflater();
//		 View   itemView = inflator.inflate(R.layout.customprogressdialog, null);
//	    dialog.addContentView(itemView, new LayoutParams(70,70));
	    dialog.show();

	    return dialog;
	}

	public MyProgressDialog(Context context) {
	    super(context, R.style.NewDialog);
	}
}
