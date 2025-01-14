package blog.com.controllers;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;

@Controller
public class BlogListController {

	@Autowired
	private HttpSession session;

	@Autowired
	private BlogService blogService;

	// Blog 一覧ページを表示する
	@GetMapping("/blog/list")
	public String getBlogList(Model model) {
		// セッションからログインしている人の情報を取得
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// account == null の場合ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		} else {
			// ログインしている人の Blog 情報を取得する
			List<Blog> blogList = blogService.selectBlogListByAccountId(account.getAccountId());
			model.addAttribute("accountName", account.getAccountName());
			model.addAttribute("blogList", blogList);
			return "blog-list.html";
		}
	}
}
