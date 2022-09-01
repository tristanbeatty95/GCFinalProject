package grandcircus.co.WebService.Repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import grandcircus.co.WebService.Models.Event;

public interface EventRepo extends MongoRepository<Event, String>{
	List<Event> findByEmployees(String employees);
}
