package org.gan.utils;

import org.gan.VO.UserVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
public class baseutil {
    public static final String appid = "511995439166098";
    public static final String secret_key  = "44661334ad5ed7153580c7cc04d600ad";
    public static final String hostname = "http://ygserver.oa.to:8080/";
    public static final String saveImgPath = "/opt/tomcat/webapps/java/ganout/";
    public static void null_or_var(PreparedStatement pstmt, int index, String what, int type) throws Exception{
        if(what ==null)
            pstmt.setNull(index, type);
        else
            pstmt.setString(index,what);
    }
    public static UserVO getVO(ResultSet rs) throws SQLException {
        UserVO result = new UserVO();
        result.setUid(rs.getInt(1));
        result.setId(rs.getString(2));
        result.setName(rs.getString(5));
        result.setFacebookID(rs.getString(4));
        return result;
    }
}
