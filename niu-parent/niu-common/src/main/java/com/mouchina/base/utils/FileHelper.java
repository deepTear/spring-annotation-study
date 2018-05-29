package com.mouchina.base.utils;

public enum FileHelper {

	OPEN_SCREEN_PIC_URL("/image/screenMobile/screenFirst.png","开屏图片地址"),
	
	FILE_SEPARATE("/","文件路径分隔符"),
	FILE_DOT(".","点分隔符"),
	FILE_COMMA(",","逗号分隔符"),
	FILE_ROOT("/moumou/resource","文件存放根路径"),
	PIC_PHOTO("/image","用户图片路径"),
	VIDEO_PATH("/video","用户视频路径"),
	EXCEL_URL("/excel","excel路径");
	
	private String marker;
	
	private String display;

	private FileHelper(String marker, String display) {
		this.marker = marker;
		this.display = display;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
}
