package org.gan.action;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements IAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        session.invalidate();

        RequestDispatcher rd = req.getRequestDispatcher("jsp/index.jsp");
        rd.forward(req,res);
    }
}
