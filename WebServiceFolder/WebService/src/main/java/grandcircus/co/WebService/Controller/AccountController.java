package grandcircus.co.WebService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import grandcircus.co.WebService.Exceptions.AccountNotFoundException;
import grandcircus.co.WebService.Models.Account;
import grandcircus.co.WebService.Repo.AccountRepo;

@RestController
public class AccountController {
	@Autowired
	AccountRepo accountRepo;
	
	@GetMapping("/account/all")
	public List<Account> getAllAccounts() {
		return accountRepo.findAll();
	}
	
	@GetMapping("/account")
	public Account getAccountById(@RequestParam String id) {
		return accountRepo.findById(id).orElseThrow(() -> new AccountNotFoundException());
	}
	
	@GetMapping("/account/{email}")
	public Account getAccount(@PathVariable("email") String email) {
		return accountRepo.findByEmail(email);
	}
	
	@PostMapping("/account")
	@ResponseStatus(HttpStatus.CREATED)
	public Account addAccount(@RequestBody Account account) {
		accountRepo.insert(account);
		return account;
	}
	
	@PatchMapping("/account/{id}")
	public Account changePassword(@PathVariable("id") String id, @RequestParam String newPassword) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new AccountNotFoundException());
		account.setPassword(newPassword);
		return account;
	}
}
