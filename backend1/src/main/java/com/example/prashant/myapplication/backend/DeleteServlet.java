package com.example.prashant.myapplication.backend;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by prashant on 2/22/17.
 */

public class DeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        EntriesDataSource.delete(String.valueOf(id));
        MessagingEndpoint messagingEndpoint = new MessagingEndpoint();
        messagingEndpoint.sendMessage(String.valueOf(id));
        resp.sendRedirect("/query");
    }
}
