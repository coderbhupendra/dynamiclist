package com.example.dynamiclist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Todo {

	int id;
	String note;
	String created_at;

	// constructors
	public Todo() {
	}

	public Todo(String note, int id) {
		this.note = note;
		this.id=id;
		this.created_at=getDateTime();
	}

	

	// setters
	public void setId(int id) {
		this.id = id;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

	// getters
	public int getId() {
		return this.id;
	}

	public String getNote() {
		return this.note;
	}
	public  String getCreatedAt(){
		return this.created_at;
		
	}
	
}
