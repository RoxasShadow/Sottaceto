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

import net.giovannicapuano.visualnovel.DialogueType;
import net.giovannicapuano.visualnovel.Utils;

public class Player implements DialogueType {	
	public String name; // Name of the variable
	public String mode; // [ input, choice ] if is the player
	public String[] choicesToView; // Choices to see
	public String[] choices; // Choices to store
	public String text; // For text inputs
	
	public Player(String name, String mode, String[] choices, String[] choicesToView) {
		this.name = name;
		this.mode = mode;
		this.choices = choices;
		this.choicesToView = choicesToView;
		this.text = "";
	}
	
	public Player(String name, String mode, String text) {
		this.name = name;
		this.mode = mode;
		this.text = text;
	}

	@Override
	public String toString() {
		if(mode.equals("choice"))
			return "Name: " + name + "\nMode: " + mode + "\nText: " + text + "\nChoices: [ " + Utils.join(choices, ", ") + " ]\nChoices to view: [ " + Utils.join(choicesToView, ", ") + " ]";
		else
			return "Name: " + name + "\nMode: " + mode;
	}
}