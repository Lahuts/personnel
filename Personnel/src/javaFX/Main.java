package javaFX;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.*;

public class Main extends Application {

	@Override
	public void start(Stage outStage) throws IOException {
		  // set up the scene
		  FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
		  Parent root = loader.load();
		  Scene scene = new Scene(root);
		  
		  outStage.getIcons().add(new Image(this.getClass().getResourceAsStream("m2l.png")));
		  outStage.setScene(scene);
		  outStage.setTitle("Connexion M2L Ligue");
		  outStage.setResizable(false);
		  outStage.show();
	}

    public static void main(String[] args) {
        launch(args);
    }

}