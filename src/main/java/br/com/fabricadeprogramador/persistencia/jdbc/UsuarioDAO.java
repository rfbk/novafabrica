package br.com.fabricadeprogramador.persistencia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;

public class UsuarioDAO {

	private Connection con = ConexaoFactory.getConnection();
	
	public void cadastrar(Usuario usu) {
		//String sql = "insert into usuario (nome, login, senha ) values ('" + usu.getNome() + "','" + usu.getLogin() + "','" + usu.getSenha() + "');";
		String sql = "insert into usuario (nome, login, senha ) values (?,?,?);";
		
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
		String sql = "update usuario set nome=?, login=?, senha=? where id=?;";
		
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

}
