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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.media.MediaPlayer;

public class MediaPlayerHandler {

	@SuppressWarnings("rawtypes")
	public static boolean stop(Hashtable<String, MediaPlayer> mediaPlayers) {
		boolean done = false;

	    Iterator<Entry<String, MediaPlayer>> it = mediaPlayers.entrySet().iterator();
	    MediaPlayer mediaPlayer;

	    while(it.hasNext()) {
	    	mediaPlayer = (MediaPlayer)(((Map.Entry)it.next()).getValue());
			if(mediaPlayer != null) {
				done = true;
				mediaPlayer.reset();
			}
	    }

	    return done;
	}

	public static boolean stop(Hashtable<String, MediaPlayer> mediaPlayers, String resource) {
		MediaPlayer mediaPlayer = mediaPlayers.get(resource);

		if(mediaPlayer == null) {
			Utils.print(mediaPlayers);
			return false;
		}

		mediaPlayer.reset();

		return !mediaPlayer.isPlaying();
	}


	@SuppressWarnings("rawtypes")
	public static void setVolume(Hashtable<String, MediaPlayer> mediaPlayers, float leftVolume, float rightVolume) {
	    Iterator<Entry<String, MediaPlayer>> it = mediaPlayers.entrySet().iterator();
	    MediaPlayer mediaPlayer;

	    while(it.hasNext()) {
	    	mediaPlayer = (MediaPlayer)(((Map.Entry)it.next()).getValue());
			if(mediaPlayer != null)
				mediaPlayer.setVolume(leftVolume, rightVolume);
	    }
	}

	public static boolean create(Hashtable<String, MediaPlayer> mediaPlayers, Context context, int id, String resource, boolean loop, float leftVolume, float rightVolume, boolean start) {
		MediaPlayer mediaPlayer = MediaPlayer.create(context, id);
		if(mediaPlayer != null) {
			mediaPlayer.setLooping(loop);
			mediaPlayer.setVolume(leftVolume, rightVolume);
			if(start)
				mediaPlayer.start();
			mediaPlayers.put(resource, mediaPlayer);
			return true;
		}

		return false;
	}

}
