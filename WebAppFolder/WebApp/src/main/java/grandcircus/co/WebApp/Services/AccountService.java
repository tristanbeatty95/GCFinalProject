package grandcircus.co.WebApp.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grandcircus.co.WebApp.Models.Account;

@Service
public class AccountService {
	
	@Value("${api.baseUrl}")
	private String baseUrl;
	
	private RestTemplate restTemplate = new RestTemplate();

	public Account[] getAllAccounts() {
		String url = baseUrl + "/account/all";
		Account[] allAccounts = restTemplate.getForObject(url, Account[].class);
		return allAccounts;
	}
	
	public Account addNewAccount(Account account) {
		String url = baseUrl + "/account";
		return restTemplate.postForObject(url, account, Account.class);
	}
	
	public Account getAccountById(String id) {
		String url = baseUrl + "/account?id=" + id;
		return restTemplate.getForObject(url, Account.class);
	}
	
	public Account getAccountByEmail(String email) {
		String url = baseUrl + "/account/" + email;
		Account account = restTemplate.getForObject(url, Account.class);
		return account;
	}
	
	public Account updatePassword(String id, String password) {
		String url = baseUrl + "/account/" + id;
		Account account = getAccountById(id);
		account.setPassword(password);
		Account accountResult = restTemplate.patchForObject(url, account, Account.class);
		return accountResult;
	}
}
