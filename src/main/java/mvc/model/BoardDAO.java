package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mvc.database.DBConnection;

public class BoardDAO {
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		if (instance == null)
			instance = new BoardDAO();

		return instance;
	}

	public int getListCount(String items, String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = 0;
		String sql;
		if (items == null && text == null)
			sql = "select count(*) from board";
		else
			sql = "select count(*) from board where " + items + " like '%" + text + "%'";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next())
				cnt = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("getListCount() 에러" + e);
		} finally {
			try {
				DBConnection.close(rs, pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return cnt;
	}// getListCount()

	public ArrayList<BoardDTO> getBoardList(int page, int limit, String items,
			String text) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int total = getListCount(items, text);
		int start = (page - 1) * limit;
		int index = start + 1;
		//select * from board order by num desc;
		//select * from board where subject like '%제목5%' order by num desc;
		//select * from board where name like '%홍길동%' order by num desc;
		//select * from board where content like '%내용%' order by num desc;
		if (items == null && text == null)
			sql = "select * from board order by num desc";
		else
			sql = "select * from board where " + items + " like '%" + 
					text + "%' order by num desc";
		
		ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql
					,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			while (rs.absolute(index)) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
				list.add(board);
				if (index < (start + limit) && index <= total)
					index++;
				else
					break;
			}//end while
				
		} catch (Exception e) {
			System.out.println("getBoardList() 에러" + e);
		} finally {
			try {
				DBConnection.close(rs, pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return list;
	}// getBoardList()
	
	public String getLoginNameById(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="select * from member where id=?";
		String name=null;
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (Exception e) {
			System.out.println("getLoginNameById() 에러 : " + e);
		}finally {
			try {
				DBConnection.close(rs, pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return name;
	}//getLoginNameById()
	
	public void insertBoard(BoardDTO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql="insert into board values(board_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getId());
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getSubject());
			pstmt.setString(4, board.getContent());
			pstmt.setString(5, board.getRegist_day());
			pstmt.setInt(6, board.getHit());
			pstmt.setString(7, board.getIp());
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnection.close(pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}//insertBoard()
	//	선택된 글의 조회수를 증가
	public void updateHit(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= null;
		try {
			sql = "select hit from board where num=?";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			int hit = 0;
			if(rs.next()) {
				hit = rs.getInt("hit")+1;
				sql = "update board set hit=? where num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, hit);
				pstmt.setInt(2, num);
				pstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnection.close(rs, pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}//updateHit()
	
	//선택된 글의 상세정보 가져오기
	public BoardDTO getBoardByNum(int num) {
		BoardDTO board = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql= null;
		updateHit(num);
		try {
			sql = "select * from board where num=?";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				DBConnection.close(rs, pstmt, conn);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return board;
	}//getBoardBydNum()
	public void deleteBoard(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;		
		String sql = "delete from board where num=?";	
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
		} catch (Exception ex) {
			System.out.println("deleteBoard() 에러 : " + ex);
		} finally {
			try {										
				DBConnection.close(pstmt, conn);
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
	}//deleteBoard
	
	public void updateBoard(BoardDTO board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "update board set name=?, "
					+ "subject=?, content=? where num=?";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			pstmt.setString(1, board.getName());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getNum());
			pstmt.executeUpdate();			
			conn.commit();
		} catch (Exception ex) {
			System.out.println("updateBoard() 에러 : " + ex);
		} finally {
			try {										
				DBConnection.close(pstmt, conn);
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}		
		}
	} //updateBoard	
}// end class
