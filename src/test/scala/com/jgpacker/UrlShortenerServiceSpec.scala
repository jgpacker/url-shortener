package com.jgpacker

import org.specs2.mutable.Specification
import spray.testkit.Specs2RouteTest
import spray.http._
import StatusCodes._
import MediaTypes._
import com.jgpacker.UrlShortenerServiceParametersJsonProtocol._

class UrlShortenerServiceSpec extends Specification with Specs2RouteTest with UrlShortenerService {
  def actorRefFactory = system
  
  "UrlShortenerService" should {

    "when retrieving the long URL, returns a JSON response" in {
      Get("/url?shortUrl=http://chrdc.co/Hd7Sg") ~> shrtn ~> check {
        mediaType === `application/json` 
      }
    }

    "responds with JSON correctly and longUrl is the same as the one from the request" in {
      Post("/encurte/url", UrlShortenerServiceParameters(longUrl = "http://chaordic.com.br/")) ~> shrtn ~> check {
        mediaType === `application/json` 
        responseAs[UrlShortenerServiceParameters].longUrl must be equalTo("http://chaordic.com.br/")
      }
    }

    "leave GET requests to other paths unhandled" in {
      Get("/self-destruct") ~> shrtn ~> check {
        handled must beFalse
      }
    }

    "return a MethodNotAllowed error for POST requests when attempting to retrieve the long URL" in {
      Post("/url") ~> sealRoute(shrtn) ~> check {
        status === MethodNotAllowed
        responseAs[String] === "HTTP method not allowed, supported methods: GET"
      }
    }
  }
}
