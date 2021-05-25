package Kontroler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Dao.AudiobookDao;
import Dao.BookDao;
import Dao.MaterialyDao;
import Dao.RezerwacjaDao;
import Dao.StatystykiDao;
import Dao.ZamowienieDao;
import application.Audiobook;
import application.Book;
import application.Main;
import application.MaterialyPDF;
import application.Rezerwacja;
import application.Zamowienie;

import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PracownikCOntroller implements Initializable {

	private MaterialyDao materialyDao;
	private AudiobookDao audiobookDao;
	private MainController mainController;
	
	@FXML
	private ChoiceBox<String> jakSzukacK;
	@FXML
	private ChoiceBox<String> jakSzukacP;
	@FXML
	private ChoiceBox<String> jakSzukacA;
	@FXML
	private TextField wyszukaj;
	@FXML
	private TextField wyszukajA;


	@FXML
    private TableView<Book> tableBook;

    @FXML
    private TableColumn<Book, Integer> colIDBook;

    @FXML
    private TableColumn<Book, String> colTytulBook;

    @FXML
    private TableColumn<Book, String> colAutorzyBook;

    @FXML
    private TableColumn<Book, String> colWydawnictwoBook;

    @FXML
    private TableColumn<Book, Integer> colRokWydaniaBook;

    @FXML
    private TableColumn<Book, String> colDzialBook;

    @FXML
    private TableColumn<Book, Integer> colEgzemplarzeBook;

    @FXML
    private TextField egzemplarze;

    @FXML
    private TableView<Audiobook> tableAudiobook;

    @FXML
    private TableColumn<Audiobook, Integer> colIDAudiobook;

    @FXML
    private TableColumn<Audiobook, String> colTytulAudiobook;

    @FXML
    private TableColumn<Audiobook, String> colAutorzyAudiobook;

    @FXML
    private TableColumn<Audiobook, String> colWydawnictwoAudiobook;

    @FXML
    private TableColumn<Audiobook, Integer> colRokWydaniaAudiobook;

    @FXML
    private TableColumn<Audiobook, String> colDzialAudiobook;

    @FXML
    private TableView<MaterialyPDF> tableMaterialyPDF;

    @FXML
    private TableColumn<MaterialyPDF, Integer> colIDPDF;

    @FXML
    private TableColumn<MaterialyPDF, String> colTytulPDF;

    @FXML
    private TableColumn<MaterialyPDF, String> colAutorzyPDF;

    @FXML
    private TableColumn<MaterialyPDF, String> colWydPDF;

    @FXML
    private TableColumn<MaterialyPDF, Integer> colRokPDF;

    @FXML
    private TableColumn<MaterialyPDF, String> colDzialPDF;

    @FXML
    private TableView<Zamowienie> tableZAMOWIENIA;

    @FXML
    private TableColumn<Zamowienie, Integer> colIDZ;

    @FXML
    private TableColumn<Zamowienie, Integer> colIDStudZ;

    @FXML
    private TableColumn<Zamowienie, String> colDATAOZ;

    @FXML
    private TableColumn<Zamowienie, String> colDATAZWRZ;

    @FXML
    private TableColumn<Zamowienie, Double> colNALZ;

    @FXML
    private TableColumn<Zamowienie, String> colSTATUSZ;

    @FXML
    private TableView<Rezerwacja> tabelaRezerwacje;

    @FXML
    private TableColumn<Rezerwacja, Integer> colIDRezerwacja;

    @FXML
    private TableColumn<Rezerwacja, Integer> colIDStudentaRezerwacje;

    @FXML
    private TableColumn<Rezerwacja, Integer> colIDKsiazkiRezerwacja;

    @FXML
    private TableColumn<Rezerwacja, String> colTytulKsiazkiRezerwacja;

    @FXML
    private TableColumn<Rezerwacja, String> colDataRezerwacji;

    @FXML
    private TableColumn<Rezerwacja, String> colDostepnoscRezerwacje;

    @FXML
    private Label lblLiczbaWYPKM;

    @FXML
    private Label lblLiczbaZKM;

    @FXML
    private Label lblWKR;

    @FXML
    private Label lblLiczbaZKR;

    @FXML
    private Label lblIDNWK;

    @FXML
    private Label lblIDNRK;

    @FXML
    private Label lblIDTNRK;

    @FXML
    private Label lblDNRK;
    
    @FXML
    private TextField txtTytulPDF;

    @FXML
    private TextField txtAutorzyPDF;

    @FXML
    private TextField txtWYdawnictwoPDF;

    @FXML
    private TextField txtRokPDF;

    @FXML
    private TextField txtDzialPDF;

    @FXML
    private TextField txtSciezkaPDF;

    @FXML
    private Label lblStatusDodania;

    @FXML
    private Label lblStatusDodaniaAudiobooka;

    @FXML
    private TextField txtTytulAudiobooka;

    @FXML
    private TextField txtAutorzyAudiobook;

    @FXML
    private TextField txtWydawnictwoAudiobook;

    @FXML
    private TextField txtRokWydaniaAudiobook;

    @FXML
    private TextField txtDzialAudiobook;

    @FXML
    private TextField txtSciezkaAudiobook;
    
    @FXML
    private TextField txtTytulKsiazki;

    @FXML
    private TextField txtAutorzyKsiazki;

    @FXML
    private TextField txtWydawnictwoKsiazki;

    @FXML
    private TextField txtRokWydaniaKsiazki;

    @FXML
    private TextField txtDzialKsiazki;

    @FXML
    private TextField txtLiczbaEgz;

    @FXML
    private Label lblStatusDodaniaKsiazki;


	public PracownikCOntroller() {
		materialyDao = new MaterialyDao();
		audiobookDao = new AudiobookDao();
	}

	@FXML
	void dodajPDF(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
		String tytul = txtTytulPDF.getText();
		String autorzy = txtAutorzyPDF.getText();
		String wydawnictwo = txtWYdawnictwoPDF.getText();
		int rokWydania = Integer.parseInt(txtRokPDF.getText());
		String dzial = txtDzialPDF.getText();
		String uploadFilePath = txtSciezkaPDF.getText();
		InputStream artykul = new FileInputStream(new File(uploadFilePath));
		MaterialyPDF nowyPDF = new MaterialyPDF(tytul, autorzy, wydawnictwo, rokWydania, dzial);
		nowyPDF.setTrescIS(artykul);
		if(bookDao.getDzialID(dzial)<0) {
            bookDao.dodajDzial(dzial);
        }
        if(bookDao.getWydawnictwoID(wydawnictwo)<0) {
            bookDao.dodajWydawnictwo(wydawnictwo);
        }
		if (materialyDao.dodajMaterialPDF(nowyPDF) > 0) {
			lblStatusDodania.setText("Pomyœlnie dodano materia³ pdf!");

			String[] autorzyTab = autorzy.split(", ");
			for (String autor : autorzyTab) {
				if (materialyDao.getAutorID(autor) < 0) {
					bookDao.dodajAutora(autor);
				}
			}
			for (String autor : autorzyTab) {
				materialyDao.dodajAutoraPDF(materialyDao.getAutorID(autor), materialyDao.getMaxPdfID());
			}
		} else {
			lblStatusDodania.setText("B³ad podczas dodawania materia³ów pdf!");
		}
	}

	@FXML
	void pokazFormularzDodania(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DodajPDF.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void pobierzPDF(ActionEvent event) throws ClassNotFoundException, SQLException {
		materialyDao.pobierzMaterialPDF(tableMaterialyPDF.getSelectionModel().getSelectedItem().getId());
	}

	@FXML
	void usunPDF(ActionEvent event) throws ClassNotFoundException, SQLException {
		materialyDao.usunAutorPDF(tableMaterialyPDF.getSelectionModel().getSelectedItem().getId());
		materialyDao.usunPDF(tableMaterialyPDF.getSelectionModel().getSelectedItem().getId());
		tableMaterialyPDF.getItems().removeAll(tableMaterialyPDF.getSelectionModel().getSelectedItem());
	}

	@FXML
	void pobierzAudiobook(ActionEvent event) throws ClassNotFoundException, SQLException {
		audiobookDao.pobierzAudiobook(tableAudiobook.getSelectionModel().getSelectedItem().getId());
	}

	@FXML
	void pokazFormularzDodaniaAudiobooka(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DodajAudiobook.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void dodajAudiobook(ActionEvent event) throws FileNotFoundException, ClassNotFoundException, SQLException {
		String tytul = txtTytulAudiobooka.getText();
		String autorzy = txtAutorzyAudiobook.getText();
		String wydawnictwo = txtWydawnictwoAudiobook.getText();
		int rokWydania = Integer.parseInt(txtRokWydaniaAudiobook.getText());
		String dzial = txtDzialAudiobook.getText();
		String uploadFilePath = txtSciezkaAudiobook.getText();
		InputStream audio = new FileInputStream(new File(uploadFilePath));
		Audiobook nowyAudiobook = new Audiobook(tytul, autorzy, wydawnictwo, rokWydania, dzial);
		nowyAudiobook.setTrescIS(audio);
		if(bookDao.getDzialID(dzial)<0) {
            bookDao.dodajDzial(dzial);
        }
        if(bookDao.getWydawnictwoID(wydawnictwo)<0) {
            bookDao.dodajWydawnictwo(wydawnictwo);
        }
		if (audiobookDao.dodajAudiobook(nowyAudiobook) > 0) {
			String[] autorzyTab = autorzy.split(", ");
			for (String autor : autorzyTab) {
				if (audiobookDao.getAutorID(autor) < 0) {
					bookDao.dodajAutora(autor);
				}
			}
			for (String autor : autorzyTab) {
				audiobookDao.dodajAutoraAudiobook(audiobookDao.getAutorID(autor), audiobookDao.getMaxAudiobookID());
			}
			lblStatusDodaniaAudiobooka.setText("Pomyœlnie dodano audiobook!");
		}
	}

	@FXML
	void usunAudiobook(ActionEvent event) throws ClassNotFoundException {
		audiobookDao.usunAutorAudiobook(tableAudiobook.getSelectionModel().getSelectedItem().getId());
		audiobookDao.usunAudiobook(tableAudiobook.getSelectionModel().getSelectedItem().getId());
		tableAudiobook.getItems().removeAll(tableAudiobook.getSelectionModel().getSelectedItem());
	}

	@FXML
	void odswiezPDF(ActionEvent event) {
		listaMaterialowPDF = materialyDao.listaMaterialowPDF();
		tableMaterialyPDF.setItems(listaMaterialowPDF);
	}

	@FXML
	void odswiezAudiobook(ActionEvent event) {
		listaAudiobookow = audiobookDao.listaAudiobookow();
		tableAudiobook.setItems(listaAudiobookow);
	}
	
	@FXML
	void odswiezKsiazka(ActionEvent event) {
		listaKsiazek = bookDao.seleckBooks();
		tableBook.setItems(listaKsiazek);
	}
	
	@FXML
	public void pokazFormularzDodaniaKsiazka() throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/fxml/DodajKsiazke.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	
	@FXML
	void gotowyDoOdbioru(ActionEvent event) {
		if (tableZAMOWIENIA.getSelectionModel().getSelectedItem().getDataOdbioru() == null) {
			zamowienieDao.ustawOdbior(tableZAMOWIENIA.getSelectionModel().getSelectedItem().getId());
		}
		listaZamowien = zamowienieDao.listaZamowien();
		tableZAMOWIENIA.setItems(listaZamowien);
	}

	@FXML
	void potwierdzOplate(ActionEvent event) {
		zamowienieDao.potwierdzOplate(tableZAMOWIENIA.getSelectionModel().getSelectedItem().getId());
		listaZamowien = zamowienieDao.listaZamowien();
		tableZAMOWIENIA.setItems(listaZamowien);
	}

	@FXML
	void zmienStatus(ActionEvent event) {
		zamowienieDao.zmienStatus(tableZAMOWIENIA.getSelectionModel().getSelectedItem().getId());
		listaZamowien = zamowienieDao.listaZamowien();
		tableZAMOWIENIA.setItems(listaZamowien);
	}

	@FXML
	void aktualizujGrzywne(ActionEvent event) {
		for (Zamowienie zamowienie : tableZAMOWIENIA.getItems()) {
			zamowienieDao.aktualizujNaleznosci(zamowienie.getId());
		}
		listaZamowien = zamowienieDao.listaZamowien();
		tableZAMOWIENIA.setItems(listaZamowien);
	}

	@FXML
	void zrealizujZamowienie(ActionEvent event) {
		if (tableZAMOWIENIA.getSelectionModel().getSelectedItem().getGrzywna() == 0) {
			zamowienieDao.zrealizujZamowienie(tableZAMOWIENIA.getSelectionModel().getSelectedItem().getId());
		}
		listaZamowien = zamowienieDao.listaZamowien();
		tableZAMOWIENIA.setItems(listaZamowien);
	}

	@FXML
	void pokazKsiazkiZamowienia(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/ZamowienieKsiazki.fxml"));
		Stage primaryStage = new Stage();
		Parent root = loader.load();
		Scene scene = new Scene(root);

		ZamowienieKsiazkiController controller = loader.getController();
		controller.initData(
				zamowienieDao.listaKsiazekzZamowienia(tableZAMOWIENIA.getSelectionModel().getSelectedItem().getId()));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void aktualizujDostepnosc(ActionEvent event) {
		for (Rezerwacja rezerwacja : tabelaRezerwacje.getItems()) {
			rezDao.zmienDostepnosc(rezerwacja.getId());
		}
		listaRezerwacji = rezDao.listaRezerwacji();
		tabelaRezerwacje.setItems(listaRezerwacji);
	}

	@FXML
	void zrealizujRezerwacje(ActionEvent event) {
		Rezerwacja r = tabelaRezerwacje.getSelectionModel().getSelectedItem();
		if (r.getDostepnosc().equals("Dostêpna")) {
			Book b = null;
			b = bookDao.ksiazkaPoID(r.getBookID());
			System.out.println(b.toString());
			ObservableList<Book> ksiazka = FXCollections.observableArrayList();
			ksiazka.add(b);
			LocalDate dataOdbioru = LocalDate.now();
			LocalDate dataZwrotu = dataOdbioru.plusMonths(3);
			Zamowienie z = new Zamowienie();
			z.setUserId(r.getUserID());
			z.setDataOdbioru(dataOdbioru.toString());
			z.setDataZwrotu(dataZwrotu.toString());
			z.setKsiazki(ksiazka);
			z.setStatus("nie odebrane");
			zamowienieDao.dodajZamowieniezRezerwacji(z);
			rezDao.deleteRezerwacja(r);
			listaRezerwacji = rezDao.listaRezerwacji();
			tabelaRezerwacje.setItems(listaRezerwacji);
		}
	}

	
	@FXML
	void dodajKsiazke(ActionEvent event) {
		String tytul = txtTytulKsiazki.getText();
		String autorzy = txtAutorzyKsiazki.getText();
		String wydawnictwo = txtWydawnictwoKsiazki.getText();
		int rokWydania = Integer.parseInt(txtRokWydaniaKsiazki.getText());
		String dzial = txtDzialKsiazki.getText();
		int liczbaEgzemplarzy = Integer.parseInt(txtLiczbaEgz.getText());
		Book nowaKsiazka = new Book(autorzy, tytul, wydawnictwo, rokWydania, dzial, liczbaEgzemplarzy);
		if(bookDao.getDzialID(dzial)<0) {
            bookDao.dodajDzial(dzial);
        }
        if(bookDao.getWydawnictwoID(wydawnictwo)<0) {
            bookDao.dodajWydawnictwo(wydawnictwo);
        }
		if (bookDao.dodajKsiazke(nowaKsiazka) > 0) {
			lblStatusDodaniaKsiazki.setText("Pomyœlnie dodano ksiazke!");

			String[] autorzyTab = autorzy.split(", ");
			for (String autor : autorzyTab) {
				if (bookDao.getAutorID(autor) < 0) {
					bookDao.dodajAutora(autor);
				}
			}
			for (String autor : autorzyTab) {
				bookDao.dodajAutoraKsiazki(bookDao.getAutorID(autor), bookDao.getMaxKsiazkaID());
			}
		} else {
			lblStatusDodaniaKsiazki.setText("B³ad podczas dodawania ksiazki!");
		}
	}

	@FXML
	void pokazFormularzDodaniaKsiazki(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/application/DodajKsiazke.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

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
    	String najczesciejWypozyczanaKsiazka = " ID: " + IDKsiazki + "	Tytul:  " + ksiazka.getTytul()+ "	Liczba wypo¿yczeñ:  " + liczbaWypozyczen;
    	lblIDNWK.setText(najczesciejWypozyczanaKsiazka);
    	ArrayList<Book> najczesciejRezerwowaneKsiazki = statystykiDao.get3NAjRezKsiazkii();
    	String najczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(0).getId() + "	Tytul:  " + najczesciejRezerwowaneKsiazki.get(0).getTytul() + "   Liczba rezerwacji:  " + najczesciejRezerwowaneKsiazki.get(0).getLiczbaRezerwacji();
    	
    	lblIDNRK.setText(najczesciejRezerwowanaKsiazka);
    	String drugaNajczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(1).getId() + "	Tytul: " + najczesciejRezerwowaneKsiazki.get(1).getTytul() + "	 Liczba rezerwacji:  " + najczesciejRezerwowaneKsiazki.get(1).getLiczbaRezerwacji();
    	
    	lblDNRK.setText(drugaNajczesciejRezerwowanaKsiazka);
    	String trzeciaNajczesciejRezerwowanaKsiazka = " ID: " + najczesciejRezerwowaneKsiazki.get(2).getId() + "	Tytul: " + najczesciejRezerwowaneKsiazki.get(2).getTytul() + "	Liczba rezerwacji:  " + najczesciejRezerwowaneKsiazki.get(2).getLiczbaRezerwacji();
    	
    	lblIDTNRK.setText(trzeciaNajczesciejRezerwowanaKsiazka);	
	}
	
	ObservableList<MaterialyPDF> listaMaterialowPDF = null;
	ObservableList<Audiobook> listaAudiobookow = null;
	ObservableList<Zamowienie> listaZamowien = null;
	ObservableList<Rezerwacja> listaRezerwacji = null;
	ObservableList<Book> listaKsiazek = null;
	ZamowienieDao zamowienieDao = new ZamowienieDao();
	RezerwacjaDao rezDao = new RezerwacjaDao();
	BookDao bookDao = new BookDao();
	StatystykiDao statystykiDao = new StatystykiDao();
	private static boolean czyInit = false;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (czyInit == false) {
			jakSzukacK.getItems().add("tytu³");
			jakSzukacK.getItems().add("autor");
			jakSzukacK.getItems().add("wydawnictwo");
			jakSzukacK.getItems().add("dzia³");
			jakSzukacK.getItems().add("rok wydania");
			
			jakSzukacP.getItems().add("tytu³");
			jakSzukacP.getItems().add("autor");
			jakSzukacP.getItems().add("wydawnictwo");
			jakSzukacP.getItems().add("dzia³");
			jakSzukacP.getItems().add("rok wydania");
			
			jakSzukacA.getItems().add("tytu³");
			jakSzukacA.getItems().add("autor");
			jakSzukacA.getItems().add("wydawnictwo");
			jakSzukacA.getItems().add("dzia³");
			jakSzukacA.getItems().add("rok wydania");
			
			listaMaterialowPDF = materialyDao.listaMaterialowPDF();
			listaAudiobookow = audiobookDao.listaAudiobookow();
			listaZamowien = zamowienieDao.listaZamowien();
			listaRezerwacji = rezDao.listaRezerwacji();
			listaKsiazek = bookDao.seleckBooks();
			
			colIDPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, Integer>("id"));
			colTytulPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("tytul"));
			colAutorzyPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("autorzy"));
			colWydPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("wydawnictwo"));
			colRokPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, Integer>("rokWydania"));
			colDzialPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("dzial"));
			tableMaterialyPDF.setItems(listaMaterialowPDF);

			colIDAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, Integer>("id"));
			colTytulAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("tytul"));
			colAutorzyAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("autorzy"));
			colWydawnictwoAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("wydawnictwo"));
			colRokWydaniaAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, Integer>("rokWydania"));
			colDzialAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("dzial"));
			tableAudiobook.setItems(listaAudiobookow);

			colIDZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, Integer>("id"));
			colIDStudZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, Integer>("userId"));
			colDATAOZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("dataOdbioru"));
			colDATAZWRZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("dataZwrotu"));
			colNALZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, Double>("grzywna"));
			colSTATUSZ.setCellValueFactory(new PropertyValueFactory<Zamowienie, String>("status"));
			tableZAMOWIENIA.setItems(listaZamowien);

			colIDRezerwacja.setCellValueFactory(new PropertyValueFactory<Rezerwacja, Integer>("id"));
			colIDKsiazkiRezerwacja.setCellValueFactory(new PropertyValueFactory<Rezerwacja, Integer>("BookID"));
			colIDStudentaRezerwacje.setCellValueFactory(new PropertyValueFactory<Rezerwacja, Integer>("UserID"));
			colTytulKsiazkiRezerwacja.setCellValueFactory(new PropertyValueFactory<Rezerwacja, String>("tytul"));
			colDataRezerwacji.setCellValueFactory(new PropertyValueFactory<Rezerwacja, String>("dataRezerwacji"));
			colDostepnoscRezerwacje.setCellValueFactory(new PropertyValueFactory<Rezerwacja, String>("dostepnosc"));
			tabelaRezerwacje.setItems(listaRezerwacji);
			
			colIDBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
			colTytulBook.setCellValueFactory(new PropertyValueFactory<Book, String>("tytul"));
			colAutorzyBook.setCellValueFactory(new PropertyValueFactory<Book, String>("autorzy"));
			colWydawnictwoBook.setCellValueFactory(new PropertyValueFactory<Book, String>("wydaw"));
			colRokWydaniaBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rok"));
			colDzialBook.setCellValueFactory(new PropertyValueFactory<Book, String>("dzial"));
			colEgzemplarzeBook.setCellValueFactory(new PropertyValueFactory<Book, Integer>("liczbaEgz"));
			tableBook.setItems(listaKsiazek);

			czyInit = true;
		}
	}
	
	@FXML
	public void szukajBook() {
		ObservableList<Book> books = FXCollections.observableArrayList();
		if (!(jakSzukacK.getValue() == null) && !wyszukaj.getText().equals("")) {
			if (jakSzukacK.getValue().equals("tytu³")) {
				books = bookDao.seleckBooksTytul("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacK.getValue().equals("autor")) {
				books = bookDao.seleckBooksAutor("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacK.getValue().equals("wydawnictwo")) {
				books = bookDao.seleckBooksWyd("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacK.getValue().equals("dzia³")) {
				books = bookDao.seleckBooksDzial("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacK.getValue().equals("rok wydania")) {
				int rok = Integer.valueOf(wyszukaj.getText());
				books = bookDao.seleckBooksRok(rok);
			}
		} else if (!wyszukaj.getText().equals("")) {
			books = bookDao.seleckBooksTytul("%" + wyszukaj.getText() + "%");
		} else {
			books = bookDao.seleckBooks();
		}
		tableBook.setItems(books);
	}

	@FXML
	public void szukajPDF() {
		ObservableList<MaterialyPDF> pdf = FXCollections.observableArrayList();
		if (!(jakSzukacP.getValue() == null) && !wyszukaj.getText().equals("")) {
			if (jakSzukacP.getValue().equals("tytu³")) {
				pdf = materialyDao.selectPDFTytul("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacP.getValue().equals("autor")) {
				pdf = materialyDao.selectPDFAutor("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacP.getValue().equals("wydawnictwo")) {
				pdf = materialyDao.selectPDFWyd("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacP.getValue().equals("dzia³")) {
				pdf = materialyDao.selectPDFDzial("%" + wyszukaj.getText() + "%");
			} else if (jakSzukacP.getValue().equals("rok wydania")) {
				int rok = Integer.valueOf(wyszukaj.getText());
				pdf = materialyDao.selectPDFRok(rok);
			}
		} else if (!wyszukaj.getText().equals("")) {
			pdf = materialyDao.selectPDFTytul("%" + wyszukaj.getText() + "%");
		} else {
			pdf = materialyDao.listaMaterialowPDF();
		}
		tableMaterialyPDF.setItems(pdf);
	}

	@FXML
	public void szukajAudio() {
		ObservableList<Audiobook> audio = FXCollections.observableArrayList();
		if (!(jakSzukacA.getValue() == null) && !wyszukajA.getText().equals("")) {
			if (jakSzukacA.getValue().equals("tytu³")) {
				audio = audiobookDao.selectAudioTytul("%" + wyszukajA.getText() + "%");
				//System.out.println("tytul");
			} else if (jakSzukacA.getValue().equals("autor")) {
				audio = audiobookDao.selectPDFAutor("%" + wyszukajA.getText() + "%");
				//System.out.println("autor");
			} else if (jakSzukacA.getValue().equals("wydawnictwo")) {
				audio = audiobookDao.selectAudioWyd("%" + wyszukajA.getText() + "%");
				//System.out.println("wydaw");
			} else if (jakSzukacA.getValue().equals("dzia³")) {
				audio = audiobookDao.selectAudioDzial("%" + wyszukajA.getText() + "%");
				//System.out.println("dzial");
			} else if (jakSzukacA.getValue().equals("rok wydania")) {
				//System.out.println("rok");
				int rok = Integer.valueOf(wyszukajA.getText());
				audio = audiobookDao.selectAudiobookRok(rok);
			}
		} else if (!wyszukajA.getText().equals("")) {
			audio = audiobookDao.selectAudioTytul("%" + wyszukajA.getText() + "%");
		} else {
			audio = audiobookDao.listaAudiobookow();
		}
		tableAudiobook.setItems(audio);
	}
	
	public void dodajEgzemplarze() {
		Book b = tableBook.getSelectionModel().getSelectedItem();
		int ile = Integer.valueOf(egzemplarze.getText());
		b.setLiczbaEgz(b.getLiczbaEgz() + ile);
		bookDao.dodajEgzemplarze(b);
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
