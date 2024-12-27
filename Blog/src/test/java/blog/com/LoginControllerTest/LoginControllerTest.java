package blog.com.LoginControllerTest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.models.entity.Account;
import blog.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareData() {
		Account test = new Account("test@test.com", "test", "1234");
		// ログインが成功：　username "test@test.com"、　password "1234"　true

		when(accountService.loginCheck("test@test.com", "1234")).thenReturn(test);
		// ログインが失敗：　username "Ana"と等しい、　パスワードはどんな値でもいい　false
		when(accountService.loginCheck(eq("1234@test.com"), any())).thenReturn(null);

	}

	// ログインページを正しく取得するテスト
	@Test

	public void testGetLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/account/login");

		mockMvc.perform(request)
				.andExpect(view().name("login.html"));
	}

	// ログインが成功した場合のテスト
	//ログインが成功したら「blog-list.html」に遷移して、入力された値が渡されているかのテスト
	@Test
	public void testLogin_NewAccount_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/account/login/process")
				.param("accountEmail", "test@test.com")
				.param("password", "1234");
		mockMvc.perform(request)
				.andExpect(status().is3xxRedirection()) // redirect確認
				.andExpect(redirectedUrl("account/blog/list")); // ルート確認
	}

	//ログインが失敗した場合のテスト

	@Test
	public void testLogin_NewAccount_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/account/login/process")
				.param("accountEmail", "1234@test.com")
				.param("password", "1234");
		mockMvc.perform(request)
				.andExpect(view().name("login.html"));
	}
}
