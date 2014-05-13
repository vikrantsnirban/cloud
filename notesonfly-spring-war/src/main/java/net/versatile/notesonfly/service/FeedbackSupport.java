package net.versatile.notesonfly.service;

import java.util.List;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.exceptions.DataStoreException;
import net.versatile.notesonfly.model.Feedback;

public interface FeedbackSupport {
	void addFeedback(Feedback feedback ) throws DataStoreException;
	List<Feedback> getAllFeedbacks() throws DataRetievalException;
}
