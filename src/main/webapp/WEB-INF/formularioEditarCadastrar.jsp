<%@page import="br.com.fabricadeprogramador.persistencia.entidade.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar ou alterar usuário</title>
</head>
<body>
	<%
	//Obtendo objeto usuario do request
	Usuario usu = (Usuario) request.getAttribute("usu");		
	%>
	
	<h1>Login:<%=usu.getLogin()%></h1>

	<form action="usucontroler.do" method="post">
		ID: <input type="text" name="id" value="<%=usu.getId()%>" />
		Nome: <input type="text" name="nome" value="<%=usu.getNome()%>"/>
		Login: <input type="text" name="login" value="<%=usu.getLogin()%>"/>
		Senha: <input type="text" name="senha" value="<%=usu.getSenha()%>"/>		
		<input type="submit" value="Salvar"  />
		
	</form>
</body>
</html>