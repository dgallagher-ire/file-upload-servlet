package my;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author dgallagher
 */
@WebServlet(name = "FileUploaderServlet", urlPatterns = {"/fileuploader"})
@MultipartConfig
public class FileUploaderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InputStream fileIn = null;
        OutputStream fileOut = null;
        try {
            final Part filePart = request.getPart("file");
            final String fullFileName = filePart.getSubmittedFileName();
            final String fileName = fullFileName.substring(fullFileName.lastIndexOf("\\")+1);
            fileIn = filePart.getInputStream();
            fileOut = new FileOutputStream("C:\\Users\\dgallagher\\Documents\\COPY" + fileName);
            int num = 0;
            byte[] chunk = new byte[1024];
            while ((num = fileIn.read(chunk)) != -1) {
                fileOut.write(chunk, 0, num);
            }
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet FileUploaderServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>File Written</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println(e.toString());
            }
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
