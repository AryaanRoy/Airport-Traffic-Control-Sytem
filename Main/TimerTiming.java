import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * @author Roy Aryaan
 * @author Almaz Alikhan 
 * @author Elias Mahmoud Elias Al Dina
 * @author Mohammed Adil
 * */
@SuppressWarnings("unused")
public class TimerTiming implements Runnable {
	
//	private static Stage stage;
	public static int timer = 1000;
	

	public TimerTiming() {
	}
	
//	public void timer() {
//		Label timerL = new Label();
//		Scene timerS = new Scene(timerL, 100, 100);
//		stage = new Stage();
//		stage.setScene(timerS);
//		stage.setAlwaysOnTop(true);
//		stage.setTitle("TimerTiming");
//		int timer = 0;
//		while(true) {
//			try {
//			stage.show();}
//			catch(Exception e) {}
//			timer++;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			timerL.setText(Integer.toString(timer));
//		
//		}	
//		
//	}
	
	
	
	
	public static int getTimer() {
		return timer;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized((Integer) timer) {
//			System.out.println("Time: "+ timer);
			try {
				Thread.sleep(200);
				timer += 15;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
	}

	public static void startTimer() {
        TimerTiming timerTiming = new TimerTiming();
        Thread timerThread = new Thread(timerTiming);
        timerThread.start(); 
    }
}
	
