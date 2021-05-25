package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookDao extends MainDao {

	public ObservableList<Book> seleckBooks() {

		String SELECT_ALL = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id;";

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ksiazka_id");
				String tytul = rs.getString("tytul");
				String wydaw = rs.getString("wnazwa");
				int rok = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				int liczbaEgz = rs.getInt("liczba_egz");
				String autorzy = selectAutorzy(id);
				books.add(new Book(id, tytul, autorzy, wydaw, rok, dzial, liczbaEgz));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public String selectAutorzy(int id) {

		String SELECT_AUTORZY = "select imie, nazwisko from autor a join autor_ksiazka ak on a.autor_id = ak.autor_id where ksiazka_id = ?;";
		String autorzy = "";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTORZY);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				autorzy += imie + " " + nazwisko + ", ";
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return autorzy;
	}

	public ObservableList<Book> seleckBooksTytul(String str) {

		String SELECT_BOOKS = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where k.tytul like ?;";

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public ObservableList<Book> seleckBooksDzial(String str) {

		String SELECT_BOOKS = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where d.nazwa like ?;";

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public ObservableList<Book> seleckBooksWyd(String str) {

		String SELECT_BOOKS = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where w.nazwa like ?;";

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public ObservableList<Book> seleckBooksRok(int rok) {

		String SELECT_BOOKS = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where rok_wydania = ?;";

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS);
			preparedStatement.setInt(1, rok);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public ObservableList<Book> seleckBooksAutor(String autor) {

		String SELECT_BOOKS = "select k.ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id"
				+ " join autor_ksiazka ak on ak.ksiazka_id = k.ksiazka_id join autor a on a.autor_id = ak.autor_id"
				+ " where a.imie like ? or a.nazwisko like ?;";
		String imie = "";
		String nazwisko = "";
		for (int i = 0; i < autor.length(); i++) {
			if (autor.charAt(i) == ' ') {
				imie += autor.substring(0, i);
				nazwisko += autor.substring(i + 1, autor.length());
				break;
			}
		}
		if (imie.equals("") || nazwisko.equals("")) {
			imie += autor;
			nazwisko += autor;
		}

		ObservableList<Book> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS);
			preparedStatement.setString(1, imie);
			preparedStatement.setString(2, nazwisko);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public ObservableList<Book> getList(ResultSet rs) {

		ObservableList<Book> books = FXCollections.observableArrayList();

		try {
			while (rs.next()) {
				int id = rs.getInt("ksiazka_id");
				String tytul = rs.getString("tytul");
				String wydaw = rs.getString("wnazwa");
				int rok = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				int liczbaEgz = rs.getInt("liczba_egz");
				String autorzy = selectAutorzy(id);
				books.add(new Book(id, tytul, autorzy, wydaw, rok, dzial, liczbaEgz));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}

	public Book getBook(String tytul) {
		String SELECT_BOOK = "select ksiazka_id from ksiazka where tytul = ?";
		Book b = null;
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK);
			preparedStatement.setString(1, tytul);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ksiazka_id");
				b = new Book(id);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return b;
	}

	public void dodajEgzemplarze(Book b) {
		String UPDATE_BOOK = "update ksiazka set liczba_egz = ? where ksiazka_id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK);
			preparedStatement.setInt(1, b.getLiczbaEgz());
			preparedStatement.setInt(2, b.getId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Book ksiazkaPoID(int ksiazka_id) {
		Book b = null;
		String SELECT_ID = "select tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, liczba_egz"
				+ " from ksiazka k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where ksiazka_id = ?;";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID);
			preparedStatement.setInt(1, ksiazka_id);
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String tytul = rs.getString("tytul");
				String wydaw = rs.getString("wnazwa");
				int rok = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				int liczbaEgz = rs.getInt("liczba_egz");
				String autorzy = selectAutorzy(ksiazka_id);
				b = new Book(ksiazka_id, tytul, autorzy, wydaw, rok, dzial, liczbaEgz);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return b;
	}

	public int dodajKsiazke(Book book) {
		int czyDodano = 0;
		String insert_book = "insert into ksiazka(tytul, wydawnictwo_id, rok_wydania, dzial_id, liczba_egz) values(?,?,?,?,?);";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(insert_book);
			preparedStatement.setString(1, book.getTytul());
			preparedStatement.setInt(2, getWydawnictwoID(book.getWydaw()));
			preparedStatement.setInt(3, book.getRok());
			preparedStatement.setInt(4, getDzialID(book.getDzial()));
			preparedStatement.setInt(5, book.getLiczbaEgz());
			System.out.println(preparedStatement);
			czyDodano = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return czyDodano;
	}

	public int getWydawnictwoID(String nazwa_wyd) {
		int id = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from wydawnictwo where nazwa = ?;")) {
			preparedStatement.setString(1, nazwa_wyd);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("wydawnictwo_id");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}

	public int getDzialID(String nazwa_dzialu) {
		int id = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from dzial where nazwa = ?;")) {
			preparedStatement.setString(1, nazwa_dzialu);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("dzial_id");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}

	public int getMaxKsiazkaID() {
		int id = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from ksiazka order by ksiazka_id desc limit 1;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("ksiazka_id");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}

	public void dodajAutora(String autor) {
		String[] imieNazwisko = autor.split(" ");
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into autor (imie, nazwisko) values(?, ?);")) {
			preparedStatement.setString(1, imieNazwisko[0]);
			preparedStatement.setString(2, imieNazwisko[1]);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public int getAutorID(String autor) {
		int id = -1;
		String[] imieNazwisko = autor.split(" ");
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from autor where imie = ? and nazwisko = ?;")) {
			preparedStatement.setString(1, imieNazwisko[0]);
			preparedStatement.setString(2, imieNazwisko[1]);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("autor_id");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}

	public int dodajAutoraKsiazki(int autor_id, int ksiazka_id) {
		int czyDodano = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into autor_ksiazka(autor_id, ksiazka_id) values(?,?);")) {
			preparedStatement.setInt(1, autor_id);
			preparedStatement.setInt(2, ksiazka_id);

			System.out.println(preparedStatement);
			czyDodano = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
		return czyDodano;
	}

	public void dodajWydawnictwo(String wyd) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into wydawnictwo (nazwa) values(?);")) {
			preparedStatement.setString(1, wyd);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void dodajDzial(String dzial) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into dzial (nazwa) values(?);")) {
			preparedStatement.setString(1, dzial);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}
}
