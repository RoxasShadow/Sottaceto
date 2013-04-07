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

import net.giovannicapuano.visualnovel.AnimationHandler.AnimationType;
import net.giovannicapuano.visualnovel.DialogueType;

public class Character implements DialogueType {
	public String name; // Name of the character
	public String character; // Name of the drawable resource
	public String text; // Dialogue
	public AnimationType animation; // Type of animation

	public Character(String name, String character, String text, AnimationType animation) {
		this.name = name;
		this.character = character;
		this.text = text;
		this.animation = (animation == null) ? AnimationType.NOTHING : animation;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\nCharacter: " + character + "\nText: " + text + "\nAnimation: " + animation;
	}
}
