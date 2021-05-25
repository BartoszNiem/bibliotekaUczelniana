package Kontroler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Dao.BookDao;
import Dao.PracownikDao;
import Dao.StatystykiDao;
import Dao.StudentDao;
import application.Book;
import application.Main;
import application.Pracownik;
import application.Student;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MenadzerHomeController implements Initializable {

	private StudentDao studentDao;
	private PracownikDao pracownikDao;
	private StatystykiDao statystykiDao;
	private BookDao bookDao;
	private MainController mainController;

	@FXML
	private TableView<Student> tableStudenci;
	@FXML
	private TableColumn<Student, Integer> IDStudenta;
	@FXML
	private TableColumn<Student, String> ImieStudenta;
	@FXML
	private TableColumn<Student, String> NazwiskoStudenta;
	@FXML
	private TableColumn<Student, String> PeselStudenta;
	@FXML
	private TableColumn<Student, String> EmailStudenta;
	@FXML
	private Button btnDelStudent;
	@FXML
	private Button btnDelPracownik;

	@FXML
	private TextField txtNowyImie;

	@FXML
	private TextField txtNowyNazwisko;

	@FXML
	private TextField txtNowyPesel;

	@FXML
	private TextField txtNowyEmail;

	@FXML
	private TextField txtNowyHaslo;

	@FXML
	private TableView<Pracownik> tablePracownicy;

	@FXML
	private TableColumn<Pracownik, Integer> colIDPrac;

	@FXML
	private TableColumn<Pracownik, String> colImiePrac;

	@FXML
	private TableColumn<Pracownik, String> colNazwiskoPrac;

	@FXML
	private TableColumn<Pracownik, String> colRolaPrac;

	@FXML
	private TableColumn<Pracownik, String> colPeselPrac;

	@FXML
	private TableColumn<Pracownik, String> colEmailPrac;

	@FXML
	private TextField txtImiePrac;

	@FXML
	private TextField txtNazwiskoPrac;

	@FXML
	private TextField txtPeselPrac;

	@FXML
	private TextField txtRolaPrac;

	@FXML
	private TextField txtEmailPrac;

	@FXML
	private TextField txtHasloPrac;

	@FXML
	private Label lblStatusDodaniaPracownika;

	@FXML
	private Label lblLiczbaWYPKM;

	@FXML
	private Label lblLiczbaZKM;

	@FXML
	private Label lblWKR;

	@FXML
	private Label lblIDNWK;

	@FXML
	private Label lblIDNRK;

	@FXML
	private Label lblIDTNRK;

	@FXML
	private Label lblDNRK;

	@FXML
	private GridPane gridPane;

	@FXML
	public void pokazStatystyki(ActionEvent e) {
		String liczbaWypozyczonychKsiazek = " " + statystykiDao.getLiczbaWypKsiazek();
		lblLiczbaWYPKM.setText(liczbaWypozyczonychKsiazek);
		String liczbaZwroconychKsiazek = " " + statystykiDao.getLiczbaZwrKsiazek();
		lblLiczbaZKM.setText(liczbaZwroconychKsiazek);
		String liczbaPrzetrzymanychKsiazek = " " + statystykiDao.getLiczbaPrzetrzymanychKsiazek();
		lblWKR.setText(liczbaPrzetrzymanychKsiazek);
		ArrayList<Integer> zwrot = statystykiDao.getMaxIDKsiazki();
		int IDKsiazki = zwrot.get(0);
		int liczbaWypozyczen = zwrot.get(1);
		Book ksiazka = bookDao.ksiazkaPoID(IDKsiazki);
		if (!(ksiazka == null)) {
			String najczesciejWypozyczanaKsiazka = " ID: " + IDKsiazki + "	Tytul:  " + ksiazka.getTytul()
					+ "	Liczba wypo¿yczeñ:  " + liczbaWypozyczen;
			lblIDNWK.setText(najczesciejWypozyczanaKsiazka);
		}
		ArrayList<Book> najczesciejRezerwowaneKsiazki = statystykiDao.get3NAjRezKsiazkii();
		if (najczesciejRezerwowaneKsiazki.size() != 0) {
			String najczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(0).getId()
					+ "	Tytul:  " + najczesciejRezerwowaneKsiazki.get(0).getTytul() + "   Liczba rezerwacji:  "
					+ najczesciejRezerwowaneKsiazki.get(0).getLiczbaRezerwacji();
			lblIDNRK.setText(najczesciejRezerwowanaKsiazka);
			String drugaNajczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(1).getId()
					+ "	Tytul: " + najczesciejRezerwowaneKsiazki.get(1).getTytul() + "	 Liczba rezerwacji:  "
					+ najczesciejRezerwowaneKsiazki.get(1).getLiczbaRezerwacji();

			lblDNRK.setText(drugaNajczesciejRezerwowanaKsiazka);
			String trzeciaNajczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(2).getId()
					+ "	Tytul: " + najczesciejRezerwowaneKsiazki.get(2).getTytul() + "	Liczba rezerwacji:  "
					+ najczesciejRezerwowaneKsiazki.get(2).getLiczbaRezerwacji();
			lblIDTNRK.setText(trzeciaNajczesciejRezerwowanaKsiazka);
		}
		tablePracownicy.setVisible(false);
		btnDelPracownik.setVisible(false);

		tableStudenci.setVisible(false);
		btnDelStudent.setVisible(false);
		gridPane.setVisible(true);
	}

	ObservableList<Student> listaStudentow = null;
	ObservableList<Pracownik> listaPracownikow = null;
	private static boolean czyInit = false;

	public MenadzerHomeController() {
		studentDao = new StudentDao();
		pracownikDao = new PracownikDao();
		statystykiDao = new StatystykiDao();
		bookDao = new BookDao();
	}

	@FXML
	public void listaS(ActionEvent e) {
		listaStudentow = studentDao.listaStudentow();
		tableStudenci.setItems(listaStudentow);
		gridPane.setVisible(false);
		tablePracownicy.setVisible(false);
		btnDelPracownik.setVisible(false);

		tableStudenci.setVisible(true);
		btnDelStudent.setVisible(true);

	}

	@FXML
	public void listaP(ActionEvent e) {
		listaPracownikow = pracownikDao.listaPracownikow();
		tablePracownicy.setItems(listaPracownikow);
		gridPane.setVisible(false);
		tableStudenci.setVisible(false);
		btnDelStudent.setVisible(false);
		tablePracownicy.setVisible(true);
		btnDelPracownik.setVisible(true);
	}

	@FXML
	public void usunWybrane(ActionEvent e) throws ClassNotFoundException {
		studentDao.usunStudenta(tableStudenci.getSelectionModel().getSelectedItem());
		tableStudenci.getItems().removeAll(tableStudenci.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void usunWybranegoPracownika(ActionEvent e) throws ClassNotFoundException {
		pracownikDao.usunPracownika(tablePracownicy.getSelectionModel().getSelectedItem());
		tablePracownicy.getItems().removeAll(tablePracownicy.getSelectionModel().getSelectedItem());
	}

	@FXML
	public void dodajStudenta(ActionEvent e) throws ClassNotFoundException {
		String imie = txtNowyImie.getText();
		String nazwisko = txtNowyNazwisko.getText();
		String pesel = txtNowyPesel.getText();
		String email = txtNowyEmail.getText();
		String haslo = txtNowyHaslo.getText();
		Student nowyStudent = new Student(imie, nazwisko, pesel, email, haslo);
		studentDao.dodajStudenta(nowyStudent);
	}

	@FXML
	public void pokazFormularzDodaniaStudenta(ActionEvent e) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DodajStudenta.fxml"));
		Scene scene = new Scene(root, 435, 604);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	public void dodajPracownika(ActionEvent e) throws ClassNotFoundException {
		String imie = txtImiePrac.getText();
		String nazwisko = txtNazwiskoPrac.getText();
		String pesel = txtPeselPrac.getText();
		String rola = txtRolaPrac.getText();
		String email = txtEmailPrac.getText();
		String haslo = txtHasloPrac.getText();
		Pracownik nowyPracownik = new Pracownik(imie, nazwisko, pesel, rola, email, haslo);
		pracownikDao.dodajPracownika(nowyPracownik);
		lblStatusDodaniaPracownika.setText("Pomyœlnie dodano pracownika!");
	}

	@FXML
	public void pokazFormularzDodaniaPracownika(ActionEvent e) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DodajPracownika.fxml"));
		Scene scene = new Scene(root, 422, 637);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (czyInit == false) {
			listaStudentow = studentDao.listaStudentow();
			listaPracownikow = pracownikDao.listaPracownikow();
			statystykiDao = new StatystykiDao();
			bookDao = new BookDao();
			IDStudenta.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
			ImieStudenta.setCellValueFactory(new PropertyValueFactory<Student, String>("imie"));
			NazwiskoStudenta.setCellValueFactory(new PropertyValueFactory<Student, String>("nazwisko"));
			PeselStudenta.setCellValueFactory(new PropertyValueFactory<Student, String>("pesel"));
			EmailStudenta.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
			tableStudenci.setItems(listaStudentow);

			colIDPrac.setCellValueFactory(new PropertyValueFactory<Pracownik, Integer>("id"));
			colImiePrac.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("imie"));
			colNazwiskoPrac.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("nazwisko"));
			colPeselPrac.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("pesel"));
			colRolaPrac.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("rola"));
			colEmailPrac.setCellValueFactory(new PropertyValueFactory<Pracownik, String>("email"));
			tablePracownicy.setItems(listaPracownikow);
			czyInit = true;
		}

	}

	@FXML
	public void wyloguj() {
		mainController.loadLoginScreen();
		Main.setObecnyPracownik(null);
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
}
