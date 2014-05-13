<%@page import="java.io.InputStream"%>
<%@page import="java.net.URL" %>
<%@page import="java.net.HttpURLConnection" %>
<%@page import="java.io.InputStreamReader" %>
<%@page import="java.io.BufferedReader" %>

<html>
<body>
	<h1> Site Browser</h1>
	<form action="/sitebrowser.jsp">
		Enter URL: <input type="text" name="url" />
		<input type="submit" value="Browse" />
	</form>
	
	<%  Object url = request.getParameter("url"); 
		if (url != null) {
	%>
		<hr/>
		<%
			URL link = new URL("http://www.example.com/comment");
		 	HttpURLConnection connection = (HttpURLConnection) link.openConnection();
		 	
		 	Object content = connection.getContent();
		 	InputStream iStream = (InputStream) content; 

			BufferedReader reader = new BufferedReader(new InputStreamReader(iStream));
			
			String output;
			while((output = reader.readLine()) != null){
		%>
		<%= output %>
	<%}} %>
</body>
</html>