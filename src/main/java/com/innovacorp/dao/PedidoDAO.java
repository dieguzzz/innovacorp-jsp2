package com.innovacorp.dao;

import com.innovacorp.correos.CorreoAPI;
import com.innovacorp.model.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoDAO {
    private static final Logger LOGGER = Logger.getLogger(PedidoDAO.class.getName());
    private static final int MINIMO_INVENTARIO = 10;
    private static boolean simulacionEnCurso = true;


    public static void iniciarSimulacion() {
        simulacionEnCurso = true;
    }


    public static void detenerSimulacion() {
        simulacionEnCurso = false;
    }

    // Agregar un pedido a la base de datos
    public void agregarPedido(Pedido pedido) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtPedido = null;
        PreparedStatement stmtInventario = null;
        PreparedStatement stmtCheckInventario = null;
        PreparedStatement stmtAddInventario = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciar transacción

            // Verificar si el producto existe en el inventario
            String sqlCheckInventario = "SELECT Cantidad FROM Inventario WHERE Producto = ?";
            stmtCheckInventario = conn.prepareStatement(sqlCheckInventario);
            stmtCheckInventario.setString(1, pedido.getProducto());
            ResultSet rs = stmtCheckInventario.executeQuery();

            if (!rs.next()) {
                String sqlAddInventario = "INSERT INTO Inventario (Producto, Cantidad) VALUES (?, ?)";
                stmtAddInventario = conn.prepareStatement(sqlAddInventario);
                stmtAddInventario.setString(1, pedido.getProducto());
                stmtAddInventario.setInt(2, pedido.getCantidad()); // Cantidad inicial
                stmtAddInventario.executeUpdate();

                if (simulacionEnCurso) {
                    String destinatario = "dieguzzz31@gmail.com";
                    String asunto = "Producto Añadido al Inventario";
                    String mensaje = "El producto " + pedido.getProducto() + " ha sido añadido con una cantidad de " + pedido.getCantidad() + "unidades.";
                    CorreoAPI.enviarCorreo(destinatario, asunto, mensaje);
                }
            } else {
                int cantidadActual = rs.getInt("Cantidad");
                int nuevaCantidad = cantidadActual + pedido.getCantidad();

                String sqlInventario = "UPDATE Inventario SET Cantidad = Cantidad + ? WHERE Producto = ?";
                stmtInventario = conn.prepareStatement(sqlInventario);
                stmtInventario.setInt(1, pedido.getCantidad());
                stmtInventario.setString(2, pedido.getProducto());
                stmtInventario.executeUpdate();

                if (nuevaCantidad <= MINIMO_INVENTARIO && simulacionEnCurso) {
                    String destinatario = "dieguzzz31@gmail.com";
                    String asunto = "Producto en Mínimo de Inventario";
                    String mensaje = "El producto " + pedido.getProducto() + " llegó al mínimo, será reabastecido con: " + pedido.getCantidad() + " unidades.";
                    CorreoAPI.enviarCorreo(destinatario, asunto, mensaje);
                }
            }


            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 3);
            Timestamp fechaEntrega = new Timestamp(calendar.getTimeInMillis());
            pedido.setFechaEntrega(fechaEntrega);

            String sqlPedido = "INSERT INTO Pedidos (Producto, Cantidad, Estado, FechaEntrega) VALUES (?, ?, ?, ?)";
            stmtPedido = conn.prepareStatement(sqlPedido);
            stmtPedido.setString(1, pedido.getProducto());
            stmtPedido.setInt(2, pedido.getCantidad());
            stmtPedido.setString(3, "pendiente"); // Estado inicial
            stmtPedido.setTimestamp(4, pedido.getFechaEntrega());
            stmtPedido.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            LOGGER.log(Level.SEVERE, "Error al agregar pedido", e);
            throw e;
        } finally {
            if (stmtCheckInventario != null) stmtCheckInventario.close();
            if (stmtAddInventario != null) stmtAddInventario.close();
            if (stmtPedido != null) stmtPedido.close();
            if (stmtInventario != null) stmtInventario.close();
            if (conn != null) conn.close();
        }
    }

    // Obtener todos los pedidos de la base de datos
    public List<Pedido> obtenerPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("Id"));
                pedido.setProducto(rs.getString("Producto"));
                pedido.setCantidad(rs.getInt("Cantidad"));
                pedido.setEstado(rs.getString("Estado"));
                pedido.setFecha(rs.getTimestamp("Fecha"));
                pedido.setFechaEntrega(rs.getTimestamp("FechaEntrega"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    // Borra un pedido de la base de datos
    public void eliminarPedido(int id) throws SQLException {
        String sql = "DELETE FROM Pedidos WHERE Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}