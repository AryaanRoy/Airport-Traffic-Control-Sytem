/**
 * A class that extends Airplane and implements Private
 * 
 * @see Airplane
 * @see Private
 * @author Roy Aryaan
 */
public class PrivatePlane extends Airplane implements Private {
	
	/**
	 * Initalizes the refernced type String, to store the name of the owner of the private plane
	 */
    String ownerName;
    
    /**
     * A constructor that constructs the details of the flight from the class Airplane and also the owner name
     * @param flightNumber The number that identifies the plane
     * @param airplaneMake The make of the plane
     * @param type The type of plane, examples: Cargo, Private, Commercial
     * @param departureTime The departure time of the flight
     * @param landingTime The landing time of the flight
     * @param flightDuration The duration of flight from or to its destination
     * @param ownerName The name of the owner of the private plane
     */
    public PrivatePlane(int flightNumber, String airplaneMake, String type, int departureTime,int landingTime, int flightDuration, boolean isLate, int lineNumber, String ownerName) {
        super(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber);
        this.ownerName = ownerName;
    }


    @Override
    public String getOwnerName() {
        return this.ownerName;
    }

    @Override
    public void setOwnerName(String name) {
        name = this.ownerName;
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
        return "Owner's name: " + this.ownerName + ", Flight Number: " + this.flightNumber
         + ", Airplane Make: " + this.airplaneMake + ", Type: " + this.type
          + ", Departure Time: " + this.departureTime + ", Landing Time: " + this.landingTime
           + ", Flight Duration: " + this.flightDuration;
    }


	@Override
	public String getAdditionalInfo() {
		return ownerName;
	}


	@Override
	public double getAdditionalInfo1() {
		return 1;
	}
    
    
}
