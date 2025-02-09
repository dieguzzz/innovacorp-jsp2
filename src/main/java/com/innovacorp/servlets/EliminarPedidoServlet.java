package com.innovacorp.servlets;

import com.innovacorp.dao.PedidoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/eliminarPedido")
public class EliminarPedidoServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;

    @Override
    public void init() {
        pedidoDAO = new PedidoDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            pedidoDAO.eliminarPedido(id);
            response.sendRedirect("obtenerPedidos");
        } catch (SQLException e) {
            throw new ServletException("Error al eliminar el pedido", e);
        }
    }
}