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

package net.giovannicapuano.visualnovel.Types;

import android.annotation.SuppressLint;
import net.giovannicapuano.visualnovel.DialogueType;

public class Event implements DialogueType {
	public enum EventType { SETBACKGROUNDIMAGE, SETBACKGROUNDMUSIC, STOPBACKGROUNDMUSIC, SETCHARACTER };

	public EventType name; // Type of the event
	public String resource; // Resource to load
	
	@SuppressLint("DefaultLocale")
	public Event(String name, String resource) {
		for(EventType e : EventType.values()) {
			if(name.toUpperCase().equals(e.toString())) {
				this.name = e;
				break;
			}
		}
		
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nResource: " + resource;
	}
}