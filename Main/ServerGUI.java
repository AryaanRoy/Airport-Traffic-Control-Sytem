import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * This class implements the working of a server
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 */
class TopServer implements Runnable {
	/**
	 *  static dpCounter that will be used as a counter for the number of schedules in departures
	 */
	static int dpCounter = 0;
	/**
	 *  static ldCounter that will be used as a counter for the number of schedules in landing
	 */
	static int ldCounter = 0;
	/**
	 * variable to keep a while loop in check
	 */
	static boolean run=true;
	/**
	 * Variable that setups serverSocket
	 */
	static ServerSocket serverSocket;
	/**
	 * Variable that setups clientSocket
	 */
	static Socket clientSocket;

	/**
	 * This method closes the server and client when called
	 * @throws IOException this exception is thrown when client or server cant be closed
	 */
	public static void closeServer() throws IOException {
		run=false;
		try {
		serverSocket.close();
		clientSocket.close();}
		catch(Exception e) {}

	}
	/*
	 * this method where the main working of server is
	 */
	public static void server() {
		try {
			while(run) {	
				serverSocket = new ServerSocket(21312);
				System.out.println("Server started...");
				clientSocket = serverSocket.accept();
				System.out.println("Client connected. ");
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String countOf = in.readLine();
				System.out.println(countOf);
				if(countOf.contains("dp")) {
					dpCounter++;
					Platform.runLater(() ->  {
					ServerGUI.dpCounterLabel.setText(Integer.toString(dpCounter));});
				}
				else if(countOf.contains("ld")) {
					ldCounter++;
					Platform.runLater(() ->  {
					ServerGUI.ldCounterLabel.setText(Integer.toString(ldCounter));});
				}
				else {
					System.out.println("Wrong type detected");
				}
				System.out.println(dpCounter);
				System.out.println(ldCounter);
				in.close();
				
				
				serverSocket.close();
				clientSocket.close();

			}	
	}
		catch(IOException e) {
			System.out.println("Server has shutdown.");
		}
		
	}

	/**
	 * this method run is from the implemented interface runnable, runs the method server()
	 */
	@Override
	public void run() {
		server();
	}
}
/**
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 */
public class ServerGUI extends Application {
	/**
	 * A label for the GUI
	 */
	static Label dpCounterLabel = new Label("0");
	/**
	 * A label for the GUI
	 */
	static Label ldCounterLabel = new Label("0");
	
	/**
	 * Where most GUI is in
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		HBox hbDp = new HBox();
		HBox hbLd = new HBox();
		Stage stg = new Stage();
		VBox vbMain = new VBox();
		stg.setTitle("Server");
		
		Scene st = new Scene(vbMain, 250, 50);
		
		Label dp = new Label("Departure Re-Schedules: ");
		Label ld = new Label("Landing Re-Schedules: ");
		
		hbDp.getChildren().addAll(dp, dpCounterLabel);
		hbLd.getChildren().addAll(ld, ldCounterLabel);

		vbMain.getChildren().addAll(hbDp, hbLd);

		
		stg.setScene(st);
		st.getStylesheets().add("javaFxcss.css");
		stg.show();

		
	}
	/**
	 * method to call the launch() in this mehtod
  	 */
	public static void gui() {
		launch();
	}
	/**
	 * main mehtod
	 * @param args no args
	 * @throws IOException when server is unable to be closed
	 */
	public static void main(String[] args) throws IOException {
		Runnable TopServer2 = new TopServer();
		Thread TopServerThread = new Thread(TopServer2);
		TopServerThread.start();
		gui();
		TopServer.closeServer();

		
		
	}

}

