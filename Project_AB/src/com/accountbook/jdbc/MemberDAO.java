package com.accountbook.jdbc;

import java.sql.*;
import java.util.*;

public class MemberDAO {
	Scanner sc = new Scanner(System.in);
	
	final String DRIVER = "oracle.jdbc.OracleDriver";
	final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	final String USER = "ACCOUNT_BOOK";
	final String PASSWORD = "accbpw";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public MemberDAO() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MemberVO selectId(String id) {
		MemberVO member = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		String sql = "SELECT AB_ID, PASSWORD, NAME, JUMIN, PHONE, EMAIL "
				   + "FROM AB_MEMBER "
				   + "WHERE AB_ID = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
				member = new MemberVO( rs.getString("AB_ID"), 
									   rs.getString("PASSWORD"),
									   rs.getString("NAME"),
									   rs.getString("JUMIN"),
									   rs.getString("PHONE"),
									   rs.getString("EMAIL") );
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return member;
	}

	public List<MemberVO> SelectAll(String id) {
		ArrayList<MemberVO> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = ""
					+ "SELECT AB_ID, PASSWORD, NAME, JUMIN, PHONE, EMAIL "
					+ "	 FROM AB_MEMBER "
					+ " ORDER BY AB_ID ";
			
			pstmt = conn.prepareStatement(sql.toString());

			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setId(rs.getString("AB_ID"));
				member.setPassword(rs.getString("PASSWORD"));
				member.setName(rs.getString("NAME"));
				member.setJumin(rs.getString("JUMIN"));
				member.setPhone(rs.getString("PHONE"));
				member.setEmail(rs.getString("EMAIL"));
				
				list.add(member);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return list;
	}
	
	
	public int InsertData(MemberVO member) {
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "INSERT INTO AB_MEMBER "
					+ "	 	  ( AB_ID, PASSWORD, NAME, JUMIN, PHONE, EMAIL ) "
					+ "VALUES(?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getJumin());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getEmail());
			
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("회원가입 성공");
			} else {
				System.out.println("회원가입 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int UpdateMember(MemberVO member) {
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
			String sql = ""
					+ "UPDATE AB_MEMBER "
					+ "   SET AB_ID = ?, PASSWORD = ?, NAME = ?, PHONE = ?, EMAIL = ? "
					+ "WHERE JUMIN = ? ";
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getJumin());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int DeleteMember(String jumin) {
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "DELETE FROM AB_MEMBER WHERE JUMIN = ? ";
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, jumin);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int login(String id, String password) {
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "SELECT PASSWORD FROM AB_MEMBER WHERE AB_ID = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString(1).equals(password)) {
					System.out.println("로그인 성공!!!");
					
					return 1;
				} else {
					System.out.println("비밀번호가 일치하지 않습니다");
					return 0;
				}
			}
			System.out.println("아이디가 존재하지 않습니다!");
			return -1;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		return result;
		
	}
	
	public int AccountInsert(MemberVO member) {
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "INSERT INTO ACCIN_OUT "
					+ "	 	  (A_NO, AB_ID, IN_OUT, MONEY_IO, ACCDATE, ACC_CATEGORY) "
					+ "VALUES(SEQ_NO.NEXTVAL, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getAccInOut());
			pstmt.setInt(3, member.getInOutMoney());
			pstmt.setString(4, member.getHiredate());
			pstmt.setString(5, member.getCategory());
			
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("입출내역등록 완료");
			} else {
				System.out.println("입출내역등록 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public MemberVO selectAccNo(int accNo) { // 시퀀스 번호 받아서 검색(값 1개)
		MemberVO member = new MemberVO();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		String sql = "SELECT A_NO, AB_ID, IN_OUT, MONEY_IO, ACCDATE, ACC_CATEGORY "
				   + "FROM ACCIN_OUT "
				   + "WHERE A_NO = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, accNo);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
				member.accMember( rs.getInt("A_NO"), 
								  rs.getString("AB_ID"),
								  rs.getString("IN_OUT"),
								  rs.getInt("MONEY_IO"),
								  rs.getString("ACCDATE"),
								  rs.getString("ACC_CATEGORY") );
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return member;
	}
	
	public MemberVO oneMonthInOut(String id) { // 입금액 - 출금액
		MemberVO member = new MemberVO();
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String sql = "SELECT ((SELECT NVL(SUM(MONEY_IO),0) FROM ACCIN_OUT WHERE AB_ID = ? AND IN_OUT LIKE '%입') - "
								  + "(SELECT NVL(SUM(MONEY_IO),0) FROM ACCIN_OUT WHERE AB_ID = ? AND IN_OUT LIKE '%출')) AS BALANCE "
								  + "FROM DUAL ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, id);
				rs = pstmt.executeQuery();
				
				if(rs.next())
					member.setBalance(rs.getInt("BALANCE"));
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn, pstmt, rs);
			}
			return member;
			
	}
	
	public ArrayList<MemberVO> AccSelectAll(String id) { // 입/출내역 전체출력
		ArrayList<MemberVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT A_NO, AB_ID, IN_OUT, MONEY_IO, ACCDATE, ACC_CATEGORY "
					   + "FROM ACCIN_OUT "
					   + "WHERE AB_ID = ? "
					   + "ORDER BY ACCDATE ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setAccNo(rs.getInt("A_NO"));
				member.setId(rs.getString("AB_ID"));
				member.setAccInOut(rs.getString("IN_OUT"));
				member.setInOutMoney(rs.getInt("MONEY_IO"));
				member.setHiredate(rs.getString("ACCDATE"));
				member.setCategory(rs.getString("ACC_CATEGORY"));
				
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return list;		
	}
	
	public ArrayList<MemberVO> AccountSelect(String id, String hiredate) { // 입/출 날짜별 출력
		ArrayList<MemberVO> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT A_NO, AB_ID, IN_OUT, MONEY_IO, ACCDATE, ACC_CATEGORY "
					   + "FROM ACCIN_OUT "
					   + "WHERE AB_ID = ? "
					   + "AND ACCDATE = ? "
			 		   + "ORDER BY A_NO ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, hiredate);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
		
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setAccNo(rs.getInt("A_NO"));
				member.setId(rs.getString("AB_ID"));
				member.setAccInOut(rs.getString("IN_OUT"));
				member.setInOutMoney(rs.getInt("MONEY_IO"));
				member.setHiredate(rs.getString("ACCDATE"));
				member.setCategory(rs.getString("ACC_CATEGORY"));
				
				list.add(member);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public int AccountUpdate(MemberVO member) { // 입/출력내역 수정
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "UPDATE ACCIN_OUT "
					   + "SET IN_OUT = ?, MONEY_IO = ?, ACCDATE = ?, ACC_CATEGORY = ? "
					   + "WHERE A_NO = ? "
					   + "AND AB_ID = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getAccInOut());
			pstmt.setInt(2, member.getInOutMoney());
			pstmt.setString(3, member.getHiredate());
			pstmt.setString(4, member.getCategory());
			pstmt.setInt(5, member.getAccNo());
			pstmt.setString(6, member.getId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int AccountDelete(int accNo) { // 입/출력내역 삭제
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "DELETE FROM ACCIN_OUT WHERE A_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, accNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public MemberVO CardSelect(String id) { // 카드 검색
		MemberVO member = new MemberVO();
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
		String sql = ""
				+ "SELECT AB_ID, CARDNAME, CARDNUM, CARDNICK "
				+ "	 FROM CARD "
				+ "WHERE AB_ID = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
				member.CardMember(rs.getString("AB_ID"), 
								  rs.getString("CARDNAME"),  
								  rs.getString("CARDNUM"), 
								  rs.getString("CARDNICK"));
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return member;
	}

	public List<MemberVO> cardSelectAll(String id) { // 카드 전체 출력(id입력 받아서)
		ArrayList<MemberVO> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			String sql = ""
					+ "SELECT AB_ID, CARDNAME, CARDNUM, CARDNICK "
					+ "	 FROM CARD "
					+ " WHERE AB_ID = ? "
					+ " ORDER BY AB_ID ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setId(rs.getString("AB_ID"));
				member.setCard(rs.getString("CARDNAME"));
				member.setCardNum(rs.getString("CARDNUM"));
				member.setCardNick(rs.getString("CARDNICK"));
				
				list.add(member);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return list;
	}
	
	public int CardInsert(MemberVO member) { // 카드 생성
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "INSERT INTO CARD "
					+ "	 	  (AB_ID, CARDNAME, CARDNUM, CARDNICK) "
					+ "VALUES(?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getCard());
			pstmt.setString(3, member.getCardNum());
			pstmt.setString(4, member.getCardNick());

			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("카드 등록 성공");
			} else {
				System.out.println("카드 등록 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int cardUpdate(MemberVO member) { // 카드 수정
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
			String sql = ""
					+ "UPDATE CARD "
					+ "	  SET CARDNAME = ?, CARDNICK = ? "
					+ "WHERE CARDNUM = ? "
					+ "AND AB_ID = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getCard());
			pstmt.setString(2, member.getCardNick());
			pstmt.setString(3, member.getCardNum());
			pstmt.setString(4, member.getId());

			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int DeleteCard(String cardNum) { // 카드 삭제
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "DELETE FROM CARD WHERE CARDNUM = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cardNum);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public ArrayList<MemberVO> CardInOutSelectAll(String id, String cardNum) { // 카드 내역 전체출력
		ArrayList<MemberVO> list = null;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT C_NO, AB_ID, CARDATE, CARD_CATEGORY, MONEY, CARDNUM "
					   + "FROM CARDIN_OUT "
					   + "WHERE AB_ID = ? "
					   + "AND CARDNUM = ? "
					   + "ORDER BY CARDATE ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, cardNum);
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
			
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setCardNo(rs.getInt("C_NO"));
				member.setId(rs.getString("AB_ID"));
				member.setHiredate(rs.getString("CARDATE"));
				member.setCategory(rs.getString("CARD_CATEGORY"));
				member.setInOutMoney(rs.getInt("MONEY"));
				member.setCardNum(rs.getString("CARDNUM"));
				
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return list;		
	}
	
	public ArrayList<MemberVO> CardInOutSelect(String id, String hiredate) { // 날짜별로 검색해서 출력
		ArrayList<MemberVO> list = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT C_NO, AB_ID, CARDATE, CARD_CATEGORY, MONEY, CARDNUM "
					   + "FROM CARDIN_OUT "
					   + "WHERE AB_ID = ? "
					   + "AND CARDATE = ? "
			 		   + "ORDER BY C_NO ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, hiredate);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<MemberVO>();
		
			while(rs.next()) {
				MemberVO member = new MemberVO();
				member.setCardNo(rs.getInt("C_NO"));
				member.setId(rs.getString("AB_ID"));
				member.setHiredate(rs.getString("CARDATE"));
				member.setCategory(rs.getString("CARD_CATEGORY"));
				member.setInOutMoney(rs.getInt("MONEY"));
				member.setCardNum(rs.getString("CARDNUM"));
				
				list.add(member);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	public MemberVO CardInOutOrderSelect(String id, int cardNo) {	// 시퀀스로 검색해서 출력
		MemberVO member = new MemberVO();

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "SELECT C_NO, AB_ID, CARDATE, CARD_CATEGORY, MONEY, CARDNUM "
					   + "FROM CARDIN_OUT "
					   + "WHERE AB_ID = ? "
					   + "AND C_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, cardNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				member.cardInOut(rs.getInt("C_NO"), 
								 rs.getString("AB_ID"), 
								 rs.getString("CARDATE"),
								 rs.getString("CARD_CATEGORY"), 
								 rs.getInt("MONEY"), 
								 rs.getString("CARDNUM"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, rs);
		}

		return member;
	}
	
	public MemberVO cardTotalOut(String id, String cardNum) { // 출금액
		MemberVO member = new MemberVO();
			try {
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
				
				String sql = "SELECT (SELECT NVL(SUM(MONEY),0) "
						  + "FROM CARDIN_OUT WHERE AB_ID = ? AND CARDNUM = ?) AS BALANCE " 
						  + "FROM DUAL ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, cardNum);
				rs = pstmt.executeQuery();
				
				if(rs.next())
					member.setBalance(rs.getInt("BALANCE"));
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(conn, pstmt, rs);
			}
			return member;
			
	}
	
	public int CardInOutInsert(MemberVO member) {	// 카드 사용 내역 추가
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = ""
					+ "INSERT INTO CARDIN_OUT "
					+ "	 	  (C_NO, AB_ID, CARDATE, CARD_CATEGORY, MONEY, CARDNUM) "
					+ "VALUES(CARD_NO.NEXTVAL, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getHiredate());
			pstmt.setString(3, member.getCategory());
			pstmt.setInt(4, member.getInOutMoney());
			pstmt.setString(5, member.getCardNum());
			
			
			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("입출내역등록 완료");
			} else {
				System.out.println("입출내역등록 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}
	
	public int CardInOutDelete(int cardNo) { // 카드 사용내역 삭제
		int result = 0;
		
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String sql = "DELETE FROM CARDIN_OUT WHERE C_NO = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cardNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);
		}
		
		return result;
	}	
	
	private void close(Connection conn, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pstmt != null)
				pstmt.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				conn.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
