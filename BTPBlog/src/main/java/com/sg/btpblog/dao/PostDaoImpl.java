package com.sg.btpblog.dao;

import com.sg.btpblog.model.Hashtag;
import com.sg.btpblog.model.Post;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PostDaoImpl implements PostDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_POST
            = "insert into Post (originalTimestamp, updatedTimestamp, title, "
            + "content, isApproved, isHidden, categoryId) "
            + "value (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_POST
            = "delete from Post where postId = ?";

    private static final String SQL_DELETE_POSTHASHTAG
            = "delete from PostHashtag where postId = ?";

    private static final String SQL_UPDATE_POST
            = "update Post set originalTimestamp = ?, updatedTimestamp = ?, title = ?, "
            + "content = ?, isApproved = ?, isHidden = ?, categoryId = ? "
            + "where postId = ?";

    private static final String SQL_GET_POST_BY_ID
            = "select * from Post where postId = ?";

    private static final String SQL_GET_ALL_POSTS
            = "select * from Post";

    private static final String SQL_INSERT_POSTHASHTAG
            = "insert into PostHashtag (postId, hashtagId) values (?, ?)";

    private static final String SQL_GET_POST_BY_HASHTAG
            = "SELECT * FROM Post "
            + "INNER JOIN PostHashtag on Post.postId = PostHashtag.PostId "
            + "INNER JOIN Hashtag on PostHashtag.hashtagId = Hashtag.hashtagId "
            + "WHERE Hashtag.hashtagId = ?";

    private static final String SQL_GET_POST_BY_CATEGORY
            = "SELECT * FROM Post "
            + "WHERE categoryId = ?;";

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

    private void insertPostHashtag(Post post) {
        final int postId = post.getPostId();
        final List<Hashtag> hashtags = post.getHashtags();

        for (Hashtag currentHashtag : hashtags) {
            jdbcTemplate.update(SQL_INSERT_POSTHASHTAG,
                    postId,
                    currentHashtag.getHashtagId());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addPost(Post post) {
        int isApproved = 0;
        int isHidden = 0;
        if (post.getIsApproved() == true) {
            isApproved = 1;
        }

        if (post.getIsHidden() == true) {
            isHidden = 1;
        }
        jdbcTemplate.update(SQL_INSERT_POST,
                post.getOriginalTimestamp(),
                post.getUpdatedTimestamp(),
                post.getTitle(),
                post.getContent(),
                isApproved,
                isHidden,
                post.getCategoryId());

        post.setPostId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                Integer.class));

        if (post.getHashtags() != null || !post.getHashtags().isEmpty()) {
            insertPostHashtag(post);
        }
    }

    @Override
    public void deletePost(int post) {
        jdbcTemplate.update(SQL_DELETE_POSTHASHTAG, post);
        jdbcTemplate.update(SQL_DELETE_POST, post);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updatePost(Post post) {
        int isApproved = 0;
        int isHidden = 0;
        if (post.getIsApproved() == true) {
            isApproved = 1;
        }

        if (post.getIsHidden() == true) {
            isHidden = 1;
        }
        jdbcTemplate.update(SQL_UPDATE_POST,
                post.getOriginalTimestamp(),
                post.getUpdatedTimestamp(),
                post.getTitle(),
                post.getContent(),
                isApproved,
                isHidden,
                post.getCategoryId(),
                post.getPostId());
        jdbcTemplate.update(SQL_DELETE_POSTHASHTAG, post.getPostId());
        insertPostHashtag(post);
    }

    @Override
    public Post getPostById(int postId) {
        try {
            return jdbcTemplate.queryForObject(SQL_GET_POST_BY_ID,
                    new PostMapper(), postId);
        } catch (EmptyResultDataAccessException ex) {
            // there were no results for the given contact id - we just
            // want to return null in this case
            return null;
        }
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = jdbcTemplate.query(SQL_GET_ALL_POSTS,
                new PostMapper());
        
        return posts;
    }

    @Override
    public List<Post> getPostByHashtag(int hashtagId) {
        return jdbcTemplate.query(SQL_GET_POST_BY_HASHTAG,
                new PostMapper(),
                hashtagId);
    }

    @Override
    public List<Post> getPostByCategory(int categoryId) {
        return jdbcTemplate.query(SQL_GET_POST_BY_CATEGORY,
                new PostMapper(), categoryId);
    }
}
