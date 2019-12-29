package example

import cats.effect._
import cats.implicits._

object IOLog extends IOApp {

  /** App's main entry point. */
  def run(args: List[String]): IO[ExitCode] =
    args.headOption match {
      case Some(num) =>
        val service = new LogService[IO]
        val t = service.run(num.toInt)
        val task = service.time(t)
        task.as(ExitCode.Success)
      case None =>
        IO(System.err.println("Usage: MonixLog num")).as(ExitCode(2))
    }

}
