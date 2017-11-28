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
            StringBuffer callbackURL = req.getRequestURL();
            int index = callbackURL.lastIndexOf("/");
            callbackURL.replace(index, callbackURL.length(), "").append("/facecallback.do");
            res.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
        }
        catch(Exception e){
            req.setAttribute("error", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("jsp/login.jsp");
            rd.forward(req, res);
        }
    }
}