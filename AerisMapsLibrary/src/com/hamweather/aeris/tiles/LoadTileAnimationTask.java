package com.hamweather.aeris.tiles;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.example.android.ImageDownloader;
import com.google.android.gms.maps.model.VisibleRegion;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.util.NetworkUtils;

public class LoadTileAnimationTask extends AsyncTask<Void, Integer, Void> {

	private static final int MAX_TIMES = 8;
	private List<Bitmap> images;
	private AerisTile tile;
	private Context context;
	private int width;
	private int height;
	private VisibleRegion region;
	private LoadTileListener listener;
	private ProgressBar bar;

	public interface LoadTileListener {
		void handleImages(List<Bitmap> images);
	}

	public LoadTileAnimationTask(Context context, AerisTile tile,
			AerisMapView mapView, LoadTileListener listener) {
		this.tile = tile;
		this.context = context;
		this.region = mapView.getMap().getProjection().getVisibleRegion();
		width = mapView.getWidth();
		height = mapView.getHeight();
		this.listener = listener;
	}

	public LoadTileAnimationTask withProgress(ProgressBar bar) {
		this.bar = bar;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		if (bar != null)
			bar.setIndeterminate(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {

	}

	@Override
	protected Void doInBackground(Void... params) {
		images = new ArrayList<Bitmap>();
		JSONObject json;
		ImageDownloader downloader = new ImageDownloader();
		try {
			json = NetworkUtils.getJSON(new URL(new BuildTileTimeURL(tile)
					.build()));
			if (json != null) {
				TimeCodeResponse response = TimeCodeResponse
						.convertFromString(json.toString());
				int times = Math.min(response.files.size(), MAX_TIMES);
				if (isCancelled()) {
					return null;
				}
				// count down so we animate oldest first
				for (int i = times - 1; i >= 0; i--) {
					if (isCancelled()) {
						return null;
					}
					String url = new BuildAnimationOverlayUrl(context, tile,
							response.files.get(i).time, region, width, height)
							.build();

					Bitmap bitmap = downloader.getBitmapFromCache(url);
					if (bitmap == null) {
						bitmap = downloader.downloadBitmap(url);
						downloader.addBitmapToCache(url, bitmap);
					}
					images.add(bitmap);
					publishProgress(getProgress(times - i, times));
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		if (!isCancelled()) {
			listener.handleImages(images);
		}
		if (bar != null)
			bar.setIndeterminate(false);
	}

	private int getProgress(int count, int total) {
		int delta = 360 / total;
		return delta * count;
	}

}
