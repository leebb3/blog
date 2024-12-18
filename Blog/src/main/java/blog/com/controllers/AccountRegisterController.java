package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.services.AccountService;

@Controller
public class AccountRegisterController {
	
	@Autowired
	private AccountService accountService;
	
	//登録画面
	@GetMapping("/account/register")
	public String getAccountPage() {
		return "register.html";
	}
	@PostMapping("/account/register/process")
	public String accountRegisterProcess(
			@RequestParam String accountEmail,
			@RequestParam String accountName,
			@RequestParam String password) {
		if(accountService.createAdmin(accountEmail, accountName, password)) {
			return "login.html";
		}else {
			return "register.html";
		}
	}
}
