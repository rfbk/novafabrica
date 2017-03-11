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
		String sql = "insert into usuario (nome, login, senha ) values (?,?,?);";
		
		//Essa sintaxe de try fecha automaticamente o PreparedStatement no final
		//Isso acontece pois o objeto PreparedStatement implementa a interface
		//AutoCloseable.
		//Dessa forma, � poss�vel economizar c�digo para fechar o objeto ao fim da execu��o		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			//Substituindo os pontos de interroga��o pelos respectivos valores
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
		String sql = "update usuario set nome=?, login=?, senha=? where id=?;";
		
		//Essa sintaxe de try fecha automaticamente o PreparedStatement no final
		//Isso acontece pois o objeto PreparedStatement implementa a interface
		//AutoCloseable.
		//Dessa forma, � poss�vel economizar c�digo para fechar o objeto ao fim da execu��o		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			//Substituindo os pontos de interroga��o pelos respectivos valores
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
			//Substituindo os pontos de interroga��o pelos respectivos valores
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
	 * Autentica um login e senha do objeto de usu�rio do par�metro
	 * @param usu
	 * @return
	 */
	public boolean autenticarUsuario(Usuario usu) {
		String sql = "select * from usuario where login = ?";
		ResultSet result;
		String senha;
		String senhaDigitada = usu.getSenha();
		
		try (PreparedStatement statement = con.prepareStatement(sql)) {
			//Inserindo login do usu�rio no par�metro
			statement.setString(1, usu.getLogin());
			
			result = statement.executeQuery();
			
			//Verdadeiro quando h� pr�ximo registro
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
	 * @return Retorna uma lista vazia quando n�o encontra registros, ou um objeto Usuario caso contr�rio.
	 */
	public List<Usuario> buscarTodos() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		Usuario usu = new Usuario();
		
		String sql = "Select * from usuario";
		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			ResultSet result = preparador.executeQuery();
			
			//Posicionando o cursor no primeiro registro
			 //move o cursor para o pr�ximo registro, ou a primeira linha
			while (result.next()) {
				usu = new Usuario();				
				usu.setId(result.getInt("id")); //�ndice ou texto da coluna
				usu.setNome(result.getString("nome"));
				usu.setLogin(result.getString("login"));
				usu.setSenha(result.getString("senha"));
				listaUsuarios.add(usu); //Adicionando usu�rio atual				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return listaUsuarios; //retornando lista de usu�rios		
	}	
	
	
	/**
	 * Busca de um registro no banco de dados conforme id do par�metro
	 * @param id � um int que representa o n�mero do id do usu�rio a ser buscado
	 * @return Retorna Null quando n�o encontra registro, ou um objeto Usuario caso contr�rio.
	 */
	public Usuario buscarPorId(Integer id) {
		Usuario usu = new Usuario();
		
		String sql = "Select * from usuario where id = ?";
		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			preparador.setInt(1, id); //par�metro e valor
			
			ResultSet result = preparador.executeQuery();
			
			//Posicionando o cursor no primeiro registro
			 //move o cursor para o pr�ximo registro, ou a primeira linha
			if (result.next()) {
				usu.setId(result.getInt("id")); //�ndice ou texto da coluna
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
