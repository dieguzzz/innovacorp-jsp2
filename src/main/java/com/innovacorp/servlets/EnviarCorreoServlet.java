package com.innovacorp.servlets;

import java.io.IOException;

import com.innovacorp.correos.CorreoAPI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/enviarCorreo")
public class EnviarCorreoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destinatario = request.getParameter("email");
        String asunto = request.getParameter("asunto");
        String mensaje = request.getParameter("mensaje");

        boolean enviado = CorreoAPI.enviarCorreo(destinatario, asunto, mensaje);

        if (enviado) {
            response.getWriter().write("Correo enviado con éxito.");
        } else {
            response.getWriter().write("Error al enviar el correo.");
        }
    }
}

