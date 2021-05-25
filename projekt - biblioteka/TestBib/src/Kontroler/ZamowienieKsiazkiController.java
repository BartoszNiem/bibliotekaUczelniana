package Kontroler;

import application.Book;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ZamowienieKsiazkiController {
	@FXML
	private TableView<Book> tableKsiazkiZam;

	@FXML
	private TableColumn<Book, Integer> colIDKsiazek;

	@FXML
	private TableColumn<Book, String> colTytullKsiazki;

	@FXML
	private TableColumn<Book, String> colAutorzyKsiazek;

	@FXML
	private TableColumn<Book, String> colWydawnictwoKsiazek;

	@FXML
	private TableColumn<Book, Integer> colRokW;

	@FXML
	private TableColumn<Book, String> colDzial;

	ObservableList<Book> listaKsiazek;

	public void initData(ObservableList<Book> lista) {
		listaKsiazek = lista;

		colIDKsiazek.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
		colTytullKsiazki.setCellValueFactory(new PropertyValueFactory<Book, String>("tytul"));
		colAutorzyKsiazek.setCellValueFactory(new PropertyValueFactory<Book, String>("autorzy"));
		colWydawnictwoKsiazek.setCellValueFactory(new PropertyValueFactory<Book, String>("wydaw"));
		colRokW.setCellValueFactory(new PropertyValueFactory<Book, Integer>("rok"));
		colDzial.setCellValueFactory(new PropertyValueFactory<Book, String>("dzial"));

		tableKsiazkiZam.setItems(listaKsiazek);
	}

}
