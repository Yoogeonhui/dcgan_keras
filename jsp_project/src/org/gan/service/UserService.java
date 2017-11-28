package org.gan.service;

import org.gan.VO.UserVO;
import org.gan.dao.UserDao;
import org.gan.exception.YouShouldLogin;
import sun.reflect.annotation.ExceptionProxy;

import java.sql.Connection;
import java.util.List;

public class UserService extends AbstractService {
    public UserVO login(UserVO user) throws Exception{
        Connection conn = null;
        try{
            conn = getConnection();

            UserDao dao = new UserDao(conn);
            UserVO result = dao.searchUser(user);

            if(result == null)
                throw new Exception("Invalid Username Or PWD");
            else
                return result;
        }
        finally{
            if(conn!=null)
                conn.close();
        }
    }
    public void signupInFacebook(UserVO user) throws Exception{
        Connection conn = null;
        try{
            conn = getConnection();

            UserDao dao = new UserDao(conn);
            UserVO result = dao.searchUserByFacebookId(user);
            if(result != null)
                throw new YouShouldLogin(result);
            //사용자 등록
            dao.insertUser(user);
        }
        finally{
            if(conn!=null) conn.close();
        }
    }
    public void signup(UserVO user) throws Exception{
        Connection conn = null;
        try{
            conn = getConnection();

            UserDao dao = new UserDao(conn);
            UserVO result = dao.searchUserById(user);
            if(result != null)
                throw new Exception("이미 사용중인 아이디 입니다.");
            //사용자 등록
            dao.insertUser(user);
        }
        finally{
            if(conn!=null) conn.close();
        }
    }
}
