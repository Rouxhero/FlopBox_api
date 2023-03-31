package com.flopbox.app.util.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.flopbox.app.util.exception.ControllerException;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;

/**
 * <h1>Class CSVDataBase</h1>
 *
 * Cette classe permet de gérer une base de données sous forme de fichier CSV.
 * Les differentes méthodes permettent d'initialiser la base de données,
 * d'insérer des données, de mettre à jour des données et de récupérer les
 * données. Cela permet d'eviter la duplication de code, et de simplifier la
 * gestion des données.
 *
 * @author Leo Le Van Canh dit Ban <a href=
 *         "mailto:leo.levancanhditban.etu@univ-lille.fr">leo.levancanhditban.etu@univ-lille.fr</a>
 *
 */
public class CSVDataBase {

	/**
	 * Le chemin vers le fichier CSV contenant les utilisateurs.
	 */
	public final static String USER_FILE = "src/main/resources/user.csv";
	/**
	 * Le chemin vers le fichier CSV contenant les serveurs.
	 */
	public final static String SERVER_FILE = "src/main/resources/server.csv";
	/**
	 * Les noms des colonnes du fichier CSV contenant les utilisateurs. (Definition
	 * des tables)
	 */
	public static final String[] USER_DB = { "username", "password", "email", "key", "server" };
	/**
	 * Les noms des colonnes du fichier CSV contenant les serveurs. (Definition des
	 * tables)
	 */
	public static final String[] SERVER_DB = { "id","alias","host", "port" };

	/**
	 * Cette méthode permet de créer un CSVWriter.
	 *
	 * @param csvFilePath Le chemin vers le fichier CSV.
	 * @param append      Si true, les données seront ajoutées à la fin du fichier.
	 *                    Si false, le fichier sera écrasé.
	 * @throws IOException
	 */
	private static CSVWriter create_writer(String csvFilePath, boolean append) throws IOException {
		return new CSVWriter(new FileWriter(csvFilePath, append), ',', ICSVWriter.NO_QUOTE_CHARACTER,
				ICSVWriter.NO_QUOTE_CHARACTER, " ");
	}

	/**
	 * Cette méthode permet d'initialiser la base de données.
	 *
	 * @param csvFilePath Le chemin vers le fichier CSV.
	 * @param columnNames Les noms des colonnes du fichier CSV.
	 * @throws IOException
	 */
	public static void initializeDatabase(String csvFilePath, String[] columnNames) throws IOException {
		CSVWriter writer = create_writer(csvFilePath, false);
		writer.writeNext(columnNames);
		writer.close();
	}

	/**
	 * Le chemin vers le fichier CSV.
	 */
	private final String csvFilePath;

	/**
	 * Constructeur de la classe CSVDataBase.
	 *
	 * @param csvFilePath Le chemin vers le fichier CSV.
	 * @param columns     Les noms des colonnes du fichier CSV.
	 * @throws IOException
	 */
	public CSVDataBase(String csvFilePath, String[] columns) throws IOException {
		this.csvFilePath = csvFilePath;
		if (!new File(csvFilePath).exists())
			initializeDatabase(csvFilePath, columns);
	}

	/**
	 * Cette méthode permet de recuperer l'essemble des lignes de la base de
	 * données.
	 *
	 * @throws CsvValidationException Si le fichier CSV est invalide.
	 * @throws IOException
	 */
	public List<String[]> getRecords() throws CsvValidationException, IOException {
		List<String[]> records = new ArrayList<>();
		CSVReader reader = init_reader();
		String[] row;
		while ((row = reader.readNext()) != null) {
			records.add(row);
		}
		reader.close();
		return records;
	}

	/**
	 * Cette méthode permet de recuperer des lignes de la base de données, en
	 * utilisant un filtre pour la selection (WHERE en sql).
	 *
	 * @param columnName Le nom de la colonne à filtrer.
	 * @param value      La valeur de la colonne à filtrer.
	 * @throws IOException
	 * @throws CsvValidationException
	 */
	public List<String[]> getRecordsWhere(String columnName, String value) throws IOException, CsvValidationException {
		List<String[]> matchingRecords = new ArrayList<>();
		CSVReader reader = init_reader();
		String[] columnNames = reader.readNext();
		int columnIndex = -1;
		for (int i = 0; i < columnNames.length; i++) {
			if (columnNames[i].equals(columnName)) {
				columnIndex = i;
				break;
			}
		}
		if (columnIndex == -1) {
			System.out.println("[WARNING] Column name not found: " + columnName);
			return matchingRecords;
		}
		String[] row;
		while ((row = reader.readNext()) != null) {
			if (row[columnIndex].equals(value)) {
				matchingRecords.add(row);
			}
		}
		reader.close();
		return matchingRecords;
	}

	/**
	 * Cette méthode permet de créer un CSVReader.
	 *
	 * @return
	 * @throws FileNotFoundException
	 */
	private CSVReader init_reader() throws FileNotFoundException {
		return new CSVReader(new FileReader(csvFilePath));
	}

	/**
	 * Cette méthode permet d'insérer une ligne dans la base de données.
	 *
	 * @param record La ligne à insérer.
	 * @throws IOException
	 */
	public void insertRecord(String[] record) throws IOException {
		CSVWriter writer = create_writer(csvFilePath, true);
		record[0] = "\n" + record[0];
		writer.writeNext(record);
		writer.close();
	}

	/**
	 * Cette méthode permet de mettre à jour une ligne de la base de données.
	 *
	 * @param currentRecord La ligne à mettre à jour.
	 * @param newRecord     La nouvelle ligne.
	 * @param on            L'index de la colonne à filtrer.
	 * @throws IOException
	 * @throws ControllerException
	 * @throws CsvException
	 */
	public void updateRecord(String[] currentRecord, String[] newRecord, int on)
			throws IOException, ControllerException, CsvException {
		CSVReader reader = new CSVReader(new FileReader(csvFilePath));
		List<String[]> allRecords = reader.readAll();
		reader.close();
		for (int i = 0; i < allRecords.size(); i++) {
			String[] record = allRecords.get(i);
			if (record[on].equals(currentRecord[on])) {
				allRecords.set(i, newRecord);
				break;
			}
		}
		CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
		for (String[] record : allRecords) {
			writer.writeNext(record);
		}
		writer.close();
	}
}
