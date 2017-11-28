package org.gan.VO;


public class UserVO {
    private String id;
    private String name;
    private String pwd;
    private String facebookID;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private int uid;


    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserVO(){}

    public UserVO(String id, String name, String nickname) {
        this.id = id;
        this.name = name;
    }

    public UserVO(String id, String name, String nickname, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }
}
