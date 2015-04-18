package com.jgpacker

import akka.actor.Actor
import scala.concurrent.ExecutionContext.Implicits.global
import spray.routing._
import spray.http._

import MediaTypes._
import org.apache.commons.validator.routines.UrlValidator

import com.jgpacker.UrlShortenerServiceParametersJsonProtocol._
import com.jgpacker.UrlShortenerApplication._


class UrlShortenerServiceActor extends Actor with UrlShortenerService {
  def actorRefFactory = context

  def receive = runRoute(shrtn)
}


trait UrlShortenerService extends HttpService {
  val shortenedDomain = "http://chrdc.co/"
  val urlValidator = new UrlValidator(
                             UrlValidator.ALLOW_2_SLASHES +
                             UrlValidator.ALLOW_ALL_SCHEMES
                     )

  val shrtn =
    path("encurte" / "url") {
      post {
        respondWithMediaType(`application/json`) {
          entity(as[UrlShortenerServiceParameters]) { params =>
            validate(urlValidator.isValid(params.longUrl), "longUrl must be a valid URL") {
              val shortUrlCode = UrlShortenerApplication.shortenUrl(params.longUrl)
              complete {
                UrlShortenerServiceParameters(
                  id      = Some(shortenedDomain + shortUrlCode),
                  longUrl = params.longUrl
                )
              }
            }
          }
        }
      }
    } ~
    path("url") {
      get {
        respondWithMediaType(`application/json`) {
          parameter('shortUrl) { shortUrl =>
            validate(shortUrl.startsWith(shortenedDomain) && urlValidator.isValid(shortUrl), 
                     s"Shortened URL should be in the domain ${shortenedDomain} and be a valid URL.") {
              val shortUrlCode = shortUrl.substring(shortenedDomain.size);
              onSuccess(UrlShortenerApplication.getLongUrl(shortUrlCode)) { maybeLongUrl =>
                complete { 
                  maybeLongUrl match {
                    case Some(longUrl) =>
                      UrlShortenerServiceParameters(
                        id      = Some(shortUrl), 
                        longUrl = longUrl,
                        status  = Some("OK")
                      )
                    case None => s"""{ "id": "${shortUrl}", "status": "NOT FOUND" }""" // :-|
                  }
                }
              }
            }
          }
        }
      }
    }
}

