package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import application.Main;
import application.Rezerwacja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RezerwacjaDao extends MainDao {

	public ObservableList<Rezerwacja> selectRezerwacje() {
		ObservableList<Rezerwacja> rez = FXCollections.observableArrayList();

		String SELECT_REZERWACJE = "select rezerwacja_id, k.tytul, data_rezerwacji from rezerwacja r join ksiazka k on r.ksiazka_id = k.ksiazka_id"
				+ " where uzytkownik_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REZERWACJE);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, Main.getObecnyStudent().getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("rezerwacja_id");
				String tytul = rs.getString("tytul");
				CharSequence data = rs.getString("data_rezerwacji");
				Rezerwacja r = new Rezerwacja(id, tytul, LocalDate.parse(data));
				rez.add(r);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rez;
	}

	public void insertRezerwacja(Rezerwacja r) {

		String INSERT_REZERWACJA = "insert into rezerwacja (uzytkownik_id, ksiazka_id, data_rezerwacji) values (?, ? , ?);";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REZERWACJA);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, Main.getObecnyStudent().getId());
			preparedStatement.setInt(2, r.getBook().getId());
			preparedStatement.setString(3, r.getData().toString());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void deleteRezerwacja(Rezerwacja r) {

		String DELETE_REZERWACJA = "delete from rezerwacja where rezerwacja_id = ?";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REZERWACJA);
			preparedStatement.setInt(1, r.getId());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}

	}

	public ObservableList<Rezerwacja> listaRezerwacji() {
		ObservableList<Rezerwacja> rez = FXCollections.observableArrayList();

		String SELECT_REZERWACJE = "select * from rezerwacja natural join ksiazka";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REZERWACJE);
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("rezerwacja_id");
				int idStud = rs.getInt("uzytkownik_id");
				int idKsiazki = rs.getInt("ksiazka_id");
				String tytul = rs.getString("tytul");
				String data = rs.getString("data_rezerwacji");
				String dost = rs.getString("czyDostepna");
				Rezerwacja r = new Rezerwacja(id, tytul, idKsiazki, idStud, data, dost);
				rez.add(r);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rez;
	}

	public void zmienDostepnosc(int rez_id) {

		String zmien_dost = "update rezerwacja set czyDostepna = ? where rezerwacja_id = ?";
		if (liczbaEgzemplarzy(zwrocIDKsiazkiPoRezID(rez_id)) > 0) {
			Connection connection = getConnection();
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(zmien_dost);
				preparedStatement.setString(1, "Dostêpna");
				preparedStatement.setInt(2, rez_id);
				System.out.println(preparedStatement);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				printSQLException(e);
			}
		}

	}

	public int liczbaEgzemplarzy(int book_id) {
		int liczbaEgz = 0;
		String select_ksiazki_liczba_egz = "select * from ksiazka where ksiazka_id = ?";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(select_ksiazki_liczba_egz);
			preparedStatement.setInt(1, book_id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				liczbaEgz = rs.getInt("liczba_egz");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczbaEgz;
	}

	public int zwrocIDKsiazkiPoRezID(int rez_id) {
		int ksiazkaID = -1;
		String select_ksiazki_liczba_egz = "select * from rezerwacja natural join ksiazka where rezerwacja_id = ?";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(select_ksiazki_liczba_egz);
			preparedStatement.setInt(1, rez_id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ksiazkaID = rs.getInt("ksiazka_id");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return ksiazkaID;
	}

}
