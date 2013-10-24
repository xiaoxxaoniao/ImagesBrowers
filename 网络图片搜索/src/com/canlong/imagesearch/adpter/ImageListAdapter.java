package com.canlong.imagesearch.adpter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.canlong.imagesearch.ImageBrowse;
import com.canlong.imagesearch.MainActivity;
import com.canlong.imagesearch.R;
import com.canlong.imagesearch.domain.ImageItem;
import com.canlong.imagesearch.util.SyncImageLoader;

public class ImageListAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private MainActivity mContext;
	private ListView mListView;
	SyncImageLoader syncImageLoader;
	private List<ImageItem> mModels = new ArrayList<ImageItem>();

	public ImageListAdapter(MainActivity context, ListView listView) {
		mInflater = LayoutInflater.from(context);
		syncImageLoader = new SyncImageLoader();
		mContext = context;
		mListView = listView;
		mListView.setOnScrollListener(onScrollListener);
	}

	public void addImage(String image1, String image2) {
		mModels.add(new ImageItem(image1, image2));
	}

	public void clean() {
		mModels.clear();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mModels.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= getCount()) {
			return null;
		}
		return mModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView image1 = null;
		ImageView image2 = null;
		if (convertView == null) {
			convertView = mInflater
					.inflate(R.layout.image_item_adapter, null);
			image1 = (ImageView) convertView.findViewById(R.id.image_1);
			image2 = (ImageView) convertView.findViewById(R.id.image_2);
			ViewCache cache = new ViewCache();
			cache.imageView1 = image1;
			cache.imageView2 = image2;
			convertView.setTag(R.layout.image_item_adapter,cache);
		}else{
			ViewCache cache = (ViewCache) convertView.getTag(R.layout.image_item_adapter);
			image1 = cache.imageView1;
			image2 = cache.imageView2;
//			image1 = (ImageView) convertView.findViewById(R.id.image_1);
//			image2 = (ImageView) convertView.findViewById(R.id.image_2);
		}
		ImageItem model =  mModels.get(position);
		convertView.setTag(position);
		
		
		//iv.setBackgroundResource(R.drawable.rc_item_bg);
		syncImageLoader.loadImage(position, model.getImageSrc1(),
				imageLoadListener,0);
		syncImageLoader.loadImage(position, model.getImageSrc2(),
				imageLoadListener,1);
		image1.setOnClickListener(new MyOnclick(0,position));
		image2.setOnClickListener(new MyOnclick(1,position));
		return convertView;
	}
	private final class ViewCache{
		public ImageView imageView1;
		public ImageView imageView2;
	}
	private class MyOnclick implements OnClickListener{
		int flag;
		int position;
		public MyOnclick(int flag,int position){
			this.flag = flag;
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext,ImageBrowse.class);
			intent.putExtra("flag", flag);
			intent.putExtra("position", position);
			intent.putStringArrayListExtra("ImageAddress", mContext.getListImage());
			mContext.startActivity(intent);
		}
		
	}
	
	
	SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener() {

		@SuppressWarnings("deprecation")
		@Override
		public void onImageLoad(Integer t, Drawable drawable,int flag) {
			// BookModel model = (BookModel) getItem(t);
			View view = mListView.findViewWithTag(t);
			if (view != null) {
				if(flag==0){
					ImageView iv1 = (ImageView) view.findViewById(R.id.image_1);
					//iv1.setBackgroundDrawable(drawable);
					iv1.setImageDrawable(drawable);
				}else{
					ImageView iv2 = (ImageView) view.findViewById(R.id.image_2);
					//iv2.setBackgroundDrawable(drawable);
					iv2.setImageDrawable(drawable);
				}
			}
		}

		@Override
		public void onError(Integer t,int flag) {
//			ImageItem model = (ImageItem) getItem(t);
			View view = mListView.findViewWithTag(t);
			if (view != null) {
				if(flag==0){
					ImageView iv1 = (ImageView) view.findViewById(R.id.image_1);
					iv1.setBackgroundResource(R.drawable.ic_launcher);
				}else{
					ImageView iv2 = (ImageView) view.findViewById(R.id.image_2);
					iv2.setBackgroundResource(R.drawable.ic_launcher);
				}
				
			}
		}

	};

	public void loadImage() {
		int start = mListView.getFirstVisiblePosition();
		
		int end = mListView.getLastVisiblePosition();
		if (end >= getCount()) {
			end = getCount() - 1;
		}
		syncImageLoader.setLoadLimit(start, end);
		syncImageLoader.unlock();
	}

	AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
				loadImage();
//				syncImageLoader.lock();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
				loadImage();
				break;
			case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
				loadImage();
//				syncImageLoader.lock();
				break;

			default:
				break;
			}

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub

		}
	};
}
 