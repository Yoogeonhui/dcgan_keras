package org.gan.action;

import org.gan.VO.UserVO;
import org.gan.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction implements IAction{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            UserService service = new UserService();
            String id = req.getParameter("id");
            String pwd = req.getParameter("pwd");

            UserVO user = new UserVO();
            user.setId(id);
            user.setPwd(pwd);

            UserVO result = service.login(user);

            HttpSession session = req.getSession();
            session.setAttribute("user", result);
            RequestDispatcher rd = req.getRequestDispatcher("jsp/home.jsp");
            rd.forward(req, res);
        }catch(Exception e){
            e.printStackTrace();
            req.setAttribute("error",e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("jsp/login.jsp");
            rd.forward(req,res);
        }
    }
}
