package blog.com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	// Blog 一覧の取得 (ログインしているユーザーのブログのみ)
	public List<Blog> selectBlogListByAccountId(Long accountId) {
		if (accountId == null) {
			return null;
		} else {
			return blogDao.findByAccountId(accountId);
		}
	}

	public boolean createBlog(String blogTitle, String categoryName, String blogImage, String article, Long accountId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogTitle, categoryName, blogImage, article, accountId, 0));
			return true;
		} else {
			return false;
		}
	}

	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}
	}

	public boolean blogUpdate(Long blogId, String blogTitle, String categoryName, String blogImage, String article,
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

	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}
	//ブログIDを検索
	public Blog getBlogById(Long blogId) {
		return blogDao.findByBlogId(blogId);
	}
	//閲覧数の増加
	public void incrementViewCount(Long blogId, int count) {
		Blog blog = getBlogById(blogId);
		if (blog != null) {
			//			blog.setViewCount(blog.getViewCount() + 1); // 增加 viewCount
			blog.setViewCount(count + 1);
			blogDao.save(blog);
		}
	}
}
