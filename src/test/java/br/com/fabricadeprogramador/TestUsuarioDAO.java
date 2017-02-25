package br.com.fabricadeprogramador;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

public class TestUsuarioDAO {

	public static void main(String[] args) {
		
		//testeCadastrar();
		
		//testeAlterar();
		
		testeExcluir();
		
	}

	public static void testeCadastrar() {
		//Criando o usu�rio
				Usuario usu = new Usuario();
				usu.setNome("Richard");
				usu.setLogin("richard");
				usu.setSenha("123");		
				
				//Criando objeto que transforma tipo Usuario em registro
				//Cadastrando usu�rio no banco de dados
				UsuarioDAO usuDAO = new UsuarioDAO();
				usuDAO.cadastrar(usu);
			
				System.out.println("Cadastrado com sucesso!");
				
	}
	
	public static void testeAlterar() {
		//Criando o usu�rio
			Usuario usu = new Usuario();
			usu.setId(3);
			usu.setNome("J�oz�o da Silva");
			usu.setLogin("jaozss");
			usu.setSenha("1234567");		
			
			//Criando objeto que transforma tipo Usuario em registro
			//Cadastrando usu�rio no banco de dados
			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.alterar(usu);
		
			System.out.println("Alterado com sucesso!");
				
	}
	
	public static void testeExcluir() {
		//Criando o usu�rio
			Usuario usu = new Usuario();
			usu.setId(3);
			
			//Criando objeto que transforma tipo Usuario em registro
			//Cadastrando usu�rio no banco de dados
			UsuarioDAO usuDAO = new UsuarioDAO();
			usuDAO.excluir(usu);
		
			System.out.println("Exclu�do com sucesso!");
				
	}
	
}
