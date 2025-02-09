package com.innovacorp.servlets;

import com.innovacorp.dao.InventarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/eliminarInventario")
public class EliminarInventarioServlet extends HttpServlet {
    private InventarioDAO inventarioDAO;

    @Override
    public void init() {
        inventarioDAO = new InventarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            inventarioDAO.eliminarInventario(id);
            response.sendRedirect("inventario");
        } catch (SQLException e) {
            throw new ServletException("Error al eliminar el inventario", e);
        }
    }
}