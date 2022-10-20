package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demoaerisproject.R;

public class PollutantView extends LinearLayout
{
	private TextView m_name;
	private TextView m_aqi;
	private TextView m_category;
	private TextView m_ppb;
	private TextView m_ugm3;

	public PollutantView(Context context, AttributeSet set)
	{
		super(context, set);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.listview_pollutant, this, true);
		m_name = (TextView) this.findViewById(R.id.tvPollutantName);
		m_aqi = (TextView) this.findViewById(R.id.tvPollutantValue);
		m_category = (TextView) this.findViewById(R.id.tvPollutantCategory);
		m_ppb = (TextView) this.findViewById(R.id.tvPollutantPPB_Value);
		m_ugm3 = (TextView) this.findViewById(R.id.tvPollutantUGM3Value);
	}

	public void setText(String name, String aqi, String category, String ppb, String umg3)
	{
		m_name.setText(name);
		m_aqi.setText(aqi);
		m_category.setText(category);
		m_ppb.setText(ppb);
		m_ugm3.setText(umg3);
	}
}
