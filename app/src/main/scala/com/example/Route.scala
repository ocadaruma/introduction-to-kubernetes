package com.example

import akka.http.scaladsl.model.{StatusCodes, HttpEntity, ContentTypes}
import akka.http.scaladsl.server.{Directive1, Directives}
import com.example.model.Post
import com.example.service.{Health, HealthCheckService, PostService}

import scala.concurrent.Future

class Route(postService: PostService, healthCheckService: HealthCheckService) extends Directives {

  private[this] def fetchPosts: Directive1[Seq[Post]] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    onSuccess(Future(postService.all()))
  }

  private[this] def createPost(title: String, content: String): Directive1[Seq[Post]] = {
    import scala.concurrent.ExecutionContext.Implicits.global

    onSuccess {
      Future(postService.create(title, content)).map(_ => postService.all())
    }
  }

  private[this] def index(posts: Seq[Post]) = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, html.index.render(posts).body))
  }

  val route = pathSingleSlash {
    fetchPosts(index)
  } ~ path("post") {
    post {
      formFields('title, 'content) { (title, content) =>
        createPost(title, content)(index)
      }
    } ~ get {
      fetchPosts(index)
    }
  } ~ path("health") {
    healthCheckService.health() match {
      case Health.Healthy => complete("ok")
      case Health.Unhealthy => complete(StatusCodes.ServiceUnavailable)
    }
  }
}
