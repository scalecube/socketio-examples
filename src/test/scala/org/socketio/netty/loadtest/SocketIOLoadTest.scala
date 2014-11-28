package org.socketio.netty.loadtest

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
      .check(regex("(.+?):").saveAs("sessionId"))
      )
      .exec(ws("Open WebSocket")
      .open(wsURL)
      )
      .pause(1 second)
      .repeat(24) {
      exec(ws("Send Heartbeat").sendText(heartbeatResponse))
        .repeat(20) {
        exec(ws("Send Hello Request")
          .sendText("3:1::Hello World!")
          .check(wsAwait.within(1 second).until(1).regex(".*Hello World.*"))
        )
          .pause(1 second)
      }
    }
      .pause(1 second)
      .exec(ws("Close WebSocket").close)
  }

  //setUp(userScenario.inject(atOnceUsers(1)).protocols(httpConf))
  //setUp(userScenario.inject(atOnceUsers(10)).protocols(httpConf))
  setUp(userScenario.inject(rampUsers(5000) over(3 minutes)).protocols(httpConf))




  //setUp(usersScn.inject(atOnceUsers(2000)).protocols(httpConf))
  //setUp(usersScn.inject(atOnceUsers(5000)).protocols(httpConf))
  //setUp(usersScn.inject(constantUsersPerSec(220) during(10 minutes)).protocols(httpConf))

  //setUp(usersScn.inject(
  //  rampUsers(1000) over(60 seconds))
  //).protocols(httpConf)

  //setUp(userScenario.inject(
  //  splitUsers(72000) into (atOnceUsers(40)) separatedBy (3000 milliseconds))
  //).protocols(httpConf)

}
