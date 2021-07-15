package vom.javaex.controller;

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



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("[UserController]");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기(업무구분)
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("joinForm".equals(action)) { //회원가입 폼
			System.out.println("[UserController.joinForm]");
			
			//회원가입폼 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinForm.jsp");
			
		}else if("join".equals(action)) { //회원가입
			System.out.println("[UserController.join]");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//System.out.println(id+","+pw+","+name+","+gender);
			
			//vo로 묶기
			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);
			
			//dao.insert(vo) --> db저장
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		}else if("loginForm".equals(action)) {
			System.out.println("[UserController.loginForm]");
			
			//로그인 폼 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
			
		}else if("login".equals(action)) {
			System.out.println("[UserController.login]");
			
			String id = request.getParameter("id");
			String password = request.getParameter("pw");
			
			
			//dao 회원정보 조회하기(세션 저장용)
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, password);
			
			if(userVo == null) {
				System.out.println("로그인성공");
				//성공일 때 세션에 저장 (id, pw일치했을 때)
				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo);
				
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite/main");
				
			}else {
				System.out.println("로그인실패");
				
				//리다이렉트-로그인용폼 페이지로 다시 돌아가기 
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			
			
			
			
		}else if("logout".equals(action)) {
			System.out.println("[UserController.logout]");
			
			//세션에 있는 "authUser"정보삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
		}
		

		
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
