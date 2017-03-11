package br.com.fabricadeprogramador;

import java.util.List;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

public class TestUsuarioDAO {

	public static void main(String[] args) {
		
		testeCadastrar();
		
		//testeAlterar();
		
		//testeExcluir();
		
//		testeSalvar();
		
		//testeBuscarPorId();
		
		//testeBuscarTodos();
		
//		testeAutenticarUsuario();
		
	}

	private static void testeBuscarTodos() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		List<Usuario> lista = usuarioDAO.buscarTodos();
		
		//repetição para cada item da lista
		for (Usuario usu : lista) {
			System.out.println(usu);
		}
		
	}

	private static void testeAutenticarUsuario() {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario user = new Usuario();
		user.setLogin("rboehnke");
		user.setSenha("123456");
		if (usuarioDAO.autenticarUsuario(user)) {
			System.out.println("Usuario autenticado com sucesso!");
			System.out.println(user);
		} else {
			System.out.println("Falha ao autenticar o usuario!");
		}
	}
	
	
	private static void testeBuscarPorId() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.buscarPorId(2);
		
		if (usuario!=null) {
			System.out.println("Registro localizado!");
			//Exibindo dados usando override de método toString
			System.out.println(usuario.toString());
			//System.out.println("Dados: " + usuario.getId() + ": " + usuario.getNome() + " - " + usuario.getLogin());
		}
		else {
			System.out.println("Falha na busca!");
		}
	}

	public static void testeCadastrar() {
		//Criando o usuário
				Usuario usu = new Usuario();
				
				//Criando objeto que transforma tipo Usuario em registro

				UsuarioDAO usuDAO = new UsuarioDAO();
				
//				Efetuando loop para cadastrar lista ficticia de usuarios			
				for (int i = 0; (i <= 20); i++) {
					usu = new Usuario();
					usu.setNome("Karen Graziele_" + i);
					usu.setLogin("karen" + i);
					usu.setSenha("123" + i);	

					//Cadastrando usuário no banco de dados
					usuDAO.cadastrar(usu);
				}
			
				System.out.println("Cadastrado com sucesso!");
				
	}
	
	public static void testeAlterar() {
		//Criando o usuário
			Usuario usu = new Usuario();
			usu.setId(3);
			usu.setNome("Jãozão da Silva");
			usu.setLogin("jaozss");
			usu.setSenha("1234567");		
			
			//Criando objeto que transforma tipo Usuario em registro
			//Cadastrando usuário no banco de dados
			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.alterar(usu);
		
			System.out.println("Alterado com sucesso!");
				
	}
	
	public static void testeExcluir() {
		//Criando o usuário
			Usuario usu = new Usuario();
			usu.setId(3);
			
			//Criando objeto que transforma tipo Usuario em registro
			//Cadastrando usuário no banco de dados
			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.excluir(usu);
		
			System.out.println("Excluído com sucesso!");
				
	}
	
	public static void testeSalvar() {
		//Criando o usuário
			Usuario usu = new Usuario();
			usu.setId(5);
			usu.setNome("Jãozão");
			usu.setLogin("jaozs2s");
			usu.setSenha("1236");

			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.salvar(usu);
		
			System.out.println("Salvo com sucesso!");
				
	}
}
