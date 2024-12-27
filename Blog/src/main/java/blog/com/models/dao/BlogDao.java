package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
	Blog save(Blog blog);

	List<Blog> findAll();

	Blog findByBlogTitle(String blogTitle);

	Blog findByBlogId(Long blogId);

	void deleteByBlogId(Long blogId);

	// accountIdから、blogを探す

	List<Blog> findByAccountId(Long accountId);
}
