package com.swkim.dungeon;

public class Model {

	String tag;
	int x;
	int y;
	int width;
	int heigth;
	String parseText;
	public Model(String tag,int x,int y,int width,int height,String parseText) {
		this.tag = tag;
		this.x= x;
		this.y = y;
		this.width = width;
		this.heigth = height;
		this.parseText = parseText;
		
	}
	

	public String getParseText() {
		return parseText;
	}
	public String getTag() {
		return tag;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeigth() {
		return heigth;
	}

}
