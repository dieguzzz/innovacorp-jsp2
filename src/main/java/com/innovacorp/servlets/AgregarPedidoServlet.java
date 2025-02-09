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

@WebServlet("/agregarPedido")
public class AgregarPedidoServlet extends HttpServlet {
    private PedidoDAO pedidoDAO;

    @Override
    public void init() {
        pedidoDAO = new PedidoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/agregarPedidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String producto = request.getParameter("producto");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        Pedido pedido = new Pedido();
        pedido.setProducto(producto);
        pedido.setCantidad(cantidad);

        try {
            pedidoDAO.agregarPedido(pedido);
            response.sendRedirect("agregarPedidos.jsp");
        } catch (SQLException e) {
            if (e.getMessage().contains("El producto no existe en el inventario")) {
                request.setAttribute("errorMessage", "El producto no existe en el inventario.");
                request.getRequestDispatcher("/agregarPedidos.jsp").forward(request, response);
            } else {
                throw new ServletException("Error al agregar el pedido", e);
            }
        }
    }
}