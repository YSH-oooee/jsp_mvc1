package step4_00_boardEx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;



public class BoardDAO {
	
	private BoardDAO() {}
	private static BoardDAO instance = new BoardDAO();
	public static BoardDAO getInstance() {
		return instance;
	}


	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public Connection getConnection() {
		
		String dbURL 	  = "jdbc:mysql://localhost:3306/STEP4_BOARD_EX?serverTimezone=UTC";
		String dbID 	  = "root";
		String dbPassword = "root";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//테스트 데이터 생성
	public void setDummy() {
		
		Random ran = new Random();
		
		try {
			
			
			String[] a = {"김", "박", "이", "최", "유", "강", "천", "정", "안", "윤", "임"};
			String[] b = {"a", "b", "c", "d", "e", "f", "g", "i", "y", "u", "t"};
			
			for (int i = 1; i < 51; i++) {

				String writer = "";
				String email = "";
				String subject = "";
				String password = "1111";
				String content = "";
				
				for (int j = 0; j < 10; j++) {
					
					writer += a[ran.nextInt(a.length)];
					subject += a[ran.nextInt(a.length)];
					content += a[ran.nextInt(a.length)];
					
					if (j > 4) {
						email += b[ran.nextInt(b.length)];
					}
					
				}
				
				email += "@gmail.com";
				
				String sql = "INSERT INTO BOARD(WRITER,EMAIL,SUBJECT,PASSWORD,REG_DATE,REF,RE_STEP,RE_LEVEL,READ_COUNT,CONTENT)";
					   sql += "VALUES(?, ?, ?, ?, now(), ?, 1, 1, 0, ?)";
				
			    conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, writer);
				pstmt.setString(2, email);
				pstmt.setString(3, subject);
				pstmt.setString(4, password);
				pstmt.setInt(5, i);
				pstmt.setString(6, content);
				pstmt.executeUpdate();
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) 	{try {rs.close();} catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
	}
	
	
	//게시글 쓰기
	public void insertBoard(BoardDTO boardDTO) {

		int ref = 0;
		int num = 0;
		
		try {
			
			conn = getConnection();

			pstmt = conn.prepareStatement("SELECT MAX(REF) FROM BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ref = rs.getInt(1) + 1;
			}

			pstmt = conn.prepareStatement("SELECT MAX(NUM) FROM BOARD");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}

			pstmt = conn.prepareStatement("INSERT INTO BOARD VALUES(?, ?, ?, ?, ?, now()," + " ?, 1, 1, 0, ?)");
			pstmt.setInt(1, num);
			pstmt.setString(2, boardDTO.getWriter());
			pstmt.setString(3, boardDTO.getEmail());
			pstmt.setString(4, boardDTO.getSubject());
			pstmt.setString(5, boardDTO.getPassword());
			pstmt.setInt(6, ref);
			pstmt.setString(7, boardDTO.getContent());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
	}

	
	//게시글 수정
	public boolean updateBoard(BoardDTO boardDTO) {

		boolean isUpdate = false;
		
		try {
			
			if (validMemberCheck(boardDTO)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("UPDATE BOARD SET SUBJECT=?, CONTENT=? WHERE NUM=?");
				pstmt.setString(1, boardDTO.getSubject());
				pstmt.setString(2, boardDTO.getContent());
				pstmt.setInt(3, boardDTO.getNum());
				pstmt.executeUpdate();
				System.out.println("board�뀒�씠釉붿씠 �뾽�뜲�씠�듃 �릺�뿀�뒿�땲�떎.");
				System.out.println(boardDTO.getNum() + "/" + boardDTO.getWriter() + "/" + boardDTO.getSubject());
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isUpdate;
		
	}

	
	//게시글 삭제
	public boolean deleteBoard(BoardDTO boardDTO) {

		boolean isDelete = false;
		
		try {
			
			if (validMemberCheck(boardDTO)) {
				conn = getConnection();
				pstmt = conn.prepareStatement("DELETE FROM BOARD WHERE NUM=?");
				pstmt.setInt(1, boardDTO.getNum());
				pstmt.executeUpdate();
				System.out.println("board�뀒�씠釉붿씠�쓽 硫ㅻ쾭媛� �궘�젣�릺�뿀�뒿�땲�떎.");
				System.out.println(boardDTO.getNum() + "/" + boardDTO.getWriter() + "/" + boardDTO.getSubject());
				isDelete = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}
		
		return isDelete;
		
	}
	
	
	// 게시글 개수 DAO (검색어에 따라 달라짐)
	public int getAllCount(String searchKeyword , String searchWord) {
		
		int totalBoardCount = 0;
		
		try {
			
			conn = getConnection();
			
			String sql = "";
			if (searchKeyword.equals("total")) { // searchKeyword : 전체 검색
				if (searchWord.equals("")) { // 검색어가 없으면, 전체 게시글 카운트 
					sql = "SELECT COUNT(*) FROM BOARD";
				}
				else {	// 해당 검색어를 포함한 게시글만 카운트
					sql = "SELECT COUNT(*) FROM BOARD ";
					sql += "WHERE SUBJECT LIKE '%" + searchWord +"%' OR ";
					sql += "WRITER LIKE '%" + searchWord +"%' "; 
				}
				
			}
			else { // searchKeyword에서 해당 검색어를 포함한 게시글만 카운트
				sql = "SELECT COUNT(*) FROM BOARD WHERE " + searchKeyword + " LIKE '%" + searchWord +"%'"; 
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totalBoardCount = rs.getInt(1);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
		return totalBoardCount;
		
	}


	//한개의 게시글 정보 조회
	public BoardDTO getOneBoard(int num) {

		BoardDTO bean = new BoardDTO();

		try {
			
			conn = getConnection();
			//조회수 증가
			pstmt = conn.prepareStatement("UPDATE BOARD SET READ_COUNT=READ_COUNT+1 WHERE NUM=?");
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			//정보 조회
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setRegDate(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setReStep(rs.getInt(8));
				bean.setReLevel(rs.getInt(9));
				bean.setReadCount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		return bean;
	}
	
	// 게시판 목록 DAO
	public ArrayList<BoardDTO> getSearchBoard(String searchKeyword, String searchWord,  int startBoardIdx, int searchCount) {

		ArrayList<BoardDTO> vec = new ArrayList<BoardDTO>();
		BoardDTO bdto = null;
		
		try {
			
			conn = getConnection();
			String sql = "";
			
			if (searchKeyword.equals("total")) { // searchKeyword : 검색분류 >> 전체 검색
				if (searchWord.equals("")) { // 검색어 없음(전체 출력)
					sql = "SELECT * FROM BOARD ORDER BY REF DESC , RE_STEP LIMIT ?,?";
				}
				else {	// 전체 검색 + 검색어 존재 >> total에서 searchWord 검색 >> 제목이나 작성자 검색
					sql = "SELECT * FROM BOARD ";
					sql += "WHERE SUBJECT LIKE '%" + searchWord +"%' OR ";
					sql += "WRITER LIKE '%" + searchWord +"%' "; 
					sql += "ORDER BY REF DESC , RE_STEP LIMIT  ?,?";
				}
				
			}
			else { // searchKeyword가 제목이나 작성자
				sql = "SELECT * FROM BOARD  WHERE " + searchKeyword + " LIKE '%" + searchWord +"%' ORDER BY REF DESC , RE_STEP LIMIT ?,?"; 
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startBoardIdx);
			pstmt.setInt(2, searchCount);


			rs = pstmt.executeQuery();

			while (rs.next()) {
				bdto = new BoardDTO();
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString(4));
				bdto.setPassword(rs.getString(5));
				bdto.setRegDate(rs.getDate(6).toString());
				bdto.setRef(rs.getInt(7));
				bdto.setReStep(rs.getInt(8));
				bdto.setReLevel(rs.getInt(9));
				bdto.setReadCount(rs.getInt(10));
				bdto.setContent(rs.getString(11));
				vec.add(bdto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
		return vec;
		
	}
	
	
	//게시글 수정을 위한 게시글 조회(조회수 증가X)
	public BoardDTO getOneUpdateBoard(int num) {

		BoardDTO bdto = new BoardDTO();

		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=?");
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				bdto.setNum(rs.getInt(1));
				bdto.setWriter(rs.getString(2));
				bdto.setEmail(rs.getString(3));
				bdto.setSubject(rs.getString(4));
				bdto.setPassword(rs.getString(5));
				bdto.setRegDate(rs.getDate(6).toString());
				bdto.setRef(rs.getInt(7));
				bdto.setReStep(rs.getInt(8));
				bdto.setReLevel(rs.getInt(9));
				bdto.setReadCount(rs.getInt(10));
				bdto.setContent(rs.getString(11));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null) {try {conn.close();}   catch (SQLException e) {}}
		}
		
		return bdto;
		
	}


	//비밀번호 인증(게시글 삭제 시, 사용)
	public boolean validMemberCheck(BoardDTO boardDTO) {

		boolean isValidMember = false;
		
		try {
			
			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE NUM=? AND PASSWORD=?");
			pstmt.setInt(1, boardDTO.getNum());
			pstmt.setString(2, boardDTO.getPassword());
			
			rs = pstmt.executeQuery();

			if (rs.next()) 	isValidMember = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}

		return isValidMember;
		
	}
	
	//답변글 쓰기
	public void reWriteBoard(BoardDTO boardDTO) {
		 
		int ref      = boardDTO.getRef();
		int reStep   = boardDTO.getReStep();
		int reLevel  = boardDTO.getReLevel();
		int boardNum = 0;

		try {

			conn = getConnection();
			
			pstmt = conn.prepareStatement("SELECT MAX(NUM) FROM BOARD");
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				boardNum = rs.getInt(1) + 1;
			}
			//원본 게시글과 REF가 같고, RE_STEP보다 큰 게시글들의 RE_STEP을 1 증가시킴
			pstmt = conn.prepareStatement("UPDATE BOARD SET RE_STEP=RE_STEP+1 WHERE REF=? AND RE_STEP > ?");
			pstmt.setInt(1, ref);
			pstmt.setInt(2, reStep);
			
			pstmt.executeUpdate();
			
			//게시글 생성
			String sql = "INSERT INTO BOARD (NUM , WRITER, EMAIL, SUBJECT, PASSWORD, ";
				   sql+= "REG_DATE, REF, RE_STEP, RE_LEVEL, READ_COUNT, CONTENT) " + "VALUES (?,?,?,?,?,NOW(),?,?,?,0,?)";
				   
			pstmt = conn.prepareStatement(sql);
			//답변글은 원본 글의 re_step, re_level을 1씩 증가해서 저장
			pstmt.setInt(1, boardNum);
			pstmt.setString(2, boardDTO.getWriter());
			pstmt.setString(3, boardDTO.getEmail());
			pstmt.setString(4, boardDTO.getSubject());
			pstmt.setString(5, boardDTO.getPassword());
			pstmt.setInt(6, ref);
			pstmt.setInt(7, reStep + 1);
			pstmt.setInt(8, reLevel + 1);
			pstmt.setString(9, boardDTO.getContent());
			
			pstmt.executeUpdate();
			
			System.out.println("답변글이 게시되었습니다.");
			System.out.println(boardDTO.getNum() + "/" + boardDTO.getWriter() + "/" + boardDTO.getSubject());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)    {try {rs.close();}    catch (SQLException e) {}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
			if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
		}

	}
	
}
