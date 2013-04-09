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

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Stack;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends Activity {

	public static Stack<DialogueType> dialogues; // stack with all the dialogues. pop() to get the next

	public static Hashtable<String, String> keyvalues; // hashtable with all the choices/inputs taken through the script
	public static Hashtable<String, MediaPlayer> mediaPlayers; // hashtable which contains all musics and sounds with their MediaPlayer intances

	public static RelativeLayout layout; // activity layout (for background)
	public static TextView textView; // contains dialogues text
	public static ImageView imageView; // character
	public static Button button1, button2; // buttons for choices
	public static Button button3; // button to go next
	public static EditText editText1; // to get inputs
	public static String editTextName; // input of editText1
	public static MenuItem[] items; // items of android menu

	public static boolean can; // support variable for IfStmt
	public static boolean mute; // set the muting for music player
	public static String script; // script to play

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapter);

		/* Initialization */
		layout = (RelativeLayout)findViewById(R.id.relativeLayout1);
		textView = (TextView)findViewById(R.id.textView);
		imageView = (ImageView)findViewById(R.id.imageView);
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		button3 = (Button)findViewById(R.id.button3);
		editText1 = (EditText)findViewById(R.id.editText1);

		editTextName = null;
		can = true;
		mediaPlayers = new Hashtable<String, MediaPlayer>();

		Intent intent = getIntent();
		mute = intent.getBooleanExtra("mute", true);
		script = intent.getStringExtra("script");
		if(script == null || script.equals(""))
			script = "script";

		/* Fill keyvalues hashtable */
		keyvalues = Memory.getKeyValues(this);

		try {
			InputStream stream = getResources().openRawResource(getResources().getIdentifier("raw/" + script, null, this.getPackageName()));
			dialogues = Parser.parseXML(stream);
			stream.close();
		}
		catch(Exception e) {
			Utils.error(this, e);
			finish();
			startActivity(new Intent(this, Start.class));
		}

		/* First dialogue */
		if(dialogues != null && !dialogues.empty())
			Dialogue.print(Game.this);
		else {
			Utils.error(this, getString(R.string.error_reading_script));
			finish();
			startActivity(new Intent(this, Start.class));
		}

		/* Next dialogues pressing the button */
		button3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		    	/* Buttons handle their behavior */
		    	if(button1.getVisibility() == View.VISIBLE || button2.getVisibility() == View.VISIBLE)
		    		return;

		    	/* To avoid to go next without filling EditText */
		    	if(editText1.getVisibility() == View.VISIBLE && editText1.getText().length() == 0)
		    		return;

		    	if(!dialogues.empty())
		    		Dialogue.print(Game.this);
		    }
		});
	}

	@Override
	public void onStop() {
		Utils.saveAll(this);

		super.onStop();
	}

	@Override
	public void onDestroy() {
		Utils.saveAll(this);

		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
	    items = new MenuItem[] { menu.getItem(1), menu.getItem(2) };
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.home:
				finish();
				startActivity(new Intent(this, Start.class));
	            return true;
	        case R.id.mute:
	        	MediaPlayerHandler.setVolume(Game.mediaPlayers, 0.0f, 0.0f);
	    		Game.mute = true;
	    		items[0].setVisible(false); // mute
	    		items[1].setVisible(true); // unmute
	            return true;
	        case R.id.unmute:
	        	MediaPlayerHandler.setVolume(Game.mediaPlayers, 0.5f, 0.5f);
	    		Game.mute = false;
	    		items[0].setVisible(true); // mute
	    		items[1].setVisible(false); // unmute
	            return true;
            default:
            	return true;
	     }
	}

}
