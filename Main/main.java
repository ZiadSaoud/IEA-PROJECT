package SmartAgent;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private double xOffset=0;
	private double yOffset=0;
	private AnchorPane root;
	@Override
	public void start(Stage primaryStage) {
		try {
			root = (AnchorPane)FXMLLoader.load(getClass().getResource("UI.fxml"));
			Scene scene = new Scene(root,1000,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
			root.setOnMousePressed(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
					
				}
			});
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					primaryStage.setX(arg0.getScreenX()-xOffset);
					primaryStage.setY(arg0.getScreenY()-yOffset);
					
				}
			});
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent arg0) {
					switch(arg0.getCode()) {
					case ESCAPE:
						try {
							root = (AnchorPane)FXMLLoader.load(getClass().getResource("UI.fxml"));
							root.setOnMousePressed(new EventHandler<MouseEvent>() {

								@Override
								public void handle(MouseEvent event) {
									xOffset = event.getSceneX();
									yOffset = event.getSceneY();
									
								}
							});
							root.setOnMouseDragged(new EventHandler<MouseEvent>() {

								@Override
								public void handle(MouseEvent arg0) {
									primaryStage.setX(arg0.getScreenX()-xOffset);
									primaryStage.setY(arg0.getScreenY()-yOffset);
									
								}
							});
							scene.setRoot(root);
							System.out.println("reset done");
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
					default:
						break;
					}
					
				}
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
