<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestión de Inventario</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Gestión de Inventario</h2>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Fecha de Actualización</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${inventario}">
            <tr>
                <td>${item.id}</td>
                <td>${item.producto}</td>
                <td>${item.cantidad}</td>
                <td>${item.fechaActualizacion}</td>
                <td>
                    <form action="actualizarInventario" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${item.id}">
                        <input type="number" name="cantidad" value="${item.cantidad}" required>
                        <button type="submit">Actualizar</button>
                    </form>
                    <form action="eliminarInventario" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${item.id}">
                        <button type="submit">Eliminar</button>
                    </form>
                </td>
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