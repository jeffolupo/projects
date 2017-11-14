package com.sg.btpblog.dao;

import com.sg.btpblog.model.Hashtag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class HashtagDaoImpl implements HashtagDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SQL_INSERT_HASHTAG
            = "INSERT INTO Hashtag (hashtagName) "
            + "VALUES (?);";

    private static final String SQL_SELECT_HASHTAG
            = "SELECT * FROM Hashtag WHERE hashtagId = ?;";

    private static final String SQL_SELECT_HASHTAGNAME
            = "SELECT * FROM Hashtag WHERE hashtagName = ?;";

    private static final String SQL_SELECT_HASHTAGS_BY_POST
            = "SELECT Hashtag.* FROM Post "
            + "INNER JOIN PostHashtag ON Post.postId = PostHashtag.postId "
            + "INNER JOIN Hashtag ON Hashtag.hashtagId = PostHashtag.hashtagId "
            + "WHERE Post.postId = ?;";

    private static final String SQL_SELECT_ALL_HASHTAGS
            = "SELECT * FROM Hashtag;";

    private static final String SQL_UPDATE_HASHTAG
            = "UPDATE Hashtag SET hashtagName = ? WHERE hashtagId = ?;";

    private static final String SQL_DELETE_POSTHASHTAG
            = "DELETE FROM PostHashtag WHERE hashtagId = ?;";

    private static final String SQL_SELECT_POSTHASHTAG_WITH_POST_ID
            = "SELECT hashtagId "
            + "FROM PostHashtag "
            + " WHERE postId = ?;";

    private static final String SQL_DELETE_HASHTAG
            = "DELETE FROM Hashtag WHERE hashtagId = ?;";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addHashtag(Hashtag hashtag) {
        jdbcTemplate.update(SQL_INSERT_HASHTAG, hashtag.getHashtagName().trim());
        hashtag.setHashtagId(jdbcTemplate.queryForObject(
                "SELECT LAST_INSERT_ID()", Integer.class));
    }

    @Override
    public Hashtag getHashtagById(Integer hashtagId) {
        try {
            return jdbcTemplate.queryForObject(
                    SQL_SELECT_HASHTAG, new HashtagMapper(), hashtagId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Hashtag getHashtagByName(String hashtagName) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_HASHTAGNAME,
                    new HashtagMapper(), hashtagName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Hashtag> getHashtagsByPost(int postId) {
        return jdbcTemplate.query(SQL_SELECT_HASHTAGS_BY_POST,
                new HashtagMapper(), postId);
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return jdbcTemplate.query(SQL_SELECT_ALL_HASHTAGS, new HashtagMapper());
    }

    @Override
    public List<Hashtag> getHashtagsByPostId(int postId) {
        List<Hashtag> hashtags = jdbcTemplate.query(SQL_SELECT_POSTHASHTAG_WITH_POST_ID,
                new HashtagPartialMapper(),
                postId);

        for (Hashtag tag : hashtags) {
            Hashtag tagName = getHashtagById(tag.getHashtagId());
            tag.setHashtagName(tagName.getHashtagName());
        }

        return hashtags;
    }

    @Override
    public void updateHashtag(Hashtag hashtag) {
        jdbcTemplate.update(SQL_UPDATE_HASHTAG,
                hashtag.getHashtagName(), hashtag.getHashtagId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteHashtag(Integer hashtagId) {
        jdbcTemplate.update(SQL_DELETE_POSTHASHTAG, hashtagId);
        jdbcTemplate.update(SQL_DELETE_HASHTAG, hashtagId);
    }

    private static final class HashtagMapper implements RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet rs, int i) throws SQLException {
            Hashtag hashtag = new Hashtag();
            hashtag.setHashtagId(rs.getInt("hashtagId"));
            hashtag.setHashtagName(rs.getString("hashtagName"));
            return hashtag;
        }
    }

    private static final class HashtagPartialMapper implements RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet rs, int i) throws SQLException {
            Hashtag hashtag = new Hashtag();
            hashtag.setHashtagId(rs.getInt("hashtagId"));
            return hashtag;
        }
    }
}
