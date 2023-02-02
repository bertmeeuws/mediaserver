package com.models

object FileManagerModels {
  case class FileItem(fileName: String, filePath: String, isFolder: Boolean)
}