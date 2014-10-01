package com.example.dynamiclist;


import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

	// Database Helper
	DatabaseHelper  db;
	int id_o;
	Button b;
	EditText e;
	static int i=0;
	private String note;
	public ArrayAdapter<String> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DatabaseHelper(getApplicationContext());
		 b=(Button) findViewById(R.id.button1);
		 e=(EditText) findViewById(R.id.editText1);
		 
		  i=db.lastid()+1;
		  
		 adapter = new ArrayAdapter<String>(this,
					R.layout.newi);
		 
		 ListView list = (ListView) findViewById(R.id.listView1);
		//setup a header to a list
			TextView title=new TextView(this);
			title.setText("items");
			
			list.addHeaderView(title);	
			list.setAdapter(adapter);
			
			
			list.setOnItemClickListener( this);
	
	// Don't forget to close database connection
		db.closeDB();
		
	}
	
    public void items(View v) {showallitems();}

	private void showallitems() {
	    
		// Getting all TODO
		List<Todo> allToDos = db.getAllToDos();
		adapter.clear();
		for (Todo todo : allToDos) {
			
			adapter.add(todo.getNote()+"\n"+todo.getCreatedAt()+"#"+todo.getId());
		}
						
	}

	public void add(View v) {
		// Creating ToDos
		String text=e.getText().toString();
				Todo todo1 = new Todo(text, i);
				i++;
				// Inserting todos in db
				
				long todo1_id = db.createToDo(todo1);	
				b.setBackgroundColor(Color.GREEN);
	    
				//add  new  item
				adapter.add(todo1.getNote() + "\n"+todo1.getCreatedAt()+"#"+todo1.getId());
		
	}
	
	

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int i, long l) {
		// TODO Auto-generated method stub
		TextView temp=(TextView) v;
		String str=(String) temp.getText();
		
		String substr_id=str.substring(str.indexOf("#"));
		String no=substr_id.substring(1);
		id_o =Integer.parseInt(no);
		
		//String substr_note=str.substring(str.indexOf("\n"));
		 note=str.substring(0,str.indexOf("\n"));
		 
		 Todo t =db.getTodo(id_o);
		 
		 note=t.getNote();
		
		Option myalert=new Option();
		myalert.show(getFragmentManager(), "My Alert");
	}
	
	public String pass_note()
	{ return note;}
	
	public void delete()
	      {db.deleteToDo(id_o);
	         //now show new list
			showallitems();}

	public void update(String string) {
	    Todo todo =new Todo();
	    todo.setId(id_o);
	    todo.setCreatedAt(todo.getDateTime());
	    todo.setNote(string);
	    
		db.updateToDo(todo);
		
		//now show new list
		showallitems();
	}
}
