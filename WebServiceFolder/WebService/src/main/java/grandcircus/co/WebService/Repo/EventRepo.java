package grandcircus.co.WebService.Repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import grandcircus.co.WebService.Models.Event;

public interface EventRepo extends MongoRepository<Event, String>{
	@Query("{$and: [{'start': {$lte: ?1}}, {'end': {$gte: ?0}}]}")
	List<Event> findByDate(String startDate, String endDate);
	
	List<Event> findByEmployees(String employees);
}
