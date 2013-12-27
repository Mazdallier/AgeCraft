package elcon.mods.agecraft.science;

public class Matter {

	public Solid solid;
	public Liquid liquid;
	public Gas gas;
	
	public double meltingTemperature;
	public double boilingTemperature;
	
	public Matter(Solid solid, Liquid liquid, Gas gas, double meltingTemperature, double boilingTemperature) {
		this.solid = solid;
		this.liquid = liquid;
		this.gas = gas;
		this.meltingTemperature = meltingTemperature;
		this.boilingTemperature = boilingTemperature;
	}	
}
