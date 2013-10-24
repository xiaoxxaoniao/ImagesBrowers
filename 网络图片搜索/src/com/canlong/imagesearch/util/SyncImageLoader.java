package com.canlong.imagesearch.util;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;

public class SyncImageLoader {

	private Object lock = new Object();

	private boolean mAllowLoad = true;

	private boolean firstLoad = true;

	private int mStartLoadLimit = 0;

	private int mStopLoadLimit = 0;
	
	public static Bitmap[] bitmap = new Bitmap[0];
	

	final Handler handler = new Handler();

	private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap<String, SoftReference<Drawable>>();

	public interface OnImageLoadListener {
		public void onImageLoad(Integer t, Drawable drawable,int flag);

		public void onError(Integer t,int flag);


	}

	public void setLoadLimit(int startLoadLimit, int stopLoadLimit) {
		if (startLoadLimit > stopLoadLimit) {
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}

	public void restore() {
		mAllowLoad = true;
		firstLoad = true;
	}

	public void lock() {
		mAllowLoad = false;
		firstLoad = false;
	}

	public void unlock() {
		mAllowLoad = true;
		synchronized (lock) {
			lock.notifyAll();
		}
	}

	public void loadImage(Integer t, String imageUrl,
			OnImageLoadListener listener, int flag) {
		final OnImageLoadListener mListener = listener;
		final String mImageUrl = imageUrl;
		final Integer mt = t;
		final int f = flag;
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!mAllowLoad) {
					synchronized (lock) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				if (mAllowLoad && firstLoad) {
					loadImage(mImageUrl, mt, mListener,f);
				}

				if (mAllowLoad && mt <= mStopLoadLimit && mt >= mStartLoadLimit) {
					loadImage(mImageUrl, mt, mListener,f);
				}
			}

		}).start();
	}

	private void loadImage(final String mImageUrl, final Integer mt,
			final OnImageLoadListener mListener,final int flag) {

		if (imageCache.containsKey(mImageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(mImageUrl);
			final Drawable d = softReference.get();
			if (d != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (mAllowLoad) {
							mListener.onImageLoad(mt, d,flag);
						}
					}
				});
				return;
			}
		}
		try {
			final Drawable d = loadImageFromUrl(mImageUrl);
			if (d != null) {
				imageCache.put(mImageUrl, new SoftReference<Drawable>(d));
			}
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (mAllowLoad) {
						mListener.onImageLoad(mt, d,flag);
					}
				}
			});
		} catch (IOException e) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					mListener.onError(mt,flag);
				}
			});
			e.printStackTrace();
		}
	}

	public static Drawable loadLagerImageFromUrl(String url) throws IOException {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/" + MD5.getMD5(url));
			if (f.exists()) {
				FileInputStream fis = new FileInputStream(f);
				Drawable d = Drawable.createFromStream(fis, "src");
				return d;
			}
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			DataInputStream in = new DataInputStream(i);
			FileOutputStream out = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			in.close();
			out.close();
			//Drawable d = Drawable.createFromStream(i, "src");
			return loadImageFromUrl(url);
		} else {
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
			i.close();
			return d;
		}

	}
	
	
	
	
	
	public static Drawable loadImageFromUrl(String url) throws IOException {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/" + MD5.getMD5(url));
			if (f.exists()) {
				//FileInputStream fis = new FileInputStream(f);
				//Drawable d = Drawable.createFromStream(fis, "src");
				Bitmap bitmap = decodeSampledBitmapFromResource(f.getPath(), 0, 150, 150);
				return new BitmapDrawable(bitmap);
			}
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			DataInputStream in = new DataInputStream(i);
			FileOutputStream out = new FileOutputStream(f);
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			in.close();
			out.close();
			//Drawable d = Drawable.createFromStream(i, "src");
			return loadImageFromUrl(url);
		} else {
			URL m = new URL(url);
			InputStream i = (InputStream) m.getContent();
			Drawable d = Drawable.createFromStream(i, "src");
			i.close();
			return d;
		}
	}
	public static Bitmap[] loadSingleImage(ArrayList<String> list) throws Exception{
		//Bitmap[] dra = new Bitmap[list.size()];
		List<Bitmap> maps = new ArrayList<Bitmap>();
		for(int i=0;i<list.size();){
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/" + MD5.getMD5(list.get(i)));
			if (f.exists()) {
				//Bitmap bitmap = decodeSampledBitmapFromResource(f.getPath(), 0, 490, 700);
				Bitmap bitmap = BitmapFactory.decodeFile(f.getPath());
//				dra[i]= bitmap;
				maps.add(bitmap);
				i++;
			}else{
				list.remove(i);
			}
			/*}else{
				URL m = new URL(list.get(i));
				InputStream is = (InputStream) m.getContent();
				DataInputStream in = new DataInputStream(is);
				FileOutputStream out = new FileOutputStream(f);
				byte[] buffer = new byte[1024];
				int byteread = 0;
				while ((byteread = in.read(buffer)) != -1) {
					out.write(buffer, 0, byteread);
				}
				in.close();
				out.close();
				dra[i]= BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
			}*/
		}
		Bitmap[] dra = new Bitmap[maps.size()];
		for(int i=0;i<maps.size();i++){
			dra[i] = maps.get(i);
		}
		return 	dra;
			
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String res, int resId,
	        int reqWidth, int reqHeight) {

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
//	    BitmapFactory.decodeResource(res, resId, options);
	    BitmapFactory.decodeFile(res,options);
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
//	    return BitmapFactory.decodeResource(res, resId, options);
	    return BitmapFactory.decodeFile(res,options);
	}
	public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {
        if (width > height) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        } else {
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }
    }
    return inSampleSize;
}
}
