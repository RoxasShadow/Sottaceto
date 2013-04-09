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
import java.util.Stack;

import javax.xml.parsers.DocumentBuilderFactory;

import net.giovannicapuano.visualnovel.AnimationHandler.AnimationType;
import net.giovannicapuano.visualnovel.Types.Character;
import net.giovannicapuano.visualnovel.Types.Event;
import net.giovannicapuano.visualnovel.Types.GoTo;
import net.giovannicapuano.visualnovel.Types.IfStmt;
import net.giovannicapuano.visualnovel.Types.Player;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;

public class Parser {

	/* Parse the XML in order to get a Stack containing DialogueType elements which identify each tag/dialogue */
	@SuppressLint("DefaultLocale")
	public static Stack<DialogueType> parseXML(InputStream stream) {
		Stack<DialogueType> dialogues = new Stack<DialogueType>();

		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream, null);
			NodeList items = doc.getElementsByTagName("dialogue");

			for(int i = items.getLength(); i >= 0; --i) {
				Element e = (Element)items.item(i);
				if(e == null)
					continue;

				if(e.hasAttribute("if")) {
					// example: parsing #feel# == 'good'
					boolean chained = e.getAttribute("chained").equals("true");
					String[] token = e.getAttribute("if").split("#");
					String name = token[1]; // feel

					token = token[2].split("'");
					String op = token[0].replaceAll("\\s", ""); // ==
					String string = token[1].replaceAll("'", ""); // good

					dialogues.push(new IfStmt(name, string, op, chained));
				}
				else if(e.hasAttribute("event")) {
					String event = e.getAttribute("event");
					String resource = e.getAttribute("resource");
					String loop = e.getAttribute("loop");

					Event evnt = loop == "" ? new Event(event, resource) : new Event(event, resource, loop.equals("true"));
					dialogues.push(evnt);
				}
				else if(e.hasAttribute("goTo")) {
					String script = e.getAttribute("goTo");

					dialogues.push(new GoTo(script));
				}
				else {
					String name = e.getAttribute("name");
					String text = e.getFirstChild().getNodeValue();
					String character = e.getAttribute("character");
					String mode = e.getAttribute("mode");
					String animation = e.getAttribute("animation");

					if(character.equals("")) { // player
						if(mode.equals("choice") && e.hasAttribute("choices")) {
							String[] choices = e.getAttribute("choices").split("(\\s+)?\\|(\\s+)?");
							String[] choicesToView = text.split("\\s+\\|\\s+");

							dialogues.push(new Player(name, mode, choices, choicesToView));
						}
						else
							dialogues.push(new Player(name, mode, text));
					}
					else { // character
						AnimationType animationType = animation.equals("") ? AnimationType.NOTHING : AnimationType.valueOf(animation.toUpperCase());

						dialogues.push(new Character(name, character, text, animationType));
					}
				}
			}
		}
		catch(Exception e) {}

		return dialogues;
	}
}
