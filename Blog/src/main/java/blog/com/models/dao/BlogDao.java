package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BlogDao extends JpaRepository<Blog, Long> {
	Blog save (Blog blog);
	
	//SELECT * FROM blog
	//用途：商品の一覧を表示させるときに使用
	List<Blog>findAll();
	
	//SELECT * FROM blog WHERE blog_name = ?
	//用途：blogの登録チェックに使用
	Blog findByBlogTitle(String blogTitle);
	
	//SELECT * FROM blog WHERE product_id = ?
	//用途：編集画面を表示する際に使用。
	Blog findByBlogId(Long blogId);
	
	//DLETE FROM blog WHERE blog_id = ?
	//用途：削除使用します。
	void deleteByBlogId(Long blogId);
}
