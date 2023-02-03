package com.controllers

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, MediaTypes, StatusCodes}
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.domain.Router
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import java.io.{File, RandomAccessFile}



class VideoController(store: ActorRef[Nothing])(val system: ActorSystem[_]) extends FailFastCirceSupport with Router {
  val videoPath = "/Users/bertmeeuws/Movies/music.mp4"

  override val routes: Route = {
    path("video") {
      pathEnd {
        get {
          optionalHeaderValueByName("Range") {
            case None => complete(StatusCodes.NoContent);
            case Some(range) =>
              {
                val file = new File(videoPath)
                val fileSize = file.length()

                val s"""bytes=$rng-""" = range
                val start = rng.toInt

                respondWithHeaders(List(
                  RawHeader("Content-Range", s"bytes ${start}-${fileSize - 1}/${fileSize}"),
                  RawHeader("Accept-Ranges", s"bytes")
                )) {
                  complete {
                    val chunkSize = 1024 * 1000 * 4 // read 4MB of data = 4,096,000 Bytes
                    val raf = new RandomAccessFile(file, "r")
                    val dataArray = Array.ofDim[Byte](chunkSize)
                    raf.seek(start) // start readinng from `start` position
                    val bytesRead = raf.read(dataArray, 0, chunkSize)
                    val readChunk = dataArray.take(bytesRead)
                    HttpResponse(StatusCodes.PartialContent,
                      entity = HttpEntity(MediaTypes.`video/mp4`, readChunk))
                  }
                }
              }
          }
        }
    }
    }
  }
}