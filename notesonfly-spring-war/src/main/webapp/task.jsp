<html>
	<body>
		<h1>Task Manager</h1> <br/><br/>
		<form action="/task" method="post">
			Enter Task Name: <input type="text" name="name" value="sampleTask"/> <br/>
			Enter Queue Name: <input type="text" name="queueName" /> <br/>
			Enter Worker Link: <input type="text" name="endpoint" value="/"/> <br/>
			Enter Parameter Name: <input type="text" name="parameterName" /> <br/>
			Enter Parameter Value: <input type="text" name="parameterValue" /> <br/>
			<input type="submit" value="Add Task">
		</form>
	</body>
</html>