package com.example.poolnew;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements SoundPool.OnLoadCompleteListener{
	final static int LOAD_MUSIC=1;
	final public static String PATH="path";
	Button single,multi,scanner;
	SoundPool sPool=null;
	float currentVolume;
	int first,second,third;
	MediaScannerConnection scan;
	String pip_path="sdcard/sounds/pip.mp3";
	String kukushka_path="sdcard/sounds/Kukushka.mp3";
	String chajki_path="sdcard/sounds/chajki.mp3";
	AudioManager audioMgr=null;
	
	List<File> dir = new ArrayList<File>();
	List<File> files = new ArrayList<File>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanner=(Button)findViewById(R.id.scan);
        
        sPool=new SoundPool(3,AudioManager.STREAM_MUSIC,0);
        sPool.setOnLoadCompleteListener(this);  
        
        audioMgr=(AudioManager)this.getSystemService(this.AUDIO_SERVICE);
        currentVolume=(float)audioMgr.getStreamVolume(audioMgr.STREAM_MUSIC);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("/"+Environment.getExternalStorageDirectory())));
		
        scanner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startSoundView();

			}
		});
    }
    protected void startSoundView() {
		Intent intent=new Intent();
		intent.setClass(this, ListFiles.class);
		startActivityForResult(intent,LOAD_MUSIC);
	}

	@Override
	public void onLoadComplete(SoundPool soundPool,final int sampleId, int status) {
		Thread thrd=new Thread(){
			@Override
			public void run(){
				sPool.play(sampleId, currentVolume, currentVolume, 1, 0, 1.0f);
			}
		};
		thrd.start();
	}
	
	@Override
	public void onActivityResult(int requestCode,int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			String p;
			p=data.getExtras().get(PATH).toString();
			sPool.load(p, 1);
		}
	}
	
	
}
