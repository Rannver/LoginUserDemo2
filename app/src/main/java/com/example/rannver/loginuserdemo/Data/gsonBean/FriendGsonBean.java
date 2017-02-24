package com.example.rannver.loginuserdemo.Data.gsonBean;

/**
 * Created by Rannver on 2017/2/21.
 */

public class FriendGsonBean {

    /**
     * friend_id : 55
     * id : 0
     * relationship : *
     * remark : *
     */

    private int friend_id;
    private int id;
    private String relationship;
    private String remark;

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
