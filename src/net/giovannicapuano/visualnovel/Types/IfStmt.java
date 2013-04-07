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

public class IfStmt implements DialogueType {
	public enum Op { EQUALS, NOT_EQUALS };

	public String variable; // feel
	public String string; // good
	public Op op; // ==
	public boolean chained;

	public IfStmt(String variable, String string, String op, boolean chained) {
		this.variable = variable;
		this.string = string;
		if(op.equals("=="))
			this.op = Op.EQUALS;
		else if(op.equals("!="))
			this.op = Op.NOT_EQUALS;
		this.chained = chained;
	}

	public IfStmt(String variable, String string, Op op, boolean chained) {
		this.variable = variable;
		this.string = string;
		this.op = op;
		this.chained = chained;
	}

	public boolean evaluate(String dbString) {
		switch(op) {
			case EQUALS:
				return string.equals(dbString);
			case NOT_EQUALS:
				return !string.equals(dbString);
			default:
				return false;
		}
	}

	@Override
	public String toString() {
		return "Variable: " + variable + "\nString: " + string + "\nOperator: " + op + "\nChained: " + chained;
	}
}
