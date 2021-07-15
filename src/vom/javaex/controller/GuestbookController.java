package vom.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;


@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("[GuestbookController]");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("[addList]");
			
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.getGuestList();
			
			request.setAttribute("gList", guestList);
			
			WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");
			
		}else if("add".equals(action)) {
			System.out.println("[add]");
			
			GuestDao guestDao = new GuestDao();
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestVo guestVo = new GuestVo(name, password, content);
			
			guestDao.guestInsert(guestVo);
			
			WebUtil.forward(request, response, "./gbc?action=addList");
			
		}else if("delete".equals(action)) {
			System.out.println("[delete]");
			
			GuestDao guestDao = new GuestDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			WebUtil.forward(request, response, "./gbc?action=addList");
			int count = guestDao.guestDelete(no, password);
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
