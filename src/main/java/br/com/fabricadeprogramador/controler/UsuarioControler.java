package br.com.fabricadeprogramador.controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.security.ssl.Debug;
import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

//http://localhost:8080/fabricaweb/usucontroler.do
@WebServlet("/usucontroler.do")
public class UsuarioControler extends HttpServlet {

	public UsuarioControler() {
		System.out.println("Novo Servlet!");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		System.out.println("Chamou o get");
		String acao = req.getParameter("acao");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		//Verificando se o req possui uma sessao, se nao, retorna à pagina de login
		if (req.getSession(false)== null) {
			resp.getWriter().print("<script>window.alert('Sessao inexistente ou expirada! Por favor, insira novamente seus dados.'); location.href='login.html';</script>");
			
		}
		
		if (acao.equals("exc")) {
			String id = req.getParameter("id");
			Usuario usu = new Usuario();
			if (id != null) {
				usu.setId(Integer.parseInt(id));
			}
			usuarioDAO.excluir(usu);
			
//			resp.getWriter().println("Excluído com sucesso!");
			
			resp.sendRedirect("usucontroler.do?acao=lis");
			
			
		} else if (acao.equals("lis")) {
//			implementar a lista
			
			List<Usuario> lista = usuarioDAO.buscarTodos();
			
//			Exibindo lista de usuários no html
//			for (Usuario usuario : lista) {
//				resp.getWriter().print(usuario.getLogin() + "<br>");
//			}
			
//			resp.getWriter().print(lista);
			
//			Preenchendo request para envio posterior ao JSP
			
			req.setAttribute("lista", lista);
			
//			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/listausu.jsp");
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/mostrarlistametodo2.jsp");
			dispatcher.forward(req,resp);
			
		} else if (acao.equals("alt")) {
//			Pegando atributo Id do request
			int id = 1;
			try {
				id = Integer.parseInt(req.getParameter("id"));				
			} catch (Exception e){
				System.out.println("Nem todos atributos foram informados: " + e.getMessage());
			}
			
			Usuario usu = usuarioDAO.buscarPorId(id);
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/formularioEditarCadastrar.jsp");
//			Passando objeto tipo Usuario para formulário JSP
			req.setAttribute("usu", usu);			
			dispatcher.forward(req, resp);
			
		}  else if (acao.equals("cad")) {
			//Criando objeto de usuário novo
			Usuario usu = new Usuario();
			usu.setId(0);
			usu.setNome("");
			usu.setLogin("");
			usu.setSenha("");
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/formularioEditarCadastrar.jsp");
//			Passando objeto tipo Usuario para formulário JSP
			req.setAttribute("usu", usu);			
			dispatcher.forward(req, resp);
		}		
		
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("init");
		super.init();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("destroy!!!");
		super.destroy();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		System.out.println("Chamou o post");
		
		String login = req.getParameter("login");
		String nome = req.getParameter("nome");
		String senha = req.getParameter("senha");
		String id = req.getParameter("id");
		
		Usuario usu = new Usuario();
		
		usu.setId(Integer.parseInt(id));
		usu.setLogin(login);
		usu.setNome(nome);
		usu.setSenha(senha);
		
		UsuarioDAO usuDAO = new UsuarioDAO();
		
		usuDAO.salvar(usu);
		
		System.out.println("Sucesso post");
				
//		resp.getWriter().print("Cadastrado com sucesso!");
		
//		Redireciona o browser para a lista atualizada
		resp.sendRedirect("usucontroler.do?acao=lis");
		
		//super.doPost(req, resp);
	}
}
