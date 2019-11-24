package example

import cats.effect._


object Log /* extends App */ {
  // run(args)
  def main(args: Array[String]): Unit = {
    run(args)
    ()
  }

  def run(args: Array[String]): ExitCode = {
    args.headOption match {
      case Some(num) =>
        time(log(num.toInt))
        ExitCode.Success
      case None =>
        System.err.println("Usage: Log num")
        ExitCode(2)
    }
  }

  def log(num: Int) = {
    for (i <- 1 to num) {
      println(s"num $i")
    }
  }

  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) + "ns")
    result
  }
}
