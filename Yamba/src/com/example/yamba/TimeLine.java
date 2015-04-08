package com.example.yamba;

public class TimeLine {
	
	public int id;
	public String createAt;
	public String source;
	public String username;
	public String text;
	public TimeLine(int id, String c, String s, String u, String t){
		
		this.id = id;
		this.createAt = c;
		this.source = s;
		this.username = u;
		this.text = t;
	}
}
