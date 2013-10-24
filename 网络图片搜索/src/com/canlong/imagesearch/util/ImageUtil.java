package com.canlong.imagesearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Environment;

public class ImageUtil {
	public static List<String> getImageURL(String json) {
		if (json == null || "".equals(json)) {
			return null;
		}

		return null;
	}

	// 获取文件夹的字节大小
	public static long getFileSize(File f) throws Exception {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	public static String sendGETRequest(String path) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		// if (conn.getResponseCode() == 200) {
		InputStream inStream = conn.getInputStream();
		byte[] data = StreamTool.read(inStream);
		String result = new String(data);
		return result;
		// }else{
		// return null;
		// }

	}

	public static List<String> resolveImageData(String json) throws Exception {
		List<String> list = new ArrayList<String>();
		JSONObject object = new JSONObject(json);
		JSONArray data = object.getJSONArray("data");
		for (int i = 0; i < data.length() - 1; i++) {
			String objURL = data.getJSONObject(i).getString("objURL");
			list.add(objURL);
		}
		return list;
	}

	public static void deleteFile(File f) {
		if(f.exists()&&f.isDirectory()){
			File[] files = f.listFiles();
			for(File file:files){
				file.delete();
			}
		}
		
	}

	public static boolean saveImageList(List<String> imageData)throws Exception {
		for(String url : imageData){
			saveImage(url);
		}
		return true;
	}
	public static boolean saveImage(String url) throws Exception{
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/image/");
			if(!f.exists()){
				f.mkdirs();
			}
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/netimage/cache/" + MD5.getMD5(url));
			if (temp.exists()) {
				FileOutputStream out = new FileOutputStream(f.getPath()+"/"+getSystemTime()+".jpg");
				FileInputStream in = new FileInputStream(temp);
				byte[] buffer = new byte[1024];
				int len = 0;
				while((len = in.read(buffer))!=-1){
					out.write(buffer, 0, len);
				}
				out.close();
				in.close();
				return true;
			}
		}
		return false;
	}
	public static String getSystemTime(){
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		return sdf.format(date)+time%1000;
	}
}