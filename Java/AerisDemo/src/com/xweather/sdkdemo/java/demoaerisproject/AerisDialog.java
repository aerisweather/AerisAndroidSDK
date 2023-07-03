package com.xweather.sdkdemo.java.demoaerisproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class AerisDialog extends DialogFragment {

	public interface AerisDialogOKListener {
		public void onOKPressed(int type, Object object);
	}

	private String message;
	private String title;
	private AerisDialogOKListener listener;
	private Object data;
	private int type = 0;

	public AerisDialog withTitle(String title) {
		this.title = title;
		return this;
	}

	public AerisDialog withMessage(String message) {
		this.message = message;
		return this;
	}

	public AerisDialog withListener(AerisDialogOKListener listener) {
		this.listener = listener;
		return this;
	}

	public AerisDialog withObject(Object object) {
		data = object;
		return this;
	}

	public AerisDialog withType(int type) {
		this.type = type;
		return this;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		if (title != null)
			builder.setTitle(title);
		if (message != null)
			builder.setMessage(message);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				if (listener != null) {
					listener.onOKPressed(type, data);
				}
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		// Create the AlertDialog object and return it
		return builder.create();
	}
}