package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.dao.PostTableImpl
import com.example.service.PostService
import com.typesafe.config.ConfigFactory
import scalikejdbc.config.DBs

object Main {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("app")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val serverPort = ConfigFactory.load().getInt("serverPort")

    DBs.setup()

    val route = new Route(new PostService(PostTableImpl))

    Http().bindAndHandle(route.route, "0.0.0.0", serverPort)
  }
}
