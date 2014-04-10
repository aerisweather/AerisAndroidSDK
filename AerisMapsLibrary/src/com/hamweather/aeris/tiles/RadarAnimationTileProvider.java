package com.hamweather.aeris.tiles;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

public class RadarAnimationTileProvider implements TileProvider{
	
	Tile[][] bs; 
	private Bitmap originalBitmap; 
	private int xStart; 
	private int yStart; 
	private int yDiff; 
	private int xDiff; 
	
	public RadarAnimationTileProvider(Bitmap bitmap, int xDiff,int yDiff, int xStart,int yStart){
		this.originalBitmap = bitmap;
		this.yDiff =yDiff +1; 
		this.xDiff = xDiff + 1;
		bs= new Tile[this.yDiff][this.xDiff ];
		divideImages(originalBitmap);
		this.yStart = yStart; 
		this.xStart = xStart; 
	}

	@Override
	public Tile getTile(int x, int y, int z) {
		return bs[(y - yStart)][(x - xStart)];
		
	}
	

	private void divideImages(Bitmap b) {
		// TODO Auto-generated method stub
		final int width = b.getWidth();
		final int height =b.getHeight();

		final int pixelByCol  = width / xDiff;
		final int pixelByRow = height / yDiff;
		//List<Bitmap> bs = new ArrayList<Bitmap>();
		for(int i=0;i<yDiff;i++){
		    System.out.println("row no. "+i);
		    for(int j=0;j<xDiff;j++){

		        System.out.println("Column no."+j);
		        int startx=pixelByCol*j;
		        int starty=pixelByRow*i;
		        Bitmap b1=Bitmap.createBitmap(b,startx,starty,pixelByCol,pixelByRow );
		        ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
				b1.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] bytes = stream.toByteArray();
				Tile tile = new Tile(256, 256, bytes);
		        bs[i][j] = tile; 
		        b1=null;
		    }

		}
	}
}
