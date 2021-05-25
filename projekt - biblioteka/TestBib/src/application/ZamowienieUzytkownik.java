package application;

public class ZamowienieUzytkownik {
	
	private int id;
	private String tytul;
	private double grzywna;
	private String data;
	private String status;

	public ZamowienieUzytkownik(int id, String tytul, double grzywna, String data, String status) {
		this.id = id;
		this.tytul = tytul;
		this.grzywna = grzywna;
		this.data = data;
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	public double getGrzywna() {
		return grzywna;
	}
	public void setGrzywna(double grzywna) {
		this.grzywna = grzywna;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
