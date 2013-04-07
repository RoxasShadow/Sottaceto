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

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import android.content.Context;

public class Memory {

	public static final String KEYVALUES = "keyvalues.bin";
	public static final String SCRIPT    = "lastscript.bin";

	@SuppressWarnings("unchecked")
	public static Hashtable<String, String> getKeyValues(Context context) {
		Hashtable<String, String> keyvalues = null;

		try {
			ObjectInputStream ois = new ObjectInputStream(context.openFileInput(KEYVALUES));
			keyvalues = (Hashtable<String, String>)ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			Utils.error(e);
		}

		return keyvalues == null ? new Hashtable<String, String>() : keyvalues;
	}

	/*@SuppressWarnings("unchecked")
	public static Stack<DialogueType> getDialogues(Context context) {
		Stack<DialogueType> dialogues = null;

		try {
			ObjectInputStream ois = new ObjectInputStream(context.openFileInput(DIALOGUES));
			dialogues = (Stack<DialogueType>)ois.readObject();
			ois.close();
		}
		catch(Exception e) {
			Utils.error(e);
		}

		return dialogues == null ? new Stack<DialogueType>() : dialogues;
	}*/

	public static String getLastScript(Context context) {
		StringBuffer buffer = new StringBuffer();

		try {			
			InputStreamReader input = new InputStreamReader(context.openFileInput(SCRIPT));
			BufferedReader reader = new BufferedReader(input);
			String line;
			while((line = reader.readLine()) != null)
				buffer.append(line);
		}
		catch(Exception e) {
			Utils.error(e);
		}

		return buffer.toString();
	}

	public static boolean putKeyValues(Context context, Hashtable<String, String> keyvalues) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(keyvalues);
			out.flush();

			FileOutputStream fos = context.openFileOutput(KEYVALUES, Context.MODE_PRIVATE);
			fos.write(bos.toByteArray());
			fos.flush();
			fos.close();
			out.close();
			bos.close();

	        return true;
		}
		catch(IOException e) {
			Utils.error(e);
			Utils.error(context, context.getString(R.string.error_saving_game));
			return false;
		}
	}

	/*public static boolean putDialogues(Context context, Stack<DialogueType> dialogues) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(dialogues);
			out.flush();

			FileOutputStream fos = context.openFileOutput(DIALOGUES, Context.MODE_PRIVATE);
			fos.write(bos.toByteArray());
			fos.flush();
			fos.close();
			out.close();
			bos.close();

	        return true;
		}
		catch(IOException e) {
			Utils.error(e);
			Utils.error(context, context.getString(R.string.error_saving_game));
			return false;
		}
	}*/

	public static boolean putLastScript(Context context, String script) {
		try {
			FileOutputStream fos = context.openFileOutput(SCRIPT, Context.MODE_PRIVATE);
			fos.write(script.getBytes());
			fos.flush();
			fos.close();

	        return true;
		}
		catch(IOException e) {
			Utils.error(e);
			Utils.error(context, context.getString(R.string.error_saving_game));
			return false;
		}
	}
}