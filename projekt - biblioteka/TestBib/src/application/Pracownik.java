package application;

public class Pracownik {
private int id;
private String imie;
private String nazwisko;
private String pesel;
private String rola;
private String email;
private String password;

public Pracownik() {}

public Pracownik(String imie, String nazwisko, String pesel, String rola, String email, String password) {
	super();
	this.imie = imie;
	this.nazwisko = nazwisko;
	this.pesel = pesel;
	this.rola = rola;
	this.email = email;
	this.password = password;
}

public Pracownik(int id, String imie, String nazwisko, String pesel, String rola, String email, String password) {
	super();
	this.id = id;
	this.imie = imie;
	this.nazwisko = nazwisko;
	this.pesel = pesel;
	this.rola = rola;
	this.email = email;
	this.password = password;
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

public String getRola() {
	return rola;
}

public void setRola(String rola) {
	this.rola = rola;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
};

}
