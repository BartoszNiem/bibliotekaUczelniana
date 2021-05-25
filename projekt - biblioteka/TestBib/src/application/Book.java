package application;

public class Book {

	private int id;
	private String autorzy;
	private String tytul;
	private String wydaw;
	private int rok;
	private String dzial;
	private int liczbaEgz;
	private int liczbaRezerwacji;

	public Book() {

	}

	public Book(int id, String tytul, String autorzy, String wydaw, int rok, String dzial, int liczbaEgz) {
		this.id = id;
		this.tytul = tytul;
		this.wydaw = wydaw;
		this.rok = rok;
		this.dzial = dzial;
		this.autorzy = autorzy;
		this.liczbaEgz = liczbaEgz;
	}

	public Book(String autorzy, String tytul, String wydaw, int rok, String dzial, int liczbaEgz) {
		super();
		this.autorzy = autorzy;
		this.tytul = tytul;
		this.wydaw = wydaw;
		this.rok = rok;
		this.dzial = dzial;
		this.liczbaEgz = liczbaEgz;
	}

	public Book(int id, String tytul, String autorzy, String wydaw, int rok, String dzial) {
		super();
		this.id = id;
		this.autorzy = autorzy;
		this.tytul = tytul;
		this.wydaw = wydaw;
		this.rok = rok;
		this.dzial = dzial;
	}

	public Book(int id) {
		this.id = id;
	}

	public String getAutorzy() {
		return autorzy;
	}

	public void setAutorzy(String autorzy) {
		this.autorzy = autorzy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTytul() {
		return tytul;
	}

	public void setTytul(String tytul) {
		this.tytul = tytul;
	}

	public String getWydaw() {
		return wydaw;
	}

	public void setWydaw(String wydaw) {
		this.wydaw = wydaw;
	}

	public int getRok() {
		return rok;
	}

	public void setRok(int rok) {
		this.rok = rok;
	}

	public String getDzial() {
		return dzial;
	}

	public void setDzial(String dzial) {
		this.dzial = dzial;
	}

	public int getLiczbaEgz() {
		return liczbaEgz;
	}

	public void setLiczbaEgz(int liczbaEgz) {
		this.liczbaEgz = liczbaEgz;
	}

	public int getLiczbaRezerwacji() {
		return liczbaRezerwacji;
	}

	public void setLiczbaRezerwacji(int liczbaRezerwacji) {
		this.liczbaRezerwacji = liczbaRezerwacji;
	}

}
