package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class UserAgentDetectServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final List<ReadableUserAgent> ls = new ArrayList<ReadableUserAgent>(100);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();

		// Get an UserAgentStringParser and analyze the requesting client
		UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();
		ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));

		if (null != agent) {
			ls.add(agent);
			out.println("total agents: " + ls.size());
			if(ls.size() >= 100) {
				// write to file and then clear
				ls.clear();
			}
		}
		
		String[] sa = agent.toString().split(",");
		for (String s : sa)
			out.println(s);
		
		// out.append("Agent" + agent);
		// out.append("You're a <em>");
		// out.append(agent.getName());
		// out.append("</em> on <em>");
		// out.append(agent.getOperatingSystem().getName());
		// out.append("</em>!");
	}

}