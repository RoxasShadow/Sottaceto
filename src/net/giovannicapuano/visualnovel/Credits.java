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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Credits extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		
		/* Set background, if exists */
		int id = getResources().getIdentifier("drawable/credits", null, getPackageName());
		if(id > 0)
			findViewById(R.id.relativeLayout1).setBackgroundResource(id);
		
		/* Vertical scrolling */
		TranslateAnimation animation = new TranslateAnimation(0f, 0f, 20f, -20f);
		animation.setDuration(4000);
		animation.setStartOffset(100); 
		animation.setRepeatMode(Animation.RESTART);

		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAnimation(animation);
		
		/* Gathering text */
		String[] credits = getResources().getStringArray(R.array.credits);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, credits);
		listView.setAdapter(adapter);
		
		/* Listener */
		listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myIntent = new Intent(Credits.this, Start.class);
				Credits.this.startActivity(myIntent);
				Credits.this.finish();
	        }
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
	    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.home:
				Intent myIntent = new Intent(Credits.this, Start.class);
				Credits.this.startActivity(myIntent);
				Credits.this.finish();
	            return true;
            default:
            	return true;
	     }
	}

}
