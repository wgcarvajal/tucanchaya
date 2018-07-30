/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aranda
 */
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String image = request.getParameter("image");
		response.setContentType("image/jpeg");
		String pathToWeb = "/Users/aranda/Desktop/";
		File f = new File(pathToWeb + "prueba.jpg");
		BufferedImage bi = ImageIO.read(f);
		OutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		out.close();

	}

}
