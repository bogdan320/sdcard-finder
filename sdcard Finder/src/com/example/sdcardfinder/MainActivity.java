package com.example.sdcardfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends SetLanguageActivity{
	protected final static String PATH_STRING="path";
	protected final static String LOAD_TYPE="type";
	protected final static String FILE="file";
	protected final static String FOLDER="folder";
	private final static int LOAD=0;
	private TextView text;
	private Button scannerFile;
	private Button scannerFolder;
	private String pathToFile;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout linear=new LinearLayout(getApplicationContext());
    	linear.setOrientation(LinearLayout.VERTICAL);
    	
        scannerFile=new Button(getApplicationContext());
        	scannerFile.setText(this.getFindFileString()); 
        scannerFolder=new Button(getApplicationContext());
        	scannerFolder.setText(this.getFindFolderString());
        text=new TextView(getApplicationContext());
        	linear.addView(scannerFile);
        	linear.addView(scannerFolder);
        	linear.addView(text);
        setContentView(linear);

        scannerFile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startFinder(FILE);
			}
		});
        
        scannerFolder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startFinder(FOLDER);
			}
		});       
    }
    protected void startFinder(String type) {
		Intent intent=new Intent();
		intent.setClass(this, ListFilesActivity.class);
		intent.putExtra(LOAD_TYPE, type);
		startActivityForResult(intent,LOAD);
	}

	@Override
	public void onActivityResult(int requestCode,int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			pathToFile=data.getExtras().get(PATH_STRING).toString();
			text.setText(pathToFile);
		}
	}
	/**
	 * Returns the path to selected file.
	 */
	public String getPath(){
		if (pathToFile!=null){
			return pathToFile;
		}else{
			return "";
		}
	}
	
}
