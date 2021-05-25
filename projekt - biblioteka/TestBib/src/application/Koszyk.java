package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Koszyk {

	private int max;
	ObservableList<Book> books;

	public Koszyk() {
		this.max = 5;
		this.books = FXCollections.observableArrayList();
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public ObservableList<Book> getBooks() {
		return this.books;
	}

	public boolean addBook(Book book) {
		if (books.size() < 5) {
			books.add(book);
			return true;
		} else {
			return false;
		}
	}

	public void removeBook(String tytul) {
		for(int i = 0; i < books.size(); i++) {
			if(books.get(i).getTytul().equals(tytul)) {
				books.remove(i);
			}
		}
	}

}
