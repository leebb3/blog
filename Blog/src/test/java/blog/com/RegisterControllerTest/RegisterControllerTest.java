package blog.com.RegisterControllerTest;

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

import blog.com.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	// サービスクラスを使ったデータ作成
	@BeforeEach
	
	public void prepareData() {
		// 登録できる場合　"test@test.com", "test", "1234"  true
		when(accountService.createAccount("test@test.com", "test", "1234")).thenReturn(true);
		// 登録できない場合　メールは"1234@test.com"と等しいこと　名前とパスワードはどんな値でもいい　false
		when(accountService.createAccount(eq("1234@test.com"), any(), any())).thenReturn(false);
	}
	// 登録画面が正常表示できるテスト
	@Test
	public void testGetRegisterPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
									.get("/account/register");
		
		mockMvc.perform(request)
				.andExpect(view().name("register.html"));
	}
	// ユーザーの登録が成功するかのテスト
	@Test
	public void testRegister_NewAccount_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
									.post("/account/register/process")
									.param("accountEmail","test@test.com")
									.param("accountName","test")
									.param("password", "1234");
		mockMvc.perform(request)
						.andExpect(view().name("login.html"));
	}
	// ユーザーの登録が失敗するかのテスト
	@Test
	public void testRegister_NewAccount_Fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
									.post("/account/register/process")
									.param("accountEmail","1234@test.com")
									.param("accountName","1234")
									.param("password", "1234abcd");
		mockMvc.perform(request)
						.andExpect(view().name("register.html"));
	}
	
}
