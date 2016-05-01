package com.mauricio.pushnotification;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GcmRegister")
public class GcmRegisterServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String deviceID = req.getParameter("regID");
		if (deviceID != null && deviceID.length() > 0) {
			System.out.println("Device " + deviceID + " connected");
			GreetingServlet.addAndroidDevices(deviceID);
		}
	}
	
}
