package com.models

import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


object FileManagerModels {
  case class FileItem(fileName: String, filePath: String, isFolder: Boolean)

  implicit val fileItemDecoder: Decoder[FileItem] = deriveDecoder
  implicit val fileItemEncoder: Encoder[FileItem] = deriveEncoder


}