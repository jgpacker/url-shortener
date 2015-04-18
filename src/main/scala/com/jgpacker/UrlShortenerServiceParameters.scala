package com.jgpacker

case class UrlShortenerServiceParameters(
                longUrl: String, 
                id:      Option[String] = None, 
                status:  Option[String] = None
)

