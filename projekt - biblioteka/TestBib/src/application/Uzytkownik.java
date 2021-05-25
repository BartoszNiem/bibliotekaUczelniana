package application;

public class Uzytkownik {

	private int id;
	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String haslo;
	
	public Uzytkownik(int id, String imie, String nazwisko, String pesel, String email, String haslo) {
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.pesel = pesel;
		this.email = email;
		this.haslo = haslo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emial) {
		this.email = emial;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}
}
