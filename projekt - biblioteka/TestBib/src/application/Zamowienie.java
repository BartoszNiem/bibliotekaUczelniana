package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Zamowienie {
	private int id;
	private int userId;
	private String dataOdbioru;
	private String dataZwrotu;
	private double grzywna;
	private String status;
	private int idPlatnosci;
	private ObservableList<Book> ksiazki;

	public Zamowienie() {
		this.ksiazki = FXCollections.observableArrayList();
	}

	public Zamowienie(int id, int userId, String dataOdbioru, String dataZwrotu, double grzywna) {
		super();
		this.id = id;
		this.userId = userId;
		this.dataOdbioru = dataOdbioru;
		this.dataZwrotu = dataZwrotu;
		this.grzywna = grzywna;
	}
	
	public Zamowienie(int id, int userId, String dataOdbioru, String dataZwrotu, double grzywna, String status) {
		super();
		this.id = id;
		this.userId = userId;
		this.dataOdbioru = dataOdbioru;
		this.dataZwrotu = dataZwrotu;
		this.grzywna = grzywna;
		this.status = status;
	}

	public Zamowienie(int id, int userId, String dataOdbioru, String dataZwrotu, double grzywna,
			ObservableList<Book> ksiazki) {
		super();
		this.id = id;
		this.userId = userId;
		this.dataOdbioru = dataOdbioru;
		this.dataZwrotu = dataOdbioru;
		this.grzywna = grzywna;
		this.ksiazki = ksiazki;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDataOdbioru() {
		return dataOdbioru;
	}

	public void String(String dataOdbioru) {
		this.dataOdbioru = dataOdbioru;
	}

	public String getDataZwrotu() {
		return dataZwrotu;
	}

	public void setDataZwrotu(String dataZwrotu) {
		this.dataZwrotu = dataZwrotu;
	}

	public ObservableList<Book> getKsiazki() {
		return ksiazki;
	}

	public void setKsiazki(ObservableList<Book> ksiazkaTytul) {
		this.ksiazki = ksiazkaTytul;
	}

	public double getGrzywna() {
		return grzywna;
	}

	public void setGrzywna(double grzywna) {
		this.grzywna = grzywna;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public int getIdPlatnosci() {
		return idPlatnosci;
	}

	public void setIdPlatnosci(int idPlatnosci) {
		this.idPlatnosci = idPlatnosci;
	}

	public void setDataOdbioru(String dataOdbioru) {
		this.dataOdbioru = dataOdbioru;
	}
}
