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

import application.MaterialyPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MaterialyDao extends MainDao {

	public ObservableList<MaterialyPDF> listaMaterialowPDF() {
		ObservableList<MaterialyPDF> materialy = FXCollections.observableArrayList();

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
						+ " from pdf k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("pdf_id");
				String tytul = rs.getString("tytul");
				String autorzy = autorzyPDF(id);
				String wydawnictwo = rs.getString("wnazwa");
				int rok_wydania = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				MaterialyPDF pdf = new MaterialyPDF(id, tytul, autorzy, wydawnictwo, rok_wydania, dzial);
				materialy.add(pdf);
			}
			
			System.out.println(materialy.size());

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return materialy;
	}
	
	public ObservableList<MaterialyPDF> selectPDFTytul(String str) {

		String SELECT_PDF = "select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from pdf p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where p.tytul like ?;";

		ObservableList<MaterialyPDF> books = FXCollections.observableArrayList();
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
	
	public ObservableList<MaterialyPDF> selectPDFWyd(String str) {

		String SELECT_PDF = "select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from pdf p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where w.nazwa like ?;";

		ObservableList<MaterialyPDF> books = FXCollections.observableArrayList();
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
	
	public ObservableList<MaterialyPDF> selectPDFDzial(String str) {

		String SELECT_PDF = "select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from pdf p join wydawnictwo w on p.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = p.dzial_id where d.nazwa like ?;";

		ObservableList<MaterialyPDF> books = FXCollections.observableArrayList();
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
	
	public ObservableList<MaterialyPDF> selectPDFRok(int rok) {

		String SELECT_BOOKS = "select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from pdf k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where rok_wydania = ?;";

		ObservableList<MaterialyPDF> books = FXCollections.observableArrayList();
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
	
	public ObservableList<MaterialyPDF> selectPDFAutor(String autor) {

		String SELECT_BOOKS = "select k.pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa"
				+ " from pdf k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id"
				+ " join autor_pdf ak on ak.pdf_id = k.pdf_id join autor a on a.autor_id = ak.autor_id"
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

		ObservableList<MaterialyPDF> books = FXCollections.observableArrayList();
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
	
	public ObservableList<MaterialyPDF> getList(ResultSet rs) {

		ObservableList<MaterialyPDF> pdf = FXCollections.observableArrayList();

		try {
			while (rs.next()) {
				int id = rs.getInt("pdf_id");
				String tytul = rs.getString("tytul");
				String wydaw = rs.getString("wnazwa");
				int rok = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				String autorzy = autorzyPDF(id);
				pdf.add(new MaterialyPDF(id, tytul, autorzy, wydaw, rok, dzial));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return pdf;
	}

	public String autorzyPDF(int id_pdf) {
		String autorzy = "";
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from autor_pdf natural join autor where pdf_id = ?;")) {
			preparedStatement.setInt(1, id_pdf);
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

	public int dodajMaterialPDF(MaterialyPDF pdf) {
		int czyDodano = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into pdf(tytul, wydawnictwo_id, rok_wydania, dzial_id, tresc) values(?,?,?,?,?);")) {
			preparedStatement.setString(1, pdf.getTytul());
			preparedStatement.setInt(2, getWydawnictwoID(pdf.getWydawnictwo()));
			preparedStatement.setInt(3, pdf.getRokWydania());
			preparedStatement.setInt(4, getDzialID(pdf.getDzial()));
			preparedStatement.setBlob(5, pdf.getTrescIS());
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

	public MaterialyPDF zwrocPDF(int id) {
		MaterialyPDF pdf = null;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement("select pdf_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa, tresc"
				+ " from pdf k join wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id join dzial d on d.dzial_id = k.dzial_id where pdf_id = ?;")) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("pdf_id");
				String tytul = rs.getString("tytul");
				String autorzy = autorzyPDF(id);
				String wydawnictwo = rs.getString("wnazwa");
				int rok_wydania = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");
				Blob tresc = rs.getBlob("tresc");
				pdf = new MaterialyPDF(id, tytul, autorzy, wydawnictwo, rok_wydania, dzial, tresc);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return pdf;
	}

	public int getMaxPdfID() {
		int id = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from pdf order by pdf_id desc limit 1;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				id = rs.getInt("pdf_id");
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

	public int dodajAutoraPDF(int autor_id, int pdf_id) {
		int czyDodano = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("insert into autor_pdf(autor_id, pdf_id) values(?,?);")) {
			preparedStatement.setInt(1, autor_id);
			preparedStatement.setInt(2, pdf_id);

			System.out.println(preparedStatement);
			czyDodano = preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return czyDodano;
	}

	public void pobierzMaterialPDF(int pdf_id) {
		MaterialyPDF pdf = zwrocPDF(pdf_id);
		int BUFFER_SIZE = 4096;
		String downloadFilePath = pdf.getTytul() + ".pdf";
		try {
			InputStream inputStream = pdf.getTresc().getBinaryStream();
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

	public void usunPDF(int pdf_id) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from pdf where pdf_id = ?;")) {
			preparedStatement.setInt(1, pdf_id);

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void usunAutorPDF(int pdf_id) {
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from autor_pdf where pdf_id = ?;")) {
			preparedStatement.setInt(1, pdf_id);

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
