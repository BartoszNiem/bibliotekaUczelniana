package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainScreen.fxml"));
			StackPane root = loader.load();
			root.setPrefHeight(600);
			root.setPrefWidth(900);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Panel u¿ytkownika");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private static Pracownik obecnyPracownik = null;
	private static Student obecnyStudent = null;
	
	public static Student getObecnyStudent() {
		return obecnyStudent;
	}

	public static void setObecnyStudent(Student obecnyStudent) {
		Main.obecnyStudent = obecnyStudent;
	}
	
	public static Pracownik getObecnyPracownik() {
		return obecnyPracownik;
	}

	public static void setObecnyPracownik(Pracownik obecnyPracownik) {
		Main.obecnyPracownik = obecnyPracownik;
	}
}
