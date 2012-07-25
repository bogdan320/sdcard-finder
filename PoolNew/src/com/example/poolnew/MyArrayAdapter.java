package com.example.poolnew;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>{
	private Context context;
	private List<String> data;
	private String partPath;
	public MyArrayAdapter(Context context, int resource,int textViewResourceId, List<String> objects,String part) {
		super(context, resource, textViewResourceId, objects);
		this.context=context;
		this.data=objects;
		partPath=part;
	}
	
	@Override
	public View getView(int position, View convertView,ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list, parent, false);
		ImageView image=(ImageView)rowView.findViewById(R.id.imageView1);
		TextView text=(TextView)rowView.findViewById(R.id.textView1);
		if(data.get(position)=="Back"){
			image.setImageResource(R.drawable.back);
		}
		else{
			File file=new File(partPath+data.get(position));
			if(file.isFile()){
				image.setImageResource(R.drawable.file);
			}else{
				image.setImageResource(R.drawable.dir);
			}
		}
		text.setText(data.get(position));
		return rowView;
	}

}
