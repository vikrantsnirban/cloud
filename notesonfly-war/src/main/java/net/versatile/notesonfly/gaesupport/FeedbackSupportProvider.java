package net.versatile.notesonfly.gaesupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Feedback;
import net.versatile.notesonfly.service.FeedbackSupport;
import net.versatile.web.utils.AppConstants;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class FeedbackSupportProvider implements FeedbackSupport{
	DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	Key commentBookKey = KeyFactory.createKey(AppConstants.COMMENT_KEY, AppConstants.COMMENT_BOOK_NAME);

	@Override
	public void addFeedback(Feedback feedback)
			throws DataStoreException {
		try{
		

		Entity comment = new Entity(AppConstants.COMMENT_ENTITY, commentBookKey);
		comment.setProperty(AppConstants.COMMENT_VALUE, feedback.getContent());
		comment.setProperty(AppConstants.COMMENT_USER, feedback.getUserName());
		comment.setProperty(AppConstants.COMMENT_UPDATE_TIME, feedback.getUpdateTime());
		
		datastoreService.put(comment);
		
		}catch(Exception exception){
			throw new DataStoreException();
		}
	}

	@Override
	public List<Feedback> getAllFeedbacks() throws DataRetievalException {
		try{
		Query query = new Query(AppConstants.COMMENT_ENTITY, commentBookKey).addSort(AppConstants.COMMENT_UPDATE_TIME,SortDirection.DESCENDING);
		List<Entity> feedbacks = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		List<Feedback> feedbacksList = new ArrayList<Feedback>();
		for(Entity feedbackEntity : feedbacks){
			Feedback feedback = new Feedback();
			feedback.setContent(feedbackEntity.getProperty(AppConstants.COMMENT_VALUE).toString());
			feedback.setUserName(feedbackEntity.getProperty(AppConstants.COMMENT_USER).toString());
			feedbacksList.add(feedback);
		}
		return feedbacksList;
		}catch(Exception exception){
			throw new DataRetievalException();
		}
	}

}
