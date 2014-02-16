package org.agecraft.core.dna.structure;

public class Gene {

	public int id;
	public String name;
	public int minValue;
	public int maxValue;
	public boolean hidden;
	public boolean avarage;
	public boolean blend;
	
	public Gene(int id, String name) {
		this.id = id;
		this.name = name;
		this.minValue = 0;
		this.maxValue = 1;
		this.hidden = false;
	}
	
	public Gene(int id, String name, int minValue, int maxValue) {
		this(id, name);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}
	
	public Gene(int id, String name, int minValue, int maxValue, boolean avarage, boolean blend) {
		this(id, name, minValue, maxValue);
		this.avarage = avarage;
		this.blend = blend;
	}
	
	public Gene setHidden(boolean hidden) {
		this.hidden = hidden;
		return this;
	}
}
