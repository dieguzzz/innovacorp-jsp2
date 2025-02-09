<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Gestión de Pedidos</title>
  <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<div class="container">
  <h2>Lista de Pedidos</h2>

  <table>
    <thead>
    <tr>
      <th>ID</th>
      <th>Producto</th>
      <th>Cantidad</th>
      <th>Estado</th>
      <th>Fecha</th>
      <th>Fecha de Entrega</th>
      <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="pedido" items="${pedidos}">
      <tr>
        <td>${pedido.id}</td>
        <td>${pedido.producto}</td>
        <td>${pedido.cantidad}</td>
        <td>${pedido.estado}</td>
        <td>${pedido.fecha}</td>
        <td>${pedido.fechaEntrega}</td>
        <td>
          <form action="eliminarPedido" method="post" style="display:inline;">
            <input type="hidden" name="id" value="${pedido.id}">
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