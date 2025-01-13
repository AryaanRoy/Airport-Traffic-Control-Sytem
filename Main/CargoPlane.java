/**
 * A class that extends Airplane and implements Cargo
 * 
 * @see Airplane
 * @see Cargo
 * @author Roy Aryaan
 */
public class CargoPlane extends Airplane implements Cargo {
	/**
	 * Initializes using the primitive type double, it is the weight of the Cargo plane
	 */
    double weight;
    /**
     * A constructor that constructs the details of the flight from the class Airplane and also the weight of the plane
     * @param flightNumber The number that identifies the plane
     * @param airplaneMake The make of the plane
     * @param type The type of plane, examples: Cargo, Private, Commercial
     * @param departureTime The departure time of the flight
     * @param landingTime The landing time of the flight
     * @param flightDuration The duration of flight from or to its destination
     * @param weight The weight of the cargo plane
     */
    public CargoPlane(int flightNumber, String airplaneMake, String type, int departureTime, int landingTime, int flightDuration, boolean isLate, int lineNumber, double weight) {
        super(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber);
        this.weight = weight;
    }


    @Override
    public double getCargoWeight() {
        return this.weight;
    }

    @Override
    public void setCargoWeight(double weight) {
        weight = this.weight;
    }


    @Override
    public String displayDetails(Airplane plane) {
        return "Cargo Weight: " + this.weight + ", Flight Number: " + this.flightNumber + ", Airplane Make: " + this.airplaneMake + ", Type: " + this.type + ", Departure Time: " + this.departureTime + ", Landing Time: " + this.landingTime + ", Flight Duration: " + this.flightDuration;
        
    }


	@Override
    public int getFlightNumber() {
		return flightNumber;
	}
	
	@Override
	public String getAirplaneMake() {
		return airplaneMake;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public int getDepartureTime() {
		return departureTime;
	}
	
	@Override
	public int getLandingTime() {
		return landingTime;
	}
	
	@Override
	public int getFlightDuration() {
		return flightDuration;
	}

	@Override
	public boolean getIsLate() {
		return isLate;

	}

	@Override
	public void setIsLate(boolean late) {
		this.isLate = late;
	}

	@Override
	public int getLineNumber() {
		return lineNumber;
	}


	@Override
	public double getAdditionalInfo1() {
		return weight;
	}


	@Override
	public String getAdditionalInfo() {
		return "hi";
	}


}
