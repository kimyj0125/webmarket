<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>  
<%@ page import="dto.Product" %>  
<%@ page import="dao.ProductRepository" %>  
<%
String id = request.getParameter("id");
if(id == null || id.trim().equals("")){
	response.sendRedirect("products.jsp");
	return;
}

ProductRepository dao = ProductRepository.getInstance();
Product goods = dao.getProductById(id);
if(goods == null){
	response.sendRedirect("exceptionNoProductId.jsp");
}

ArrayList<Product> list = (ArrayList<Product>)session.getAttribute("cartlist");

Product goodsQnt;
for (int i = 0; i < list.size(); i++) {
	goodsQnt = list.get(i);
	if (goodsQnt.getProductId().equals(id)) {
		list.remove(goodsQnt);
	}
}

response.sendRedirect("cart.jsp");
%>  
