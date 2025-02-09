package com.innovacorp.servlets;

import com.innovacorp.dao.ReporteDAO;
import com.innovacorp.model.Reporte;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/reportes")
public class ReporteServlet extends HttpServlet {
    private ReporteDAO reporteDAO;

    @Override
    public void init() {
        reporteDAO = new ReporteDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Reporte> reportes = reporteDAO.obtenerReportes();
            request.setAttribute("reportes", reportes);
            request.getRequestDispatcher("/reportes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener los reportes", e);
        }
    }
}