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

public class GoTo implements DialogueType {
	public String script;
	
	public GoTo(String script) {		
		this.script = script;
	}

	@Override
	public String toString() {
		return "Go to script: " + script;
	}
}