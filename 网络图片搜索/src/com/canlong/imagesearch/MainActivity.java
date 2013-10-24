
package com.canlong.imagesearch;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.canlong.imagesearch.adpter.ImageListAdapter;
import com.canlong.imagesearch.util.ImageUtil;

public class MainActivity extends Activity {
	private int page;
	private EditText keyWord;
	private ListView listView;
	private ImageListAdapter adapter;
	private List<String> imageData ;
	private MyHandler myHandler;
	private String word;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iniData();	
	}
	private void iniData() {
		keyWord = (EditText) this.findViewById(R.id.key_word);
		listView = (ListView) this.findViewById(R.id.image_list);
		adapter = new ImageListAdapter(this, listView);
		listView.setAdapter(adapter);
		myHandler = new MyHandler();
		
		page = 1;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/");
			if(!f.exists()){
				f.mkdirs();
			}
		}
	}

	public void searchImage(View v){
		word = keyWord.getText().toString();
		if(word==null||"".equals(word.trim())){
			Toast.makeText(this, "请输入搜索的图片关键字", Toast.LENGTH_LONG).show();
			return;
		}
		try {
			String path = "http://image.baidu.com/i?";
			String param = "tn=baiduimagejson&ie=utf-8&ic=0&rn=20&pn="+page+"&word=" + URLEncoder.encode(word,"UTF-8");
			String json = ImageUtil.sendGETRequest(path+param);
			String str = new String (json.getBytes("GBK"),"UTF-8");
			imageData = ImageUtil.resolveImageData(json);
			loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void lastPage(View v){
		if(word==null||word.trim().equals("")){
			Toast.makeText(this, "请输入搜索的图片关键字", Toast.LENGTH_LONG).show();
			return;
		}
		if(page<=20){
			Toast.makeText(this, "已经是第一页了", Toast.LENGTH_LONG).show();
			return;
		}
		page = page - 20;
		try {
			String path = "http://image.baidu.com/i?";
			String param = "tn=baiduimagejson&ie=utf-8&ic=0&rn=20&pn="+page+"&word=" + URLEncoder.encode(word,"UTF-8");
			String json = ImageUtil.sendGETRequest(path+param);
			String str = new String (json.getBytes("GBK"),"UTF-8");
			imageData = ImageUtil.resolveImageData(json);
			loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setSelection(0);
	}
	public void nextPage(View v){
		if(word==null||word.trim().equals("")){
			Toast.makeText(this, "请输入搜索的图片关键字", Toast.LENGTH_LONG).show();
			return;
		}
		page = page + 20;
		try {
			String path = "http://image.baidu.com/i?";
			String param = "tn=baiduimagejson&ie=utf-8&ic=0&rn=20&pn="+page+"&word=" + URLEncoder.encode(word,"UTF-8");
			String json = ImageUtil.sendGETRequest(path+param);
			String str = new String (json.getBytes("GBK"),"UTF-8");
			imageData = ImageUtil.resolveImageData(json);
			loadData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setSelection(0);
	}
	/*
	 * 获取Image地址组
	 */
	public ArrayList<String> getListImage(){
		return (ArrayList)this.imageData;
	}
	
	public void loadData(){
		if(adapter!=null){
			adapter.clean();
		}
		for(int i=0;i<imageData.size();i+=2){
			if(i+1<imageData.size()){
				adapter.addImage(imageData.get(i), imageData.get(i+1));
			}else{
				adapter.addImage(imageData.get(i), "");
			}
		}
		Message msg = new Message();
		myHandler.sendMessage(msg);
	}
	@Override
	public void finish() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/");
			if(f.exists()){
				try {
					long size = ImageUtil.getFileSize(f);
					if(size>30*1024*1024){
						ImageUtil.deleteFile(f);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		super.finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(1,1,1,"保存所有图片");
		menu.add(1, 2, 1, "退出");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	    //响应每个菜单项(通过菜单项的ID)
		case 1:
			if(imageData!=null&&imageData.size()>0){
				try {
					ImageUtil.saveImageList(imageData);
					Toast.makeText(this, "图片保存在/netimage/image目录", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(this, "图片保存失败", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}else{
				Toast.makeText(this, "没有图片可以保存", Toast.LENGTH_LONG).show();
			}
			break;
		case 2:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			adapter.notifyDataSetChanged();
			super.handleMessage(msg);
		}
	}
}
