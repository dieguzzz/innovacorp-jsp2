package com.innovacorp.servlets;

import com.innovacorp.dao.PedidoDAO;
import com.innovacorp.model.Pedido;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/obtenerPedidos")
public class ObtenerPedidosServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;

    @Override
    public void init() {
        pedidoDAO = new PedidoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Pedido> pedidos = pedidoDAO.obtenerPedidos();
            request.setAttribute("pedidos", pedidos);
            request.getRequestDispatcher("/obtenerPedidos.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener los pedidos", e);
        }
    }
}