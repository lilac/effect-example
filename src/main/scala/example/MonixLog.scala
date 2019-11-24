package example

import cats.effect._
import cats.implicits._
import monix.eval._
import monix.execution.Scheduler.Implicits.global

object MonixLog extends TaskApp {

  /** App's main entry point. */
  def run(args: List[String]): Task[ExitCode] =
    args.headOption match {
      case Some(num) =>
        val t = runWithMonix(num.toInt)
        val task = time(t)
        task.as(ExitCode.Success)
      case None =>
        Task(System.err.println("Usage: MonixLog num")).as(ExitCode(2))
    }

  def runWithMonix(num: Int) = {
    val tasks = for (i <- 1 to num)
      yield
        Task {
          println(s"num $i")
        }
    Task.sequence(tasks) // .foreach(println)
  }

  def time[R](task: Task[R]): Task[R] = {
    val timer = Task(System.nanoTime())
    for {
      start <- timer
      r <- task
      end <- timer
      diff = end - start
      _ = println(s"time: $end - $start = $diff ns")
    } yield r
  }
}
