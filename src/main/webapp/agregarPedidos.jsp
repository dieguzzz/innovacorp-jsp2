<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gestión de Pedidos</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
    <h2>Agregar Pedido</h2>
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    <form action="agregarPedido" method="post">
        <input type="hidden" name="action" value="agregar">
        <input type="text" name="producto" placeholder="Producto" required>
        <input type="number" name="cantidad" placeholder="Cantidad" required>
        <button type="submit">Agregar Pedido</button>
    </form>
    <form action="index.jsp" method="get">
        <button type="submit" class="btn-regresar">Regresar al Menú Principal</button>
    </form>
</div>
</body>
</html>