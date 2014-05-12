package net.versatile.notesonfly.service;

import java.util.List;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Subscriber;

public interface SubscriberSupport {
	void addSubsriber(Subscriber subscriber) throws DataStoreException;
	List<Subscriber> getAllSubscribers() throws DataRetievalException;
}
