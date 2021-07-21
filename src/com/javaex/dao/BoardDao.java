package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver"; // 드라이버
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // IP주소와 포트번호
	private String id = "webdb"; // SQL 계정 이름
	private String pw = "webdb"; // SQL 계정 비밀번호

	// DB 연결
	private void getConnection() {
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// Read 정보 불러오기
	public BoardVo readBoard(int boardNo) {

		BoardVo boardVo = null;

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String select = "";
			select += " SELECT ";
			select += "     b.no, ";
			select += "     u.name, ";
			select += "     b.hit, ";
			select += "     b.reg_date, ";
			select += "     b.title, ";
			select += "     b.content, ";
			select += "     b.user_no ";
			select += " FROM ";
			select += "     users  u ";
			select += "     LEFT OUTER JOIN board  b ON u.no = b.user_no ";
			select += " WHERE ";
			select += "     b.no = ? ";

			pstmt = conn.prepareStatement(select);

			pstmt.setInt(1, boardNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int userNo = rs.getInt("user_no");

				boardVo = new BoardVo(no, name, hit, reg_date, title, content, userNo);
			}

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
		return boardVo;
	}

	// 게시판 목록
	public List<BoardVo> boardList() {

		return boardList("");
	}

	// 게시판 목록 불러오기 + 키워드 검색
	public List<BoardVo> boardList(String keyword) {
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String search = "";
			search += " SELECT ";
			search += "     rn, ";
			search += "     ub.no, ";
			search += "     ub.title, ";
			search += "     ub.hit, ";
			search += "     ub.name, ";
			search += "     ub.content, ";
			search += "     ub.reg_date, ";
			search += "     ub.user_no ";
			search += " FROM ";
			search += "     ( ";
			search += "         SELECT ";
			search += "             ROWNUM rn, ";
			search += "             b.no, ";
			search += "             b.title, ";
			search += "             b.hit, ";
			search += "             u.name, ";
			search += "             b.content, ";
			search += "             b.reg_date, ";
			search += "             b.user_no ";
			search += "         FROM ";
			search += "             users  u, ";
			search += "             board  b ";
			search += "         WHERE ";
			search += "             u.no = b.user_no ";
			search += "         ORDER BY ";
			search += "             b.reg_date ASC ";
			search += "     ) ub ";

			if (keyword == null || keyword.equals("")) {
				search += " ORDER BY ";
				search += "     rn DESC ";

			} else {
				search += " WHERE ";
				search += "     ( ub.name ";
				search += "       || ub.title ";
				search += "       || ub.content ) LIKE '%" + keyword + "%' ";
				search += " ORDER BY ";
				search += "     rn DESC ";

			}

			pstmt = conn.prepareStatement(search);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int rownum = rs.getInt("rn");
				int no = rs.getInt("no");
				String title = rs.getString("title");
				int hit = rs.getInt("hit");
				String name = rs.getString("name");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");

				BoardVo boardVo = new BoardVo(rownum, no, title, hit, name, content, reg_date, userNo);

				boardList.add(boardVo);
			}

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();

		return boardList;
	}

	// 조회수
	public void hit(int no) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String hit = "";
			hit += " UPDATE board ";
			hit += " SET ";
			hit += "     hit = hit + 1 ";
			hit += " WHERE ";
			hit += "     no = ? ";

			pstmt = conn.prepareStatement(hit);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

	// 게시물 수정
	public void update(BoardVo boardVo) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String update = "";
			update += " UPDATE board ";
			update += " SET ";
			update += "     title = ?, ";
			update += "     content = ?";
			update += " WHERE ";
			update += "     no = ? ";

			pstmt = conn.prepareStatement(update);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

	// 게시글 등록
	public void insert(BoardVo boardVo) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String insert = "";
			insert += " INSERT INTO board VALUES ( ";
			insert += "     seq_board_no.NEXTVAL, ";
			insert += "     ?, ";
			insert += "     ?, ";
			insert += "     0, ";
			insert += "     sysdate, ";
			insert += "     ? ";
			insert += " ) ";

			pstmt = conn.prepareStatement(insert);

			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUser_no());

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

	// 게시글 삭제
	public void delete(int no) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String delete = "";
			delete += " DELETE FROM board ";
			delete += " WHERE ";
			delete += "     no = ? ";

			pstmt = conn.prepareStatement(delete);

			pstmt.setInt(1, no);

			pstmt.executeUpdate();

			// 4.결과처리

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

}