package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest") // 이 주소가 있어야 연결이 된다.
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// post 방식 사용 시 한글 깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// 파라미터 action="값" 읽어오기.
		String action = request.getParameter("action");

		// GuestDao() 생성
		GuestbookDao guestbookDao = new GuestbookDao();

		// if문 시작
		if ("addList".equals(action)) {
			// guestList 불러오기
			List<GuestbookVo> guestList = guestbookDao.getGuestList();

			// request에 guestList 넣기
			request.setAttribute("gList", guestList);

			// Controller와 addList.jsp 포워드 하기
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("insert".equals(action)) {

			// 파라미터 값 꺼내기(name, password, content)
			String name = request.getParameter("name");
			String password = request.getParameter("pw");
			String content = request.getParameter("content");

			// 파라미터 값 Vo로 묶기
			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);

			// insert 메소드 사용
			guestbookDao.guestInsert(guestbookVo);

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");

		} else if ("dform".equals(action)) {

			// 입력된 파라미터 값 꺼내기(no)
			int no = Integer.parseInt(request.getParameter("no"));

			// request에 no 데이터 넣기
			request.setAttribute("no", no);

			// Controller와 deleteForm.jsp 포워드 하기
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {

			// 파라미터 값 꺼내기(no, password)
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");

			// delete 메소드 사용
			guestbookDao.guestDelete(no, password);

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}