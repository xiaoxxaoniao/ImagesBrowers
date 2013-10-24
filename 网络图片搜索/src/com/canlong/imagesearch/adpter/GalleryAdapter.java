package com.canlong.imagesearch.adpter;



import com.canlong.imagesearch.MyImageView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

public class GalleryAdapter extends BaseAdapter {
	int mGalleryItemBackground;
	private Context mContext;
	private Bitmap[] bitmap;
	private boolean bol = true;
	
	public GalleryAdapter(Context c,Bitmap[] dra){
		this.mContext = c;
		this.bitmap = dra;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return bitmap.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if(bitmap==null||bitmap[position]==null){
			return null;
		}
		MyImageView view = new MyImageView(mContext, bitmap[position].getWidth(), bitmap[position].getHeight());
		view.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		view.setImageBitmap(bitmap[position]);
		return view;
	}
}