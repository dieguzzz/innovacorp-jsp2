<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Reportes Automatizados</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Reportes Automatizados</h2>

    <ul>
        <c:forEach var="reporte" items="${reportes}">
            <li>
                <strong>${reporte.titulo}</strong><br>
                    ${reporte.detalle}<br>
                <small>Fecha: ${reporte.fecha}</small>
            </li>
        </c:forEach>
    </ul>
    <form action="index.jsp" method="get">
        <button type="submit" class="btn-regresar">Regresar al Menú Principal</button>
    </form>
</div>
</body>
</html>