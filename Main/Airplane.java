/**
 * This abstract class is used for representation of the details of a plane
 * @author Roy Aryaan
 */
public abstract class Airplane {
	/**
	 * Initialization using primitive type integer, which will be used to store the flight number
	 */
    int flightNumber;
    /**
	 * Initialization using referenced type String, which will be used to store the make of the plane
	 */
    String airplaneMake;
    /**
	 * Initialization using referenced type String, which will be used to store the type of the plane
	 */
    String type;
    /**
	 * Initialization using primitive type integer, which will be used to store the depature time of the flight
	 */
    int departureTime;
    /**
	 * Initialization using primitive type integer, which will be used to store the landing time of the flight
	 */
    int landingTime;
    /**
	 * Initialization using primitive type integer, which will be used to store the flight duration
	 */
    int flightDuration;
    
    /**
     * This is the line number from the file it was read from
     */
    int lineNumber;
    
    /**
     * This is a check to whether the plane is late or not
     */
    boolean isLate;

    /**
     * This constructor is used to initialize the values of the fields that makes and identifies an airplane
     * @param flightNumber The number that identifies the plane
     * @param airplaneMake The make of the plane
     * @param type The type of plane, examples: Cargo, Private, Commercial
     * @param departureTime The departure time of the flight
     * @param landingTime The landing time of the flight
     * @param flightDuration The duration of flight from or to its destination
     */
    public Airplane(int flightNumber, String airplaneMake, String type, int departureTime, int landingTime, int flightDuration, boolean isLate, int lineNumber ) {  
        this.flightNumber = flightNumber;
        this.airplaneMake = airplaneMake;
        this.type = type;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.flightDuration = flightDuration;
        this.isLate = isLate;
        this.lineNumber = lineNumber;
    }

    public abstract int getFlightNumber();

	public abstract String getAirplaneMake();

	public abstract String getType();

	public abstract int getDepartureTime();

	public abstract int getLandingTime();

	public abstract int getFlightDuration();
    public abstract String getAdditionalInfo();
    public abstract double getAdditionalInfo1();
	
	public abstract boolean getIsLate();

    public abstract void setIsLate(boolean late);
    
	
	public abstract int getLineNumber();

    public String getLateFlightData() {
        // Construct the string representation for a late flight
        String lateFlightData = flightNumber + "," + airplaneMake + "," + type + ","
                                + departureTime + "," + landingTime + "," + flightDuration + ","
                                + isLate + "," + getAdditionalInfo1(); // Assuming this method returns a double or appropriate data type
        return lateFlightData;
    }
    
	

	/**
     * A abstract method that is to be implemented by child classes by overriding that is meant to return the details of the Airplane in a systematic way
     * @param plane is the everything about the plane using the refrenced type Airplane
     * @see Airplane
     * @return returns null at the moment, as it is to be implemented by children classes to return details of the airplane
     */
    public abstract String displayDetails(Airplane plane);

    @Override
    public String toString() {
        return this.type;
    }


}
