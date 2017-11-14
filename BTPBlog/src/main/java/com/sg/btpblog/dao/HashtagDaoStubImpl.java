/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.btpblog.dao;

import com.sg.btpblog.model.Hashtag;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffolupo
 */
public class HashtagDaoStubImpl implements HashtagDao {

    List<Hashtag> tagList1 = new ArrayList<>();

    public HashtagDaoStubImpl() {

        Hashtag tag1 = new Hashtag();
        tag1.setHashtagId(1);
        tag1.setHashtagName("tag1");
        tagList1.add(tag1);

        Hashtag tag2 = new Hashtag();
        tag2.setHashtagId(2);
        tag2.setHashtagName("tag2");
        tagList1.add(tag2);

        Hashtag tag3 = new Hashtag();
        tag3.setHashtagId(3);
        tag3.setHashtagName("tag3");
        tagList1.add(tag3);
    }

    @Override
    public void addHashtag(Hashtag hashtag) {

    }

    @Override
    public Hashtag getHashtagById(Integer id) {
        return tagList1.get(0);
    }

    @Override
    public Hashtag getHashtagByName(String hashtagName) {
         for (Hashtag hash : tagList1){
             if(hash.getHashtagName().equals(hashtagName)){
                 return hash;
             }
         }
         return null;
    }

    @Override
    public List<Hashtag> getHashtagsByPost(int postId) {
        return tagList1;
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return tagList1; 
    }

    @Override
    public void updateHashtag(Hashtag hashtag) {

    }

    @Override
    public void deleteHashtag(Integer hashtagId) {

    }

    @Override
    public List<Hashtag> getHashtagsByPostId(int postId) {
        return tagList1;
    }

}
