package com.innovacorp.servlets;

import com.innovacorp.dao.InventarioDAO;
import com.innovacorp.model.Inventario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/inventario")
public class InventarioServlet extends HttpServlet {
    private InventarioDAO inventarioDAO;

    @Override
    public void init() {
        inventarioDAO = new InventarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Inventario> inventario = inventarioDAO.obtenerInventario();
            request.setAttribute("inventario", inventario);
            request.getRequestDispatcher("/inventario.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el inventario", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        Inventario item = new Inventario();
        item.setId(id);
        item.setCantidad(cantidad);

        try {
            inventarioDAO.actualizarInventario(item);
        } catch (SQLException e) {
            throw new ServletException("Error al actualizar el inventario", e);
        }

        response.sendRedirect("inventario");
    }
}