/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.tucanchaya.controller.util.Util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wilson Carvajal
 */
public class PhotoEscServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

        @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String image = request.getParameter("photo");
            try {
                
                if(image.equals(""))
                {
                    image = "defaultSportCenter.png";
                }
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(Util.RUTAFOTOSESCENARIOS + image));
               
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
