package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.models.entity.Blog;
import blog.com.services.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogEditController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	//編集画面に表示
	@GetMapping("/product/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		//セッションからログインしている人の情報をadminという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし、admin==null　ログイン画面にリダイレクトする
		//そうでない場合
		//ログインしている人の名前の情報を画面に渡して商品一覧のhtmlを表示。
		if (account == null) {
			return "redirect:/blog/list";

		} else {
			Blog blog = blogService.blogEditCheck(blogId);
			//もしproducts==nullだったら、商品の一覧ページにリダイレクトする
			//そうでない場合、編集画面に編集する内容を渡す
			//編集画面に表示
			model.addAttribute("accountName", account.getAccountName());
			model.addAttribute("blog", blog);
			return "blog-edit.html";
		}
	}

	// 更新処理をする
	@PostMapping("blog/edit/process")
	public String blogUpdate(
			@RequestParam String blogTitle,
			@RequestParam String categoryName,
			@RequestParam MultipartFile blogImage,
			@RequestParam String article,
			@RequestParam Long blogId) {
		//セッションからログインしている人の情報をadminという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		// もし、admin == nullだったら、ログイン画面にリダイレクトする
		// そうでない場合、
		// ファイルの保存
		// もし、blogUpdateの結果がtrueの場合は、商品一覧にリダイレクト
		// そうでない場合、blog編集画面にリダイレクトする
		if(account == null) {
			return "redirect:/account/login";
			
		}else {
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/"+fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(blogService.blogUpdate(blogId, blogTitle, categoryName, fileName, article, blogId)) {
				return "redirect:/blog/list";
			}else {
				return "redirect:/blog/edit"+blogId;
			}
		}
	}
}
