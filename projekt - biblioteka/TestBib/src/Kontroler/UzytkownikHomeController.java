package Kontroler;

import java.time.LocalDate;

import Dao.AudiobookDao;
import Dao.BookDao;
import Dao.LoginDao;
import Dao.MaterialyDao;
import Dao.ZamowienieDao;
import application.Audiobook;
import application.Book;
import application.Koszyk;
import application.MaterialyPDF;
import application.Rezerwacja;
import application.Zamowienie;
import application.ZamowienieUzytkownik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class UzytkownikHomeController {

	private Koszyk koszyk;
	private BookDao bookDao;
	private ZamowienieDao zamowienieDao;
	private MaterialyDao materialyDao;
	private AudiobookDao audiobookDao;
	private MainController mainController;

	@FXML
	private StackPane mainStackPane;
	@FXML
	private ChoiceBox<String> jakSzukacK;
	@FXML
	private ChoiceBox<String> jakSzukacP;
	@FXML
	private ChoiceBox<String> jakSzukacA;
	@FXML
	private AnchorPane koszykPane;
	@FXML
	private Label koszykStatus;
	@FXML
	private TextField wyszukaj;
	@FXML
	private TextField wyszukajA;

	@FXML
	private TableView<Book> tabelaWynik;
	@FXML
	private TableColumn<Book, Integer> idCol;
	@FXML
	private TableColumn<Book, String> tytulCol;
	@FXML
	private TableColumn<Book, String> autorCol;
	@FXML
	private TableColumn<Book, String> wydCol;
	@FXML
	private TableColumn<Book, Integer> rokCol;
	@FXML
	private TableColumn<Book, String> dzialCol;
	@FXML
	private TableColumn<Book, Integer> egzCol;

	@FXML
	private TableView<Book> tabelaKoszyk;
	@FXML
	private TableColumn<Book, Integer> idCol1;
	@FXML
	private TableColumn<Book, String> tytulCol1;
	@FXML
	private TableColumn<Book, String> autorCol1;
	@FXML
	private TableColumn<Book, String> wydCol1;
	@FXML
	private TableColumn<Book, Integer> rokCol1;
	@FXML
	private TableColumn<Book, String> dzialCol1;
	@FXML
	private TableColumn<Book, Integer> egzCol1;

	@FXML
	private TableView<ZamowienieUzytkownik> tabelaMoje;
	@FXML
	private TableColumn<ZamowienieUzytkownik, Integer> mojeColId;
	@FXML
	private TableColumn<ZamowienieUzytkownik, String> mojeColTytul;
	@FXML
	private TableColumn<ZamowienieUzytkownik, String> mojeColData;
	@FXML
	private TableColumn<ZamowienieUzytkownik, Double> mojeColNaleznosci;
	@FXML
	private TableColumn<ZamowienieUzytkownik, String> mojeColStatus;

	@FXML
	private TableView<Rezerwacja> tabelaRezerwacje;
	@FXML
	private TableColumn<Rezerwacja, Integer> RezerwacjeColId;
	@FXML
	private TableColumn<Rezerwacja, String> rezerwacjeColTytul;
	@FXML
	private TableColumn<Rezerwacja, String> rezerwacjeColData;

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

	public UzytkownikHomeController() {
		koszyk = new Koszyk();
		bookDao = new BookDao();
		zamowienieDao = new ZamowienieDao();
		materialyDao = new MaterialyDao();
		audiobookDao = new AudiobookDao();
	}

	@FXML
	public void initialize() {
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

		idCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		tytulCol.setCellValueFactory(new PropertyValueFactory<Book, String>("tytul"));
		autorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("autorzy"));
		wydCol.setCellValueFactory(new PropertyValueFactory<Book, String>("wydaw"));
		rokCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rok"));
		dzialCol.setCellValueFactory(new PropertyValueFactory<Book, String>("dzial"));
		egzCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("liczbaEgz"));

		colIDAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, Integer>("id"));
		colTytulAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("tytul"));
		colAutorzyAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("autorzy"));
		colWydawnictwoAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("wydawnictwo"));
		colRokWydaniaAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, Integer>("rokWydania"));
		colDzialAudiobook.setCellValueFactory(new PropertyValueFactory<Audiobook, String>("dzial"));

		colIDPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, Integer>("id"));
		colTytulPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("tytul"));
		colAutorzyPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("autorzy"));
		colWydPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("wydawnictwo"));
		colRokPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, Integer>("rokWydania"));
		colDzialPDF.setCellValueFactory(new PropertyValueFactory<MaterialyPDF, String>("dzial"));

		idCol1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		tytulCol1.setCellValueFactory(new PropertyValueFactory<Book, String>("tytul"));
		autorCol1.setCellValueFactory(new PropertyValueFactory<Book, String>("autorzy"));
		wydCol1.setCellValueFactory(new PropertyValueFactory<Book, String>("wydaw"));
		rokCol1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rok"));
		dzialCol1.setCellValueFactory(new PropertyValueFactory<Book, String>("dzial"));
		egzCol1.setCellValueFactory(new PropertyValueFactory<Book, Integer>("liczbaEgz"));

		mojeColId.setCellValueFactory(new PropertyValueFactory<ZamowienieUzytkownik, Integer>("id"));
		mojeColTytul.setCellValueFactory(new PropertyValueFactory<ZamowienieUzytkownik, String>("tytul"));
		mojeColData.setCellValueFactory(new PropertyValueFactory<ZamowienieUzytkownik, String>("data"));
		mojeColNaleznosci.setCellValueFactory(new PropertyValueFactory<ZamowienieUzytkownik, Double>("grzywna"));
		mojeColStatus.setCellValueFactory(new PropertyValueFactory<ZamowienieUzytkownik, String>("status"));

		tabelaMoje.setItems(zamowienieDao.selectZamowienie());

		RezerwacjeColId.setCellValueFactory(new PropertyValueFactory<Rezerwacja, Integer>("id"));
		rezerwacjeColTytul.setCellValueFactory(new PropertyValueFactory<Rezerwacja, String>("tytul"));
		rezerwacjeColData.setCellValueFactory(new PropertyValueFactory<Rezerwacja, String>("data"));

		tabelaRezerwacje.setItems(zamowienieDao.selectRezerwacje());
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
		tabelaWynik.setItems(books);
	}

	@FXML
	public void szukajPDF() {
		ObservableList<MaterialyPDF> pdf = FXCollections.observableArrayList();
		if (!(jakSzukacP.getValue() == null) && !wyszukaj.getText().equals("")) {
			if (jakSzukacP.getValue().equals("tytu³")) {
				pdf = materialyDao.selectPDFTytul("%" + wyszukaj.getText() + "%");
				//System.out.println("tytul");
			} else if (jakSzukacP.getValue().equals("autor")) {
				pdf = materialyDao.selectPDFAutor("%" + wyszukaj.getText() + "%");
				//System.out.println("autor");
			} else if (jakSzukacP.getValue().equals("wydawnictwo")) {
				pdf = materialyDao.selectPDFWyd("%" + wyszukaj.getText() + "%");
				//System.out.println("wydaw");
			} else if (jakSzukacP.getValue().equals("dzia³")) {
				pdf = materialyDao.selectPDFDzial("%" + wyszukaj.getText() + "%");
				//System.out.println("dzial");
			} else if (jakSzukacP.getValue().equals("rok wydania")) {
				int rok = Integer.valueOf(wyszukaj.getText());
				//System.out.println("rok");
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

	@FXML
	public void dodaj() {
		Book b = tabelaWynik.getSelectionModel().getSelectedItem();
		if (b.getLiczbaEgz() == 0) {
			Rezerwacja r = new Rezerwacja(b);
			zamowienieDao.insertRezerwacja(r);
			tabelaRezerwacje.setItems(zamowienieDao.selectRezerwacje());
		} else if (koszyk.getBooks().size() == 0) {
			koszyk.addBook(b);
			tabelaKoszyk.setItems(koszyk.getBooks());
		} else {
			for (int i = 0; i < koszyk.getBooks().size(); i++) {
				if (!(b.getId() == koszyk.getBooks().get(i).getId())) {
					if (koszyk.addBook(b)) {
						tabelaKoszyk.setItems(koszyk.getBooks());
					} else {
						koszykStatus.setText("koszyk pe³ny!");
					}
					break;
				}
			}
		}
	}

	@FXML
	public void usun() {
		Book b = tabelaKoszyk.getSelectionModel().getSelectedItem();
		koszyk.removeBook(b.getTytul());
	}

	@FXML
	public void zamow() {
		Zamowienie z = new Zamowienie();
		z.setUserId(LoginDao.getCurrentUser().getId());
		z.setKsiazki(koszyk.getBooks());
		zamowienieDao.insertZamowienie(z);
		wyswietlMojeZamowienia();
		tabelaKoszyk.getItems().clear();
	}

	@FXML
	public void rezygnuj() {
		Rezerwacja r = tabelaRezerwacje.getSelectionModel().getSelectedItem();
		System.out.println(r.getId());
		zamowienieDao.deleteRezerwacja(r);
		wyswietlRezerwacje();
	}

	@FXML
	public void przedluz() {
		ZamowienieUzytkownik zu = tabelaMoje.getSelectionModel().getSelectedItem();
		Book b = bookDao.getBook(zu.getTytul());
		if (zamowienieDao.czyMoznaPrzedluzyc(b.getId())) {
			Zamowienie z = zamowienieDao.selectZamowienie(zu.getId());
			z.setDataZwrotu(LocalDate.parse(z.getDataZwrotu()).plusMonths(1).toString());
			zamowienieDao.insertPrzedluzoneZamowienie(z);
			wyswietlMojeZamowienia();
		}
	}

	@FXML
	void pobierzAudiobook() {
		audiobookDao.pobierzAudiobook(tableAudiobook.getSelectionModel().getSelectedItem().getId());
	}

	@FXML
	void pobierzPDF() {
		materialyDao.pobierzMaterialPDF(tableMaterialyPDF.getSelectionModel().getSelectedItem().getId());
	}

	public void wyswietlMojeZamowienia() {
		tabelaMoje.setItems(zamowienieDao.selectZamowienie());
	}

	public void wyswietlRezerwacje() {
		tabelaRezerwacje.setItems(zamowienieDao.selectRezerwacje());
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@FXML
	void wyloguj() {
		mainController.loadLoginScreen();
		LoginDao.setCurrentUser(null);
	}

}
