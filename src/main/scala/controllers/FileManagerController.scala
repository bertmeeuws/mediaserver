package controllers

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import java.io.File
import java.nio.file.FileSystems
import javax.swing.ImageIcon
import scala.{::, _}



class FileManagerController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport {
  var currentPath = "/Users/bertmeeuws/Movies"

  import com.models.FileManagerModels._

  val routes: Route = {
    path("filemanager") {
      pathEnd {
        val dir = new File(currentPath)

        val libraryFiles = dir.listFiles().toList.foldLeft[List[FileItem]](List())((acc: List[FileItem], file: File)  => {
          val fileItem = FileItem(fileName = file.getName, filePath = file.getPath, isFolder = file.isDirectory)

          fileItem :: acc
        })

        println(libraryFiles)

        complete(StatusCodes.OK)
      }
    }
  }
}
