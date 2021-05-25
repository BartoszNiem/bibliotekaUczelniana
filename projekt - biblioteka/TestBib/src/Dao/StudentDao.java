package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDao extends MainDao {

	public ObservableList<Student> listaStudentow() {
		ObservableList<Student> studenci = FXCollections.observableArrayList();
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement("select * from uzytkownik;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("uzytkownik_id");
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				String pesel = rs.getString("pesel");
				String email = rs.getString("email");
				String haslo = rs.getString("uzytkownik_password");
				Student s = new Student(id, imie, nazwisko, pesel, email, haslo);
				studenci.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return studenci;
	}

	public void dodajStudenta(Student s) {

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"insert into uzytkownik (imie, nazwisko, pesel, email, uzytkownik_password) values (?, ?, ?, ?, ?);")) {
			preparedStatement.setString(1, s.getImie());
			preparedStatement.setString(2, s.getNazwisko());
			preparedStatement.setString(3, s.getPesel());
			preparedStatement.setString(4, s.getEmail());
			preparedStatement.setString(5, s.getPassword());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void usunStudenta(Student s) {

		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("delete from uzytkownik where uzytkownik_id = ?;")) {
			preparedStatement.setInt(1, s.getId());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
}
