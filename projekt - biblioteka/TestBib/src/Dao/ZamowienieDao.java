package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import application.Book;
import application.Zamowienie;
import application.ZamowienieUzytkownik;
import application.Rezerwacja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ZamowienieDao extends MainDao {

	public void insertZamowienie(Zamowienie z) {

		int plaId = insertPlatnosc();
		String INSERT_ZAMOWIENIE = "insert into zamowienie (uzytkownik_id, platnosc_id, status) values (? ,?, ?);";
		String SELECT_ID = "select max(zamowienie_id) from zamowienie;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ZAMOWIENIE);
			PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_ID);
			preparedStatement.setInt(1, z.getUserId());
			preparedStatement.setInt(2, plaId);
			preparedStatement.setString(3, "nie odebrano");
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement2.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("max(zamowienie_id)");
			}
			for (Book b : z.getKsiazki()) {
				insertZamowienieKasiazka(id, b.getId());
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void dodajZamowieniezRezerwacji(Zamowienie z) {

		int plaId = insertPlatnosc();
		String INSERT_ZAMOWIENIE = "insert into zamowienie (uzytkownik_id, data_odbioru, data_zwrotu, status,  platnosc_id) values (?,?,?, ?, ?);";
		String SELECT_ID = "select max(zamowienie_id) from zamowienie;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ZAMOWIENIE);
			PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_ID);
			preparedStatement.setInt(1, z.getUserId());
			preparedStatement.setString(2, z.getDataOdbioru().toString());
			preparedStatement.setString(3, z.getDataZwrotu().toString());
			preparedStatement.setString(4, z.getStatus());
			preparedStatement.setInt(5, plaId);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement2.executeQuery();
			int id = 0;
			while (rs.next()) {
				id = rs.getInt("max(zamowienie_id)");
			}

			for (Book b : z.getKsiazki()) {
				insertZamowienieKasiazka(id, b.getId());
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public int insertPlatnosc() {
		String INSERT_PLATNOSC = "insert into platnosc (kwota, potwierdzenie_zaplaty) values (0, \"nie\");";
		String SELECT_ID = "select max(platnosc_id) from platnosc;";
		int id = 0;
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PLATNOSC);
			PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_ID);
			System.out.println(preparedStatement);
			System.out.println(preparedStatement2);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement2.executeQuery();
			while (rs.next()) {
				id = rs.getInt("max(platnosc_id)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return id;
	}

	public void insertZamowienieKasiazka(int zaId, int ksiId) {
		String INSERT_ZAMOWIENIE_KSIAZKA = "insert into zamowienie_ksiazka (zamowienie_id, ksiazka_id) values (?, ?);";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ZAMOWIENIE_KSIAZKA);
			preparedStatement.setInt(1, zaId);
			preparedStatement.setInt(2, ksiId);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public ObservableList<Zamowienie> listaZamowien() {

		ObservableList<Zamowienie> lista = FXCollections.observableArrayList();
		String SELECT_ZAMOWIENIA = "select * from zamowienie natural join platnosc;";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ZAMOWIENIA);
			System.out.println(preparedStatement);

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("zamowienie_id");
				int userId = rs.getInt("uzytkownik_id");
				String dataO = (rs.getString("data_odbioru"));
				String dataZwr = (rs.getString("data_zwrotu"));
				Double kwota = rs.getDouble("kwota");
				String status = rs.getString("status");
				Zamowienie z = new Zamowienie(id, userId, dataO, dataZwr, kwota, status);
				lista.add(z);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return lista;
	}

	public void ustawOdbior(int idZamowienia) {
		String update_daty = "update zamowienie set data_odbioru = ?, data_zwrotu = ? where zamowienie_id = ?;";
		LocalDate dataOdbioru = LocalDate.now();
		LocalDate dataZwrotu = dataOdbioru.plusMonths(3);
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(update_daty);
			preparedStatement.setString(1, dataOdbioru.toString());
			preparedStatement.setString(2, dataZwrotu.toString());
			preparedStatement.setInt(3, idZamowienia);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void zmienStatus(int idZamowienia) {
		String update_status = "update zamowienie set status = ? where zamowienie_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(update_status);
			preparedStatement.setString(1, "odebrane");
			preparedStatement.setInt(2, idZamowienia);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Zamowienie zwrocZamowienie(int idZamowienia) {
		Zamowienie z = null;
		String select_status = "select * from zamowienie natural join platnosc where zamowienie_id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(select_status);
			preparedStatement.setInt(1, idZamowienia);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int userId = rs.getInt("uzytkownik_id");
				String dataO = rs.getString("data_odbioru");
				String dataZwr = (rs.getString("data_zwrotu"));
				Double kwota = rs.getDouble("kwota");
				String status = rs.getString("status");
				int platnosc_id = rs.getInt("platnosc_id");
				z = new Zamowienie(idZamowienia, userId, dataO, dataZwr, kwota, status);
				z.setIdPlatnosci(platnosc_id);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return z;
	}

	public void potwierdzOplate(int idZamowienia) {
		Zamowienie z = zwrocZamowienie(idZamowienia);
		int platnosc_id = z.getIdPlatnosci();
		String update_status = "update platnosc set kwota = ? where platnosc_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(update_status);
			preparedStatement.setDouble(1, 0);
			preparedStatement.setInt(2, platnosc_id);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void zrealizujZamowienie(int idZamowienia) {
		String delete_zamowienie = "delete from zamowienie where zamowienie_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(delete_zamowienie);
			preparedStatement.setInt(1, idZamowienia);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public void aktualizujNaleznosci(int idZamowienia) {
		Zamowienie z = zwrocZamowienie(idZamowienia);
		LocalDate dataDzis = LocalDate.now();
		LocalDate dataZwrotu = LocalDate.parse(z.getDataZwrotu());
		long daysBetween = ChronoUnit.DAYS.between(dataZwrotu, dataDzis);
		int liczbaKsiazek = liczbaKsiazekWZamowieniu(idZamowienia);
		double grzywna = 0;
		int platnosc_id = z.getIdPlatnosci();
		if (daysBetween > 0) {
			grzywna = (liczbaKsiazek * daysBetween * 0.1);
		}

		String delete_zamowienie = "update platnosc set kwota = ? where platnosc_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(delete_zamowienie);
			preparedStatement.setDouble(1, grzywna);
			preparedStatement.setInt(2, platnosc_id);
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public int liczbaKsiazekWZamowieniu(int idZamowienia) {
		int liczba = 0;
		String zapytanie = "select count(*) from zamowienie_ksiazka where zamowienie_id = ? group by zamowienie_id;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(zapytanie);
			preparedStatement.setDouble(1, idZamowienia);
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			while (rs.next()) {
				liczba = rs.getInt("count(*)");
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return liczba;
	}

	public ObservableList<Book> listaKsiazekzZamowienia(int IdZamowienia) {

		ObservableList<Book> lista = FXCollections.observableArrayList();
		String SELECT_ZAMOWIENIA = "select ksiazka_id, tytul, w.nazwa as wnazwa, rok_wydania, d.nazwa as dnazwa from"
				+ " zamowienie_ksiazka natural join ksiazka k join dzial d on k.dzial_id = d.dzial_id join"
				+ " wydawnictwo w on k.wydawnictwo_id = w.wydawnictwo_id where zamowienie_id = ?;";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ZAMOWIENIA);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, IdZamowienia);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ksiazka_id");
				String tytul = rs.getString("tytul");
				String autorzy = selectAutorzy(id);
				String wydawnictwo = rs.getString("wnazwa");
				int rok_wydania = rs.getInt("rok_wydania");
				String dzial = rs.getString("dnazwa");

				Book ksiazka = new Book(id, tytul, autorzy, wydawnictwo, rok_wydania, dzial);
				lista.add(ksiazka);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return lista;
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

	public ObservableList<ZamowienieUzytkownik> selectZamowienie() {

		ObservableList<ZamowienieUzytkownik> lista = FXCollections.observableArrayList();
		String SELECT_ZAMOWIENIA = "select z.zamowienie_id, z.status, z.data_zwrotu, p.kwota, k.tytul from zamowienie z join platnosc p on z.platnosc_id = p.platnosc_id"
				+ " join zamowienie_ksiazka zk on zk.zamowienie_id = z.zamowienie_id join ksiazka k on k.ksiazka_id = zk.ksiazka_id where uzytkownik_id = ?";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ZAMOWIENIA);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, LoginDao.getCurrentUser().getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("zamowienie_id");
				String data = rs.getString("data_zwrotu");
				Double kwota = rs.getDouble("kwota");
				String tytul = rs.getString("tytul");
				String status = rs.getString("status");
				ZamowienieUzytkownik z = new ZamowienieUzytkownik(id, tytul, kwota, data, status);
				lista.add(z);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return lista;
	}

	public Zamowienie selectZamowienie(int id) {
		String SELECT_ZAMOWIENIE = "select * from zamowienie where zamowienie_id = ?";
		Zamowienie z = null;
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ZAMOWIENIE);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int ids = rs.getInt("zamowienie_id");
				String data1 = rs.getString("data_odbioru");
				String data2 = rs.getString("data_zwrotu");
				z = new Zamowienie(ids, LoginDao.getCurrentUser().getId(), data1,
						data2, 0);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return z;
	}

	public ObservableList<Rezerwacja> selectRezerwacje() {
		ObservableList<Rezerwacja> rez = FXCollections.observableArrayList();

		String SELECT_REZERWACJE = "select rezerwacja_id, k.tytul, data_rezerwacji from rezerwacja r join ksiazka k on r.ksiazka_id = k.ksiazka_id"
				+ " where uzytkownik_id = ?;";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REZERWACJE);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, LoginDao.getCurrentUser().getId());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("rezerwacja_id");
				String tytul = rs.getString("tytul");
				String data = rs.getString("data_rezerwacji");
				Rezerwacja r = new Rezerwacja(id, tytul, LocalDate.parse(data));
				rez.add(r);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return rez;
	}

	public void insertPrzedluzoneZamowienie(Zamowienie z) {
		String UPDATE_ZAMOWIENIE_KSIAZKA = "update zamowienie_ksiazka set zamowienie_id = ? where zamowienie_id = ?;";
		String INSERT_ZAMOWIENIE = "insert into zamowienie (uzytkownik_id, data_odbioru, data_zwrotu, platnosc_id, status) values (? ,?, ?, ?, ?);";
		String SELECT_ID = "select max(zamowienie_id) from zamowienie;";
		String UPDATE_ZAMOWIENIE = "update zamowienie set data_zwrotu = ? where zamowienie_id = ?;";

		int plaId = insertPlatnosc();
		int ile = getLiczbaWZamowieniu(z);
		Connection connection = getConnection();
		try {
			if (ile == 1) {
				PreparedStatement preparedStatement4 = connection.prepareStatement(UPDATE_ZAMOWIENIE);
				preparedStatement4.setString(1, z.getDataZwrotu().toString());
				preparedStatement4.setInt(2, z.getId());
				System.out.println(preparedStatement4);
				preparedStatement4.executeUpdate();
			} else {
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ZAMOWIENIE);
				PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_ID);
				PreparedStatement preparedStatement3 = connection.prepareStatement(UPDATE_ZAMOWIENIE_KSIAZKA);
				preparedStatement.setInt(1, z.getUserId());
				preparedStatement.setString(2, LocalDate.now().toString());
				preparedStatement.setString(3, z.getDataZwrotu().toString());
				preparedStatement.setInt(4, plaId);
				preparedStatement.setString(5, "odebrano");
				System.out.println(preparedStatement);
				System.out.println(preparedStatement2);
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement2.executeQuery();
				int id = 0;
				while (rs.next()) {
					id = rs.getInt("max(zamowienie_id)");
				}
				preparedStatement3.setInt(1, id);
				preparedStatement3.setInt(2, z.getId());
				System.out.println(preparedStatement3);
				preparedStatement3.executeUpdate();
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public boolean czyMoznaPrzedluzyc(int id) {
		boolean czyMozna = true;
		String SELECT_REZERWACJE = "select * from rezerwacja where ksiazka_id = ?;";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REZERWACJE);
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				czyMozna = false;
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return czyMozna;
	}

	public int getLiczbaWZamowieniu(Zamowienie z) {
		int ile = 0;
		String SELECT = "select * from zamowienie_ksiazka natural join zamowienie where zamowienie_id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT);
			preparedStatement.setInt(1, z.getId());
			System.out.println(preparedStatement);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ile++;
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return ile;
	}

	public void insertRezerwacja(Rezerwacja r) {

		String INSERT_REZERWACJA = "insert into rezerwacja (uzytkownik_id, ksiazka_id, data_rezerwacji, czyDostepna) values (?, ? , ?, ?);";

		Connection connection = getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REZERWACJA);
			System.out.println(preparedStatement);
			preparedStatement.setInt(1, LoginDao.getCurrentUser().getId());
			preparedStatement.setInt(2, r.getBook().getId());
			preparedStatement.setString(3, r.getData().toString());
			preparedStatement.setString(4, "nie dostêpna");
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
}
