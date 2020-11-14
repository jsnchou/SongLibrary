/**
 * Jason Chou
 * Ty Goldin
 * Group#44
 * 
 */

package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.SongLibController;

public class SongLib extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SongLib.class.getResource("/view/songLib.fxml"));
		
		BorderPane mainBorderPane = (BorderPane)loader.load();
		
		SongLibController listController = loader.getController();
		listController.start();
		
		primaryStage.setScene(new Scene(mainBorderPane));
		primaryStage.setTitle("Song Library");
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				listController.shutDown();
				Platform.exit();
				System.exit(0);
			}
		});
	}
	
}
