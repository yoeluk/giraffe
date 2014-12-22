package com.zaporbit.giraffe

import org.openbabel._

import akka.actor._
import concurrent.Future
import scala.concurrent.duration._
import akka.util.Timeout
import scala.language.postfixOps
import spray.http._
import HttpMethods._
import ContentTypes._
import scala.util.{Success, Failure}
import HttpCharsets._

import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext.Implicits.global

sealed trait Molecule {
  lazy val mol   = new OBMol
  lazy val conv  = new OBConversion
  lazy val gen2d = OBOp.FindType("gen2d")
  lazy val gen3d = OBOp.FindType("gen3d")
}

final case class GMol(molecule : String, 
                      inFormat : String, 
                      outFormat: String) extends Molecule {
  conv.SetInAndOutFormats(inFormat, outFormat)
  conv.ReadString(mol, molecule)
}

class OpenbabelApi extends Actor with ActorLogging {
  import OpenbabelProtocol._

  def receive = {

    case req: MolIn => 
      val captSender  = sender
      val gm = new GMol(req.mol, 
                        req.inFormat, 
                        req.outFormat)
      val mol = gm.mol
      val conv = gm.conv
      gm.gen2d.Do(mol)
      captSender ! MolOut( conv.WriteString(mol) )
  }
}