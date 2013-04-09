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

import net.giovannicapuano.visualnovel.Types.Character;
import net.giovannicapuano.visualnovel.Types.Event;
import net.giovannicapuano.visualnovel.Types.GoTo;
import net.giovannicapuano.visualnovel.Types.IfStmt;
import net.giovannicapuano.visualnovel.Types.Player;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class Dialogue {

	public static void print(final Context context) {
		if(Game.dialogues.empty()) {
			Game.button1.setVisibility(View.GONE);
			Game.button2.setVisibility(View.GONE);
			Game.button3.setVisibility(View.GONE);
			Game.editText1.setVisibility(View.GONE);
			return;
		}

		DialogueType dialogue = Game.dialogues.pop();

		/* Hide choice buttons */
		if(Game.button1.getVisibility() != View.GONE) {
			Game.button1.setVisibility(View.GONE);
			Game.button2.setVisibility(View.GONE);
		}

		/* Discover what implementation of DialogueType is dialogue */
		Player player = null;
		Character character = null;
		IfStmt ifStmt = null;
		Event event = null;
		GoTo goTo = null;
		Class<?> type = null;

		if(dialogue.getClass() == Player.class) {
			player = (Player)dialogue;
			type = Player.class;
		}
		else if(dialogue.getClass() == Character.class) {
			character = (Character)dialogue;
			type = Character.class;
		}
		else if(dialogue.getClass() == IfStmt.class) {
			ifStmt = (IfStmt)dialogue;
			type = IfStmt.class;
		}
		else if(dialogue.getClass() == Event.class) {
			event = (Event)dialogue;
			type = Event.class;
		}
		else if(dialogue.getClass() == GoTo.class) {
			goTo = (GoTo)dialogue;
			type = GoTo.class;
		}

		/* Save the content of EditText when text input is required */
		if(Game.editTextName != null && Game.editText1.getVisibility() == View.VISIBLE) {
			Game.editText1.setVisibility(View.GONE);

			Game.keyvalues.put(Game.editTextName, Game.editText1.getText().toString());

			Game.editTextName = null;
		}

		if(type == Character.class) {
			if(!Game.keyvalues.isEmpty())
				Game.textView.setText(character.name + ":\n" + Utils.parseString(character.text, "#(.*?)#", Game.keyvalues));
			else
				Game.textView.setText(character.name + ":\n" + character.text);

			/* Get the characterId. If it is valid and the character is not yet shown, just show the character and start the fade animation */
			int id = context.getResources().getIdentifier("net.giovannicapuano.visualnovel:drawable/" + character.character + "_character", null, null);
			if(id > 0)
				AnimationHandler.execute(character.animation, context, Game.imageView, Game.textView, Game.button3, id);
		}
		else if(type == Player.class) {
			if(player.mode.equals("choice")) {
				/* Show buttons and fill them with choices */
				Game.button1.setVisibility(View.VISIBLE);
				Game.button2.setVisibility(View.VISIBLE);

				Game.button1.setText(player.choicesToView[0]);
				Game.button2.setText(player.choicesToView[1]);

				final Player _player = player;

				/* Save the choice relatives to the pressed button */
				Game.button1.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						Game.keyvalues.put(_player.name, _player.choices[0]);

						if(!Game.dialogues.empty())
							Dialogue.print(context);
					}
				});

				Game.button2.setOnClickListener(new OnClickListener() {
					public void onClick(View view) {
						Game.keyvalues.put(_player.name, _player.choices[1]);

						if(!Game.dialogues.empty())
							Dialogue.print(context);
					}
				});
			}
			else if(player.mode.equals("input")) {
				Game.editText1.setVisibility(View.VISIBLE);
				Game.textView.setText(player.text);
				Game.editTextName = player.name;
			}
		}
		else if(type == IfStmt.class) {
			if(!Game.keyvalues.isEmpty()) {
				if(ifStmt.evaluate(Game.keyvalues.get(ifStmt.variable))) // check if, for example, 'woot' == 'wat'
					Dialogue.print(context);
				else { // skip tag until if-related tags end
					while(true) {
						if(Game.dialogues.empty())
							break;
						DialogueType next = Game.dialogues.pop();
						if(next.getClass() == IfStmt.class) {
							if(!((IfStmt)(next)).chained) {
								Game.dialogues.pop();
								break;
							}
						}
						else
							break;
					}
					Dialogue.print(context);
				}
			}
		}
		else if(type == Event.class) {
			if(event.name == Event.EventType.SETBACKGROUNDIMAGE) {
				int id = context.getResources().getIdentifier("drawable/" + event.resource + "_background", null, context.getPackageName());
				if(id > 0)
					Game.layout.setBackgroundResource(id);
			}
			else if(event.name == Event.EventType.PLAYSOUND) {
				int id = context.getResources().getIdentifier("raw/" + event.resource + "_music", null, context.getPackageName());
				if(id > 0) {
					float leftVolume = Game.mute ? 0.0f : 0.5f;
					float rightVolume = Game.mute ? 0.0f : 0.5f;
					if(!MediaPlayerHandler.create(Game.mediaPlayers, context, id, event.resource, event.loop, leftVolume, rightVolume, true))
						Utils.error(context, context.getString(event.loop ? R.string.error_playing_music : R.string.error_playing_sound));
				}

			}
			else if(event.name == Event.EventType.STOPSOUND) {
				if(!MediaPlayerHandler.stop(Game.mediaPlayers, event.resource))
					Utils.error(context, context.getString(event.loop ? R.string.error_stopping_music : R.string.error_stopping_sound));
			}
			Dialogue.print(context);
		}
		else if(type == GoTo.class) {
			Memory.putLastScript(context, goTo.script);
			Utils.saveAll(context);

        	Intent intent = new Intent(context, Game.class);
        	intent.putExtra("mute", Game.mute);
        	intent.putExtra("script", goTo.script);
    		context.startActivity(intent);
    		((Activity)context).finish();
		}
	}
}