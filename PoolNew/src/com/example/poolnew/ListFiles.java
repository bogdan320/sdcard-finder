package com.example.poolnew;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

public class ListFiles extends ListActivity{
	private MyArrayAdapter adapter;
	private List<String> pathStrings=null;
	private String partPath=null;
	String pathRoot;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		pathRoot=Environment.getExternalStorageDirectory().getAbsolutePath();
		fillNewFolder(pathRoot);
	}

	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
		String str=partPath+(String)getListAdapter().getItem(position);
		if((String)getListAdapter().getItem(position)=="Back"){
			int y;
			for (y=partPath.length()-2;y>=1;y--){
				if(str.charAt(y)=='/'){
					break;
				}
			}
			String backPath=String.valueOf(partPath).substring(0, y+1);
			fillNewFolder(backPath);
		}else{
			File file=new File(str);
			if(file.isFile()){
				Intent resIntent=new Intent();
				resIntent.putExtra(MainActivity.PATH, str);
				resIntent.setClass(this, MainActivity.class);
				this.setResult(RESULT_OK, resIntent);
				finish();
			}else{
				fillNewFolder(str);
			};
		}
	}
	private void fillNewFolder(String str) {
		pathStrings= new ArrayList<String>();
		File file = new File (str);
		int y;
		if(partPath!=null){
			pathStrings.add("Back");
		}
		File[] listFile=file.listFiles();
		for(File f:listFile){
			String p=String.valueOf(f);
			for (y=p.length()-1;y>=1;y--){
				if(p.charAt(y)=='/'){
					break;
				}
			}
			pathStrings.add(String.valueOf(f).substring(y+1));
			partPath=String.valueOf(f).substring(0, y+1);
		}
		if(listFile.length==0){partPath=str;}
		
		if(partPath.equals(pathRoot+"/")){
			pathStrings.remove(0);
		}
		
		adapter=new MyArrayAdapter(this,R.layout.list,R.id.textView1,pathStrings,partPath);
		setListAdapter(adapter);
		pathStrings=null;
		adapter=null;
	}
}
