package com.innovacorp.simulador;

import com.innovacorp.dao.InventarioDAO;
import com.innovacorp.dao.ReporteDAO;
import com.innovacorp.model.Inventario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SimuladorInventario {
    private Timer timer;
    private boolean activa;
    private List<String> mensajesReabastecimiento;
    private ReporteDAO reporteDAO;

    public SimuladorInventario() {
        this.mensajesReabastecimiento = new ArrayList<>();
        this.reporteDAO = new ReporteDAO();
    }

    public void iniciarSimulacion() {
        if (activa) return;

        activa = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    simularDia();
                } catch (Exception e) {
                    System.err.println("Error en la simulación: " + e.getMessage());
                }
            }
        }, 0, 5000);
    }

    public void detenerSimulacion() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        activa = false;


        generarReporte();
    }

    public boolean estaActiva() {
        return activa;
    }

    public List<String> getMensajesReabastecimiento() {
        return mensajesReabastecimiento;
    }

    private void simularDia() throws SQLException {
        InventarioDAO inventarioDAO = new InventarioDAO();
        List<Inventario> inventario = inventarioDAO.obtenerInventario();
        mensajesReabastecimiento.clear();

        for (Inventario item : inventario) {

            int cantidadVendida = (int) (Math.random() * 5) + 1;
            item.setCantidad(Math.max(0, item.getCantidad() - cantidadVendida));


            if (item.getCantidad() <= 10) {
                int cantidadReabastecida = (int) (Math.random() * 41) + 10;
                item.setCantidad(item.getCantidad() + cantidadReabastecida);
                String mensaje = "Reabastecido: " + item.getProducto() + " - Cantidad añadida: " + cantidadReabastecida;
                mensajesReabastecimiento.add(mensaje);
            }

            inventarioDAO.actualizarInventario(item);
        }

        System.out.println("Simulacion de un dia completada.");
    }

    private void generarReporte() {
        if (mensajesReabastecimiento.isEmpty()) {
            System.out.println("No hay datos para generar un reporte.");
            return;
        }

        String titulo = "Reporte de Simulación - " + java.time.LocalDate.now();
        StringBuilder detalle = new StringBuilder("Resumen de la simulación:\n");

        for (String mensaje : mensajesReabastecimiento) {
            detalle.append("- ").append(mensaje).append("\n");
        }

        try {
            reporteDAO.agregarReporte(titulo, detalle.toString());
            System.out.println("Reporte generado y guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al guardar el reporte: " + e.getMessage());
        }
    }
}