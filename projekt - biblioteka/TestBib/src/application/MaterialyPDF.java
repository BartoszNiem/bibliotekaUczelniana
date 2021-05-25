package application;

import java.io.InputStream;
import java.sql.Blob;

public class MaterialyPDF {
	private int id;
	private String tytul;
	private String autorzy;
	private String wydawnictwo;
	private int rokWydania;
	private String dzial;
	private Blob tresc;
	private InputStream trescIS;
	public MaterialyPDF() {
		super();
	}

	public MaterialyPDF(String tytul, String autorzy, String wydawnictwo, int rokWydania, String dzial) {
		super();
		this.tytul = tytul;
		this.autorzy = autorzy;
		this.wydawnictwo = wydawnictwo;
		this.rokWydania = rokWydania;
		this.dzial = dzial;
		
	}

	public MaterialyPDF(int id, String tytul, String autorzy, String wydawnictwo, int rokWydania, String dzial) {
		super();
		this.id = id;
		this.tytul = tytul;
		this.autorzy = autorzy;
		this.wydawnictwo = wydawnictwo;
		this.rokWydania = rokWydania;
		this.dzial = dzial;
	}

	public MaterialyPDF(int id, String tytul, String autorzy, String wydawnictwo, int rokWydania, String dzial,
			Blob tresc) {
		super();
		this.id = id;
		this.tytul = tytul;
		this.autorzy = autorzy;
		this.wydawnictwo = wydawnictwo;
		this.rokWydania = rokWydania;
		this.dzial = dzial;
		this.tresc = tresc;
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

	public String getAutorzy() {
		return autorzy;
	}

	public void setAutorzy(String autorzy) {
		this.autorzy = autorzy;
	}

	public String getWydawnictwo() {
		return wydawnictwo;
	}

	public void setWydawnictwo(String wydawnictwo) {
		this.wydawnictwo = wydawnictwo;
	}

	public int getRokWydania() {
		return rokWydania;
	}

	public void setRokWydania(int rokWydania) {
		this.rokWydania = rokWydania;
	}

	public String getDzial() {
		return dzial;
	}

	public void setDzial(String dzial) {
		this.dzial = dzial;
	}

	public Blob getTresc() {
		return tresc;
	}

	public void setTresc(Blob tresc) {
		this.tresc = tresc;
	}

	public InputStream getTrescIS() {
		return trescIS;
	}

	public void setTrescIS(InputStream trescIS) {
		this.trescIS = trescIS;
	}
	
	
}
