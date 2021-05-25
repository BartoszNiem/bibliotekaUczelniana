package Dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Audiobook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AudiobookDao extends MainDao {

	public ObservableList<Audiobook> listaAudiobookow() {
		ObservableList<Audiobook> audiobooki = FXCollections.observableArrayList();

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement( "select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
						+ " from audiobook k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id;")) {
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("audiobook_id");
				String tytul = rs.getString("tytul");
				String autorzy = autorzyAudiobook(id);
				String wydawnictwo = rs.getString("wnazwa");
				int rok_wydania = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				Audiobook audiobook = new Audiobook(id, tytul, autorzy, wydawnictwo, rok_wydania, dzial);
				audiobooki.add(audiobook);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audiobooki;
	}
	
	public ObservableList<Audiobook> selectAudioTytul(String str) {

		String SELECT_PDF = "select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from audiobook p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where p.tytul like ?;";

		ObservableList<Audiobook> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PDF);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}
	
	public ObservableList<Audiobook> selectAudioDzial(String str) {

		String SELECT_PDF = "select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from audiobook p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where d.nazwa like ?;";

		ObservableList<Audiobook> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PDF);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}
	
	public ObservableList<Audiobook> selectAudioWyd(String str) {

		String SELECT_PDF = "select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from audiobook p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where w.nazwa like ?;";

		ObservableList<Audiobook> books = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PDF);
			preparedStatement.setString(1, str);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			books = getList(rs);
		} catch (SQLException e) {
			printSQLException(e);
		}
		return books;
	}
	
	public ObservableList<Audiobook> selectAudiobookRok(int rok) {

		String SELECT_BOOKS = "select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from audiobook k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where rok_wydania = ?;";

		ObservableList<Audiobook> books = FXCollections.observableArrayList();
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
	
	public ObservableList<Audiobook> selectPDFAutor(String autor) {

		String SELECT_BOOKS = "select k.audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from audiobook k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id"
				+ " join autor_audiobook ak on ak.audiobook_id = k.audiobook_id join autor a on a.autor_id = ak.autor_id"
				+ " where a.imie like ? or a.nazwisko like ?;";
		String imie = "";
		String nazwisko = "";
		for (int i = 0; i < autor.length(); i++) {
			if (autor.charAt(i) == ' ') {
				imie += autor.substring(0, i);
				nazwisko += autor.substring(i+1, autor.length());
				break;
			}
		}
		if(imie.equals("") || nazwisko.equals("")) {
			imie += autor;
			nazwisko += autor;
		}

		ObservableList<Audiobook> books = FXCollections.observableArrayList();
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
	
	public ObservableList<Audiobook> getList(ResultSet rs) {

		ObservableList<Audiobook> audio = FXCollections.observableArrayList();

		try {
			while (rs.next()) {
				int id = rs.getInt("audiobook_id");
				String tytul = rs.getString("tytul");
				String wydaw = rs.getString("wnazwa");
				int rok = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				String autorzy = autorzyAudiobook(id);
				audio.add(new Audiobook(id, tytul, autorzy, wydaw, rok, dzial));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return audio;
	}

	public String autorzyAudiobook(int id_audiobook) {
		String autorzy = "";

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from autor_audiobook natural join autor where audiobook_id = ?;")) {
			preparedStatement.setInt(1, id_audiobook);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				autorzy += imie + "  " + nazwisko + ",  ";
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return autorzy;
	}

	public int dodajAudiobook(Audiobook audiobook) {
		int czyDodano = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into audiobook(tytul, wydawnictwo_id, rok_wydania, dzial_id, tresc) values(?,?,?,?,?);")) {
			preparedStatement.setString(1, audiobook.getTytul());
			preparedStatement.setInt(2, getWydawnictwoID(audiobook.getWydawnictwo()));
			preparedStatement.setInt(3, audiobook.getRokWydania());
			preparedStatement.setInt(4, getDzialID(audiobook.getDzial()));
			preparedStatement.setBlob(5, audiobook.getTrescIS());
			System.out.println(preparedStatement);
			czyDodano = preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
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

			e.printStackTrace();
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

			e.printStackTrace();
		}
		return id;
	}

	public Audiobook zwrocAudiobook(int id) {
		Audiobook audiobook = null;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement("select audiobook_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, tresc"
						+ " from audiobook k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where audiobook_id = ?;")) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("audiobook_id");
				String tytul = rs.getString("tytul");
				String autorzy = autorzyAudiobook(id);
				String wydawnictwo = rs.getString("wnazwa");
				int rok_wydania = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				Blob tresc = rs.getBlob("tresc");
				audiobook = new Audiobook(id, tytul, autorzy, wydawnictwo, rok_wydania, dzial, tresc);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return audiobook;
	}

	public int getMaxAudiobookID() {
		int id = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from audiobook order by audiobook_id desc limit 1;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("audiobook_id");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return id;
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

			e.printStackTrace();
		}
		return id;
	}

	public int dodajAutoraAudiobook(int autor_id, int audiobook_id) {
		int czyDodano = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into autor_audiobook(autor_id, audiobook_id) values(?,?);")) {
			preparedStatement.setInt(1, autor_id);
			preparedStatement.setInt(2, audiobook_id);

			System.out.println(preparedStatement);
			czyDodano = preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return czyDodano;
	}

	public void pobierzAudiobook(int audiobook_id) {
		Audiobook audiobook = zwrocAudiobook(audiobook_id);
		int BUFFER_SIZE = 4096;
		String downloadFilePath = audiobook.getTytul() + ".mp3";
		try {
			InputStream inputStream = audiobook.getTresc().getBinaryStream();
			OutputStream outputStream = new FileOutputStream(downloadFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outputStream.close();
			System.out.println("File saved");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void usunAudiobook(int audiobook_id) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from audiobook where audiobook_id = ?;")) {
			preparedStatement.setInt(1, audiobook_id);

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void usunAutorAudiobook(int audiobook_id) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from autor_audiobook where audiobook_id = ?;")) {
			preparedStatement.setInt(1, audiobook_id);

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
