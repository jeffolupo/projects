package com.sg.btpblog.model;

import java.util.Objects;

public class Hashtag {

    private Integer hashtagId;
    private String hashtagName;

    public Hashtag() {
    }

    public Integer getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(Integer hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getHashtagName() {
        return hashtagName;
    }

    public void setHashtagName(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.hashtagId;
        hash = 19 * hash + Objects.hashCode(this.hashtagName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hashtag other = (Hashtag) obj;
        if (this.hashtagId != other.hashtagId) {
            return false;
        }
        if (!Objects.equals(this.hashtagName, other.hashtagName)) {
            return false;
        }
        return true;
    }

}
