package grandcircus.co.WebApp.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grandcircus.co.WebApp.Models.Account;

@Service
public class AccountService {
	private RestTemplate restTemplate = new RestTemplate();

	public Account[] getAllAccounts() {
		String url = "http://localhost:8080/account/all";
		Account[] allAccounts = restTemplate.getForObject(url, Account[].class);
		return allAccounts;
	}
	
	public Account addNewAccount(Account account) {
		return restTemplate.postForObject("http://localhost:8080/account", account, Account.class);
	}
	
	public Account getAccountById(String id) {
		String url = "http://localhost:8080/account?id=" + id;
		return restTemplate.getForObject(url, Account.class);
	}
	
	public Account getAccountByEmail(String email) {
		String url = "http://localhost:8080/account/" + email;
		Account account = restTemplate.getForObject(url, Account.class);
		return account;
	}
	
	public Account updatePassword(String id, String password) {
		String url = "http://localhost:8080/account/" + id;
		Account account = getAccountById(id);
		account.setPassword(password);
		Account accountResult = restTemplate.patchForObject(url, account, Account.class);
		return accountResult;
	}
}
