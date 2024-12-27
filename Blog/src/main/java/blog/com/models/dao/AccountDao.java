package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	//保存処理と更新処理　insertとupdate
	Account save(Account account);

	//SELECT * FROM admin WHERE admin_email = ?
	//用途：管理者の登録処理をするときに、同じメールアドレスがあったらば登録させないようにする
	//1行だけしかレコードは取得できない
	Account findByAccountEmail(String accountEmail);

	//SELECT * FROM admin WHERE admin_email=? AND password=?
	//用途：ログイン処理に使用。入力したメールアドレスとパスワードが一致してるときだけデータを取得する
	Account findByAccountEmailAndPassword(String accountEmail, String password);

}
