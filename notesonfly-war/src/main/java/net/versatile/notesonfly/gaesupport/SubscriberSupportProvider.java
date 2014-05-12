package net.versatile.notesonfly.gaesupport;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Subscriber;
import net.versatile.notesonfly.service.SubscriberSupport;
import net.versatile.web.utils.AppConstants;

public class SubscriberSupportProvider implements SubscriberSupport{

	Key subscriberKey = KeyFactory.createKey(AppConstants.SUBSCRIBER_KEY, AppConstants.SUBSCRIBER_LIST);
	DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
	
	@Override
	public void addSubsriber(Subscriber subsriber) throws DataStoreException {
		try{

		Entity subscriber = new Entity(AppConstants.SUBSCRIBER_ENTITY, subscriberKey);
		subscriber.setProperty(AppConstants.SUBSCRIBER_EMAIL, subsriber.getEmailAddress());

		datastoreService.put(subscriber);
		
		}catch(Exception e){
			throw new DataStoreException();
		}
		
	}

	@Override
	public List<Subscriber> getAllSubscribers() throws DataRetievalException {
		try{
		Query query = new Query(AppConstants.SUBSCRIBER_ENTITY, subscriberKey);
		List<Entity> subscribers = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());
		List<Subscriber> subscribersList = new ArrayList<Subscriber>();
		for(Entity subscriber : subscribers){
			subscribersList.add(new Subscriber(subscriber.getProperty(AppConstants.SUBSCRIBER_EMAIL).toString()));
		}
		return subscribersList;
		}catch(Exception exception){
			throw new DataRetievalException();
		}
	}

}
