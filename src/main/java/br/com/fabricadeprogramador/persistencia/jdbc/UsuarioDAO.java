package br.com.fabricadeprogramador.persistencia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;

public class UsuarioDAO {

	private Connection con = ConexaoFactory.getConnection();
	
	public void cadastrar(Usuario usu) {
		//String sql = "insert into usuario (nome, login, senha ) values ('" + usu.getNome() + "','" + usu.getLogin() + "','" + usu.getSenha() + "');";
		String sql = "insert into usuario (nome, login, senha ) values (?,?,md5(?));";
		
		//Essa sintaxe de try fecha automaticamente o PreparedStatement no final
		//Isso acontece pois o objeto PreparedStatement implementa a interface
		//AutoCloseable.
		//Dessa forma, é possível economizar código para fechar o objeto ao fim da execução		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			//Substituindo os pontos de interrogação pelos respectivos valores
			preparador.setString(1, usu.getNome());
			preparador.setString(2, usu.getLogin());
			preparador.setString(3, usu.getSenha());
			//Exeutando o comando SQL no banco de dados
			preparador.execute();			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
	}

	public void alterar(Usuario usu) {
		String sql = "update usuario set nome=?, login=?, senha=md5(?) where id=?;";
		
		//Essa sintaxe de try fecha automaticamente o PreparedStatement no final
		//Isso acontece pois o objeto PreparedStatement implementa a interface
		//AutoCloseable.
		//Dessa forma, é possível economizar código para fechar o objeto ao fim da execução		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			//Substituindo os pontos de interrogação pelos respectivos valores
			preparador.setString(1, usu.getNome());
			preparador.setString(2, usu.getLogin());
			preparador.setString(3, usu.getSenha());
			//preparador.setString(4, usu.getId().toString());
			preparador.setInt(4, usu.getId());
			//Exeutando o comando SQL no banco de dados
			preparador.execute();			
		} catch (SQLException e) {			
			e.printStackTrace();
		}		
	}

	public void excluir(Usuario usu) {
		String sql = "delete from usuario where id = ?;";				
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			//Substituindo os pontos de interrogação pelos respectivos valores
			preparador.setInt(1, usu.getId());
			//Exeutando o comando SQL no banco de dados
			preparador.execute();			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	public void salvar(Usuario usu) {
		if (usu.getId()!=null && usu.getId() != 0) {
			alterar(usu);
		} else {
			cadastrar(usu);
		}		
	}
	
	
	/**
	 * Autentica um login e senha do objeto de usuário do parâmetro
	 * @param usu
	 * @return
	 */
	public Usuario autenticar(Usuario usu) {
		String sql = "select * from usuario where login = ? and senha=md5(?)";
		ResultSet result;
		String senhaDigitada = usu.getSenha();
		
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			//Inserindo login do usuário no parâmetro
			statement.setString(1, usu.getLogin());
			statement.setString(2, senhaDigitada);
			
			//Obtendo lista de resultados da consulta no banco de dados
			result = statement.executeQuery();
			
			//Verdadeiro quando há próximo registro
			if (result.next()) { //Salvando informacoes no objeto Usuario			
				usu.setId(result.getInt("id"));
				usu.setNome(result.getString("nome"));
				return usu;
			}
		} catch (SQLException e) {
			e.printStackTrace();					
		}
//		Nenhum registro com o login informado foi localizado
		return null;
	}
	
	
	
	/**
	 * Autentica um login e senha do objeto de usuário do parâmetro
	 * @param usu
	 * @return
	 */
	public boolean autenticarUsuario(Usuario usu) {
		String sql = "select * from usuario where login = ?";
		ResultSet result;
		String senha;
		String senhaDigitada = usu.getSenha();
		
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			//Inserindo login do usuário no parâmetro
			statement.setString(1, usu.getLogin());
			
			result = statement.executeQuery();
			
			//Verdadeiro quando há próximo registro
			while (result.next()) {
				senha = result.getString("senha");
				//System.out.println(senha == senhaDigitada);
				usu.setId(result.getInt("id"));
				usu.setNome(result.getString("nome"));		
				
				return (senha.equals(senhaDigitada));
			}
			return false;			
					
		} catch (SQLException e) {
			e.printStackTrace();
			//Nenhum registro com o login informado foi localizado
			return false;
		}
	}
	
	/**
	 * Busca de todos registros no banco de dados	 
	 * @return Retorna uma lista vazia quando não encontra registros, ou um objeto Usuario caso contrário.
	 */
	public List<Usuario> buscarTodos() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario usu = new Usuario();
		
		String sql = "Select * from usuario";
		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			ResultSet result = preparador.executeQuery();
			
			//Posicionando o cursor no primeiro registro
			 //move o cursor para o próximo registro, ou a primeira linha
			while (result.next()) {
				usu = new Usuario();				
				usu.setId(result.getInt("id")); //índice ou texto da coluna
				usu.setNome(result.getString("nome"));
				usu.setLogin(result.getString("login"));
				usu.setSenha(result.getString("senha"));
				listaUsuarios.add(usu); //Adicionando usuário atual				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return listaUsuarios; //retornando lista de usuários		
	}	
	
	
	/**
	 * Busca de um registro no banco de dados conforme id do parâmetro
	 * @param id É um int que representa o número do id do usuário a ser buscado
	 * @return Retorna Null quando não encontra registro, ou um objeto Usuario caso contrário.
	 */
	public Usuario buscarPorId(Integer id) {
		Usuario usu = new Usuario();
		
		String sql = "Select * from usuario where id = ?";
		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			preparador.setInt(1, id); //parâmetro e valor
			
			ResultSet result = preparador.executeQuery();
			
			//Posicionando o cursor no primeiro registro
			 //move o cursor para o próximo registro, ou a primeira linha
			if (result.next()) {
				usu.setId(result.getInt("id")); //índice ou texto da coluna
				usu.setNome(result.getString("nome"));
				usu.setLogin(result.getString("login"));
				usu.setSenha(result.getString("senha"));
				
				return usu;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;		
	}	
}
