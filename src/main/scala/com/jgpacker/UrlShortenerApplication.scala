package com.jgpacker

import scala.concurrent.Future

import com.twitter.hashing.KeyHasher
import com.jgpacker.UrlTable._

object UrlShortenerApplication {
   val hasher = KeyHasher.FNV1_32

   def shortenUrl(longUrl: String): String = {
       val hashNumber = Math.abs(hasher.hashKey(longUrl.getBytes))
       val shortUrlCode = java.lang.Long.toString(hashNumber, 36)
       UrlTable.insertUrl(shortUrlCode, longUrl)
       return shortUrlCode
   }

   def getLongUrl(shortUrlCode: String): Future[Option[String]] = {
       UrlTable.getLongUrlByShortUrlCode(shortUrlCode)
   }

}
