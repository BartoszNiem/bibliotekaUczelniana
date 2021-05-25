package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Pracownik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PracownikDao extends MainDao {
	public ObservableList<Pracownik> listaPracownikow() {
		ObservableList<Pracownik> pracownicy = FXCollections.observableArrayList();

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement("select * from pracownik;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("pracownik_id");
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				String rola = rs.getString("rola");
				String pesel = rs.getString("pesel");
				String email = rs.getString("email");
				String haslo = rs.getString("pracownik_password");
				Pracownik p = new Pracownik(id, imie, nazwisko, pesel, rola, email, haslo);
				pracownicy.add(p);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return pracownicy;
	}

	public void dodajPracownika(Pracownik pracownik) {

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into pracownik (imie, nazwisko, rola, pesel, email, pracownik_password) values (?, ?, ?, ?, ?, ?);")) {
			preparedStatement.setString(1, pracownik.getImie());
			preparedStatement.setString(2, pracownik.getNazwisko());
			preparedStatement.setString(3, pracownik.getRola());
			preparedStatement.setString(4, pracownik.getPesel());
			preparedStatement.setString(5, pracownik.getEmail());
			preparedStatement.setString(6, pracownik.getPassword());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void usunPracownika(Pracownik p) {

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from pracownik where pracownik_id = ?;")) {
			preparedStatement.setInt(1, p.getId());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
