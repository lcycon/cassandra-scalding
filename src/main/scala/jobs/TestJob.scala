package jobs

import com.twitter.scalding._
import scala.collection.Iterable
import scala.collection.immutable.List
import java.util.Iterator
import cascading.tuple.Fields
import com.twitter.scalding._
import com.twitter.scalding.Args

class WordCount(args: Args) extends Job(args) {
  val table = new CassandraSource("Test", "Test", "key", List("text"))
  table.read
  .flatMap('text -> 'word) { line: String => line.split("\\s+") }
  .groupBy('word) { _.size }
  .write(Tsv( "output_this" ))
}
