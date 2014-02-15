package org.agecraft.science;

public class Units {

	public static double kelvinToCelsius(double temperature) {
		return temperature - 273.15;
	}
	
	public static double celsiusToKelvin(double temperature) {
		return temperature + 273.15;
	}
	
	public static double fahrenheitToCelsius(double temperature) {
		return (temperature - 32.0) * (5.0 / 9.0);
	}
	
	public static double celsiusToFahrenheit(double temperature) {
		return temperature * (9.0 / 5.0) + 32;
	}
	
	public static double fahrenheitToKelvin(double temperature) {
		return (temperature - 459.67) * (5.0 / 9.0);
	}
	
	public static double kelvinToFahrenheit(double temperature) {
		return temperature * (9.0 / 5.0) + 459.67;
	}
}
