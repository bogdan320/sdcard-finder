package com.example.sdcardfinder;


import android.app.ListActivity;
import android.os.Bundle;

public class SetLanguageListActivity extends ListActivity{
	
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
	 * returns string "back" on System language.
	 */
	public String getBackString(){
		
		switch (langString){
			case 2:
				return "Назад";
			case 3:
				return "Zurück";
			case 4:
				return "Espalda";
			case 5:
				return "Назад";
			default:
				return "Back";
		}
	}
	
	/**
	 * returns string "New folder" on System language.
	 */
	public String getNewFolderString(){
		
		switch (langString){
			case 2:
				return "Новая папка";
			case 3:
				return "neuen ordner";
			case 4:
				return "nueva carpeta";
			case 5:
				return "Нова папка";
			default:
				return "New Folder";
		}
	}
	
	/**
	 * returns string "Enter name of new folder" on System language.
	 */
	public String getNewFolderNameString(){
		
		switch (langString){
			case 2:
				return "Введите имя новой папки";
			case 3:
				return "Geben Sie den neuen Ordner";
			case 4:
				return "Introduzca la nueva carpeta";
			case 5:
				return "Введіть назву нової папки";
			default:
				return "Enter name of new folder";
		}
	}
	/**
	 * returns string "select" on System language.
	 */
	public String getSelectString(){
		switch (langString){
		case 2:
			return "Выбрать";
		case 3:
			return "Wählen";
		case 4:
			return "Seleccionar";
		case 5:
			return "Вибрати";
		default:
			return "Select";
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setLangString();
	}

}
