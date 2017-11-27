package org.gan.dao;

import org.gan.VO.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn = null;
    public UserDao(Connection conn){
        this.conn = conn;
    }

    public UserVO searchUser(UserVO vo) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM USER WHERE ID=? AND PWD=?";

        ResultSet rs = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPwd());
            rs = pstmt.executeQuery();

            UserVO result = null;

            if (rs.next()) {
                result = new UserVO();
                result.setId(rs.getString(1));
                result.setName(rs.getString(3));
                result.setNickname(rs.getString(4));
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("화면의 사용자 조회 중 오류가 발생하였습니다.");
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }
    }


    public List<UserVO> searchUserList() throws Exception{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT ID, NAME, NICKNAME FROM USER";

        try{
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<UserVO> list = new ArrayList<>();
            UserVO result = new UserVO();
            while(rs.next()){
                result = new UserVO();
                result.setId(rs.getString(1));
                result.setName(rs.getString(2));
                result.setNickname(rs.getString(3));
                list.add(result);
            }
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            throw new Exception("화면의 사용자 조회 중 오류가 발생하였습니다.");
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
                result = new UserVO();
                result.setId(rs.getString(1));
                result.setName(rs.getString(3));
                result.setNickname(rs.getString(4));
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
        String sql = "INSERT INTO USER VALUES(?,?,?,?)";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vo.getId());
            pstmt.setString(2, vo.getPwd());
            pstmt.setString(3, vo.getName());
            pstmt.setString(4, vo.getNickname());

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
