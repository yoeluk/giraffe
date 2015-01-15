package com.zaporbit.giraffe

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import spray.can.Http
import spray.can.Http.Bind

object GiraffeFrontend extends App {
  
  class FrontendRestInterface extends RestInterface with RemoteOpenbabelApiCreator

  val config = ConfigFactory.load("frontend")
  val host   = config.getString("http.host")
  val port   = config.getInt("http.port")
  val system = ActorSystem("frontend", config)

  val restInterface = system.actorOf(
     props = Props[FrontendRestInterface],
     name  = "restInterface"
  )

  Http(system).manager ! Bind(
    listener  = restInterface,
    interface = host,
    port      = port
  )
}