package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Main;
import application.Pracownik;
import application.Uzytkownik;

public class LoginDao extends MainDao {

	private static Uzytkownik uzytkownik;

	public boolean zaloguj(String email, String password) {
		boolean czyZnal = false;
		String LOGOWANIE = "select * from uzytkownik where email = ? and uzytkownik_password = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(LOGOWANIE);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				czyZnal = true;
				int id = rs.getInt("uzytkownik_id");
				String imie = rs.getString("imie");
				String nazwisko = rs.getString("nazwisko");
				String pesel = rs.getString("pesel");
				Uzytkownik u = new Uzytkownik(id, imie, nazwisko, pesel, email, password);
				uzytkownik = u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return czyZnal;
	}

	public static Uzytkownik getCurrentUser() {
		return uzytkownik;
	}

	public boolean zalogujA(String email, String password) {
		boolean czyZnal = false;
		Connection connection = getConnection();

		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select * from pracownik where email = ? and pracownik_password = ? ")) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			int id = -1;
			String imie = "";
			String nazwisko = "";
			String pesel = "";
			String rola = "";
			while (rs.next()) {
				czyZnal = true;
				id = rs.getInt("pracownik_id");
				imie = rs.getString("imie");
				nazwisko = rs.getString("nazwisko");
				pesel = rs.getString("pesel");
				rola = rs.getString("rola");
			}
			Pracownik p = new Pracownik(id, imie, nazwisko, pesel, rola, email, password);
			Main.setObecnyPracownik(p);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return czyZnal;
	}

	public static void setCurrentUser(Uzytkownik u) {
		uzytkownik = u;
	}
}
