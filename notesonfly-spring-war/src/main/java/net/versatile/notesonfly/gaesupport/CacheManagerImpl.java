package net.versatile.notesonfly.gaesupport;

import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import net.versatile.notesonfly.exceptions.DataRetievalException;
import net.versatile.notesonfly.model.Subscriber;
import net.versatile.notesonfly.service.CacheManager;
import net.versatile.notesonfly.service.SubscriberSupport;
import net.versatile.web.utils.AppConstants;

public class CacheManagerImpl implements CacheManager{

	MemcacheService memCacheService = MemcacheServiceFactory.getMemcacheService();
	Logger logger = Logger.getLogger(this.getClass().getName());
	SubscriberSupport subscriberSupport = new SubscriberSupportProvider();
	
	@Override
	public List<Subscriber> getAllSubsribers() {
		List<Subscriber> subscribersInList = null;
		Object allSubrcibersInObject = memCacheService.get(AppConstants.ALL_SUBSCRIBERS_KEY);
		logger.info(AppConstants.ALL_SUBSCRIBERS_KEY + " from Cache :" + allSubrcibersInObject); 
		
		if(allSubrcibersInObject != null){
			subscribersInList = (List<Subscriber>) allSubrcibersInObject;
		}else{
			logger.info(AppConstants.ALL_SUBSCRIBERS_KEY + "does not exists in Cache");
			try {
				subscribersInList = subscriberSupport.getAllSubscribers();
				logger.info("Pulled Subribers from Datastore: " + subscribersInList);
				memCacheService.put(AppConstants.ALL_SUBSCRIBERS_KEY,subscribersInList);
			} catch (DataRetievalException e) {
				logger.warning("Exception occured: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return subscribersInList;
	}

}
