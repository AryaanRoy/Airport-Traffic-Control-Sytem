import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 * */

@SuppressWarnings("unused")
public class MainMenuPanel extends Application implements Runnable {
	
	
    public MainMenuPanel() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        AirportManagementSystem OfficialAMS = new AirportManagementSystem();
        
        HBox hb = new HBox();
        VBox vb = new VBox();

        // Adding buttons for main page
        Button addFlightBtn = new Button("Add Flight");
        addFlightBtn.setPadding(new Insets(5));
        Button displayQueueBtn = new Button("Show Flights");
        displayQueueBtn.setPadding(new Insets(5));
        Button sortFlightsBtn = new Button("Sort and Filter Flights");
        sortFlightsBtn.setPadding(new Insets(5));
        sortFlightsBtn.setOnAction(new SortFlights(OfficialAMS));
        // Button loadFlightsBtn = new Button("Load flights from file");
        Button requeueFlightsBtn = new Button("Requeue late flights");
        requeueFlightsBtn.setPadding(new Insets(5));
        requeueFlightsBtn.setOnAction(new reQueueLateFlights(OfficialAMS));
        Button exitApplicationBtn = new Button("Exit");
        exitApplicationBtn.setPadding(new Insets(5));
        Button loadFileBtn = new Button("Load A File");
        loadFileBtn.setPadding(new Insets(5));
        queueDisplayPanel displayGUI = new queueDisplayPanel(); 

        loadFileBtn.setOnAction(e-> {
            try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please Select A Text File");
            File file = fileChooser.showOpenDialog(stage);
            String filename = file.getAbsolutePath();
            System.out.println(filename);
            MainMenuPanel2 mainMenuPanel2 = new MainMenuPanel2(stage,filename);
            mainMenuPanel2.start(stage);
            } catch (Exception e1) {
                System.out.println("Error" + e1.getMessage());
            }
        });
        

        // Current button actions
        addFlightBtn.setOnAction(e -> AddFlightMenu.showAddFlightWindow());
                displayQueueBtn.setOnAction(new DisplayQueues(displayGUI, OfficialAMS));
                exitApplicationBtn.setOnAction(new ExitApplication(stage));
                // loadFlightsBtn.setOnAction(new LoadFlightData(OfficialAMS));
        
                hb.getChildren().addAll(addFlightBtn, displayQueueBtn);
                vb.getChildren().add(hb);

                vb.getChildren().addAll(sortFlightsBtn, requeueFlightsBtn, exitApplicationBtn,loadFileBtn);

                vb.setAlignment(Pos.CENTER);

                hb.setAlignment(Pos.CENTER);
                hb.maxWidth(Double.POSITIVE_INFINITY);
                hb.maxHeight(Double.POSITIVE_INFINITY);
        
                Scene s = new Scene(vb, 850, 325);
                hb.setPadding(new Insets(10));
                vb.setPadding(new Insets(10));
                VBox.setMargin(exitApplicationBtn, new Insets(10));
                VBox.setMargin(sortFlightsBtn, new Insets(10));
                VBox.setMargin(requeueFlightsBtn, new Insets(10));
                HBox.setMargin(addFlightBtn, new Insets(5));                
                s.getStylesheets().add("javaFxcss.css");
                stage.setScene(s);
                stage.setTitle("Main");
                stage.show();
            }
    
    /**
     * Exits the application
     */
            // Exit handling
            public class ExitApplication implements EventHandler<ActionEvent> {
                private Stage stage;
        
                public ExitApplication(Stage stage) {
                    this.stage = stage;
                }
        
                @Override
                public void handle(ActionEvent arg0) {
                    System.out.println("Application is closed");
                    System.exit(0);
                }
            }

            

            public class reQueueLateFlights implements EventHandler<ActionEvent>{                

                private static AirportManagementSystem ams; 

                public reQueueLateFlights(AirportManagementSystem ams) {

                    MainMenuPanel.AddFlightMenu.ams = ams;
                }
                
                @Override
                public void handle(ActionEvent arg0) {
                    //thread timer to start the timer from 0

                    AirportManagementSystem ams = new AirportManagementSystem();
                    try {
                        ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error: The flight data file contains invalid data.");
                    }
                    String[][] dpArray = AirportManagementSystem.departureArray();
                    int arSize = AirportManagementSystem.departureQueue.getSize();
                    Node<Airplane> departureNode = AirportManagementSystem.departureQueue.getFirst();
                    int timer = TimerTiming.getTimer();
                    System.out.println("The time is: "+timer);
                    List<Integer> lateFlightsLineNumbers = new ArrayList<>();

                    for(int i=0; i<arSize; i++) {
                        TimerTiming.startTimer();
                        System.out.println("The time is: "+timer);
                        final int index1 = i;
                        System.out.println("eeeee");
                        System.out.println("Queue size (arSize): " + arSize);

                        if (departureNode == null) break;
                        if (dpArray[i][3] == null) break;
                        if (dpArray == null) break;

                        Airplane currentFlight = departureNode.getValue();
                        currentFlight.setIsLate(false);
                        if(Integer.parseInt(dpArray[i][3]) > timer) {
                                currentFlight.setIsLate(true);
                            } 
                            
                        int flightNum = currentFlight.getFlightNumber();
                        String airplaneMake = currentFlight.getAirplaneMake();
                        String flightType = currentFlight.getType();
                        int departureTime = currentFlight.getDepartureTime();
                        int landingTime = currentFlight.getLandingTime();
                        int flightDuration = currentFlight.getFlightDuration();
                        boolean isLate = currentFlight.getIsLate();
                        double additionalInfo1 = currentFlight.getAdditionalInfo1();
                        String additionalInfo = currentFlight.getAdditionalInfo();
                        System.out.println(currentFlight.departureTime+" " + currentFlight.getIsLate());

                        //Write the updated flight to the file
                        if (flightType.equals( "Cargo" ) || flightType.equals( "Commercial")) {
                            System.out.println("eeeee");
                            if (currentFlight.isLate == true) {
                                String[][] currentDepartureArray = AirportManagementSystem.departureArray();
                                int flightNumber = Integer.parseInt(currentDepartureArray[i][0]); // Parse flight number
                                ams.removeFlightFromFile("departureFlights.txt", flightNumber);
                                

                                //lateflightslist.add(currentFlight.getLateFlightData()); 
                                //ams.removeFlightFromFileByLine("departureFlights.txt", index1);
                                AirportManagementSystem.addFlightToFile("departureFlights.txt", flightNum, airplaneMake, flightType,departureTime, landingTime, flightDuration, isLate, additionalInfo1);
                                
                            }
                            
                            // Remove from the queue
                        } else if (flightType.equals( "Private" )) {
                            System.out.println("PRIVACYYPLANE");
                            if (currentFlight.isLate == true) {
                                String[][] currentDepartureArray = AirportManagementSystem.departureArray();
                                int flightNumber = Integer.parseInt(currentDepartureArray[i][0]); // Parse flight number
                                ams.removeFlightFromFile("departureFlights.txt", flightNumber);
                            //lateflightslist.add(currentFlight.getLateFlightData()); 
                            //ams.removeFlightFromFileByLine("departureFlights.txt", index1);
                            AirportManagementSystem.addFlightToFile1("departureFlights.txt", flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo);
                        }

                        } else {
                            //System.out.println("eeeee");
                            //ams.removeFlightFromFile("departureFlights.txt", index1);
                            System.out.println("Wrong Type");
                        }
                        // Move to the next flight in the queue
                        departureNode = departureNode.getNext();
        
                    }

                    System.out.println("RESULt"+ lateFlightsLineNumbers);


                
                    //System.out.println("Late flights have been requeued and removed.");
                                    
                }
            }

            public class SortFlights implements EventHandler<ActionEvent> {

                private static AirportManagementSystem ams;
            
                public SortFlights(AirportManagementSystem ams) {
                    MainMenuPanel.SortFlights.ams = ams; // Loading ams for GUI
                }
            
                @Override
                public void handle(ActionEvent event) {
                    ams.loadFlightData("departureFlights.txt");
                    ams.loadFlightData("landingFlights.txt");

                    //System.out.println("Current Queues");
                    //AirportManagementSystem.displayQueues();

                    Stage sortOptionsStage = new Stage();
                    VBox v = new VBox();
                    HBox sortHB = new HBox();
                    HBox filterHB = new HBox();
                    Label sortL = new Label("Sort Flights by");
                    Label filterL = new Label("Filter Flights by");

                    Button sortByDepartureTime = new Button("Departure Time");
                    Button sortByTimeRange = new Button("Time Range");
                    Button sortByFlightNumber = new Button("Flight Number");
                    Button sortByCargo = new Button("Show Cargo Flights Only");
                    Button sortByPrivate = new Button("Show Private Flights Only");
                    Button sortByCommercial = new Button("Show Commercial Flights Only");
                    Button ClearFilters = new Button("Clear Filters");

                    sortHB.getChildren().addAll(sortByDepartureTime, sortByTimeRange, sortByFlightNumber);  

                    sortHB.setAlignment(Pos.CENTER);

                    VBox.setMargin(sortL, new Insets(7.5));

                    filterHB.getChildren().addAll(sortByCargo, sortByPrivate, sortByCommercial, ClearFilters);      

                    filterHB.setAlignment(Pos.CENTER);

                    VBox.setMargin(filterL, new Insets(7.5));
                
                    HBox.setMargin(sortByDepartureTime, new Insets(5));

                    HBox.setMargin(sortByTimeRange, new Insets(5));

                    HBox.setMargin(sortByFlightNumber, new Insets(5));



                    HBox.setMargin(sortByCargo, new Insets(5));

                    HBox.setMargin(sortByPrivate, new Insets(5));

                    HBox.setMargin(sortByCommercial, new Insets(5));


                    v.getChildren().addAll(sortL, sortHB, filterL, filterHB);


                    ClearFilters.setOnAction(e -> {
  
                        ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                });

                    sortByDepartureTime.setOnAction(e -> {
                        AirportManagementSystem.landingQueue.clear();
                    	AirportManagementSystem.departureQueue.clear();
                    	ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        sortQueues();
                        updateFile("landingFlights.txt", convertQueueToList(AirportManagementSystem.landingQueue));
                        updateFile("departureFlights.txt", convertQueueToList(AirportManagementSystem.departureQueue));
                        System.out.println("Flights sorted.");
                        sortOptionsStage.close(); 
                    });

                    sortByTimeRange.setOnAction(e -> {
                        AirportManagementSystem.landingQueue.clear();
                    	AirportManagementSystem.departureQueue.clear();
                    	ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        sortQueuesDuration();
                        updateFile("landingFlights.txt", convertQueueToList(AirportManagementSystem.landingQueue));
                        updateFile("departureFlights.txt", convertQueueToList(AirportManagementSystem.departureQueue));
                        System.out.println("Flights sorted.");
                        sortOptionsStage.close();
                    });

                    sortByFlightNumber.setOnAction(e -> {
                        AirportManagementSystem.landingQueue.clear();
                    	AirportManagementSystem.departureQueue.clear();
                    	ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        sortQueuesFlightNum();
                        updateFile("landingFlights.txt", convertQueueToList(AirportManagementSystem.landingQueue));
                        updateFile("departureFlights.txt", convertQueueToList(AirportManagementSystem.departureQueue));
                        System.out.println("Flights sorted.");
                        sortOptionsStage.close();
                    });
                    
                    sortByPrivate.setOnAction( e -> { AirportManagementSystem.landingQueue.clear();

                    	AirportManagementSystem.departureQueue.clear();

                    	ams.loadFlightData("departureFlights.txt");

                        ams.loadFlightData("landingFlights.txt");
                        queueDisplayPanel displayGUI2 = new queueDisplayPanel();
                        AirportManagementSystem OfficialAMS2 = new AirportManagementSystem();
                        new DisplayTypes(displayGUI2, OfficialAMS2, "Private").handle(event);;
                    } );
            
                    sortByCargo.setOnAction( e -> { 
                        queueDisplayPanel displayGUI2 = new queueDisplayPanel();
                        AirportManagementSystem OfficialAMS2 = new AirportManagementSystem();
                        new DisplayTypes(displayGUI2, OfficialAMS2, "Cargo").handle(event);;
                });

                    sortByCommercial.setOnAction( e -> { 
                        queueDisplayPanel displayGUI2 = new queueDisplayPanel();
                        AirportManagementSystem OfficialAMS2 = new AirportManagementSystem();
                        new DisplayTypes(displayGUI2, OfficialAMS2, "Commercial").handle(event);;
                    });
            
                    Scene scene = new Scene(v, 900, 200);
                    scene.getStylesheets().add("javaFxcss.css");
                    sortOptionsStage.setScene(scene);
                    sortOptionsStage.setTitle("Sort and Filter Flights");
                    sortOptionsStage.show();
            
                    // Sort both landing and departure queues
                    //sortQueues();

                    //sorting by time range!!!!
                    //LinkedList<Airplane> landingList = sortTimeRange(convertQueueToList(AirportManagementSystem.landingQueue), 1000, 1200);
                    //LinkedList<Airplane> departureList = sortTimeRange(convertQueueToList(AirportManagementSystem.departureQueue), 1000, 1200);
                    //updateFile("landingFlights.txt", landingList);
                    //updateFile("departureFlights.txt", departureList);


            
                    // Sending the new queues to the files
                    //updateFile("landingFlights.txt", convertQueueToList(AirportManagementSystem.landingQueue));
                    //updateFile("departureFlights.txt", convertQueueToList(AirportManagementSystem.departureQueue));
            
                    //System.out.println("Flights sorted.");
                }
            

                public static void sortQueues() {

                    LinkedList<Airplane> landingList = convertQueueToList(AirportManagementSystem.landingQueue);
                    LinkedList<Airplane> departureList = convertQueueToList(AirportManagementSystem.departureQueue);
 
                    bubbleSortLandingList(landingList);
                    bubbleSortDepartureList(departureList);
            

                    AirportManagementSystem.landingQueue.clear();
                    AirportManagementSystem.departureQueue.clear();
            
                    for (Airplane airplane : landingList) {
                        AirportManagementSystem.landingQueue.enqueue(airplane);
                    }
                    for (Airplane airplane : departureList) {
                        AirportManagementSystem.departureQueue.enqueue(airplane);
                    }
                }

                public static void sortQueuesDuration() {

                    LinkedList<Airplane> landingList = convertQueueToList(AirportManagementSystem.landingQueue);
                    LinkedList<Airplane> departureList = convertQueueToList(AirportManagementSystem.departureQueue);
 
                    bubbleSortFlightDuration(landingList);
                    bubbleSortFlightDuration(departureList);
            

                    AirportManagementSystem.landingQueue.clear();
                    AirportManagementSystem.departureQueue.clear();
            
                    for (Airplane airplane : landingList) {
                        AirportManagementSystem.landingQueue.enqueue(airplane);
                    }
                    for (Airplane airplane : departureList) {
                        AirportManagementSystem.departureQueue.enqueue(airplane);
                    }
                }

                public static void sortQueuesFlightNum() {

                    LinkedList<Airplane> landingList = convertQueueToList(AirportManagementSystem.landingQueue);
                    LinkedList<Airplane> departureList = convertQueueToList(AirportManagementSystem.departureQueue);
 
                    bubbleSortByFlightNum(landingList);
                    bubbleSortByFlightNum(departureList);
            

                    AirportManagementSystem.landingQueue.clear();
                    AirportManagementSystem.departureQueue.clear();
            
                    for (Airplane airplane : landingList) {
                        AirportManagementSystem.landingQueue.enqueue(airplane);
                    }
                    for (Airplane airplane : departureList) {
                        AirportManagementSystem.departureQueue.enqueue(airplane);
                    }
                }
            
                //for converting queue to a list
                private static LinkedList<Airplane> convertQueueToList(NodeQueue<Airplane> queue) {
                    LinkedList<Airplane> list = new LinkedList<>();
                    Node<Airplane> currentNode = queue.getFirst();
                    while (currentNode != null) {
                        list.add(currentNode.getValue());
                        currentNode = currentNode.getNext();
                    }
                    return list;
                }
            
                // Bubble Sort for landing list
                private static void bubbleSortLandingList(LinkedList<Airplane> list) {
                    int n = list.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (list.get(j).getLandingTime() > list.get(j + 1).getLandingTime()) {
                                Airplane temp = list.get(j);
                                list.set(j, list.get(j + 1));
                                list.set(j + 1, temp);
                            }
                        }
                    }
                }
            
                // Bubble Sort for departure list
                private static void bubbleSortDepartureList(LinkedList<Airplane> list) {
                    int n = list.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (list.get(j).getDepartureTime() > list.get(j + 1).getDepartureTime()) {
                                Airplane temp = list.get(j);
                                list.set(j, list.get(j + 1));
                                list.set(j + 1, temp);
                            }
                        }
                    }
                }
            
                // Method to write the sorted flight data back to the file
                private static void updateFile(String filename, LinkedList<Airplane> flightList) {
                    try {	
                        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
                        for (Airplane flight : flightList) {
                            int flightNum = flight.getFlightNumber();
                            String airplaneMake = flight.getAirplaneMake();
                            String flightType = flight.getType();
                            int departureTime = flight.getDepartureTime();
                            int landingTime = flight.getLandingTime();
                            int flightDuration = flight.getFlightDuration();
                            boolean isLate = flight.getIsLate();
                            double additionalInfo1 = flight.getAdditionalInfo1();
                            String additionalInfo = flight.getAdditionalInfo();
            
                            writer.write(flight.getLateFlightData());
                            writer.newLine();
                        }
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            
                // Method to sort by flight duration. 
                private static void bubbleSortFlightDuration(LinkedList<Airplane> list) {
                    int n = list.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (list.get(j).getFlightDuration() > list.get(j + 1).getFlightDuration()) {
                                Airplane temp = list.get(j);
                                list.set(j, list.get(j + 1));
                                list.set(j + 1, temp);
                            }
                        }
                    }
                }
            
                // Method to sort by flight number not implemented
                private static void bubbleSortByFlightNum(LinkedList<Airplane> list) {
                    int n = list.size();
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (list.get(j).getFlightNumber() > list.get(j + 1).getFlightNumber()) {
                                Airplane temp = list.get(j);
                                list.set(j, list.get(j + 1));
                                list.set(j + 1, temp);
                            }
                        }
                    }
                }
            
                // Sort by type (Cargo/Private/Commercial)
                public static LinkedList<Airplane> sortByType(LinkedList<Airplane> airplanes, String type) {
                    LinkedList<Airplane> sortedByType = new LinkedList<>();
                    for (Airplane airplane : airplanes) {
                        if (airplane.getType().equals(type)) {
                            sortedByType.add(airplane);
                        }
                    }
                    return sortedByType;
                }

                //filter and sort airplanes by time range
                public static LinkedList<Airplane> sortTimeRange(LinkedList<Airplane> airplanes, int time1, int time2) {
                    LinkedList<Airplane> sortedByTimeRange = new LinkedList<>();

                    for (Airplane airplane : airplanes) {
                        if (time1 <= airplane.getDepartureTime() && airplane.getLandingTime() <= time2) {
                            sortedByTimeRange.add(airplane);
                        }
                    }

                    bubbleSortFlightDuration(sortedByTimeRange); 

                    return sortedByTimeRange;
                }

            
                public static void main(String[] args) {
                }
            }
            
            

        
            // Showing flights class
            public class DisplayQueues implements EventHandler<ActionEvent> {
                private Runnable dispGUI;
                private AirportManagementSystem ams;
            
                public DisplayQueues(Runnable dispGUI, AirportManagementSystem ams) {
                    this.dispGUI = dispGUI;
                    this.ams = ams;
                }
            
                @Override
                public void handle(ActionEvent arg0) {
                    // First clear and reload data                        
                    AirportManagementSystem.departureQueue.clear();
                    AirportManagementSystem.landingQueue.clear();
                    try {
                        ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error: The flight data file contains invalid data.");
                    }
            
                    // Create new window
                    Stage stage1 = new Stage();
                    VBox layout = new VBox(20);
            
                    // Labels with styled fonts
                    Label departureLabel = new Label("Departure Flights");
                    Label landingLabel = new Label("Landing Flights");
                    departureLabel.setFont(new Font("Typewriter", 19));
                    landingLabel.setFont(new Font("System", 19));
            
                    // departing and landing queues lists
                    ListView<HBox> departureListView = new ListView<>();
                    ListView<HBox> landingListView = new ListView<>();
            
            
                    // initizzalise the arrays from ams
                    String[][] departureArray = AirportManagementSystem.departureArray();
                    String[][] landingArray = AirportManagementSystem.landingArray();
            
                    // Clear previous 
                    departureListView.getItems().clear();
                    landingListView.getItems().clear();
            
            
                    // Fill departure list
                    for (int i = 0; i < AirportManagementSystem.departureQueue.getSize(); i++) {
                        final int index = i;

                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            departureArray[i][0],  // Flight number
                            departureArray[i][1],  // Airplane make
                            departureArray[i][2],  // Type (Cargo, Private, or Commercial)
                            departureArray[i][3],  // Departure time
                            departureArray[i][4],  // Landing time
                            departureArray[i][5],  // Flight duration
                            departureArray[i][6],
                            departureArray[i][8]
                        );
            
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        Button removeButton = new Button("Remove");
                        if(departureArray[i][6] == Boolean.toString(true)) {
                        	flightDetailsLabel.setTextFill(Color.RED);
                        	
                        }
            
                        removeButton.setOnAction(e -> {
                            Stage confirmStage = new Stage();
                            VBox confirmLayout = new VBox();
            
                            Label confirmLabel = new Label("Are you sure you want to remove this flight?");
                            HBox buttonBox = new HBox();
                            Button confirmYes = new Button("Yes");
                            Button confirmNo = new Button("No");
            
                            buttonBox.getChildren().addAll(confirmYes, confirmNo);
                            String[][] dpArray = AirportManagementSystem.departureArray();
                            int arSize = AirportManagementSystem.departureQueue.getSize();
                            int lineNumber = 0;

                            confirmYes.setOnAction(yesEvent -> {
                            	try {
                                    String[][] currentDepartureArray = AirportManagementSystem.departureArray();
                                    int flightNumber = Integer.parseInt(currentDepartureArray[index][0]); // Parse flight number

                                ams.removeFlightFromFile("departureFlights.txt", flightNumber);
                                
                        
                                // Remove from the queue
                                AirportManagementSystem.departureQueue.remove(index);
                                // currentDepartureArray = AirportManagementSystem.departureArray();
                        
                                // Remove from the ListView
                                departureListView.getItems().remove(flightHBox);
                                stage1.close();
                                queueDisplayPanel displayGUI = new queueDisplayPanel();
                                AirportManagementSystem OfficialAMS = new AirportManagementSystem();
                                new DisplayQueues(displayGUI, OfficialAMS).handle(arg0);;
                                
                                confirmStage.close();
                                Socket socket = new Socket("localhost", 21312);
                        		Scanner scanner = new Scanner(System.in);
                        		scanner.close();
                        		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        		out.println("dp");
                        		out.close();
                                socket.close();

                                
                                }
                            	catch(Exception e2) {
                            		System.out.println("");
                            	}
                            }
                        );
                            
            
                            confirmNo.setOnAction(noEvent -> confirmStage.close());
            
                            confirmLayout.getChildren().addAll(confirmLabel, buttonBox);
            
                            Scene confirmScene = new Scene(confirmLayout);
                            confirmStage.setTitle("Confirm Removal");
                            confirmStage.setScene(confirmScene);
                            confirmScene.getStylesheets().add("javaFxcss.css");
                            confirmStage.show();
                        });
            
                        flightHBox.getChildren().addAll(flightDetailsLabel, removeButton);
                        departureListView.getItems().add(flightHBox);
                    }
            
                    // Fill landing  list
                    for (int i = 0; i < AirportManagementSystem.landingQueue.getSize(); i++) {
                        final int index = i;
                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            landingArray[i][0],  // Flight number
                            landingArray[i][1],  // Airplane make
                            landingArray[i][2],  // Type (Cargo, Private, or Commercial)
                            landingArray[i][3],  // Departure time
                            landingArray[i][4],  // Landing time
                            landingArray[i][5],  // Flight duration
                            landingArray[i][6],  // IsLate
                            landingArray[i][8]   // Additional Information
                        );
                        
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        Button removeButton = new Button("Remove");
            
                        removeButton.setOnAction(e -> {
                            Stage confirmStage = new Stage();
                            VBox confirmLayout = new VBox();
            
                            Label confirmLabel = new Label("Are you sure you want to remove this landing flight?");
                            HBox buttonBox = new HBox();
                            Button confirmYes = new Button("Yes");
                            Button confirmNo = new Button("No");
            
                            buttonBox.getChildren().addAll(confirmYes, confirmNo);
            
                            confirmYes.setOnAction(yesEvent -> {
                                String[][] currentLandingArray = AirportManagementSystem.landingArray();
                                try {
                                int flightNumber = Integer.parseInt(currentLandingArray[index][0]); // Parse flight number
                                ams.removeFlightFromFile("landingFlights.txt", flightNumber);
                                // Remove from the queue
                                AirportManagementSystem.landingQueue.remove(index);
                        
                                // Remove from the ListView
                                landingListView.getItems().remove(flightHBox);
                                stage1.close();
                                queueDisplayPanel displayGUI = new queueDisplayPanel();
                                AirportManagementSystem OfficialAMS = new AirportManagementSystem();
                                new DisplayQueues(displayGUI, OfficialAMS).handle(arg0);
                                confirmStage.close();
                                Socket socket = new Socket("localhost", 21312);
                        		Scanner scanner = new Scanner(System.in);
                        		scanner.close();
                        		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        		out.println("ld");
                        		out.close();
                                socket.close();

                                }
                                catch(Exception e2) {
                                	System.out.println("");
                                }
                        

                            });
            
                            confirmNo.setOnAction(noEvent -> confirmStage.close());
            
                            confirmLayout.getChildren().addAll(confirmLabel, buttonBox);
            
                            Scene confirmScene = new Scene(confirmLayout);
                            confirmStage.setTitle("Confirm Removal");
                            confirmStage.setScene(confirmScene);
                            confirmScene.getStylesheets().add("javaFxcss.css");
                            confirmStage.show();
                        });
            
                        flightHBox.getChildren().addAll(flightDetailsLabel, removeButton);
                        landingListView.getItems().add(flightHBox);
                    }
            
                    layout.getChildren().addAll(
                            departureLabel,
                            departureListView,
                            landingLabel,
                            landingListView
                    );
            
                    Scene scene = new Scene(layout, 1300, 700);
                    stage1.setTitle("Current Queues");
                    stage1.setScene(scene);
                    scene.getStylesheets().add("javaFxcss.css");
                    stage1.show();
                }
            
            }


            public class DisplayTypes implements EventHandler<ActionEvent> {
                private Runnable dispGUI;
                private AirportManagementSystem ams;
                private String type;
                
            
                public DisplayTypes(Runnable dispGUI, AirportManagementSystem ams, String type) {
                    this.dispGUI = dispGUI;
                    this.ams = ams;
                    this.type = type;
                }

                public DisplayTypes(Runnable dispGUI, AirportManagementSystem ams) {
                    this.dispGUI = dispGUI;
                    this.ams = ams;
                
                }
            
                @Override
                public void handle(ActionEvent arg0) {
//                    System.out.println("HI testing");
                    // First clear and reload data                        
                    AirportManagementSystem.departureQueue.clear();
                    AirportManagementSystem.landingQueue.clear();
                    try {
                        ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error: The flight data file contains invalid data.");
                    }
            
                    // Create new window
                    Stage stageType = new Stage();
                    VBox layout = new VBox(20);
            
                    // Labels with styled fonts
                    Label departureLabel = new Label("Departure Flights");
                    Label landingLabel = new Label("Landing Flights");
                    departureLabel.setFont(new Font("Typewriter", 19));
                    landingLabel.setFont(new Font("System", 19));
            
                    // departing and landing queues lists
                    ListView<HBox> departureListView = new ListView<>();
                    ListView<HBox> landingListView = new ListView<>();
            
            
                    // initizzalise the arrays from ams
                    String[][] departureArray = AirportManagementSystem.departureArray();
                    String[][] landingArray = AirportManagementSystem.landingArray();
            
                    // Clear previous 
                    departureListView.getItems().clear();
                    landingListView.getItems().clear();
                    
            
                    // Fill departure list
                    for (int i = 0; i < AirportManagementSystem.departureQueue.getSize(); i++) {
                        final int index = i;
                        System.out.println(departureArray[i][2]);
                        System.out.println(type);
                        if(departureArray[i][2].contains(type)){
                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            departureArray[i][0],  // Flight number
                            departureArray[i][1],  // Airplane make
                            departureArray[i][2],  // Type (Cargo, Private, or Commercial)
                            departureArray[i][3],  // Departure time
                            departureArray[i][4],  // Landing time
                            departureArray[i][5],  // Flight duration
                            departureArray[i][6],
                            departureArray[i][8] //
                        );
            
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        flightHBox.getChildren().addAll(flightDetailsLabel);
                        departureListView.getItems().add(flightHBox);
                        }

            
                    }
                    // Fill landing  list
                    for (int i = 0; i < AirportManagementSystem.landingQueue.getSize(); i++) {
                        final int index = i;
                        if(landingArray[i][2].contains(type)){
                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            landingArray[i][0],  // Flight number
                            landingArray[i][1],  // Airplane make
                            landingArray[i][2],  // Type (Cargo, Private, or Commercial)
                            landingArray[i][3],  // Departure time
                            landingArray[i][4],  // Landing time
                            landingArray[i][5],  // Flight duration
                            landingArray[i][6],  // IsLate
                            landingArray[i][8]   // Additional Information
                            		
                        );
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        flightHBox.getChildren().addAll(flightDetailsLabel);
                        landingListView.getItems().add(flightHBox);
                        
                    }
                 }
                    layout.getChildren().addAll(
                            departureLabel,
                            departureListView,
                            landingLabel,
                            landingListView
                    );
                    
                    Scene scene = new Scene(layout, 1250, 450);
                    stageType.setTitle(type+" Flights Only");
                    stageType.setScene(scene);
                    scene.getStylesheets().add("javaFxcss.css");
                    stageType.show();

                }

                public void handle2(ActionEvent arg0) {
                    AirportManagementSystem.departureQueue.clear();
                    AirportManagementSystem.landingQueue.clear();
                    try {
                        System.out.println("TESTESTEST");
                        ams.loadFlightData("departureFlights.txt");
                        ams.loadFlightData("landingFlights.txt");
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Error: The flight data file contains invalid data.");
                    }
            
                    // Create new window
                    Stage stageType = new Stage();
                    VBox layout = new VBox(20);
            
                    // Labels with styled fonts
                    Label departureLabel = new Label("Departure Flights");
                    Label landingLabel = new Label("Landing Flights");
                    departureLabel.setFont(new Font("Typewriter", 19));
                    landingLabel.setFont(new Font("System", 19));
            
                    // departing and landing queues lists
                    ListView<HBox> departureListView = new ListView<>();
                    ListView<HBox> landingListView = new ListView<>();
            
            
                    // initizzalise the arrays from ams
                    String[][] departureArray = AirportManagementSystem.departureArray();
                    String[][] landingArray = AirportManagementSystem.landingArray();
            
                    // Clear previous 
                    departureListView.getItems().clear();
                    landingListView.getItems().clear();
                    
            
                    // Fill departure list
                    for (int i = 0; i < AirportManagementSystem.departureQueue.getSize(); i++) {
                        final int index = i;
                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            departureArray[i][0],  // Flight number
                            departureArray[i][1],  // Airplane make
                            departureArray[i][2],  // Type (Cargo, Private, or Commercial)
                            departureArray[i][3],  // Departure time
                            departureArray[i][4],  // Landing time
                            departureArray[i][5],  // Flight duration
                            departureArray[i][6],
                            departureArray[i][8] //
                        );
            
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        flightHBox.getChildren().addAll(flightDetailsLabel);
                        departureListView.getItems().add(flightHBox);
                        }

            
                    
                    // Fill landing  list
                    for (int i = 0; i < AirportManagementSystem.landingQueue.getSize(); i++) {
                        final int index = i;
                        String row = String.format(
                            "Flight Number: %-5s | Airplane Make: %-25s | Type: %-20s | Departure Time: %-20s | Landing Time: %-20s | Duration: %-15s | isLate: %-15s | additionalInfo: %-20s ",
                            landingArray[i][0],  // Flight number
                            landingArray[i][1],  // Airplane make
                            landingArray[i][2],  // Type (Cargo, Private, or Commercial)
                            landingArray[i][3],  // Departure time
                            landingArray[i][4],  // Landing time
                            landingArray[i][5],  // Flight duration
                            landingArray[i][6],  // IsLate
                            landingArray[i][8]   // Additional Information
                            		
                        );
                        HBox flightHBox = new HBox();
                        Label flightDetailsLabel = new Label(row);
                        flightHBox.getChildren().addAll(flightDetailsLabel);
                        landingListView.getItems().add(flightHBox);
                        
                    
                 }
                    layout.getChildren().addAll(
                            departureLabel,
                            departureListView,
                            landingLabel,
                            landingListView
                    );
                    
                    Scene scene = new Scene(layout, 1250, 450);
                    stageType.setTitle(type+" Flights Only");
                    stageType.setScene(scene);
                    scene.getStylesheets().add("javaFxcss.css");
                    stageType.show();
                    

                }
            }
            
            
            
            public class AddFlightMenu {

                private static Runnable disp;
                private static AirportManagementSystem ams; 

                public AddFlightMenu(Runnable disp, AirportManagementSystem ams) {
                    MainMenuPanel.AddFlightMenu.disp = disp;
                    MainMenuPanel.AddFlightMenu.ams = ams;
                }
                                    
                                    
                public static void showAddFlightWindow() {
                
                    AirportManagementSystem ams = new AirportManagementSystem();
                    Stage addFlightStage = new Stage(); // Create a new window
                    addFlightStage.setTitle("Add Flight");
                
                    // Create a VBox layout
                    VBox vbox = new VBox(10);
                

                    Label flightNumLabel = new Label("Flight Number:");
                    TextField flightNumField = new TextField();
                    vbox.getChildren().addAll(flightNumLabel, flightNumField);
    
                    Label airplaneMakeLabel = new Label("Airplane Make:");
                    TextField airplaneMakeField = new TextField();
                    vbox.getChildren().addAll(airplaneMakeLabel, airplaneMakeField);
                

                    Label flightTypeLabel = new Label("Flight Type:");
                
                    //radio buttons 
                    RadioButton commercialRadioButton = new RadioButton("Commercial");
                    RadioButton privateRadioButton = new RadioButton("Private");
                    RadioButton cargoRadioButton = new RadioButton("Cargo");
                
                    
                    ToggleGroup flightTypeGroup = new ToggleGroup();
                    commercialRadioButton.setToggleGroup(flightTypeGroup);
                    privateRadioButton.setToggleGroup(flightTypeGroup);
                    cargoRadioButton.setToggleGroup(flightTypeGroup);
                
                    vbox.getChildren().addAll(flightTypeLabel, commercialRadioButton, privateRadioButton, cargoRadioButton);
                
                    Label additionalInfoLabel = new Label("Extra Information: ");
                    vbox.getChildren().add(additionalInfoLabel);
                
                    //actions based on a flight type
                    commercialRadioButton.setOnAction(e -> showCommercialPopup(additionalInfoLabel));
                    privateRadioButton.setOnAction(e -> showPrivatePopup(additionalInfoLabel));
                    cargoRadioButton.setOnAction(e -> showCargoPopup(additionalInfoLabel));
                
        
                    Label departureTimeLabel = new Label("Departure Time:");
                    TextField departureTimeField = new TextField();
                    vbox.getChildren().addAll(departureTimeLabel, departureTimeField);
                
            
                    Label landingTimeLabel = new Label("Landing Time:");
                    TextField landingTimeField = new TextField();
                    vbox.getChildren().addAll(landingTimeLabel, landingTimeField);

        
                    Label flightDurationLabel = new Label("Flight Duration:");
                    TextField flightDurationField = new TextField();
                    vbox.getChildren().addAll(flightDurationLabel, flightDurationField);

                    
                    Button addFlightButtonDP = new Button("Add Flight to Departure List");
                    Button addFlightButtonLD = new Button("Add Flight to Landing List");

                    addFlightButtonDP.setOnAction(e -> {
                    	try {
                            int flightNum = Integer.parseInt(flightNumField.getText());
                            String airplaneMake = airplaneMakeField.getText();
                            int departureTime = Integer.parseInt(departureTimeField.getText());
                            int landingTime = Integer.parseInt(landingTimeField.getText());
                            int flightDuration = Integer.parseInt(flightDurationField.getText());;
                            String additionalInfo = additionalInfoLabel.getText();
                            int additionalInfo1 = 0;
                            String flightType = "";
                            Boolean isLate = true;
                            int lineNumber= 1;

                            if (commercialRadioButton.isSelected()) {
                                flightType = "Commercial";
                                additionalInfo1 = Integer.parseInt(additionalInfoLabel.getText());
                            } else if (privateRadioButton.isSelected()) {
                                flightType = "Private";
                            } else if (cargoRadioButton.isSelected()) {
                                flightType = "Cargo";
                                additionalInfo1 = Integer.parseInt(additionalInfoLabel.getText());
                            }

                            AirportManagementSystem.displayQueues();
                            String fileName = "departureFlights.txt";
                            

                            if (flightType == "Cargo") {
                                Airplane Plane = new CargoPlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo1);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo1);
                                
                            } else if (flightType == "Private") {
                                Airplane Plane = new PrivatePlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile1(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo);
                                
                            } else if (flightType == "Commercial") {
                                Airplane Plane = new CommercialPlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo1);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo1);
                                
                        }
                        System.out.println("New flight added successfully");
                        AirportManagementSystem.displayQueues();
                        try {
                        Socket socket = new Socket("localhost", 21312);
                		Scanner scanner = new Scanner(System.in);
                		scanner.close();
                		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                		out.println("dp");
                		out.close();
                        socket.close();
                        }catch(Exception e2) {System.out.println("Server not found.");}
                        addFlightStage.close();
                    	}
                    	catch(Exception e2) {
                    		System.out.println("The details are not in correct format, retry and check again.");
                    		Stage errStage = new Stage();
                    		Label errLabel = new Label("All Details are not Filled!!");
                    		errLabel.setTextFill(Color.RED);
                    		errLabel.setFont(new Font("Century", 20));
                    		errLabel.autosize();
                    		errLabel.setAlignment(Pos.CENTER);
                    		HBox hb = new HBox(errLabel);
                    		hb.setAlignment(Pos.CENTER);
                    		Scene errScene = new Scene(hb, 300, 100);

                            errScene.getStylesheets().add("javaFxcss.css");
                    		errStage.setScene(errScene);
                    		errStage.show();
                    		errStage.setTitle("Error");
                    		Platform.runLater(() ->  {
                    			try {
									Thread.sleep(1200);
								} catch (InterruptedException e1) {}
                    			errStage.close();
                    			});
                    	}


                                
                    });
                    
                    addFlightButtonLD.setOnAction(e -> {
                    	try {
                            int flightNum = Integer.parseInt(flightNumField.getText());
                            String airplaneMake = airplaneMakeField.getText();
                            int departureTime = Integer.parseInt(departureTimeField.getText());
                            int landingTime = Integer.parseInt(landingTimeField.getText());
                            int flightDuration = Integer.parseInt(flightDurationField.getText());;
                            String additionalInfo = additionalInfoLabel.getText();
                            int additionalInfo1 = 0;
                            String flightType = "";
                            Boolean isLate = true;
                            int lineNumber= 1;

                            if (commercialRadioButton.isSelected()) {
                                flightType = "Commercial";
                                additionalInfo1 = Integer.parseInt(additionalInfoLabel.getText());
                            } else if (privateRadioButton.isSelected()) {
                                flightType = "Private";
                            } else if (cargoRadioButton.isSelected()) {
                                flightType = "Cargo";
                                additionalInfo1 = Integer.parseInt(additionalInfoLabel.getText());
                            }

                            AirportManagementSystem.displayQueues();
                            String fileName = "landingFlights.txt";
                            

                            if (flightType == "Cargo") {
                                Airplane Plane = new CargoPlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo1);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo1);
                                
                            } else if (flightType == "Private") {
                                Airplane Plane = new PrivatePlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile1(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo);
                                
                            } else if (flightType == "Commercial") {
                                Airplane Plane = new CommercialPlane(flightNum, airplaneMake, flightType, departureTime, landingTime, flightDuration, isLate, lineNumber, additionalInfo1);
                                ams.addQueue(Plane);
                                AirportManagementSystem.addFlightToFile(fileName, flightNum, airplaneMake,flightType, departureTime, landingTime, flightDuration, isLate, additionalInfo1);
                                
                        }
                        System.out.println("New flight added successfully");
                        AirportManagementSystem.displayQueues();
                        try {
                            Socket socket = new Socket("localhost", 21312);
                    		Scanner scanner = new Scanner(System.in);
                    		scanner.close();
                    		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    		out.println("ld");
                    		out.close();
                            socket.close();
                            }catch(Exception e2) {System.out.println("Server not found.");} 
                            addFlightStage.close();
                    	}
                    	catch(Exception e2) {
                    		System.out.println("The details are not in correct format, retry and check again.");
                    		Stage errStage = new Stage();
                    		Label errLabel = new Label("All Details are not Filled!!");
                    		errLabel.setTextFill(Color.RED);
                    		errLabel.setFont(new Font("Century", 20));
                    		errLabel.autosize();
                    		errLabel.setAlignment(Pos.CENTER);
                    		HBox hb = new HBox(errLabel);
                    		hb.setAlignment(Pos.CENTER);
                    		Scene errScene = new Scene(hb, 300, 100);
                    		errStage.setScene(errScene);
                            
                    		errScene.getStylesheets().add("javaFxcss.css");
                    		errStage.show();
                    		errStage.setTitle("Error");
                    		Platform.runLater(() ->  {
                    			try {
									Thread.sleep(1200);
								} catch (InterruptedException e1) {}
                    			errStage.close();
                    			});
                    	}       
                    });
                    

            vbox.getChildren().add(addFlightButtonDP);
            vbox.getChildren().add(addFlightButtonLD);


            Scene addFlightScene = new Scene(vbox, 700, 520);
            addFlightStage.setScene(addFlightScene);
            addFlightScene.getStylesheets().add("javaFxcss.css");
            addFlightStage.show();
        }

    
                
    //POPUP STAGE FOR ADDITIONAL FIELD BASED ON SELECTING TYPE AS COMMERCIAL

                /**
                 * This popups an additional stage to show a text field to input the name of the owner of the private flight
                 * @param additionalInfoLabel it takes the info about the number of passengers in the plane
                 */
    private static void showCommercialPopup(Label additionalInfoLabel) {
        // Popup for Commercial Flight Type
        Stage popup = new Stage();
        popup.setTitle("Commercial Flight Info");

        VBox popupLayout = new VBox(10);
        Label closingLabel = new Label("Closing in 5 seconds...");
        Label passengerCountLabel = new Label("Passenger Count:");
        TextField passengerCountField = new TextField();
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String passengerCount = passengerCountField.getText();
            additionalInfoLabel.setText(passengerCount);
            popup.close();
        });
        popupLayout.getChildren().addAll(passengerCountLabel, passengerCountField,closingLabel,saveButton);

        Scene popupScene = new Scene(popupLayout, 200, 200);
        popupScene.getStylesheets().add("javaFxcss.css");
        popup.setScene(popupScene);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> popup.close());// Close the popup after the specified time
            }
        }, 5000);
        popup.show();
    }

    /**
     * This popups an additional stage to show a text field to input the name of the owner of the private flight
     * @param additionalInfoLabel it takes the info about the name of the owner of the private flight
     */
    //POPUP STAGE FOR ADDITIONAL FIELD BASED ON SELECTING TYPE AS PRIVATE
    private static void showPrivatePopup(Label additionalInfoLabel) {
        Stage popup = new Stage();
        popup.setTitle("Private Flight Info");

        VBox popupLayout = new VBox(10);
        Label closingLabel = new Label("Closing in 5 seconds...");
        Label ownerNameLabel = new Label("Owner's Name:");
        TextField ownerNameField = new TextField();
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String ownerName = ownerNameField.getText();
            additionalInfoLabel.setText("Owner: " + ownerName);
            popup.close();
        });
        popupLayout.getChildren().addAll(ownerNameLabel, ownerNameField,closingLabel,saveButton);

        Scene popupScene = new Scene(popupLayout, 200, 200);
        popup.setScene(popupScene);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> popup.close()); // Close the popup after the specified time
            }
        }, 5000);
        popup.show();
    }

    /**
     * This popups an additional stage to show a text field to input the cargo weight
     * @param additionalInfoLabel it takes the info about the weight of the cargo
     */
    //POPUP STAGE FOR ADDITIONAL FIELD BASED ON SELECTING TYPE AS CARGO
    private static void showCargoPopup(Label additionalInfoLabel) {
        // Popup for Cargo Flight Type
        Stage popup = new Stage();
        popup.setTitle("Cargo Flight Info");

        VBox popupLayout = new VBox();
        Label closingLabel = new Label("Closing in 5 seconds...");
        Label cargoWeightLabel = new Label("Cargo Weight:");
        TextField cargoWeightField = new TextField();
        Button saveButton = new Button("Save");

        saveButton.setOnAction(e -> {
            String cargoWeight = cargoWeightField.getText();
            additionalInfoLabel.setText(cargoWeight);
            popup.close();
        });
        popupLayout.getChildren().addAll(cargoWeightLabel, cargoWeightField,closingLabel,saveButton);

        Scene popupScene = new Scene(popupLayout, 200, 200);
        popup.setScene(popupScene);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                 Platform.runLater(() -> popup.close());// Close the popup after the specified time
            }
        }, 5000);
        popup.show();
    }
}

	

    // What's this main method for?
            //I agree whats this for....(Adil)
//    public static void main(String[] args) {
//        AirportManagementSystem ams = new AirportManagementSystem();
//        ams.loadFlightData("departureFlights.txt");
//        ams.loadFlightData("landingFlights.txt");
//        String[][] dpArray = AirportManagementSystem.departureArray();
//        String[][] ldArray = AirportManagementSystem.landingArray();
//        int dpSize = AirportManagementSystem.departureQueue.getSize();
//        for (int i = 0; i < dpSize; i++) {
//            HBox hb = HBoxFactory();
//            for (int j = 0; j < 9; j++) {
//                String addStr;
//                addStr = dpArray[i][j];
//                Label l = labelFactory(addStr);
//                hb.getChildren().add(l);
//            }
//        }
//    }

            /**
             * used to create a label 
             * @param name gets the text name for the label
             * @return returns the label
             */
    public static Label labelFactory(String name) {
        Label l = new Label(name);
        Color color = Color.DARKSLATEBLUE;
        l.setFont(new Font("Comic Sans", 20));
        l.setTextFill(color);
        l.setPadding(new Insets(5));
        return l;
    }

    public VBox VBoxFactory() {
        VBox vb = new VBox();
        return vb;
    }

    public static HBox HBoxFactory() {
        HBox hb = new HBox();
        return hb;
    }

    @Override
    public void run() {
        launch();
    }
}
