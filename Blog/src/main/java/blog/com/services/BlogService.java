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
}
