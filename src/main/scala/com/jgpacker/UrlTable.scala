package com.jgpacker;

import scala.concurrent.Future

import com.datastax.driver.core.Row
import com.websudos.phantom.Implicits._
import com.jgpacker.SimpleCassandraConnector._

case class UrlModel (
  id:      String,
  longUrl: String
)

sealed class UrlTable extends CassandraTable[UrlTable, UrlModel] {
  object id      extends StringColumn(this) with PartitionKey[String]
  object longUrl extends StringColumn(this)

  override def fromRow(row: Row): UrlModel = {
    UrlModel(id(row), longUrl(row));
  }
}

object UrlTable extends UrlTable with SimpleCassandraConnector {
    def getLongUrlByShortUrlCode(shortUrlCode: String): Future[Option[String]] = {
      select(_.longUrl).where(_.id eqs shortUrlCode).one()
    }

   def insertUrl(shortUrlCode: String, longUrl: String) = {
      insert.value(_.id, shortUrlCode).value(_.longUrl, longUrl).future()
   }
}
