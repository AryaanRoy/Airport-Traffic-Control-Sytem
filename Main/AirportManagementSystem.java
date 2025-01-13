import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The main class, where file handling, enqueue, dequeue and displaying queues is implemented
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 * */
@SuppressWarnings("unused")
public class AirportManagementSystem {
	/**  The queue for departing airplanes.
	 *  @see NodeQueue
	 */
    public static NodeQueue<Airplane> departureQueue;
    
    /**
	 *  The queue for landing airplanes.
	 *  @see NodeQueue
	 */
    public static NodeQueue<Airplane> landingQueue;

    /**
     * Constructor that initializes the departure and landing queues.
     */
    public AirportManagementSystem() {
        departureQueue = new NodeQueue<>();
        landingQueue = new NodeQueue<>();
    }
    
    /**
     * This method is for enqueuing onto the depratureQueue and landingQueue, while checking its type.
     * @param plane The plane that's going to be added.
     * @see Airplane
     */
    public void addQueue(Airplane plane) {
        if (plane instanceof Cargo) {
            departureQueue.enqueue(plane);
            landingQueue.enqueue(plane);
        } else if (plane instanceof Commercial) {
            departureQueue.enqueue(plane);
            landingQueue.enqueue(plane);
        } else if (plane instanceof Private) {
            departureQueue.enqueue(plane);
            landingQueue.enqueue(plane);
        }
    }

    
    public static String[][] departureArray() {
    	int rowSize = departureQueue.getSize();
    	int columnSize = 9;
    	String[][] dpQueueArray = new String[rowSize][columnSize];
    	Node<Airplane> departureNode = departureQueue.getFirst();
    	for(int i=0;i<rowSize;i++) {
            if (departureNode.getValue().getType().toLowerCase().contains("cargo")) {
            CargoPlane nodeValue = (CargoPlane) departureNode.getValue();
//          flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, cargoWeight
            dpQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
            dpQueueArray[i][1] = nodeValue.getAirplaneMake();
            dpQueueArray[i][2] = nodeValue.getType();
            dpQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
            dpQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
            dpQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
            dpQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
            dpQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
            dpQueueArray[i][8] = Double.toString(nodeValue.getCargoWeight());
            }
            else if (departureNode.getValue().getType().toLowerCase().contains("private")) {
            	 PrivatePlane nodeValue = (PrivatePlane) departureNode.getValue();
            	 dpQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
                 dpQueueArray[i][1] = nodeValue.getAirplaneMake();
                 dpQueueArray[i][2] = nodeValue.getType();
                 dpQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
                 dpQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
                 dpQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
                 dpQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
                 dpQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
                 dpQueueArray[i][8] = nodeValue.getOwnerName();
            }
            else if (departureNode.getValue().getType().toLowerCase().contains("commercial")) {
           	 	CommercialPlane nodeValue = (CommercialPlane) departureNode.getValue();
           	 dpQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
             dpQueueArray[i][1] = nodeValue.getAirplaneMake();
             dpQueueArray[i][2] = nodeValue.getType();
             dpQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
             dpQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
             dpQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
             dpQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
             dpQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
             dpQueueArray[i][8] = Double.toString(nodeValue.getNumberOfPassengers());
           }
            else {System.out.println("Wrong Type");}
            departureNode = departureNode.getNext();
           
    	}
    
    	
		return dpQueueArray;
		
    }
    
    public static String[][] landingArray() {
    	int rowSize = landingQueue.getSize();
    	int columnSize = 9;
    	String[][] ldQueueArray = new String[rowSize][10];
    	Node<Airplane> landingNode = landingQueue.getFirst();
    	for(int i=0;i<rowSize;i++) {
            if (landingNode.getValue().getType().toLowerCase().contains("cargo")) {
            CargoPlane nodeValue = (CargoPlane) landingNode.getValue();
//          flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, cargoWeight
            ldQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
            ldQueueArray[i][1] = nodeValue.getAirplaneMake();
            ldQueueArray[i][2] = nodeValue.getType();
            ldQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
            ldQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
            ldQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
            ldQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
            ldQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
            ldQueueArray[i][8] = Double.toString(nodeValue.getCargoWeight());
            }
            else if (landingNode.getValue().getType().toLowerCase().contains("private")) {
            	 PrivatePlane nodeValue = (PrivatePlane) landingNode.getValue();
            	 ldQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
            	 ldQueueArray[i][1] = nodeValue.getAirplaneMake();
            	 ldQueueArray[i][2] = nodeValue.getType();
                 ldQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
                 ldQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
                 ldQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
                 ldQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
                 ldQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
                 ldQueueArray[i][8] = nodeValue.getOwnerName();
            }
            else if (landingNode.getValue().getType().toLowerCase().contains("commercial")) {
           	 	CommercialPlane nodeValue = (CommercialPlane) landingNode.getValue();
           	 ldQueueArray[i][0] = Integer.toString(nodeValue.getFlightNumber());
           	 ldQueueArray[i][1] = nodeValue.getAirplaneMake();
           	 ldQueueArray[i][2] = nodeValue.getType();
           	 ldQueueArray[i][3] = Integer.toString(nodeValue.getDepartureTime());
           	 ldQueueArray[i][4] = Integer.toString(nodeValue.getLandingTime());
           	 ldQueueArray[i][5] = Integer.toString(nodeValue.getFlightDuration());
           	 ldQueueArray[i][6] = Boolean.toString(nodeValue.getIsLate());
           	 ldQueueArray[i][7] = Integer.toString(nodeValue.getLineNumber());
           	 ldQueueArray[i][7] = Double.toString(nodeValue.getNumberOfPassengers());
           }
            else {System.out.println("Wrong Type");}
            landingNode = landingNode.getNext();
    	}
    	
		return ldQueueArray;
    	
    }


    

    
    
    
    /**
     * This method looks at the file name and depending on whether it contains departure or landing its enqueued into its respective queue from the parameter plane 
     * @param plane The plane that's going to be added.
     * @param fileName is the name of the text file being processed.
     */
    public void addQueueFromFile(Airplane plane, String fileName) {
    	// checking if the String fileName contains departure
        if (fileName.contains("departure")) {
            if (plane instanceof Cargo || plane instanceof Commercial || plane instanceof Private) {
                departureQueue.enqueue(plane);
            }
        }
     // checking if the String fileName contains landing
        if (fileName.contains("landing")) {
            if (plane instanceof Cargo || plane instanceof Commercial || plane instanceof Private) {
                landingQueue.enqueue(plane);
            }
        }
    }

    public void addQueueFromFile2(Airplane plane) {
    	// checking if the String fileName contains departure

            if (plane instanceof Cargo || plane instanceof Commercial || plane instanceof Private) {
                departureQueue.enqueue(plane);
            }
     
        }
    
    
    /**
     * This method dequeues an airplane from the departureQueue and prints out its flight number.
     */
    public void deQueueDeparture() {
    	Node<Airplane> firstNode = departureQueue.getFirst();
    	departureQueue.dequeue();
    	System.out.println("Flight number of removed plane: "+firstNode.getValue().flightNumber);
    }
    
    /**
     * This method dequeues an airplane from the landingQueue and prints out its flight number.
     */
    public void deQueueLanding() {
    	Node<Airplane> firstNode = landingQueue.getFirst();
    	landingQueue.dequeue();
    	System.out.println("Flight number of removed plane: "+firstNode.getValue().flightNumber);
    }

    /**
     * Displays the content of both queues, using the method displayQueue.
     * @return 
     */
    public static void displayQueues() {
        System.out.println("\nDeparting Queue: ");
        displayQueue(departureQueue);

        System.out.println("\nLanding Queue:");
        displayQueue(landingQueue);
    }



    /**
     * Iterates specified queue node by node printing the details of all airplanes within.
     * @param queue The queue that contains the airplanes/nodes to display.
     */
    static void displayQueue(NodeQueue<Airplane> queue) {
    	// planeNumber used to keep count of the number of nodes
        int planeNumber = 1;
        Node<Airplane> currentNode = queue.getFirst();
//        System.out.println(currentNode);
//        Airplane currentAirplane = currentNode.getValue();
//        currentAirplane.displayDetails(currentAirplane);

        while (currentNode != null) {
            Airplane currentAirplane = currentNode.getValue();
            System.out.println(currentAirplane + " plane " + planeNumber + "- " + currentAirplane.displayDetails(currentAirplane));
            System.out.println("--------------------------------------------------------------------\n");
            currentNode = currentNode.getNext();
            planeNumber++;

        }

    }
    /**
     * This method reads from a specified text file line by line and enqueues the airplanes.
     * @param fileName The name of text file to be read from.
     */
    public void loadFlightData(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
    
                String[] flightDetails = line.split(",");  
                //Skips line if fields are insufficient
                if (flightDetails.length < 8) {
                    System.out.println("Error: Invalid data format, missing fields in line: " + line);
                    continue;
                }
                //Initializing plane details from the flightDetails array in order
                int flightNumber = Integer.parseInt(flightDetails[0]);
                String airplaneMake = flightDetails[1];
                String type = flightDetails[2];
                int departureTime = Integer.parseInt(flightDetails[3]);
                int landingTime = Integer.parseInt(flightDetails[4]);
                int flightDuration = Integer.parseInt(flightDetails[5]);
                boolean isLate = Boolean.parseBoolean(flightDetails[6]);
                String additionalInfo = flightDetails[7];
                
                //Creating airplane objects based on their  type.
                if (type.equals("Cargo")) {
                    double cargoWeight = Double.parseDouble(additionalInfo);
                    Airplane cargoPlane = new CargoPlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, cargoWeight);
                    this.addQueueFromFile(cargoPlane, fileName);  

                } else if (type.equals("Commercial")) {
                    double passengers = Double.parseDouble(additionalInfo);
                    Airplane commercialPlane = new CommercialPlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, passengers);
                    this.addQueueFromFile(commercialPlane, fileName);  
                }else if (type.equals("Private")) {
                    String ownerName = additionalInfo;
                    Airplane privatePlane = new PrivatePlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, ownerName);
                    this.addQueueFromFile(privatePlane, fileName);  
                } else {
                    System.out.println("Unknown airplane type at line: "+line);
                }
                lineNumber ++;
            }
                //exception handling
                  scanner.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Error: File not found: "+ fileName);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid data format in file: " + fileName);
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred:" + e.getMessage());
                }
            }

            public void loadFlightData2(String fileName) {
                try {
                    Scanner scanner = new Scanner(new File(fileName));
                    int lineNumber = 1;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
            
                        String[] flightDetails = line.split(",");  
                        //Skips line if fields are insufficient
                        if (flightDetails.length < 8) {
                            System.out.println("Error: Invalid data format, missing fields in line: " + line);
                            continue;
                        }
                        //Initializing plane details from the flightDetails array in order
                        int flightNumber = Integer.parseInt(flightDetails[0]);
                        String airplaneMake = flightDetails[1];
                        String type = flightDetails[2];
                        int departureTime = Integer.parseInt(flightDetails[3]);
                        int landingTime = Integer.parseInt(flightDetails[4]);
                        int flightDuration = Integer.parseInt(flightDetails[5]);
                        boolean isLate = Boolean.parseBoolean(flightDetails[6]);
                        String additionalInfo = flightDetails[7];
                        
                        //Creating airplane objects based on their  type.
                        if (type.equals("Cargo")) {
                            double cargoWeight = Double.parseDouble(additionalInfo);
                            Airplane cargoPlane = new CargoPlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, cargoWeight);
                            this.addQueueFromFile2(cargoPlane);  
        
                        } else if (type.equals("Commercial")) {
                            double passengers = Double.parseDouble(additionalInfo);
                            Airplane commercialPlane = new CommercialPlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, passengers);
                            this.addQueueFromFile2(commercialPlane);  
                        }else if (type.equals("Private")) {
                            String ownerName = additionalInfo;
                            Airplane privatePlane = new PrivatePlane(flightNumber, airplaneMake, type, departureTime, landingTime, flightDuration, isLate, lineNumber, ownerName);
                            this.addQueueFromFile2(privatePlane);  
                        } else {
                            System.out.println("Unknown airplane type at line: "+line);
                        }
                        lineNumber ++;
                    }
                        //exception handling
                          scanner.close();
                        } catch (FileNotFoundException e) {
                            System.out.println("Error: File not found: "+ fileName);
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid data format in file: " + fileName);
                        } catch (Exception e) {
                            System.out.println("An unexpected error occurred:" + e.getMessage());
                        }
                    }


            public void removeFlightFromFile(String fileName, int flightNumber) {
                try {

                    File inputFile = new File(fileName);
                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            

                    StringBuilder content = new StringBuilder();
                    String line;
                    
                    while ((line = reader.readLine()) != null) {
                        String[] flightDetails = line.split(",");
                        int currentFlightNumber = Integer.parseInt(flightDetails[0]);
            
                        if (currentFlightNumber != flightNumber) {
                            content.append(line).append("\n");
                        }
                    }
            
                    reader.close(); 
            

                    FileWriter writer = new FileWriter(inputFile);
                    writer.write(content.toString()); 
                    writer.close();
                    System.out.println("removed flight from file!");
            
                } catch (IOException e) {
                    System.out.println("Error removing flight: " + e.getMessage());
                }
            }            
            
            
            

            public static void addFlightToFile(String fileName, int flightNum, String airplaneMake, String flightType, int departureTime, int landingTime, int flightDuration, boolean isLate, double additionalInfo1) {
                try {
                    // appending mode
                    FileWriter writer = new FileWriter(fileName, true);
                    String flightDetails = flightNum + "," + airplaneMake + "," + flightType + "," + departureTime + "," + landingTime + "," + flightDuration + "," + isLate + "," + additionalInfo1;
                    writer.write(flightDetails + "\n");
                    writer.close();
                    System.out.println("Flight added successfully to " + fileName);
                } catch (IOException e) {
                    System.out.println("Error while adding flight to file: " + e.getMessage());
                }
            }
            

            public static void addFlightToFile1(String fileName, int flightNum, String airplaneMake, String flightType, int departureTime, int landingTime, int flightDuration, boolean isLate, String additionalInfo) {
                try {
                    // FileWriterappend mode
                    FileWriter writer = new FileWriter(fileName, true);
                    String flightDetails = flightNum + "," + airplaneMake + "," + flightType + "," + departureTime + "," + landingTime + "," + flightDuration + "," + isLate + "," + additionalInfo;
                    writer.write(flightDetails + "\n");
                    writer.close();
                    System.out.println("Flight added successfully to " + fileName);
                } catch (IOException e) {
                    System.out.println("Error while adding flight to file: " + e.getMessage());
                }
            }
            


    















    /**
     * The main method where the required methods loadFlightData, displayQueues, deQueueDeparture, deQueueLanding are used.
     * @param args No args
     */
    public static void main(String[] args) {
    	

//        AirportManagementSystem ams = new AirportManagementSystem();
//        ams.loadFlightData("departureFlights.txt");
//        ams.loadFlightData("landingFlights.txt");
    	
    	System.out.println("This is AMS class, not AirportGUI");
        
//         
//       System.out.println(ams.departureQueue.getSize());
//       System.out.println(ams.landingQueue.getSize());
//       System.out.println("");
//        
//        Node<Airplane> departureNode = ams.departureQueue.getFirst();
//        if (departureNode.getValue().getType().toLowerCase().contains("cargo")) {
//        CargoPlane nodeValue = (CargoPlane) departureNode.getValue();
//        System.out.println(nodeValue.getAirplaneMake());
//        System.out.println(nodeValue.getDepartureTime());
//        System.out.println(nodeValue.getFlightDuration());
//        System.out.println(nodeValue.getFlightNumber());
//        System.out.println(nodeValue.getLandingTime());
//        System.out.println(nodeValue.getType());
//        System.out.println(nodeValue.getCargoWeight());
//        System.out.println(nodeValue.getIsLate());
//        System.out.println(nodeValue.getLineNumber());
//        }
//        else if (departureNode.getValue().getType().toLowerCase().contains("private")) {
//        	 PrivatePlane nodeValue = (PrivatePlane) departureNode.getValue();
//             System.out.println(nodeValue.getAirplaneMake());
//             System.out.println(nodeValue.getDepartureTime());
//             System.out.println(nodeValue.getFlightDuration());
//             System.out.println(nodeValue.getFlightNumber());
//             System.out.println(nodeValue.getLandingTime());
//             System.out.println(nodeValue.getType());
//             System.out.println(nodeValue.getOwnerName());
//             System.out.println(nodeValue.getIsLate());
//             System.out.println(nodeValue.getLineNumber());
//        	
//        }
//        else if (departureNode.getValue().getType().toLowerCase().contains("commercial")) {
//       	 	CommercialPlane nodeValue = (CommercialPlane) departureNode.getValue();
//            System.out.println(nodeValue.getAirplaneMake());
//            System.out.println(nodeValue.getDepartureTime());
//            System.out.println(nodeValue.getFlightDuration());
//            System.out.println(nodeValue.getFlightNumber());
//            System.out.println(nodeValue.getLandingTime());
//            System.out.println(nodeValue.getType());
//            System.out.println(nodeValue.getNumberOfPassengers());
//            System.out.println(nodeValue.getIsLate());
//            System.out.println(nodeValue.getLineNumber());
//       }
//        else {System.out.println("Wrong Type");}
//        
//        Node<Airplane> landingNode = ams.departureQueue.getFirst();
//        if (landingNode.getValue().getType().toLowerCase().contains("cargo")) {
//        CargoPlane nodeValue = (CargoPlane) landingNode.getValue();
//        System.out.println(nodeValue.getAirplaneMake());
//        System.out.println(nodeValue.getDepartureTime());
//        System.out.println(nodeValue.getFlightDuration());
//        System.out.println(nodeValue.getFlightNumber());
//        System.out.println(nodeValue.getLandingTime());
//        System.out.println(nodeValue.getType());
//        System.out.println(nodeValue.getCargoWeight());
//        System.out.println(nodeValue.getIsLate());
//        System.out.println(nodeValue.getLineNumber());
//        }
//        else if (landingNode.getValue().getType().toLowerCase().contains("private")) {
//        	 PrivatePlane nodeValue = (PrivatePlane) landingNode.getValue();
//             System.out.println(nodeValue.getAirplaneMake());
//             System.out.println(nodeValue.getDepartureTime());
//             System.out.println(nodeValue.getFlightDuration());
//             System.out.println(nodeValue.getFlightNumber());
//             System.out.println(nodeValue.getLandingTime());
//             System.out.println(nodeValue.getType());
//             System.out.println(nodeValue.getOwnerName());
//             System.out.println(nodeValue.getIsLate());
//             System.out.println(nodeValue.getLineNumber());
//        	
//        }
//        else if (landingNode.getValue().getType().toLowerCase().contains("commercial")) {
//       	 	CommercialPlane nodeValue = (CommercialPlane) landingNode.getValue();
//            System.out.println(nodeValue.getAirplaneMake());
//            System.out.println(nodeValue.getDepartureTime());
//            System.out.println(nodeValue.getFlightDuration());
//            System.out.println(nodeValue.getFlightNumber());
//            System.out.println(nodeValue.getLandingTime());
//            System.out.println(nodeValue.getType());
//            System.out.println(nodeValue.getNumberOfPassengers());
//            System.out.println(nodeValue.getIsLate());
//            System.out.println(nodeValue.getLineNumber());
//       }
//        else {System.out.println("Wrong Type");}

        
        
        
//        airportManagementSystem.displayQueues();

//        //removing a plane from the departure queue
//        System.out.println("\nRemoving plane from the departure queue.");
//        airportManagementSystem.deQueueDeparture();
//    
//        //removing a plane from the landing queue
//        System.out.println("\nRemoving plane from the landing queue.");
//        airportManagementSystem.deQueueLanding();
//    
//        //displaying updated queues after plane removal
//        System.out.println("\nUpdated Queues:");
//        airportManagementSystem.displayQueues();
        
        }

    
}
