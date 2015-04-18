package com.jgpacker

import spray.json._
import UrlShortenerServiceParameters._
import spray.httpx.SprayJsonSupport._ 

object UrlShortenerServiceParametersJsonProtocol extends DefaultJsonProtocol with spray.httpx.SprayJsonSupport {
  implicit val urlShortenerServiceParametersFormat = jsonFormat3(UrlShortenerServiceParameters)
}
