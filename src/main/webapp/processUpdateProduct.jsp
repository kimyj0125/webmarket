<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%@ page import="java.util.*" %>
<%@ include file="dbconn.jsp" %>
<%
	request.setCharacterEncoding("UTF-8");

	String realFolder = "c:\\upload";
	int maxSize = 5*1024*1024; //5MB
	String encType = "UTF-8";
	MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize,
			encType, new DefaultFileRenamePolicy());
	
	String productId = multi.getParameter("productId");
	String name = multi.getParameter("name");
	String unitPrice = multi.getParameter("unitPrice");
	String description = multi.getParameter("description");
	String manufacturer = multi.getParameter("manufacturer");
	String category = multi.getParameter("category");
	String unitsInStock = multi.getParameter("unitsInStock");
	String condition = multi.getParameter("condition");
	
	Integer price;
	if(unitPrice.isEmpty())
		price = 0;
	else
		price = Integer.valueOf(unitPrice);
	
	long stock;
	if(unitsInStock.isEmpty())
		stock = 0;
	else
		stock = Long.valueOf(unitsInStock);
	
	Enumeration  files = multi.getFileNames();
	String fname = (String)files.nextElement();
	String filename = multi.getFilesystemName(fname);

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "select * from product where p_id=?";
	pstmt = conn.prepareStatement(sql);
	pstmt.setString(1, productId);
	rs = pstmt.executeQuery();
	if(rs.next()){
		sql="UPDATE product SET p_name=?, p_unitPrice=?," + 
				"p_description=?, p_manufacturer=?, p_category=?," + 
				"p_unitsInStock=?, p_condition=?";
		if(filename != null){
			sql+=", p_fileName=? WHERE p_id=?";
		}	else{
			sql+=" WHERE p_id=?";
		}
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, price);
		pstmt.setString(3, description);
		pstmt.setString(4, manufacturer);
		pstmt.setString(5, category);
		pstmt.setLong(6, stock);
		pstmt.setString(7, condition);
		if(filename != null){
			pstmt.setString(8, filename);
			pstmt.setString(9, productId);
		}else{
			pstmt.setString(8, productId);
		}
		pstmt.executeUpdate();
	}//	if(rs.next())
	if(rs != null)
		rs.close();
	if(pstmt != null)
		pstmt.close();
	if(conn != null)
		conn.close();
	response.sendRedirect("editProduct.jsp?edit=update");
%>
