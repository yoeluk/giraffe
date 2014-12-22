package com.zaporbit.giraffe

import com.typesafe.config.ConfigFactory
import akka.actor.{Props, ActorSystem}

object GiraffeBackend extends App {
  // load openbabel native library
  System.loadLibrary("openbabel_java")

  val config = ConfigFactory.load("backend")
  val system = ActorSystem("backend", config)
  system.actorOf(Props[OpenbabelApi], "openbabelApi")
}