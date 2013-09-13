package elcon.mods.agecraft.core.weather;

public class Seasons {

	public enum Season {
		SPRING(0.25F),
		SUMMER(0.5F),
		AUTUMN(-0.25F),
		WINTER(-0.5F);
		
		public float temperature;
		
		Season(float temperature) {
			this.temperature = temperature;
		}
	}
}
