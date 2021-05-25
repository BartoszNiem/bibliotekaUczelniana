package Kontroler;

import java.io.IOException;
import Dao.LoginDao;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class LoginController {

	private LoginDao loginDao;
	private MainController mainController;

	@FXML
	private TextField txtEmail;
	@FXML
	private PasswordField passField;

	public LoginController() {
		this.loginDao = new LoginDao();
	}

	@FXML
	public void zaloguj() {

		if (txtEmail.getText().contains("admin")) {
			if (loginDao.zalogujA(txtEmail.getText(), passField.getText()) == true) {
				if (Main.getObecnyPracownik().getRola().equals("menad¿er")) {
					FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MenadzerHome.fxml"));
					AnchorPane pane = null;
					try {
						pane = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					MenadzerHomeController controller = loader.getController();
					controller.setMainController(mainController);
					mainController.setScreen(pane);
					System.out.println("Zalogowano!");
				} else {
					FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/PracownikHome1.fxml"));
					StackPane pane = null;
					try {
						pane = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					PracownikCOntroller controller = loader.getController();
					controller.setMainController(mainController);
					mainController.setScreen(pane);
					System.out.println("Zalogowano!");
				}
			}
		} else if (loginDao.zaloguj(txtEmail.getText(), passField.getText())) {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/UzytkownikHome.fxml"));
			StackPane pane = null;
			try {
				pane = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			UzytkownikHomeController controller = loader.getController();
			controller.setMainController(mainController);
			mainController.setScreen(pane);
		}
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
