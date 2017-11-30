package org.gan.action;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import org.gan.utils.baseutil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

public class FacebookAuthAction implements IAction{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String path = req.getParameter("path");
            Facebook facebook = new FacebookFactory().getInstance();
            facebook.setOAuthAppId(baseutil.appid, baseutil.secret_key);
            facebook.setOAuthPermissions("email, publish_actions");
            HttpSession sess = req.getSession();
            sess.setAttribute("face", facebook);
            sess.setAttribute("pathAfterFB", path);
            res.sendRedirect(facebook.getOAuthAuthorizationURL(baseutil.hostname.substring(0,baseutil.hostname.length()-1)+req.getContextPath()+"/facecallback.do"));
        }
        catch(Exception e){
            Writer a =res.getWriter();
            a.write(e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("jsp/login.jsp?error="+e.getMessage());
            rd.forward(req, res);
        }
    }
}