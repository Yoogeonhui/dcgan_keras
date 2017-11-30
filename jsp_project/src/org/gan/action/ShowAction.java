package org.gan.action;

import org.gan.utils.baseutil;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

//참조 : https://stackoverflow.com/questions/27860507/listing-files-of-a-directory-in-a-webpage

public class ShowAction implements IAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        File dir = new File(baseutil.saveImgPath);
        List<File> result = new ArrayList<>();
        if(!dir.isDirectory()) throw new IllegalStateException("이게 무슨소리야");
        for(File file : dir.listFiles()) {
            result.add(file);
        }
        req.setAttribute("result", result);
        RequestDispatcher rd = req.getRequestDispatcher("jsp/dcgan_show.jsp");
        rd.forward(req,res);
    }
}
