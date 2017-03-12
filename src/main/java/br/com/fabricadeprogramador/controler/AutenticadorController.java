package br.com.fabricadeprogramador.controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

@WebServlet("/autenticador.do")
public class AutenticadorController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession sessao = req.getSession(false);
		if (sessao != null) {
			//Removendo a sessao
			sessao.invalidate();
		}
		//Voltando para tela de login
		resp.sendRedirect("login.html");		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
				
		Usuario usu = new Usuario();
		usu.setLogin(login);
		usu.setSenha(senha);
		Usuario usuAutenticado = usuarioDAO.autenticar(usu);
		if (usuAutenticado!= null) {
//			Usuário existe
			
			HttpSession sessao = req.getSession();
			sessao.setAttribute("usuAutenticado",usuAutenticado);
			
			sessao.setMaxInactiveInterval(30);
			
			//Redirecionando para a lista de usuários após login correto
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/index.jsp");
			
			dispatcher.forward(req, resp);
			
		} else {
			resp.getWriter().print("<script> window.alert('Nao encontrado!'); location.href='login.html'</script>");
		}
		

	}
	
}
