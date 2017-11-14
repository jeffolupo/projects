package com.sg.btpblog.dao;

import com.sg.btpblog.model.Page;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PageDaoImpl implements PageDao {
    
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private static final String SQL_INSERT_PAGE            
        = "insert into `Page` (title, content) values (?,?);"; 
    
    private static final String SQL_DELETE_PAGE
        = "delete from `Page` where pageId = ?;";
    
    private static final String SQL_UPDATE_PAGE
        = "update `Page` set title = ?, content = ? " +
          "where pageId = ?;";
    
    private static final String SQL_SELECT_PAGE
        = "select * " +
          "from `Page` " +
          "where pageId = ?;";
    
    private static final String SQL_SELECT_ALL_PAGES        
         = "select * " +
          "from `Page`; ";

    @Override
    public void addPage(Page page) {
        jdbcTemplate.update(SQL_INSERT_PAGE,
                page.getTitle(),
                page.getContent());
        page.setPageId(jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class));
    }

    @Override
    public void deletePage(int pageId) {
        jdbcTemplate.update(SQL_DELETE_PAGE,
                pageId);

    }

    @Override
    public void updatePage(Page page) {
        jdbcTemplate.update(SQL_UPDATE_PAGE,
                page.getTitle(),
                page.getContent(),
                page.getPageId());
    }

    @Override
    public Page getPageById(int pageId) {
        try{
            Page page = jdbcTemplate.queryForObject(SQL_SELECT_PAGE, new PageMapper(),
                pageId);
            return page;            
        } catch(EmptyResultDataAccessException ex){
            return null;
        }
    
    }

    @Override
    public List<Page> getAllPages() {
        return jdbcTemplate.query(SQL_SELECT_ALL_PAGES, 
                new PageMapper());
    }
    
    private static final class PageMapper implements RowMapper<Page> {
        
        @Override
        public Page mapRow(ResultSet rs, int i) throws SQLException{
            Page page = new Page();
            page.setPageId(rs.getInt("PageId"));
            page.setTitle(rs.getString("Title"));
            page.setContent(rs.getString("Content"));
            
            return page;
        }
    }

}
