package com.example.sdcardfinder;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>{
	private Context context;
	private List<String> data;
	private String partPath;
	private RelativeLayout listItem;
	
	public MyArrayAdapter(Context context, List<String> objects,String part) {
		super(context,-1, objects);
		this.context=context;
		this.data=objects;
		partPath=part;
	}
	
	@Override
	public View getView(int position, View convertView,ViewGroup parent){
		listItem=new RelativeLayout(context);
		ImageView image=new ImageView(context);
			RelativeLayout.LayoutParams imageLayout=new RelativeLayout.LayoutParams(80, 50);
			imageLayout.addRule(RelativeLayout.CENTER_VERTICAL);
			imageLayout.setMargins(5, 15, 5, 15);
			image.setLayoutParams(imageLayout);
			image.setId(145);
			listItem.addView(image);
			
		TextView text=new TextView(context);
			RelativeLayout.LayoutParams textLayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			textLayout.addRule(RelativeLayout.RIGHT_OF, image.getId());
			textLayout.addRule(RelativeLayout.CENTER_VERTICAL);
			text.setLayoutParams(textLayout);
			text.setTextSize(18);
			listItem.addView(text);
		
		if((position==0)&&(!partPath.equals((Environment.getExternalStorageDirectory().getAbsolutePath()+"/")))){
			image.setImageResource(R.drawable.back);
		}
		else{
			String currentFile=data.get(position);
			File file=new File(partPath+currentFile);
			if(file.isFile()){
				int u;
				for (u=currentFile.length()-1;u>=1;u--){
					if(currentFile.charAt(u)=='.'){break;}
				}
				String format=currentFile.substring(u+1);
				if(format.equals("mp3")){
					image.setImageResource(R.drawable.file_music);
				}else{
					if(format.equals("txt")){
						image.setImageResource(R.drawable.file_text);
					}else{
						image.setImageResource(R.drawable.file_other);
					}
				}
			}else{
				image.setImageResource(R.drawable.dir);
			}
		}
		text.setText(data.get(position));
		return listItem;
	}

}