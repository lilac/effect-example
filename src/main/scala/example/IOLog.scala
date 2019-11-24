package example

import cats.effect._
import cats.implicits._
// import monix.eval._
// import monix.execution.Scheduler.Implicits.global
// import cats.Traverse

object IOLog extends IOApp {

  /** App's main entry point. */
  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(num) =>
        val t = runWithIO(num.toInt)
        val task = time(t)
        task.as(ExitCode.Success)
      case None =>
        IO(System.err.println("Usage: MonixLog num")).as(ExitCode(2))
    }

  def runWithIO(num: Int) = {
    val tasks = for (i <- 1 to num)
      yield
        IO {
          println(s"num $i")
        }
    tasks.toStream.sequence // .foreach(println)
  }

  def time[R](task: IO[R]): IO[R] = {
    val timer = IO(System.nanoTime())
    for {
      start <- timer
      r <- task
      end <- timer
      diff = end - start
      _ = println(s"time: $end - $start = $diff ns")
    } yield r
  }
}
