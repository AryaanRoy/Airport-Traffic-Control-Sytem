/**
 * The main class where GUI threads are being called and run
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 * */
public class AirportGUI {

	public static void main(String[] args) {
		Runnable mainPanel = new MainMenuPanel();
		Thread mainThread = new Thread(mainPanel);
		mainThread.start();
		System.out.println("AirportGUI is running");
	}

}
