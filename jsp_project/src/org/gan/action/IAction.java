package org.gan.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAction {
    void execute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
