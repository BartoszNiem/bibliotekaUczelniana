package application;

public class Student {
	private int id;
	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String password;
	public Student() {
		super();
	}
	public Student(String imie, String nazwisko, String pesel, String email) {
		super();
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.pesel = pesel;
		this.email = email;
	}
	
	public Student(String imie, String nazwisko, String pesel, String email, String password) {
		super();
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.pesel = pesel;
		this.email = email;
		this.password = password;
	}
	public Student(int id, String imie, String nazwisko, String pesel, String email, String password) {
		super();
		this.id = id;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.pesel = pesel;
		this.email = email;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
