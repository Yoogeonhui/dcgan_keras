package org.gan.action;

import org.gan.VO.UserVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckAction implements IAction{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession sess = req.getSession();
        UserVO user = (UserVO)sess.getAttribute("user");
        if(user == null){
            RequestDispatcher rd = req.getRequestDispatcher("./login.jsp");
            rd.forward(req,res);
        }else{
            String path = (String)req.getAttribute("path");
            RequestDispatcher rd = req.getRequestDispatcher(path);
            rd.forward(req,res);
        }
    }
}
