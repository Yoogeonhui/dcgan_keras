package org.gan.action;

import org.gan.VO.UserVO;
import org.gan.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignupAction implements IAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("utf-8");
        try {
            String id = req.getParameter("id");
            String pwd = req.getParameter("pwd");
            String pwd_ver = req.getParameter("pwd_ver");
            if(!pwd.equals(pwd_ver))
                throw new Exception("비밀번호가 일치하지 않습니다.");
            String nickname = req.getParameter("nickname");

            System.out.println("id: " + id);
            System.out.println("pwd: " + pwd);
            System.out.println("nickname: " + nickname);

            UserVO user = new UserVO();
            user.setId(id);
            user.setPwd(pwd);
            user.setName(nickname);

            UserService service = new UserService();
            service.signup(user);

            RequestDispatcher rd = req.getRequestDispatcher("jsp/login.jsp");
            rd.forward(req, res);
        }
        catch(Exception e){
            e.printStackTrace();
            req.setAttribute("error",e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("jsp/signup.jsp");
            rd.forward(req,res);
        }
    }
}
