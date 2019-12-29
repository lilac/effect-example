package example

import cats.effect._
import cats.implicits._
import monix.eval._

object MonixLog extends TaskApp {

  /** App's main entry point. */
  def run(args: List[String]): Task[ExitCode] =
    args.headOption match {
      case Some(num) =>
        val service = new LogService[Task]
        val t = service.run(num.toInt)
        val task = service.time(t)
        task.as(ExitCode.Success)
      case None =>
        Task(System.err.println("Usage: MonixLog num")).as(ExitCode(2))
    }

}
