package com.example.prashant.myapplication.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by prashant on 2/22/17.
 */

public class PostDataServlet extends HttpServlet {

    final Logger logger = Logger.getLogger("test");
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String jsonstr = req.getParameter("entries_json");
        if (jsonstr == null || jsonstr.equals("")) {
            return;
        }

        try
        {
            EntriesDataSource.deleteall();
            JSONArray jsonArray = new JSONArray();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonstr);
            jsonArray.add(obj.get("JSON"));
            JSONArray current = (JSONArray) jsonArray.get(0);
            for(int i = 0; i < current.size(); i++)
            {
                logger.info(current.toString());
                logger.info(current.get(i).toString());
                JSONObject jsonObject = (JSONObject) current.get(i);
                EntriesDataSource.add(jsonObject);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
