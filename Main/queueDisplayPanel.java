import java.lang.reflect.Method;

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


@SuppressWarnings("unused")
public class queueDisplayPanel implements Runnable {
    private static Stage stage;
    private static VBox mainVB;

    public queueDisplayPanel() {
        // Empty constructor
    }
    
    public void close() {
        if (stage != null) {
            stage.close();
        }
    }
            
    public static void clear() {
        Platform.runLater(() -> {
            // Close old stage if it exists
            if (stage != null) {
                stage.close();
            }

            // Create fresh stage and components
            stage = new Stage();
            mainVB = new VBox();
            Scene scene = new Scene(mainVB, 700, 250);
            stage.setScene(scene);
            stage.setTitle("Flight Queue");

            // Create display content
            VBox vbDeparture = new VBox();
            
            String[][] dpArray = AirportManagementSystem.departureArray();
            int dpSize = AirportManagementSystem.departureQueue.getSize();

            for (int i = 0; i < dpSize; i++) {
                HBox hb = HBoxFactory();
                for (int j = 0; j < 9; j++) {
                    Label l = labelFactory(dpArray[i][j]);
                    hb.getChildren().add(l);
                }
                vbDeparture.getChildren().add(hb);
            }

            mainVB.getChildren().add(vbDeparture);
            stage.show();
        });
    }
		

	
//	Scene s = new Scene(mainVB, 700, 250);

	
	
	public static Label labelFactory(String name) {
		Label l = new Label(name);
		Color color = Color.DARKSLATEBLUE;
		l.setFont(new Font("Comic Sans", 20));
		l.setTextFill(color);
		l.setPadding(new Insets(5));
		return l;
		
	}
	
	public static Label stageFactory(String name) {
		Stage s = new Stage();
		return null;
	}
	
	public static VBox VBoxFactory() {
		VBox vb = new VBox();
		return vb;
	}
	
	public static Scene scenceFactory(VBox vb) {
		Scene s = new Scene(vb, 700, 250);
		return s;
		
	}
	
	public static HBox HBoxFactory() {
		HBox hb = new HBox();
		return hb;	
	}
	
	@Override
	public void run() {
		clear();
		
	}
}

