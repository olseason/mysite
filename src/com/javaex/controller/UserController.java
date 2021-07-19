package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// post방식 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// 파라미터 action="값" 읽어오기.
		String action = request.getParameter("action");

		// UserDao 생성
		UserDao userDao = new UserDao();

		if ("joinForm".equals(action)) {
			/*************** joinForm ****************/
			System.out.println("[UserController.joinForm]");

			// joinForm.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			/*************** join ****************/
			System.out.println("[UserController.join");

			// 파라미터 값 꺼내기(id, password, name, gender)
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// Vo로 묶기
			UserVo userVo = new UserVo(id, pw, name, gender);

			// Dao.insert(Vo) 메소드 사용
			userDao.insert(userVo);

			// join.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) {
			/*************** loginForm ****************/
			System.out.println("[UserController.loginForm");

			// loginForm.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");

		} else if ("login".equals(action)) {
			/*************** login ****************/
			System.out.println("[UserController.login]");

			// 파라미터 값 꺼내기(id, password)
			String id = request.getParameter("id");
			String password = request.getParameter("pw");

			// Dao.getUser() 메소드 사용
			UserVo userVo = userDao.getUser(id, password);

			if (userVo != null) { // 로그인 성공일 때(id, pw 일치했을 때)

				// session 객체 생성
				HttpSession session = request.getSession();

				// userVo 데이터를 session에 저장
				session.setAttribute("authUser", userVo);

				// 리다이렉트 - 메인
				WebUtil.redirect(request, response, "/mysite/main");

			} else { // 로그인 실패일 때
				System.out.println("로그인 실패");

				// 리다이렉트 - 로그인폼
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}

		} else if ("logout".equals(action)) {
			/*************** logout ****************/
			System.out.println("[UserController.logout]");

			// session 객체 생성
			HttpSession session = request.getSession();

			// 세션에 있는 "authUser" 메모리 정보를 삭제
			session.removeAttribute("authUser");
			session.invalidate();

			// 리다이렉트 - 메인
			WebUtil.redirect(request, response, "/mysite/main");

		} else if ("modifyForm".equals(action)) {
			/*************** modifyForm ****************/
			System.out.println("[UserController.modifyForm]");

			// session 객체 생성
			HttpSession session = request.getSession();

			// authUser 데이터를 getAttribute() 통해서 가져오기(session)
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			if (authUser != null) { // 로그인 상태일 때 회원 수정

				// authUser.getNo()를 이용해 getUser() 메소드 사용(no에 맞는 회원정보를 가져오기 위함)
				UserVo userVo = userDao.getUser(authUser.getNo());

				// getUser() 메소드 데이터를 request에 저장
				request.setAttribute("userVo", userVo);

				// modifyForm.jsp 포워드
				WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");

			} else { // 비로그인 상태일 때 메인 페이지로 리다이렉트

				// 리다이렉트 - 메인
				WebUtil.redirect(request, response, "/mysite/main");

			}

		} else if ("update".equals(action)) {
			/*************** update ****************/
			System.out.println("[UserController.update]");

			// 파라미터 값 꺼내기(no, pw, name, gender)
			int no = Integer.parseInt(request.getParameter("no"));
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String gender = request.getParameter("gender");

			// Vo로 묶기
			UserVo userVo = new UserVo(no, name, pw, gender);

			// update() 메소드 사용
			userDao.update(userVo);

			// session 객체 생성
			HttpSession session = request.getSession();

			// 회원정보 수정 후 세션에 저장
			// session.setAttribute("authUser", userVo);  --> 덮어쓰기
			((UserVo)session.getAttribute("authUser")).setName(name); // --> 이름만 수정

			// 리다이렉트 - 메인
			WebUtil.redirect(request, response, "/mysite/main");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}