<%@page import="br.com.fabricadeprogramador.persistencia.entidade.Usuario"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Minha tabela de resultado</title>
</head>
<body>

<table border=2 bordercolor='#ADADFD'>
<%
List<Usuario> lista = (List<Usuario>) request.getAttribute("lista"); //Lista obtida e convertida  

//Iniciando repeticao para obter html da tabela que será exibida
String htmlT = "";

//Criando header
htmlT = "<tr><th>ID</th><th>Nome</th><th>Usuario</th><th>Acao</th></tr>";

for (Usuario usu : lista) {
	//Repetindo cada linha	
	htmlT = htmlT + "<tr>";	
	htmlT = htmlT + "<td>" + usu.getId().toString() + "</td>";
	htmlT = htmlT + "<td>" + usu.getNome() + "</td>";
	htmlT = htmlT + "<td background='#E8E8E0'>" + usu.getLogin() + "</td>";
	htmlT = htmlT + "<td>" + "Excluir" + "</td>";
	htmlT = htmlT + "</tr>";
}

out.print(htmlT);

%>
</table>
</body>
</html>