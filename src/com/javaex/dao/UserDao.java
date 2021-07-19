package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
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

	// 생성자

	// 메소드 - GS

	// 메소드 - 일반

	// 유저 저장
	public int insert(UserVo userVo) {

		int count = -1;

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String insert = "";
			insert += " INSERT INTO users VALUES ( ";
			insert += "     seq_user_no.NEXTVAL, ";
			insert += "     ?, ";
			insert += "     ?, ";
			insert += "     ?, ";
			insert += "     ? ";
			insert += " ) ";

			pstmt = conn.prepareStatement(insert);

			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			count = pstmt.executeUpdate();

			// 4.결과처리

			System.out.println("[" + count + "건이 저장되었습니다.]");
			System.out.println("[" + userVo.getName() + "님이 회원가입 하셨습니다.]");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();

		return count;
	}

	// 유저 1명 정보 가져오기(로그인)
	public UserVo getUser(String id, String pass) {

		UserVo userVo = null;

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String selectOne = "";
			selectOne += " SELECT ";
			selectOne += "     no, ";
			selectOne += "     name ";
			selectOne += " FROM ";
			selectOne += "     users ";
			selectOne += " WHERE ";
			selectOne += "     id = ? ";
			selectOne += "     AND password = ? ";

			pstmt = conn.prepareStatement(selectOne);

			pstmt.setString(1, id);
			pstmt.setString(2, pass);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				userVo = new UserVo();

				// 생성자가 없는 경우 setter를 이용할 수 있다.
				userVo.setNo(no);
				userVo.setName(name);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();

		return userVo;
	}

	// 유저 1명 정보 가져오기(회원정보 수정)
	public UserVo getUser(int userNo) {

		UserVo userVo = null;

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String selectOne = "";
			selectOne += " SELECT ";
			selectOne += "     no, ";
			selectOne += "     name, ";
			selectOne += "     password, ";
			selectOne += "     gender ";
			selectOne += " FROM ";
			selectOne += "     users ";
			selectOne += " WHERE ";
			selectOne += "     no = ? ";

			pstmt = conn.prepareStatement(selectOne);

			pstmt.setInt(1, userNo);

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String gender = rs.getString("gender");

				userVo = new UserVo(no, name, password, gender);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();

		return userVo;
	}

	// 회원정보 수정
	public void update(UserVo userVo) {

		// 2번, 4번 메소드
		this.getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String update = "";
			update += " UPDATE users ";
			update += " SET ";
			update += "     password = ?, ";
			update += "     name = ?, ";
			update += "     gender = ? ";
			update += " WHERE ";
			update += "     no = ? ";

			pstmt = conn.prepareStatement(update);

			pstmt.setString(1, userVo.getPw());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());

			pstmt.executeUpdate();

			// 4.결과처리

			System.out.println("수정되었습니다.");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 5번 메소드
		this.close();
	}

}
