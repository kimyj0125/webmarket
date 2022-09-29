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
if(list==null){
	list = new ArrayList<Product>();
	session.setAttribute("cartlist", list);
}

int cnt = 0;
Product goodsQnt;

//장바구니에 담긴 상품의 수량 1증가 
for (int i = 0; i < list.size(); i++) {
	goodsQnt = list.get(i);
	if (goodsQnt.getProductId().equals(id)) {
		cnt++;
		int orderQuantity = goodsQnt.getQuantity() + 1;
		goodsQnt.setQuantity(orderQuantity);
	}
}
//장바구니 새로운 상품 추가
if (cnt == 0) { 
	goods.setQuantity(1);
	list.add(goods);
}

response.sendRedirect("product.jsp?id=" + id);
%>  
