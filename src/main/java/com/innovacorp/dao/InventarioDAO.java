package com.innovacorp.dao;

import com.innovacorp.model.Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    // Metodo para obtener todos los items del inventario
    public List<Inventario> obtenerInventario() throws SQLException {
        List<Inventario> inventario = new ArrayList<>();
        String sql = "SELECT * FROM Inventario";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Inventario item = new Inventario();
                item.setId(rs.getInt("Id"));
                item.setProducto(rs.getString("Producto"));
                item.setCantidad(rs.getInt("Cantidad"));
                item.setFechaActualizacion(rs.getTimestamp("FechaActualizacion"));
                inventario.add(item);
            }
        }
        return inventario;
    }

    // Metodo para actualizar la cantidad de un item en el inventario
    public void actualizarInventario(Inventario item) throws SQLException {
        String sql = "UPDATE Inventario SET Cantidad = ? WHERE Id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getCantidad());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
        }
    }

    // Metodo para eliminar un item del inventario
    public void eliminarInventario(int id) throws SQLException {
        String sql = "DELETE FROM Inventario WHERE Id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}