package com.bpf.servlets;
import javax.servlet.http.*;
import java.io.*;
import com.bpf.SearchEngine;

import javax.servlet.annotation.WebServlet;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{

        String json = new SearchEngine(request).search(request);
        response.setContentType("application/json");
        response.getWriter().write(json);

    }
}
