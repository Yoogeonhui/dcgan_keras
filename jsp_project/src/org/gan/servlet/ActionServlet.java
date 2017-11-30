package org.gan.servlet;

import org.gan.action.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns="*.do")
public class ActionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Map<String, IAction> actions = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    public void init() throws ServletException {
        actions.put("check", new CheckAction());
        actions.put("faceauth", new FacebookAuthAction());
        actions.put("login", new LoginAction());
        actions.put("facecallback", new FacebookCallback());
        actions.put("logout", new LogoutAction());
        actions.put("signup", new SignupAction());
        actions.put("upload", new UploadAction());
        actions.put("uploadface", new UploadtoFacebook());
        actions.put("show", new ShowAction());
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        try{
            //1. uri (/login.do)
            String uri = req.getRequestURI();
            System.out.println("uri: "+uri);
            String actionName = uri.substring(uri.lastIndexOf("/")+1);
            actionName = actionName.substring(0, actionName.indexOf("."));
            System.out.println("Action Name: "+actionName);

            IAction action = actions.get(actionName);

            if(action==null){
                throw new Exception(actionName+"에 해당하는 Action Class가 없습니다.");
            }
            action.execute(req,resp);
        } catch(Exception e){
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("jsp/error.jsp");
            rd.forward(req,resp);
        }
    }
}
