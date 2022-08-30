package grandcircus.co.WebService.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import grandcircus.co.WebService.Models.Event;

public interface EventRepo extends MongoRepository<Event, String>{
	
}
