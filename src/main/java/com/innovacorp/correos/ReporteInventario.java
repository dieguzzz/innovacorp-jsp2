package com.innovacorp.correos;

import java.io.IOException;

public class ReporteInventario {

    public void generarYEnviarReporte(String contenido) throws IOException {
        String destinatario = "dieguzzz31@gmail.com";
        String asunto = "Reporte de Inventario";
        String mensaje = "Reporte de Inventario:\n\n" + contenido;
        boolean correoEnviado = CorreoAPI.enviarCorreo(destinatario, asunto, mensaje);

        if (correoEnviado) {
            System.out.println("Reporte enviado correctamente.");
        } else {
            System.out.println("Error al enviar el reporte.");
        }
    }
}
