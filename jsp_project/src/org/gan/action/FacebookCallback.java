package org.gan.action;

import com.sun.org.apache.xpath.internal.SourceTree;
import facebook4j.Facebook;
import facebook4j.auth.AccessToken;
import org.gan.VO.UserVO;
import org.gan.exception.YouShouldLogin;
import org.gan.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacebookCallback implements IAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession sess = req.getSession();
        Facebook facebook = (Facebook) sess.getAttribute("face");
        String oauthCode = req.getParameter("code");
        try{

            facebook.getOAuthAccessToken(oauthCode);
            UserService service = new UserService();
            //있으면 로그인으로 처리
            //없으면 그냥 페이스북만 로드

            if(sess.getAttribute("user")==null) {
                UserVO user = new UserVO();
                user.setFacebookID(facebook.getId());
                user.setName(facebook.getName());
                user.setId(null);
                user.setPwd(null);
                try {
                    service.signupInFacebook(user);
                    sess.setAttribute("user", user);
                } catch (YouShouldLogin e) {
                    sess.setAttribute("user", e.getOrigin());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    String path = (String)sess.getAttribute("pathAfterFB");
                    res.sendRedirect(path);
                }
            }else{
                String path = (String)sess.getAttribute("pathAfterFB");
                res.sendRedirect(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher rd = req.getRequestDispatcher("jsp/login.jsp");
            req.setAttribute("error",e.getMessage());
            rd.forward(req,res);
        }
    }
}
