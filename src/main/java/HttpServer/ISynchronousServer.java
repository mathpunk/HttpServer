package HttpServer;

/*
Performs basic socket IO. User of the class can create an instance of the class,
then call `nextConnection` to get a connection, closing the current connection and setting
a class variable to the new connection.

From there they can call `readLine()` to get the next line from the socket and
`writeLine(myString)` to write the variable `myString` to the connection.*/

public interface ISynchronousServer {
    public void nextConnection();
    public String readLine();
    public void writeLine(String line);
}
