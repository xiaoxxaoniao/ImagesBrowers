package com.canlong.imagesearch.domain;

public class ImageItem {
	private String imageSrc1;
	private String imageSrc2;
	public ImageItem(){}
	public ImageItem(String imageSrc1, String imageSrc2) {
		this.imageSrc1 = imageSrc1;
		this.imageSrc2 = imageSrc2;
	}
	public String getImageSrc1() {
		return imageSrc1;
	}
	public void setImageSrc1(String imageSrc1) {
		this.imageSrc1 = imageSrc1;
	}
	public String getImageSrc2() {
		return imageSrc2;
	}
	public void setImageSrc2(String imageSrc2) {
		this.imageSrc2 = imageSrc2;
	}
	
}
