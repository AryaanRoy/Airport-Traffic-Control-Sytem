/**
 * A class that extends Airplane and implemnts Commercial
 * 
 * @see Airplane
 * @see Commercial
 * @author Roy Aryaan
 * 
 */
public class CommercialPlane extends Airplane implements Commercial {
	
	/**
	 * Initializes of primitive type integer, which will be used to store the number of passengers
	 */
    double numberOfPassengers;
    
    /**
     * A constructor that constructs the details of the flight from the class Airplane and also the number of passengers
     * @param flightNumber The number that identifies the plane
     * @param airplaneMake The make of the plane
     * @param type The type of plane, examples: Cargo, Private, Commercial
     * @param departureTime The departure time of the flight
     * @param landingTime The landing time of the flight
     * @param flightDuration The duration of flight from or to its destination
     * @param numberOfPassengers The number of passengers in the plane
     */
    public CommercialPlane(int flightNumber, String airplaneMake, String type, int departureTime, int landingTime, int flightDuration, boolean isLate, int lineNumber, double numberOfPassengers) {
        super(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber);
        this.numberOfPassengers = numberOfPassengers;
    }

   
    @Override
    public double getNumberOfPassengers() {
        return numberOfPassengers;
    }
    
   
    @Override
    public void setNumberOfPassengers(double numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
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
    public String displayDetails(Airplane plane) {
        return "Number of passengers: " + this.numberOfPassengers + ", Flight Number: " + this.flightNumber
         + ", Airplane Make: " + this.airplaneMake + ", Type: " + this.type
          + ", Departure Time: " + this.departureTime + ", Landing Time: " + this.landingTime
           + ", Flight Duration: " + this.flightDuration;
    }


	@Override
	public String getAdditionalInfo() {
		return "hi";
	}


	@Override
	public double getAdditionalInfo1() {
		return numberOfPassengers;
	}
    
}
