package blog.com.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;

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
	@PostMapping("/account/login/process")
	public String accountLoginProcess(
			@RequestParam String accountEmail,
			@RequestParam String password,Model model) {
		//もし、account==nullログイン画面にとどまります
		//そうでない場合、sessionにログイン情報に保存
		//blog一覧画面にリダイレクトする
		Account account = accountService.loginCheck(accountEmail, password);

		if (account == null) {
			return "login.html";
		} else {
			session.setAttribute("loginAccountInfo", account);
			model.addAttribute("accountMail", accountEmail);
			return "redirect:/blog/list";
		}
	}

}
