package mvc.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.model.BoardDAO;
import mvc.model.BoardDTO;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final int LIST_COUNT = 5;
	BoardDAO dao = BoardDAO.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		String command = requestURI.substring(contextPath.length());
		
		resp.setContentType("text/html; charset=UTF-8");	
		req.setCharacterEncoding("UTF-8");
		
		if(command.equals("/BoardListAction.do")) {
			requestBoardList(req);
			RequestDispatcher rd
				= req.getRequestDispatcher("./board/list.jsp");
			rd.forward(req, resp);
		}else if(command.equals("/BoardWriteForm.do")) {
			requestLoginName(req);
			RequestDispatcher rd
				= req.getRequestDispatcher("./board/writeForm.jsp");
			rd.forward(req, resp);
		}else if(command.equals("/BoardWriteAction.do")) {
			requestBoardWrite(req);
			RequestDispatcher rd
				= req.getRequestDispatcher("/BoardListAction.do");
			rd.forward(req, resp);
		}else if(command.equals("/BoardViewAction.do")) {
			requestBoardView(req);
			RequestDispatcher rd
				= req.getRequestDispatcher("./board/view.jsp");
			rd.forward(req, resp);
		}else if(command.equals("/BoardDeleteAction.do")) {
			requestBoardDelete(req);
			RequestDispatcher rd
				= req.getRequestDispatcher("/BoardListAction.do");	
			rd.forward(req, resp);
		}else if(command.equals("/BoardUpdateAction.do")) {
			requestBoardUpdate(req);
			RequestDispatcher rd
			= req.getRequestDispatcher("/BoardListAction.do");	
			rd.forward(req, resp);
		}
	}//doPost()
	private  void requestBoardUpdate(HttpServletRequest request){
		int num = Integer.parseInt(request.getParameter("num"));
		BoardDTO board = new BoardDTO();		
		board.setNum(num);
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));		
	
		dao.updateBoard(board);		
	}
	private void requestBoardDelete(HttpServletRequest request){
		int num = Integer.parseInt(request.getParameter("num"));
		dao.deleteBoard(num);
	}
	private void requestBoardView(HttpServletRequest request) {
		BoardDTO board=null;
		int num = Integer.parseInt(request.getParameter("num"));
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		board = dao.getBoardByNum(num);
		
		request.setAttribute("board", board);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);
	}
	
	private void requestBoardWrite(HttpServletRequest request) {
		BoardDTO board = new BoardDTO();
		board.setId(request.getParameter("id"));
		board.setName(request.getParameter("name"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		java.text.SimpleDateFormat formatter 
			= new java.text.SimpleDateFormat("yyyy/MM/dd(HH:mm:ss)");
		String regist_day = formatter.format(new java.util.Date()); 
		board.setHit(0);
		board.setRegist_day(regist_day);
		board.setIp(request.getRemoteAddr());
		dao.insertBoard(board);
	}//requestBoardWrite
	
	private void requestLoginName(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = dao.getLoginNameById(id);
		request.setAttribute("name", name);
	}
	
	private void requestBoardList(HttpServletRequest request) {
		BoardDAO dao = BoardDAO.getInstance();
		List<BoardDTO> boardList = null;
		int pageNum = 1;
		int limit = LIST_COUNT;
		
		if(request.getParameter("pageNum") != null)
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		String items = request.getParameter("items");  //subject, content, name
		String text = request.getParameter("text"); //사용자가 입력한 검색어(텍스트)
		
		int total_record = dao.getListCount(items, text);
		boardList = dao.getBoardList(pageNum, limit, items, text);
		
		int total_page = total_record / limit;
		if(total_record % limit !=0)
			total_page += 1;
		
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("total_page", total_page);
		request.setAttribute("total_record", total_record);
		request.setAttribute("boardList", boardList);
	}//requestBoardList()
	
}//end BoardController
