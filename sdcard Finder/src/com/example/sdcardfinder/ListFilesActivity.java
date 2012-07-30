package com.example.sdcardfinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListFilesActivity extends SetLanguageListActivity implements DialogInterface.OnClickListener{
	private MyArrayAdapter adapter;
	private List<String> pathStrings=null;
	private String partPath=null;
	private MediaPlayer mPlayer;
	private String pathRoot;
	private String loadType;
	private ListView lv;
	private static final int NEW_FOLDER=3;
	private EditText dialogNewFolderName;
	
	@Override
	public void onStart(){
		super.onStart();
		loadType=getIntent().getExtras().getString(MainActivity.LOAD_TYPE);
		if(loadType.equals(MainActivity.FOLDER)){
			createFoldersFinder();
		}
		try{
			pathRoot=Environment.getExternalStorageDirectory().getAbsolutePath();
			fillNewFolder(pathRoot);
		}catch(RuntimeException e){
			Toast.makeText(getApplicationContext(), "There is no access to sdcard", Toast.LENGTH_SHORT).show();
			this.setResult(RESULT_CANCELED);
			finish();
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		this.getListView().setLongClickable(true);
		this.getListView().setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,	int position, long id) {
				try {
					if(loadType.equals(MainActivity.FOLDER)){return false;}
					String musPath=partPath+(String)getListAdapter().getItem(position);
					File f=new File(musPath);
					if(f.isFile()){
						int u;
						for (u=musPath.length()-1;u>=1;u--){
							if(musPath.charAt(u)=='.'){break;}
						}
						String format=musPath.substring(u+1);
						if(format.equals("mp3")){
							closePlayer();
							mPlayer=new MediaPlayer();
							mPlayer.setDataSource(getApplicationContext(),Uri.parse(musPath));
							mPlayer.prepare();
							mPlayer.start();
						}
					}
				}catch (IllegalArgumentException e) {
					e.printStackTrace();
				}catch (IllegalStateException e) {
					e.printStackTrace();
				}catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
		});
	}

	@Override
	public void onStop(){
		super.onStop();
		closePlayer();
	}
	
	private void closePlayer(){
		if(mPlayer!=null){
			mPlayer.stop();
			mPlayer.release();
			mPlayer=null;
		}
	}
	
	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
		String currentItem;
		if(loadType.equals(MainActivity.FILE)){
			currentItem=(String)getListAdapter().getItem(position);
		}else{
			currentItem=(String)lv.getAdapter().getItem(position);
		}
		String str=partPath+currentItem;
		if((position==0)&&(!partPath.equals((Environment.getExternalStorageDirectory().getAbsolutePath()+"/")))){
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
				if(loadType.equals(MainActivity.FILE)){
					Intent resIntent=new Intent();
					resIntent.putExtra(MainActivity.PATH_STRING,str);
					resIntent.setClass(this, MainActivity.class);
					setResult(RESULT_OK, resIntent);
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "Please, select a folder.", Toast.LENGTH_SHORT).show();
				}
			}else{
				fillNewFolder(str);
			};
		}
	}
	
	private void fillNewFolder(String str) {
		this.setTitle(str);
		pathStrings= new ArrayList<String>();
		File file = new File (str);
		
		pathStrings.add(this.getBackString());

		File[] listFile=file.listFiles();
		int y;
		for(File f:listFile){
			String p=String.valueOf(f);
			for (y=p.length()-1;y>=1;y--){
				if(p.charAt(y)=='/'){
					break;
				}
			}
			partPath=String.valueOf(f).substring(0, y+1);

			if((f.isFile())&&(loadType.equals(MainActivity.FOLDER))){continue;}  //если мы ищем папку, то файлы не показывать
			if(p.charAt(y+1)=='.'){	continue;}  //не отображаем скрытые папки
			pathStrings.add(String.valueOf(f).substring(y+1));
			partPath=String.valueOf(f).substring(0, y+1);
		}
		if(listFile.length==0){partPath=str;}
		
		if(partPath.equals(pathRoot+"/")){
			pathStrings.remove(0);
		}
		
		adapter=new MyArrayAdapter(this,pathStrings,partPath);
		if(loadType.equals(MainActivity.FILE)){
			setListAdapter(adapter);
		}else
		{
			lv.setAdapter(adapter);
		}
		pathStrings=null;
		adapter=null;
	}
	
	private void createFoldersFinder() {
		RelativeLayout mainContainer=new RelativeLayout(this);
			RelativeLayout buttonContainer=new RelativeLayout(this);
			RelativeLayout.LayoutParams lay=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			lay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			buttonContainer.setLayoutParams(lay);
			buttonContainer.setId(43);
			Button selectBut=new Button(this);	
				selectBut.setText(this.getSelectString());
				selectBut.setWidth(150);
				buttonContainer.addView(selectBut);
				selectBut.setId(44);
				
			Button newFolderBut=new Button(this);	
				newFolderBut.setText(this.getNewFolderString());
				newFolderBut.setWidth(150);
				RelativeLayout.LayoutParams openButParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
				openButParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				newFolderBut.setLayoutParams(openButParams);
				buttonContainer.addView(newFolderBut);
				
			RelativeLayout.LayoutParams listParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
			RelativeLayout listViewContainer=new RelativeLayout(this);
				listViewContainer.setLayoutParams(listParams);
				listParams.addRule(RelativeLayout.ABOVE,buttonContainer.getId());
				lv=new ListView(this);
				lv.setId(android.R.id.list);
				listViewContainer.addView(lv);
		
		mainContainer.addView(listViewContainer);
		mainContainer.addView(buttonContainer);
				
		selectBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent resIntent=new Intent();
				resIntent.putExtra(MainActivity.PATH_STRING,partPath);
				resIntent.setClass(getApplicationContext(), MainActivity.class);
				setResult(RESULT_OK, resIntent);
				finish();
			}
		});
		
		newFolderBut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(NEW_FOLDER);
			}
		});
		
		this.setContentView(mainContainer);
	}
	
	@Override
	public Dialog onCreateDialog(int id){
		AlertDialog.Builder builder=null;
		switch (id){
			case NEW_FOLDER:
				builder=new AlertDialog.Builder(this);
				LinearLayout dialogLayout=new LinearLayout(getApplicationContext());
				dialogLayout.setOrientation(LinearLayout.VERTICAL);
				TextView text=new TextView(getApplicationContext());
				text.setText(this.getNewFolderNameString());
				dialogNewFolderName=new EditText(getApplicationContext());
				dialogLayout.addView(text);
				dialogLayout.addView(dialogNewFolderName);
				builder.setView(dialogLayout);
				builder.setPositiveButton("Ok",this);
				builder.setNegativeButton("Cancel", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.cancel();
					}
				});
		}
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		String name=dialogNewFolderName.getText().toString();
		if((new File(partPath+"/"+name)).exists()){
			Toast.makeText(getApplicationContext(), "Такая папка уже существует.", Toast.LENGTH_SHORT).show();
		}else{
			File file=new File(partPath+"/"+name);
			file.mkdir();
			this.fillNewFolder(partPath+"/"+name);
		}
	}
	
}
