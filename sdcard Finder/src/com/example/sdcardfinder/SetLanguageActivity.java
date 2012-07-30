package com.example.sdcardfinder;


import android.app.Activity;
import android.os.Bundle;

public class SetLanguageActivity extends Activity{
	
	private int langString;

	public int getLangString() {
		return langString;
	}

	private void setLangString() {
		String lng=this.getApplicationContext().getResources().getConfiguration().locale.getLanguage();
		if(lng.equals("en")){this.langString=1;return;}
		if(lng.equals("ru")){this.langString=2;return;}
		if(lng.equals("de")){this.langString=3;return;}
		if(lng.equals("es")){this.langString=4;return;}
		if(lng.equals("uk")){this.langString=5;return;}
	}
	/**
	 * returns string "Find file" on System language.
	 */
	public String getFindFileString(){
		
		switch (langString){
			case 2:
				return "Найти файл";
			case 3:
				return "Suchen Sie die Datei";
			case 4:
				return "Busque el archivo";
			case 5:
				return "Знайти файл";
			default:
				return "Find file";
		}
	}
	
	/**
	 * returns string "Find folder" on System language.
	 */
	public String getFindFolderString(){
		switch (langString){
		case 2:
			return "Найти папку";
		case 3:
			return "Suchen Sie den Ordner";
		case 4:
			return "Busque la carpeta";
		case 5:
			return "Знайти папку";
		default:
			return "Find folder";
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setLangString();
	}
}
