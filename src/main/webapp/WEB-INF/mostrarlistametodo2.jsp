<%@page import="br.com.fabricadeprogramador.persistencia.entidade.Usuario"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Minha tabela de resultado</title>

<script type="text/javascript">
function confirmaExclusao (id) {
	if (window.confirm('Tem certeza que deseja excluir?')) {
		location.href="usucontroler.do?acao=exc&id=" + id;
	}
}
</script>

</head>
<body>

<%@include file="menu.jsp" %>

<%
List<Usuario> lista = (List<Usuario>) request.getAttribute("lista"); //Lista obtida e convertida  
%>
Minha Lista: <br>
<table border=2 bordercolor="#ADADFD">
<tr><th>ID</th><th>Nome</th><th>Usuario</th><th>Acoes</th></tr>

<%for (Usuario usu : lista) {  %>		
	<tr>
	<td align="left" ><%out.print(usu.getId().toString());%></td>
	<td align="right"><%=usu.getNome()%></td>
	<td align="right" background="#D8A8E0"><%=usu.getLogin()%></td>
	<td align="right"><a href="javascript:confirmaExclusao(<%=usu.getId().toString()%>)">Excluir</a> | <a href="usucontroler.do?acao=alt&id=<%=usu.getId().toString()%>">Editar</a></td>		
	</tr>
<%} %>

</table>
</body>
</html>