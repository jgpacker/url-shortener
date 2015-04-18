package com.jgpacker

import scala.concurrent. { blocking, Future }
import com.datastax.driver.core.{ Cluster, Session }
import com.websudos.phantom.Implicits._

//class based on https://github.com/websudos/phantom/blob/master/phantom-example/src/main/scala/com/newzly/phantom/example/basics/DBConnector.scala

object SimpleCassandraConnector {
  val keySpace = "url"
  lazy val cluster = Cluster.builder()
                            .addContactPoint("localhost")
                            .withPort(9042)
                            .withoutJMXReporting()
                            .withoutMetrics()
                            .build()

  lazy val session = blocking {
      cluster.connect(keySpace)
  }
}

trait SimpleCassandraConnector { 
  self: CassandraTable[_, _] =>

  def createTable(): Future[Unit] = {
    create.future() map (_ => ())
  }

  implicit lazy val datastax: Session = SimpleCassandraConnector.session
}
