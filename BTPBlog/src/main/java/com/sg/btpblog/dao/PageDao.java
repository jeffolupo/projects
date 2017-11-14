package com.sg.btpblog.dao;

import com.sg.btpblog.model.Page;
import java.util.List;

public interface PageDao {

    public void addPage(Page page);

    public void deletePage(int pageId);

    public void updatePage(Page page);

    public Page getPageById(int pageId);

    public List<Page> getAllPages();

}
