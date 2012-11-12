package com.twitter.scalding

import com.clojurewerkz.cascading.cassandra.CassandraTap;
import com.clojurewerkz.cascading.cassandra.CassandraScheme;
import cascading.pipe.Pipe
import cascading.pipe.assembly.Coerce
import cascading.scheme.Scheme
import cascading.tap.{Tap, SinkMode}
import cascading.tuple.Fields
import org.apache.hadoop.mapred.{ RecordReader, OutputCollector, JobConf }
import scala.collection.JavaConversions._
import scala.collection.mutable.WrappedArray
import com.twitter.scalding._

class CassandraSource(
  keyspaceName: String,
  columnFamilyName: String,
  keyName: String,
  columns: List[String]) extends Source {

  override val hdfsScheme = new CassandraScheme(
    "127.0.0.1",
    "9160",
    keyspaceName,
    columnFamilyName,
    keyName,
    columns
  ).asInstanceOf[Scheme[JobConf, RecordReader[_, _], OutputCollector[_, _], _, _]]

  override def createTap(readOrWrite: AccessMode)(implicit mode: Mode): Tap[_, _, _] = {
    val pCassandraScheme = hdfsScheme match {
      case scheme: CassandraScheme => scheme
      case _ => throw new ClassCastException("Failed casting from Scheme to CassandraScheme")
    }
    mode match {
      case hdfsMode @ Hdfs(_, _) => readOrWrite match {
        case Read => {
          new CassandraTap(pCassandraScheme)
        }
        case Write => {
          new CassandraTap(pCassandraScheme)
        }
      }
      case _ => super.createTap(readOrWrite)(mode)
    }
  }
}
