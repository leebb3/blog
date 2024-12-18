package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AccountLoginController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession session;

	//ログイン画面
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "login.html";
	}

	//ログイン処理
	@PostMapping("account/login/process")
	public String accountLoginProcess(
			@RequestParam String accountEmail,
			@RequestParam String password) {
		//もし、admin==nullログイン画面にとどまります
		//そうでない場合、sessionにログイン情報に保存
		//blog一覧画面にリダイレクトする
		Account account = accountService.loginCheck(accountEmail, password);
		
		if(account == null) {
			return "login.html";
		}else {
			session.setAttribute("loginAccountInfo", account);
			return "register.html";
		}
	}

}
