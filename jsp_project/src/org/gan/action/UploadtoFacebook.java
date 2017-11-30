package org.gan.action;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Media;
import facebook4j.PhotoUpdate;
import org.gan.utils.baseutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public class UploadtoFacebook implements IAction{
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Facebook facebook = (Facebook)req.getSession().getAttribute("face");
        String file_name = req.getParameter("filename");
        if(facebook==null){
            res.sendRedirect(req.getContextPath()+"/jsp/login.jsp?path="+req.getContextPath()+"/uploadface.do?filename="+file_name);
        }else{
            try {
                String url = baseutil.saveImgPath+file_name;
                PhotoUpdate photoUpdate = null;
                Media media=new Media(new File(url));
                photoUpdate = new PhotoUpdate(media);
                photoUpdate.message("DCGAN Test");
                String idPost = facebook.postPhoto(photoUpdate);
                System.out.println(idPost);
            } catch (FacebookException e) {
                e.printStackTrace();
            }finally {
                res.sendRedirect(req.getContextPath() + "/show.do");
            }
        }
    }
}
