package com.canlong.imagesearch;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.canlong.imagesearch.adpter.GalleryAdapter;
import com.canlong.imagesearch.domain.MyGallery;
import com.canlong.imagesearch.util.ImageUtil;
import com.canlong.imagesearch.util.SyncImageLoader;

public class ImageBrowse extends Activity implements OnTouchListener {

	private ImageView My_view;
	private int position;
	private ArrayList<String> ImageList;
	private static final String TAG = "ImageBrowse";
	private int CheckedPosition;
	public static int screenWidth;
	public static int screenHeight;
	private MyGallery gallery;
	private boolean isScale = false; 
	private float beforeLenght = 0.0f; 
	private float afterLenght = 0.0f;
	private float currentScale = 1.0f;
	private Bitmap[] map;
	private void init() {
		Intent intent = getIntent();
		int flag = intent.getIntExtra("flag", 0);
		ImageList = intent.getStringArrayListExtra("ImageAddress");
		position = intent.getIntExtra("position", 0);
		CheckedPosition = position * 2 + flag + 1;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_image_browse);
		gallery = (MyGallery) findViewById(R.id.mygallery);
		gallery.setVerticalFadingEdgeEnabled(false);
		init();
		gallery.setHorizontalFadingEdgeEnabled(false);
		try { 
			map = SyncImageLoader.loadSingleImage(ImageList);
			gallery.setAdapter(new GalleryAdapter(this, map));
			if(CheckedPosition>map.length){
				CheckedPosition = map.length;
			}
			gallery.setSelection(CheckedPosition - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		screenWidth = getWindow().getWindowManager().getDefaultDisplay()
				.getWidth();
		screenHeight = getWindow().getWindowManager().getDefaultDisplay()
				.getHeight();
	}
	private class GalleryChangeListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentScale = 1.0f;
			isScale = false;
			beforeLenght = 0.0f;
			afterLenght = 0.0f;
		}
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	public boolean onTouch(View v, MotionEvent event) {
		Log.i("lyc", "touched---------------");
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			beforeLenght = spacing(event);
			if (beforeLenght > 5f) {
				isScale = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isScale) {
				afterLenght = spacing(event);
				if (afterLenght < 5f)
					break;
				float gapLenght = afterLenght - beforeLenght;
				if (gapLenght == 0) {
					break;
				} else if (Math.abs(gapLenght) > 5f) {
					float scaleRate = gapLenght / 854;
					Animation myAnimation_Scale = new ScaleAnimation(
							currentScale, currentScale + scaleRate,
							currentScale, currentScale + scaleRate,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					myAnimation_Scale.setDuration(100);
					myAnimation_Scale.setFillAfter(true);
					myAnimation_Scale.setFillEnabled(true);
					currentScale = currentScale + scaleRate;
					gallery.getSelectedView().setLayoutParams(
							new Gallery.LayoutParams(
									(int) (480 * (currentScale)),
									(int) (854 * (currentScale))));
					beforeLenght = afterLenght;
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			isScale = false;
			break;
		}
		return false;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(1,1,1,"保存当前图片");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    //响应每个菜单项(通过菜单项的ID)
		case 1:
			if(ImageList!=null&&ImageList.size()>0){
				try {
					
					int index = gallery.getSelectedItemPosition();
					ImageUtil.saveImage(ImageList.get(index));
					Toast.makeText(this, "图片保存在/netimage/image目录", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(this, "图片保存失败", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}else{
				Toast.makeText(this, "没有图片可以保存", Toast.LENGTH_LONG).show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void finish() {
		for(Bitmap bm : map){
			if(bm!=null&&!bm.isRecycled()){
				bm.recycle();
			}
		}
		super.finish();
	}
}
