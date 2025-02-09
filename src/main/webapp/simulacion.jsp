<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Simulación de Inventario</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Simulación de Inventario</h2>

    <form action="simulacion" method="post">
        <button type="submit" name="action" value="iniciar" class="btn-iniciar" ${simulacionActiva ? 'disabled' : ''}>
            Iniciar Simulación
        </button>
        <button type="submit" name="action" value="detener" class="btn-detener" ${simulacionActiva ? '' : 'disabled'}>
            Detener Simulación
        </button>
    </form>

    <div class="mensajes">
        <h3>Mensajes de Reabastecimiento</h3>
        <ul>
            <c:forEach var="mensaje" items="${mensajesReabastecimiento}">
                <li>${mensaje}</li>
            </c:forEach>
        </ul>
    </div>

    <h3>Inventario Actual</h3>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Fecha de Actualización</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${inventario}">
            <tr>
                <td>${item.id}</td>
                <td>${item.producto}</td>
                <td>${item.cantidad}</td>
                <td>${item.fechaActualizacion}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form action="index.jsp" method="get">
        <button type="submit" class="btn-regresar">Regresar al Menú Principal</button>
    </form>
</div>
</body>
</html>