package blog.com.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Account;
import blog.com.services.BlogService;

@Controller
public class BlogRegisterController {

	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	//blog画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		//セッションからログインしている人の情報をadminという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もしaccount==null ログイン画面にリダイレクトする
		//そうでない場合は、ログインしている人の名前を画面に渡す
		//blog登録のhtmlを表示させる
		if (account == null) {
			return "redirect:/account/login";
		} else {
			model.addAttribute("accountName", account.getAccountName());
			return "blog-register.html";
		}
	}

	//blogの登録処理
	@PostMapping("/blog/register/process")
	public String blogRegisterProcess(@RequestParam String blogTitle,
			@RequestParam String categoryName,
			@RequestParam MultipartFile blogImage,
			@RequestParam String blogDetail) {
		//セッションからログインしている人の情報をadminという変数に格納
		Account account = (Account) session.getAttribute("loginAccountInfo");
		//もし、account==nullだったら、ログイン画面にリダイレクトする
		//そうでない場合は、画像のファイル名を取得
		//画像のアップロード
		//もし、同じファイルの名前がなかったら保存
		//blogの一覧画面にリダイレクトする
		//そうでない場合、blog登録画面にとどまります。
		if (account == null) {
			return "redirect:/account/login";
		} else {
			//ファイルの名前を取得
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ blogImage.getOriginalFilename();
			//ファイルの保存作業
			try {
				Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (blogService.createBlog(blogTitle, categoryName, fileName, blogDetail, account.getAccountId())) {
				return "blog-register-fix.html";
			} else {
				return "blog-register.html";
			}
		}

	}

}
