
package com.zaporbit.giraffe

object OpenbabelProtocol {
	import spray.json._

	case class MolIn(mol: String, inFormat: String, outFormat: String)

	case class MolOut(mol: String)

	case class InternalError(errorMsg: String)

	object MolIn extends DefaultJsonProtocol {
  	implicit val format = jsonFormat3(MolIn.apply)
  }

  object MolOut extends DefaultJsonProtocol {
  	implicit val format = jsonFormat1(MolOut.apply)
  }

  object InternalError extends DefaultJsonProtocol {
    implicit val format = jsonFormat1(InternalError.apply)
  }
  
}