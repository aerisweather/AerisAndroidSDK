package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demoaerisproject.R;

public class TwoPartView extends LinearLayout {

	private TextView titleTextView;
	private TextView valueTextView;

	public TwoPartView(Context context, AttributeSet set) {
		super(context, set);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_twopart, this, true);
		titleTextView = (TextView) this.findViewById(R.id.tvTitle);
		valueTextView = (TextView) this.findViewById(R.id.tvValue);
	}

	public void setText(String title, String value) {
		titleTextView.setText(title);
		valueTextView.setText(value);
	}
}
