package blog.com.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Account;

@Controller
public class BlogProfileController {
	@Autowired
	private HttpSession session;

	// Blog 一覧ページを表示する
	@GetMapping("/blog/profile")
	public String getBlogList(Model model) {
		// セッションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// account == null の場合ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// ログインしている人の Blog 情報を取得する
			//            List<Blog> blogList = blogService.selectBlogListByAccountId(account.getAccountId());
			//            model.addAttribute("accountName", account.getAccountName());
			//            model.addAttribute("blogList", blogList);
			return "profile.html";
		}
	}
}
