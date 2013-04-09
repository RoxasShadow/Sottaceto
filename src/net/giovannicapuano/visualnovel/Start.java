/*
* Copyright(C) 2013 Giovanni Capuano <webmaster@giovannicapuano.net>
*
* This file is part of Sottaceto.
*
* Sottaceto is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Sottaceto is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with Sottaceto.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.giovannicapuano.visualnovel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Start extends Activity {

	public static boolean mute;
	public static MenuItem[] items;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		/* Set background, if exists */
		int id = getResources().getIdentifier("drawable/start", null, getPackageName());
		if(id > 0)
			findViewById(R.id.relativeLayout1).setBackgroundResource(id);

		/* Gathering text */
		ListView listView = (ListView)findViewById(R.id.listView1);
		String[] menu = getResources().getStringArray(R.array.start);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
		listView.setAdapter(adapter);

		/* Initialization */
		mute = false;

		/* Listener */
		listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	Intent intent;
		        switch(position) {
			        case 0: // continue
			        	String script = Memory.getLastScript(Start.this);
			        	if(script.length() > 0) {
				        	intent = new Intent(Start.this, Game.class);
				        	intent.putExtra("mute", mute);
				        	intent.putExtra("script", script);
				    		Start.this.startActivity(intent);
			        	}
			        	else
			        		Toast.makeText(Start.this, getResources().getString(R.string.error_no_save_available), Toast.LENGTH_LONG).show();
			        	break;
			        case 1: // new game
			        	intent = new Intent(Start.this, Game.class);
			        	intent.putExtra("mute", mute);
			        	intent.putExtra("script", "");
			    		Start.this.startActivity(intent);
			        	break;
			        case 2: // credits
			    		Start.this.startActivity(new Intent(Start.this, Credits.class));
						break;
			        case 3: // quit
			    		Start.this.finish();
			        	break;
		        }
	        }
	    });
	}
}
