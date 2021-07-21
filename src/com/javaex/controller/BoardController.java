package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// post방식 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// 파라미터 action="값" 읽어오기.
		String action = request.getParameter("action");

		// UserDao 생성
		BoardDao boardDao = new BoardDao();

		// session 객체 생성
		HttpSession session = request.getSession();

		// authUser 데이터를 getAttribute() 통해서 가져오기(session)
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if ("list".equals(action)) {

			List<BoardVo> boardList;

			// 파라미터 값 가져요기(keyword)
			String keyword = request.getParameter("keyword");

			if (keyword != null) {
				// search() 메소드 사용
				boardList = boardDao.boardList(keyword);

			} else {
				// bList 불러오기
				boardList = boardDao.boardList();
			}

			// request에 boardList 넣기
			request.setAttribute("boardList", boardList);

			// list.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("read".equals(action)) {

			// 입력된 파라미터 값 꺼내기(no)
			int no = Integer.parseInt(request.getParameter("no"));

			// readBoard() 메소드 Vo에 넣기
			BoardVo readBoard = boardDao.readBoard(no);

			// Vo 전송
			request.setAttribute("readBoard", readBoard);

			// 조회수
			boardDao.hit(no);

			// read.jsp 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("modifyForm".equals(action)) {

			// 입력된 파라미터 값 꺼내기(no)
			int no = Integer.parseInt(request.getParameter("no"));

			// readBoard() 메소드 Vo에 넣기
			BoardVo readBoard = boardDao.readBoard(no);

			if (authUser == null) {

				// 리다이렉트 - 게시판
				WebUtil.redirect(request, response, "/mysite/board?action=list");

			} else if (authUser.getNo() == readBoard.getUser_no()) {

				// Vo 전송
				request.setAttribute("readBoard", readBoard);

				// modifyForm.jsp 포워드
				WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

			} else {

				// 리다이렉트 - 게시판
				WebUtil.redirect(request, response, "/mysite/board?action=list");
			}

		} else if ("modify".equals(action)) {
			// 입력된 파라미터 값 꺼내기(no, title, content)
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// Vo로 묶기
			BoardVo boardVo = new BoardVo(no, title, content);

			// update() 메소드 사용
			boardDao.update(boardVo);

			// 리다이렉트 - 게시판
			WebUtil.redirect(request, response, "/mysite/board?action=list");

		} else if ("writeForm".equals(action)) {

			if (authUser != null) {

				// writeForm.jsp 포워드
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

			} else {

				// 리다이렉트 - 게시판
				WebUtil.redirect(request, response, "/mysite/board?action=list");
			}

		} else if ("write".equals(action)) {

			// 파라미터 값 가져오기(user_no, title, content)
			int user_no = authUser.getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			// Vo로 묶기
			BoardVo boardVo = new BoardVo(title, content, user_no);

			// insert() 메소드 사용
			boardDao.insert(boardVo);

			// 리다이렉트 - 게시판
			WebUtil.redirect(request, response, "/mysite/board?action=list");

		} else if ("delete".equals(action)) {

			// 파라미터 값 가져오기(no)
			int no = Integer.parseInt(request.getParameter("no"));

			// delete() 메소드 사용
			boardDao.delete(no);

			// 리다이렉트 - 게시판
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}