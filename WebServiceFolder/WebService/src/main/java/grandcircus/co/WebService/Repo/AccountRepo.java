package grandcircus.co.WebService.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import grandcircus.co.WebService.Models.Account;

public interface AccountRepo extends MongoRepository<Account, String>{
	public Account findByEmail(String email);
	public Account findByPassword(String password);
}
