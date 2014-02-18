package org.sfsoft.jfighter2dx.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.sfsoft.jfighter2dx.util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Clase que gestiona la configuración del juego
 * @author Santiago Faci
 * @version 1.0
 *
 */
public class ConfigurationManager {

	private Preferences prefs;
	
	public ConfigurationManager() {
		prefs = Gdx.app.getPreferences(Constants.APP);
	}
	
	public boolean isSoundEnabled() {
		
		return prefs.getBoolean("sound");
	}
	
	/**
	 * Añade la puntuación de un jugador a la base de datos
	 * Si la tabla no existe se crea (la primera vez)
	 */
	public static void addScores(String name, int score) {
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:sqlite:" + Gdx.files.internal("scores.db"));
		
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS scores (id integer primary key autoincrement, name text, score int)");
			statement.executeUpdate("INSERT INTO scores (name, score) VALUES ('" + name + "', " + score + ")");
			
			if (statement != null)
				statement.close();
			if (connection != null)
				connection.close();
		
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	 * Devuelve la lista de las diez mejores puntuaciones del juego
	 * @return La lista de puntuaciones
	 */
	public static List<Score> getTopScores() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:sqlite:" + Gdx.files.internal("scores.db"));
		
			Statement statement = connection.createStatement();
			ResultSet res = statement.executeQuery("SELECT name, score FROM scores ORDER BY score DESC LIMIT 10");
			List<Score> scores = new ArrayList<Score>();
			Score score = null;
			while (res.next()) {
				score = new Score();
				score.name = res.getString("name");
				score.score = res.getInt("score");
				scores.add(score);
			}
			
			if (statement != null)
				statement.close();
			if (res != null)
				res.close();
			if (connection != null)
				connection.close();
			
			return scores;
		
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return new ArrayList<Score>();
	}
	
	public static class Score {
		public String name;
		public int score;
	}
}
