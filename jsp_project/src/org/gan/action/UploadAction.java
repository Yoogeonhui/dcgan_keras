package org.gan.action;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.gan.utils.baseutil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringWriter;

public class UploadAction implements IAction {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //StackOverFlow 참조 : https://stackoverflow.com/questions/24146395/save-canvas-image-into-png-image-file-from-java-servlet
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(req);
            InputStream is = wrappedRequest.getInputStream();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");

            String imageString = writer.toString();
            imageString = imageString.substring("data:image/png;base64,"
                    .length());
            byte[] contentData = imageString.getBytes();
            byte[] decodedData = Base64.decodeBase64(contentData);
            String imgName = baseutil.saveImgPath+"faceImage"+String.valueOf(System.currentTimeMillis()) + ".png";
            fos = new FileOutputStream(imgName);
            fos.write(decodedData);
        } catch (Exception e) {
            e.printStackTrace();

            //CVAException.printException(loggerMessage + e.getMessage());

        } finally {
            if (in != null) {
                in.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

    }
}
