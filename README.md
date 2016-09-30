# Socket.IO Examples

Examples for [Socket.IO Java Server](https://github.com/scalecube/socketio) project.

In order to start demo client clone this project and execute following commands in the root directory:

``` bash
# Build project
mvn clean install

# Run demo client
java -jar target/socketio-examples-1.0.jar
```

Then open in browser link [http://localhost:9001/client/index.html](http://localhost:9001/client/index.html)

To start client with SSL (self-signed certificate) run following command:
 
``` bash
java -cp target/socketio-examples-1.0.jar io.scalecube.socketio.examples.client.ClientOverSslLauncher
```

And open in browser link [https://localhost:9002/client/index.html](https://localhost:9002/client/index.html)

Examples how to configure and start Socket.IO server located at package 
[io.scalecube.socketio.examples.server](https://github.com/scalecube/socketio-examples/tree/master/src/main/java/io/scalecube/socketio/examples/server)
