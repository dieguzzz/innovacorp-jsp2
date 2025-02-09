package com.innovacorp.servlets;

import com.innovacorp.dao.InventarioDAO;
import com.innovacorp.simulador.SimuladorInventario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/simulacion")
public class SimulacionServlet extends HttpServlet {
    private SimuladorInventario simulador;
    private InventarioDAO inventarioDAO;

    @Override
    public void init() {
        simulador = new SimuladorInventario();
        inventarioDAO = new InventarioDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("inventario", inventarioDAO.obtenerInventario());

            request.setAttribute("simulacionActiva", simulador.estaActiva());
            request.setAttribute("mensajesReabastecimiento", simulador.getMensajesReabastecimiento());

            request.getRequestDispatcher("/simulacion.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener datos para la simulación", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("iniciar".equals(action)) {
            simulador.iniciarSimulacion();
        } else if ("detener".equals(action)) {
            simulador.detenerSimulacion();
        }

        response.sendRedirect("simulacion");
    }
}