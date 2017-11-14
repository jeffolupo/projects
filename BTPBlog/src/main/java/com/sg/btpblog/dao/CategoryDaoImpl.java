package com.sg.btpblog.dao;

import com.sg.btpblog.model.Category;
import com.sg.btpblog.model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_CATEGORY
            = "insert into Category (categoryName) values (?)";

    private static final String SQL_DELETE_CATEGORY
            = "DELETE FROM Category WHERE categoryId = ?;";

    private static final String SQL_UPDATE_CATEGORY
            = "update Category set CategoryName = ? "
            + "where categoryId = ?;";

    private static final String SQL_SELECT_CATEGORY
            = "SELECT * "
            + "FROM Category "
            + "WHERE categoryId = ?;";

    private static final String SQL_SELECT_ALL_CATEGORIES
            = "SELECT * "
            + "FROM Category ";

    private static final String SQL_SELECT_ALL_POST_WHERE_CATEGORIES_ID
            = "select * "
            + "from Post "
            + "where categoryId = ?";

    private static final String SQL_DELETE_FROM_POST_WHERE_CATEGORY_ID
            = "DELETE FROM Post where categoryId = ?";

    private static final String SQL_DELETE_FROM_POSTHASHTAG
            = "DELETE FROM PostHashtag WHERE postId = ?";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addCategory(Category category) {
        jdbcTemplate.update(SQL_INSERT_CATEGORY, category.getCategoryName());
        category.setCategoryId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));
    }

    @Override
    public void deleteCategory(int categoryId) {
        List<Post> p = new ArrayList<>();
        p = jdbcTemplate.query(SQL_SELECT_ALL_POST_WHERE_CATEGORIES_ID,
                new PostMapper(),
                categoryId);

        for (Post post : p) {
            jdbcTemplate.update(SQL_DELETE_FROM_POSTHASHTAG, post.getPostId());
        }

        jdbcTemplate.update(SQL_DELETE_FROM_POST_WHERE_CATEGORY_ID, categoryId);
        jdbcTemplate.update(SQL_DELETE_CATEGORY, categoryId);

    }

    @Override
    public void updateCategory(Category category) {
        jdbcTemplate.update(SQL_UPDATE_CATEGORY,
                category.getCategoryName(),
                category.getCategoryId());

    }

    @Override
    public Category getCategoryById(int categoryId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_CATEGORY,
                    new CategoryMapper(),
                    categoryId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Category> getAllCategories() {
        return jdbcTemplate.query(SQL_SELECT_ALL_CATEGORIES, new CategoryMapper());
    }

    private static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category category = new Category();
            category.setCategoryId(rs.getInt("categoryId"));
            category.setCategoryName(rs.getString("categoryName"));
            return category;
        }
    }

    private static final class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {

            Post post = new Post();
            post.setOriginalTimestamp(Timestamp.valueOf(rs.getString("originalTimestamp")));
            post.setUpdatedTimestamp(Timestamp.valueOf(rs.getString("updatedTimestamp")));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setIsApproved(rs.getInt("isApproved") == 1);
            post.setIsHidden(rs.getInt("isHidden") == 1);
            post.setCategoryId(rs.getInt("categoryId"));
            post.setPostId(rs.getInt("postId"));
            return post;
        }
    }

}
