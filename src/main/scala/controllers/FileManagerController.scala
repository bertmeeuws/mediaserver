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
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.domain.Router
import com.domain.CORSHandler
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._

import java.net.URLEncoder





class FileManagerController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport with Router {
  private val mainPath = "/Users/bertmeeuws/Movies"


  import com.models.FileManagerModels._
  import com.auth.Utils._

  def loadLibrary(path: Option[String]): Either[String,List[FileItem]] = {

    val queriedPath = path.getOrElse(mainPath)

    val dir = new File(queriedPath)

    val doesPathExist = dir.exists()

    if(doesPathExist){
      val rawFiles = dir.listFiles()

      val libraryFiles = rawFiles.toList.foldLeft[List[FileItem]](List())((acc: List[FileItem], file: File) => {
        val fileItem = FileItem(fileName = file.getName, filePath = file.getPath, isFolder = file.isDirectory)

        fileItem :: acc
      })

      Right(libraryFiles)
    } else {
      Left("The file path does not exist")
    }
  }

  override val routes: Route = cors() {
    pathPrefix("filemanager") {
      authenticateBasic(realm = "secure site", authenticator) { userName =>
      pathEnd {
        val libraryFiles = loadLibrary(None)

        libraryFiles match {
          case Left(a) => complete(StatusCodes.BadRequest, a)
          case Right(b) => complete(StatusCodes.OK, b)
        }

        } ~ path(Remaining) { path => {
        val libraryFiles = loadLibrary(Some(path))


        libraryFiles match {
          case Left(a) => complete(StatusCodes.BadRequest, a)
          case Right(b) => complete(StatusCodes.OK, b)
        }
      }}
      }
    }
  }
}
