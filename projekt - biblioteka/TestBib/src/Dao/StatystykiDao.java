package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Book;

public class StatystykiDao extends MainDao {

	public int getLiczbaWypKsiazek() {
		int liczba = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select count(*) from zamowienie_ksiazka ;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				liczba = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczba;
	}

	public int getLiczbaZwrKsiazek() {
		int liczba = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select count(*) from zwrocone_ksiazki;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				liczba = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczba;
	}

	public int getLiczbaPrzetrzymanychKsiazek() {
		int liczba = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from zamowienie natural join platnosc natural join zamowienie_ksiazka where kwota > 0;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				liczba = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczba;
	}

	public int getLiczbaKsiazekZamowienia(int zamowienie_id) {
		int liczba = -1;
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection
				.prepareStatement("select count(*) from zamowienie_ksiazka where zamowienie_id = ?;")) {
			preparedStatement.setInt(1, zamowienie_id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				liczba = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczba;
	}

	public ArrayList<Integer> getMaxIDKsiazki() {
		int ksiazka_id = -1;
		ArrayList<Integer> zwrot = new ArrayList<>();
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select ksiazka_id,COUNT(*) as c from (SELECT *  FROM zamowienie_ksiazka union all select  * "
				+ "from zwrocone_ksiazki)k group by ksiazka_id order by c desc limit 1 ;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ksiazka_id = rs.getInt("ksiazka_id");
				zwrot.add(ksiazka_id);
				zwrot.add(rs.getInt("c"));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return zwrot;
	}

	public ArrayList<Book> get3NAjRezKsiazkii() {
		ArrayList<Book> ksiazki = new ArrayList<>();
		Connection connection = getConnection();
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select ksiazka_id, tytul, count(*) as c from rezerwacja natural join ksiazka"
				+ " group by ksiazka_id order by c desc limit 3;")) {

			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int ksiazka_id = rs.getInt("ksiazka_id");
				String tytul = rs.getString("tytul");
				int liczbaRezerwacji = rs.getInt("c");
				Book ksiazka = new Book();
				ksiazka.setId(ksiazka_id);
				ksiazka.setTytul(tytul);
				ksiazka.setLiczbaRezerwacji(liczbaRezerwacji);
				ksiazki.add(ksiazka);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return ksiazki;
	}
}