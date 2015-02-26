package net.versatile.notesonfly.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.web.utils.AppConstants;

import java.util.logging.Logger;

public class FeedbackSupportProvider implements FeedbackSupport{
/*	String INSERT_CHAT_SQL = "INSERT INTO CHAT_DETAILS (USER, TEXT) VALUES (?, ?);";
	String SELECT_ALL_CHAT_SQL = "SELECT * FROM CHAT_DETAILS;";
	Logger logger = Logger.getLogger(this.getClass().getName());
*/	
	static List<Feedback> feedbacksList = new ArrayList<Feedback>();
	@Override
	public void addFeedback(Feedback feedback)
			throws DataStoreException {

/*		Connection connection = getDBConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHAT_SQL);
			preparedStatement.setString(1, feedback.getUserName());
			preparedStatement.setString(2, feedback.getContent());
			preparedStatement.execute();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/	
		feedbacksList.add(feedback);
	}

	@Override
	public List<Feedback> getAllFeedbacks() throws DataRetievalException {
/*		Connection connection = getDBConnection();
		 List<Feedback> feedbacksList = new ArrayList<Feedback>();
		try {
			Statement preparedStatement = connection.createStatement();
			ResultSet resultSet = preparedStatement.executeQuery(SELECT_ALL_CHAT_SQL);
			while(resultSet.next()){
				Feedback feedback = new Feedback();
				feedback.setContent(resultSet.getString(1));
				feedback.setUserName(resultSet.getString(2));
				feedbacksList.add(feedback);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/		return feedbacksList;
	}
	
/*	public Connection getDBConnection(){
		Connection connection = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      connection = DriverManager.getConnection("jdbc:sqlite:versatile.db");
	    } catch ( Exception e ) {
	    	logger.info("Exception while getting connection : " + e);
	    }
	    return connection;
	}

*/}
