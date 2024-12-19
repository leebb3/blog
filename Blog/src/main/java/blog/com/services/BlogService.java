package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;



@Service
public class BlogService {
	@Autowired
	private  BlogDao blogDao;
	// blog一覧のチェック
	// もしadminId==null 戻り値としてnull
	// findAll内容をコントローラークラスに渡す
	public List<Blog> selectAllBlogtList(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
}
	

	//blogの登録処理チェック
	//もし、findByBlogTitleが==nullだったら、
	//保存処理
	//true
	//そうでない場合
	//false
	public boolean createBlog(
			String blogTitle,
			String categoryName,
			String blogImage,
			String article,
			Long accountId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId));
			return true;
		} else {
			return false;
		}
	}

	//編集画面を表示するときのチェック
	//もし、blogId == null
	//そうでない場合、
	//findByBlogIdの情報をコントローラクスに渡す
	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}
	//更新処理のチェックの

	//もし、blogId==nullだったら、更新処理はしない
	//false
	//そうでない場合、
	//更新処理をする
	//コントローラークラスからもらった、blogIdを使って、編集する前の、データを取得
	//変更するべきところだけ、セッターを使用してデータの更新をする。
	//trueを返す
	public boolean blogUpdate(Long blogId,
			String blogTitle,
			String categoryName,
			String blogImage,
			String article,
			Long accountId) {
		if (blogId == null) {
			return false;
		} else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogTitle(blogTitle);
			blog.setCategoryName(categoryName);
			blog.setBlogImage(blogImage);
			blog.setArticle(article);
			blog.setAccountId(accountId);
			blogDao.save(blog);
			return true;
		}
	}

	//削除処理のチェック
	//もし、コントローラークラスから受け取ったblogIdがnull
	//false
	//そうでない場合、deleteByBlogIdを使って削除処理
	//true
	public boolean deleteBlog(Long blogId) {
			if(blogId == null) {
				return false;
			}else {
				blogDao.deleteByBlogId(blogId);
				return true;
			}
}
}
