package com.example.dynamiclist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


@SuppressLint("NewApi")
public class Option extends DialogFragment {
	
	EditText update;
	String oldnote;
	public Option() {
		// TODO Auto-generated constructor stub
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	
		LayoutInflater inflater=getActivity().getLayoutInflater();
		View view=inflater.inflate(R.layout.option, null);
		
		//put node into update box
		oldnote=((MainActivity)getActivity()).pass_note();
		
		update=(EditText) view.findViewById(R.id.editText1);
		update.setText(oldnote);
		
		AlertDialog.Builder builder=new Builder(getActivity());
		builder.setMessage("change the note");
		builder.setTitle("Update or delete");
		builder.setView(view);
		
		
		builder.setNegativeButton("delete", new DialogInterface.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity)getActivity()).delete();
				
			}
		});
		builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MainActivity)getActivity()).update(update.getText().toString());
			}
		});
		
		Dialog dialog=builder.create();
     	return dialog;
}
	
public void  delete(View v) { MainActivity ma=new MainActivity(); ma.delete();}

public void  update(View v) {((MainActivity)getActivity()).update(update.getText().toString());}
	
}
