package com.hamweather.aeris.maps;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ProgressBar;

import com.hamweather.aeris.communication.AerisProgressListener;
import com.hamweather.aeris.tiles.AerisTile;
import com.hamweather.aeris.tiles.LoadTileAnimationTask;
import com.hamweather.aeris.tiles.LoadTileAnimationTask.LoadTileListener;

@SuppressLint("NewApi")
public class AnimationController implements LoadTileListener {

	static final float MAX_RATE = 1000;
	static final float MIN_RATE = 100;
	private static AnimationController inst = null;
	private List<Bitmap> images;
	private AerisMapView mapView;
	private AnimationDrawable animation;
	private LoadTileAnimationTask imageLoaderTask;
	private AerisMapOptions options;
	private ProgressBar progressBar;
	private AerisProgressListener listener;

	public static AnimationController getInstance(AerisMapView mapView,
			ProgressBar progressBar, AerisProgressListener listener) {
		synchronized (AnimationController.class) {
			if (inst == null) {
				inst = new AnimationController();
			}
			inst.mapView = mapView;
			inst.options = AerisMapOptions.getPreference(mapView.getContext());
			inst.progressBar = progressBar;
			inst.listener = listener;
			return inst;
		}

	}

	public void updateOptions(AerisMapOptions options) {
		this.options = options;
	}

	/**
	 * Loads animations for the radar.
	 * 
	 * @param context
	 *            Context needed to perform communications
	 * @param mapView
	 *            AerisMapView to provide data for calculations
	 */
	public void loadAnimations() {
		AerisTile tile = mapView.getTile();
		if (tile == null || tile == AerisTile.NONE) {
			tile = AerisTile.RADAR;
		}
		if (imageLoaderTask != null) {
			imageLoaderTask.cancel(true);
		}

		imageLoaderTask = new LoadTileAnimationTask(mapView.getContext(), tile,
				mapView, this);
		imageLoaderTask.withProgress(progressBar);
		if (listener != null)
			listener.showProgress();
		imageLoaderTask.execute();

	}

	/**
	 * Hides the animation
	 */
	public void hideAnimation() {
		mapView.getAnimationView().setVisibility(View.GONE);
		// mapView.setTiles(mapView.getTile(), options.getOpacity());
		mapView.setTileOverlayVisible(true);
	}

	/**
	 * Cancel the timer
	 */
	public void stopTimer() {
		if (animation != null)
			animation.stop();
		mapView.getAnimationView().setBackgroundDrawable(null);
		if (imageLoaderTask != null)
			imageLoaderTask.cancel(true);
		if (images != null) {
			for (Bitmap bitmap : images) {
				bitmap.recycle();
			}
		}
		hideAnimation();
	}

	@Override
	public void handleImages(List<Bitmap> images) {
		if (images.size() > 0) {
			this.images = images;
			mapView.getAnimationView().setVisibility(View.VISIBLE);
			int rate = (int) (MIN_RATE + ((MAX_RATE - MIN_RATE) * ((100 - options
					.getAnimationSpeed()) / 100)));
			AnimationDrawable animation = new AnimationDrawable();
			animation.setOneShot(false);
			for (int i = 0; i < images.size(); i++) {
				if (i == images.size() - 1) {
					rate += 500;
				}
				animation.addFrame(
						new BitmapDrawable(mapView.getAnimationView()
								.getContext().getResources(), images.get(i)),
						rate);
			}
			mapView.getAnimationView().setAlpha(
					(float) options.getOpacity() / 255);
			mapView.getAnimationView().setBackgroundDrawable(animation);
			mapView.setTileOverlayVisible(false);// hide the tile overlay for
													// now
			animation.start();
		} else {
			// Error retrieving animations

		}
		if (listener != null)
			listener.hideProgress();
	}

}