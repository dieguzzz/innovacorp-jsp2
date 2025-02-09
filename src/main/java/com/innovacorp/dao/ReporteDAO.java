package com.innovacorp.dao;

import com.innovacorp.model.Reporte;
import com.innovacorp.correos.ReporteInventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ReporteDAO {

    // Metodo para obtener todos los reportes
    public List<Reporte> obtenerReportes() throws SQLException {
        List<Reporte> reportes = new ArrayList<>();
        String sql = "SELECT * FROM Reportes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reporte reporte = new Reporte();
                reporte.setId(rs.getInt("Id"));
                reporte.setTitulo(rs.getString("Titulo"));
                reporte.setDetalle(rs.getString("Detalle"));
                reporte.setFecha(rs.getTimestamp("Fecha"));
                reportes.add(reporte);
            }
        }
        return reportes;
    }

    // Metodo para agregar un nuevo reporte
    public void agregarReporte(String titulo, String detalle) throws SQLException {
        String sql = "INSERT INTO Reportes (Titulo, Detalle) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, detalle);
            stmt.executeUpdate();

            List<Reporte> reportes = obtenerReportes();
            if (reportes.isEmpty()) {
                System.out.println("No hay datos para generar un reporte.");
                return;
            }

            // Generar y enviar el reporte por correo
            ReporteInventario reporteInventario = new ReporteInventario();
            String contenido = "Titulo: " + titulo + "\nDetalle: " + detalle;
            reporteInventario.generarYEnviarReporte(contenido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}