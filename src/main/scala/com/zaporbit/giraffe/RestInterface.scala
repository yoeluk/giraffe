package com.zaporbit.giraffe

import akka.actor._

import spray.routing._
import spray.http.StatusCodes
import spray.httpx.SprayJsonSupport._
import spray.routing.RequestContext
import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps

class RestInterface extends HttpServiceActor with RestApi {
  def receive = runRoute(routes)
}

trait RestApi extends HttpService with ActorLogging with OpenbabelApiCreator { actor: Actor =>
  import OpenbabelProtocol._
  import context._
  implicit val timeout = Timeout(10 seconds)
  import akka.pattern.ask
  import akka.pattern.pipe

  val openbabelApi = createOpenbabelApi

  def routes: Route =
    get {
      path(Segment) { smile => requestContext =>
        val request = MolIn(smile, "smi", "mol")
        val responder = createResponder(requestContext)
        openbabelApi.ask(request).pipeTo(responder)
      }
    } ~
    post {
      entity(as[MolIn]) { mol =>  requestContext =>
        val responder = createResponder(requestContext)
        openbabelApi.ask(mol).pipeTo(responder)
      }
    }

  def createResponder(requestContext: RequestContext) = {
    context.actorOf(Props(new Responder(requestContext, openbabelApi)))
  }
}

class Responder(requestContext: RequestContext, openbabelApi: ActorRef) extends Actor with ActorLogging {

  import OpenbabelProtocol._
  import spray.httpx.SprayJsonSupport._

  context.setReceiveTimeout(30 seconds)

  def receive = {
    case mol: MolOut =>
      requestContext.complete(StatusCodes.OK, mol)
      self ! PoisonPill
    case error: InternalError =>
      requestContext.complete(StatusCodes.InternalServerError, error)
      self ! PoisonPill
    case ReceiveTimeout =>
      context.setReceiveTimeout(Duration.Undefined)
      self ! PoisonPill
  }
}