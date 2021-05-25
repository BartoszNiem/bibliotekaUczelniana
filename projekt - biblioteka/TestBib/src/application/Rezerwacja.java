package application;

import java.time.LocalDate;

public class Rezerwacja {
	private int id;
	private String tytul;
	private LocalDate data;
	private Book book;
	private int BookID;
	private int UserID;
	private String dataRezerwacji;
	private String dostepnosc;

	public Rezerwacja(int id, String tytul, LocalDate data) {
		this.id = id;
		this.tytul = tytul;
		this.data = data;
	}

	public Rezerwacja(Book book) {
		this.data = LocalDate.now();
		this.book = book;
		this.tytul = book.getTytul();
	}

	public Rezerwacja(int id, String tytul, int bookID, int userID, String dataRezerwacji, String dostepnosc) {
		super();
		this.id = id;
		this.tytul = tytul;
		BookID = bookID;
		UserID = userID;
		this.dataRezerwacji = dataRezerwacji;
		this.dostepnosc = dostepnosc;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book b) {
		this.book = b;
		setTytul(b.getTytul());
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getBookID() {
		return BookID;
	}

	public void setBookID(int bookID) {
		BookID = bookID;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getDataRezerwacji() {
		return dataRezerwacji;
	}

	public void setDataRezerwacji(String dataRezerwacji) {
		this.dataRezerwacji = dataRezerwacji;
	}

	public String getDostepnosc() {
		return dostepnosc;
	}

	public void setDostepnosc(String dostepnosc) {
		this.dostepnosc = dostepnosc;
	}
}
