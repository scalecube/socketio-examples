# Socket.IO Examples

Examples and demo client for [ScaleCube Socket.IO](https://github.com/scalecube/socketio) Java server project.

To run examples clone and build this project:
  
``` bash
# Clone project
git clone git@github.com:scalecube/socketio-examples.git

# Switch to project root directory
cd socketio-examples/

# Build project
mvn clean install
```

All commands listed below suppose that you run them from project root directory and that project was built before. 

## Client

To launch demo client at [http://localhost:9001/client/index.html](http://localhost:9001/client/index.html) run 
following command:

``` bash
java -cp target/socketio-examples-1.0.jar io.scalecube.socketio.examples.client.ClientLauncher
``` 

To launch demo client with SSL/TLS (self-signed certificate) at [https://localhost:9002/client/index.html](https://localhost:9002/client/index.html) 
run following command:
 
``` bash
java -cp target/socketio-examples-1.0.jar io.scalecube.socketio.examples.client.SslClientLauncher
``` 

Also you can use demo client from [http://scalecube.io/socketio/](http://scalecube.io/socketio/).

## Server

To launch echo Socket.IO server on port `4810` run following command:
 
``` bash
java -cp target/socketio-examples-1.0.jar io.scalecube.socketio.examples.server.ServerLauncher

# or just
java -jar target/socketio-examples-1.0.jar
```

To launch echo Socket.IO server with logging to `server.log` file run following command:
 
``` bash
java -cp target/socketio-examples-1.0.jar -Dlog4j.configurationFile=log4j2-file.xml io.scalecube.socketio.examples.server.ServerLauncher 
```

To launch echo Socket.IO server without logs run following command:

``` bash
java -cp target/socketio-examples-1.0.jar -Dlog4j.configurationFile=log4j2-off.xml io.scalecube.socketio.examples.server.ServerLauncher 
```

To launch echo Socket.IO server with SSL/TLS (self-signed certificate) on port `4815` run following command: 

``` bash
java -cp target/socketio-examples-1.0.jar io.scalecube.socketio.examples.server.SslServerLauncher
```

**Note:** You will need to open in your browser [https://localhost:4815/socket.io/1/](https://localhost:4815/socket.io/1/) 
and accept certificate to allow demo client connect to the started server.

Examples how to configure and start Socket.IO server located at package 
[io.scalecube.socketio.examples.server](https://github.com/scalecube/socketio-examples/tree/master/src/main/java/io/scalecube/socketio/examples/server).
