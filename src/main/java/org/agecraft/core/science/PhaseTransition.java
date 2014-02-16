package org.agecraft.core.science;

import static org.agecraft.core.science.Phase.GAS;
import static org.agecraft.core.science.Phase.LIQUID;
import static org.agecraft.core.science.Phase.SOLID;

public enum PhaseTransition {

	MELTING("melting", SOLID, LIQUID),
	FREEZING("freezing", LIQUID, SOLID),
	VAPORIZATION("vaporization", LIQUID, GAS),
	CONDENSATION("condensation", GAS, LIQUID),
	SUBLIMATION("sublimation", SOLID, GAS),
	DEPOSITION("deposition", GAS, SOLID);
	
	public String name;
	public Phase from;
	public Phase to;
	
	PhaseTransition(String name, Phase from, Phase to) {
		this.name = name;
		this.from = from;
		this.to = to;
	}
}
