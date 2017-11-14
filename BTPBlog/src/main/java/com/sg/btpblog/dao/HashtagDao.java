package com.sg.btpblog.dao;

import com.sg.btpblog.model.Hashtag;
import java.util.List;

public interface HashtagDao {

    void addHashtag(Hashtag hashtag);

    Hashtag getHashtagById(Integer id);

    Hashtag getHashtagByName(String hashtagName);

    List<Hashtag> getHashtagsByPost(int postId);

    List<Hashtag> getAllHashtags();

    void updateHashtag(Hashtag hashtag);

    void deleteHashtag(Integer hashtagId);
    
    List<Hashtag> getHashtagsByPostId (int postId);
}
