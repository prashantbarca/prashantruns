package com.example.prashant.myapplication.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by prashant on 2/22/17.
 */

public class QueryServlet extends HttpServlet {

    private static final long serialVersionUID = 2L;
    public static final String ALL_ENTRIES_KEY = "AllEntries";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ExerciseEntry> allEntries = EntriesDataSource.fetchAll();
        Logger info = Logger.getLogger("ABCD");
        info.info(String.valueOf(allEntries.size()));
        req.setAttribute(ALL_ENTRIES_KEY, allEntries);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<html>\n");

        out.write("<head>\n");
        out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n");
        out.write("<title>MyRuns</title>\n");
                out.write("</head>\n");
                        out.write("<body>\n");
        out.write("<h1>Exercise Entries List</h1>\n");
        out.write("<table border=\"1\">\n");
        out.write(" <thead>\n");
                out.write(" <tr>\n");
                        out.write("<th>ID</th>\n");
                                out.write("  <th>Input Type</th>\n");
                                        out.write(" <th>Activity Type</th>\n");
        out.write(" <th>Duration</th>\n");
        out.write("  <th>Distance</th>\n");
        out.write(" <th>Average Speed</th>\n");
        out.write("<th>Calories</th>\n");
        out.write("<th>Climb</th>\n");
        out.write("<th>Heart Rate</th>\n");
        out.write("<th>Comment</th>\n");
        out.write("<th>Action</th>\n");
        out.write("</tr>\n");
        out.write("</thead>\n");
        out.write("<tbody>\n");
        List<ExerciseEntry> resultEntries = (List<ExerciseEntry>) req.getAttribute("AllEntries");
        if (resultEntries != null) {
            for (ExerciseEntry entry : resultEntries) {
                out.write("<tr>\n");
                out.write("<td>" + entry.id +"</td>\n");
                out.write("<td>" + entry.getmInputType() +"</td>\n");
                out.write("<td>" + entry.getmActivityType()+"</td>\n");
                out.write("<td>" + entry.mDuration +"</td>\n");
                out.write("<td>" + entry.getmDistance() +"</td>\n");
                out.write("<td>" + entry.getmAvgSpeed() +"</td>\n");
                out.write("<td>" + entry.getmCalorie() +"</td>\n");
                out.write("<td>" + entry.getmClimb() +"</td>\n");
                out.write("<td>" + entry.getmHeartRate() + "</td>\n");
                out.write("<td>" + entry.getmComment() + "</td>\n");
                out.write("<td>\n");
                out.write("<a href=\"/delete?id=" + entry.getId() + "\">Delete</a>");
                out.write("</td>\n");
                out.write("</tr>\n");
            }
        }
        out.write("</tbody>\n");
        out.write("</table>\n");
        out.write("</body>\n");
        out.write("</html>");
        out.close();
    }
}
