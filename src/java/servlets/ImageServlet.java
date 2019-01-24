/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aranda
 */
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
        private static final String RUTAFOTOSCENTROSDEPORTIVOS= "/Users/aranda/filesTucanchaya/fotosCentrosdeportivos/";

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String image = request.getParameter("image");
            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(RUTAFOTOSCENTROSDEPORTIVOS + image));
                // Get image contents.
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                // Write image contents to response.
                response.getOutputStream().write(bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}


