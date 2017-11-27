package org.gan.service;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public abstract class AbstractService {
    public Connection getConnection() throws Exception{
        try{
            Context context = new InitialContext();
            DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/mysql");
            return dataSource.getConnection();
        } catch(Exception e){
            e.printStackTrace();
            throw new Exception("DB연결에 실패하였습니다.");
        }
    }
}
