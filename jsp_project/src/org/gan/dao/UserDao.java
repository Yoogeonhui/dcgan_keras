package org.gan.dao;

import org.gan.VO.UserVO;
import org.gan.utils.baseutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn = null;
    public UserDao(Connection conn){
        this.conn = conn;
    }
    public UserVO searchUser(UserVO vo) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM USER WHERE ID=? AND password=?";

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPwd());
            rs = pstmt.executeQuery();

            UserVO result = null;

            if (rs.next()) {
                result = baseutil.getVO(rs);
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("사용자 조회 중 오류가 발생하였습니다.");
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }
    public UserVO searchUserByFacebookId(UserVO vo) throws Exception{
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM USER WHERE facebook_id=?";

        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getFacebookID());
            rs = pstmt.executeQuery();
            UserVO result = null;
            if(rs.next()){
                result = baseutil.getVO(rs);
            }
            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("사용자 조회 중 오류가 발생하였습니다.");
        }
        finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
        }
    }
    public UserVO searchUserById(UserVO vo) throws Exception{
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM USER WHERE ID=?";

        ResultSet rs = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getId());
            rs = pstmt.executeQuery();
            UserVO result = null;
            if(rs.next()){
                result = baseutil.getVO(rs);
        }

            return result;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("사용자 조회 중 오류가 발생하였습니다.");
        }
        finally{
            if(rs != null) rs.close();
            if(pstmt != null) pstmt.close();
        }
    }

    public void insertUser(UserVO vo) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO USER(ID, password, facebook_id, nickname) VALUES(?,?,?,?)";
        try{
            pstmt = conn.prepareStatement(sql);
            baseutil.null_or_var(pstmt, 1, vo.getId(), Types.VARCHAR);
            baseutil.null_or_var(pstmt, 2, vo.getPwd(), Types.VARCHAR);
            baseutil.null_or_var(pstmt, 3, vo.getFacebookID(), Types.VARCHAR);
            baseutil.null_or_var(pstmt, 4, vo.getName(), Types.VARCHAR);
            int cnt = pstmt.executeUpdate();
            if(cnt == 0) throw new Exception("사용자 등록에 실패하였습니다.");
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("사용자 조회 중 오류가 발생하였습니다.");
        }
        finally{
            if(pstmt != null) pstmt.close();
        }
    }
}
