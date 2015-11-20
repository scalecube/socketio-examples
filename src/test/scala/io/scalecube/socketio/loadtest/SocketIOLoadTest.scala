package io.scalecube.socketio.loadtest

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class SocketIOLoadTest extends Simulation {

  val port = Integer.getInteger("port", 4810)
  val host = System.getProperty("host", "127.0.0.1")

  // Here is the root for all relative URLs
  val httpConf = http.baseURL(s"http://$host:$port")

  val heartbeatResponse = "2:1::"
  val wsURL = s"ws://$host:$port/socket.io/1/websocket/" + "${sessionId}"

  // A scenario is a chain of requests and pauses
  val userScenario = {
    scenario("Socket.IO Load Test")
      .exec(http("Handshake")
      .get("/socket.io/1/").queryParam("t", System.currentTimeMillis())
      .check(regex("(.+?):").saveAs("sessionId")))
      .exec(ws("Open WebSocket")
      .open(wsURL))
      .pause(1 second)
      .during(3 minutes, "m") {
      exec(ws("Send Heartbeat")
        .sendText(heartbeatResponse))
        .during(20 seconds, "n") {
        exec(ws("Send Hello Request")
          .sendText("3:1::Hello Request Number ${m}-${n}")
          .check(wsAwait.within(900 milliseconds).until(1).regex(".*Hello Request Number ${m}-${n}.*")))
          .pause(1 second)
        }
      }
      .pause(1 second)
      .exec(ws("Close WebSocket").close)
    }

  //setUp(userScenario.inject(rampUsers(4500) over (9 minutes)).protocols(httpConf))
  setUp(userScenario.inject(rampUsers(21000) over (9 minutes)).protocols(httpConf))

}
