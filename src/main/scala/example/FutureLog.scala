
package example

import cats.effect._
import cats.implicits._
import monix.eval._
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.Future


object FutureLog extends App {
  run(args)
  /** App's main entry point. */
  def run(args: Array[String]): Future[ExitCode] =
    args.headOption match {
      case Some(num) =>
        // val t = runWithFuture(num.toInt)
        val task = time(runWithFuture(num.toInt))
        task.as(ExitCode.Success)
      case None =>
        Future(System.err.println("Usage: MonixLog num")).as(ExitCode(2))
    }

  def runWithFuture(num: Int) = {
    val tasks = for (i <- 1 to num)
      yield
        Future {
          println(s"num $i")
        }
    Future.sequence(tasks) // .foreach(println)
  }

  def time[R](task: => Future[R]): Future[R] = {
    // val timer = Future(System.nanoTime())
    val start = System.nanoTime()
    for {
      r <- task
      end = System.nanoTime()
      diff = end - start
      _ = println(s"time: $end - $start = $diff ns")
    } yield r
  }
}

