package example

import cats.Traverse
import cats.effect._
import cats.implicits._

class LogService[F[_]: Effect] {
  def run(num: Int) = {
    val tasks =
      for (i <- 1 to num)
        yield Sync[F].delay {
          println(s"num $i")
        }
    // or Traverse[List].sequence(tasks.toList)
    tasks.toStream.sequence
  }

  def time[R](task: F[R])(implicit clock : Clock[F]): F[R] = {
    val timer = clock.monotonic(scala.concurrent.duration.MILLISECONDS)
    for {
      start <- timer
      r <- task
      end <- timer
      diff = end - start
      _ = println(s"time: $end - $start = $diff ms")
    } yield r
  }
}
