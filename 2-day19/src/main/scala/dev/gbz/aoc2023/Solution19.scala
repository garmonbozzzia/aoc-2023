package dev.gbz
package aoc2023
package day19

import TraceOps._
import zio._
import dev.gbz.aoc2023.Helpers.green
import dev.gbz.aoc2023.Show.defaultShow

trait Rule
case class Goto(key: String) extends Rule
case class G(c: Char, v: Int, key: String) extends Rule 
case class L(c: Char, v: Int, key: String) extends Rule

case class Workflow(rules: Seq[Rule]) {

  val show: Show[Workflow] = defaultShow

  def apply(rr: RatingRange): Set[RatingRange] = 
    rules.foldLeft(Option(rr), Set.empty[RatingRange]) { 
      case ((Some(input), output), rule) =>
        val outOpt = input.out(rule)
        val reminder = input.reminder(rule)
        (reminder, outOpt.map(output + _).getOrElse(output))
      case ((None, output), _) => (None, output)
    }._2
}

object Workflow {
  def apply(s: String) = {
    new Workflow(s.split(",").toSeq.map(ss => Rule(ss)))
  }
}
case class WorkflowMap(map: Map[String, Workflow])

object WorkflowMap {
  def apply(s: String) = {
    val map = 
      s.split("\n").map {
        case s"$k{$w}" => (k, Workflow(w))
      }.toMap
      
    new WorkflowMap(map)
  }
}

object Rule {
  def apply(s: String): Rule = s match {
    case s"$c>$v:$k" => G(c.head, v.toInt, k)
    case s"$c<$v:$k" => L(c.head, v.toInt, k)
    case key => Goto(key)
  }

  implicit val show: Show[Rule] = _ match {
    case G(c, v, key) => s"$c>$v:$key"
    case L(c, v, key) => s"$c<$v:$key"
    case Goto(key) => key
  }
}

object RatingRange {
  implicit val show: Show[RatingRange] = defaultShow
}

case class RatingRange(key: String, ranges: Map[Char, (Int, Int)]) {

  def sum = ranges.values.map { case (from, to) => (to - from + 1).toLong }.product

  def ff(c: Char, k: String, fx: (Int, Int) => (Boolean, (Int, Int))) = {
    val (from, to) = ranges(c)
    val (p, interval)   = fx(from, to)//.trace
    if(p) Some(RatingRange(k, ranges.updated(c, interval))) 
    else None
  }

  def out(rule: Rule): Option[RatingRange] = rule match {
    case Goto("R")  => None
    case Goto(k)    => Some(RatingRange(k, ranges))
    case G(c, v, k) => 
      ff(c, k, (from, to) => (v < to, (from.max(v + 1), to)))
    case L(c, v, k) => 
      ff(c, k, (from, to) => (from < v, (from, to.min(v - 1))))
  }

  def reminder(rule: Rule): Option[RatingRange] = rule match {
    case Goto(_)   => None 
    case G(c, v, _) => 
      ff(c, key, (from, to) => (from <= v, (from, to.min(v))))
    case L(c, v, _) => 
      ff(c, key, (from, to) => (v <= to, (v.max(from), to)))
  }
}

case class SolvePart2(input: String) {
  val Array(a, b) = input.split("\n\n")

  val workflowMap = WorkflowMap(a)//.trace
  workflowMap.map.keySet.trace.mkString

  workflowMap.map("in").rules.mkString("\n").trace

  val init = RatingRange("in", "xmas".map(_ -> (1, 4000)).toMap)

  workflowMap.map("in").apply(init).trace


  def xmas = {
    // val init = (Set(init), 0)
    Helpers.green("xmas").trace
    Helpers.eval(Set(init), 0L) { case (ranges, res) =>
      if(ranges.isEmpty) Right(res)
      else {
        val sum = ranges.foldLeft(0L) { 
          case (sum, RatingRange("A", ranges)) => 
            sum + ranges.values.map { case (from, to) => (to - from + 1).toLong }.ttrace("accepted").product
          case (sum, _) => sum
        }
        sum.ttrace("Accepted")
        // set.map(x => )
        val updated = ranges.filterNot(r => r.key == "A" || r.key == "R")
          .flatMap { r => 
            val workflow = workflowMap.map(r.key)
            green("workflow").trace
            r.ttrace("in")
            workflow.rules.foreach(_.ttrace("rule"))//.mkString("\n").trace
            val out = workflow.apply(r)
            green("out").trace
            out.foreach(_.ttrace("out")) //mkString("\n").trace
            out
          }
        // updated.mkString("\n").trace
        // "".pause
        Left (updated, (res + sum))
      }
    }
  }
// 134167097000000 != 
// 167409079868000
  val result = xmas

}

case class SolutionLive(day: Int) extends Solution {
  def solve(input: String) = ZIO.succeed(SolvePart2(input).result.toString)

  def test(input: String): IO[Error,Map[String,String]] = 
    for {
      result <- solve(input)
    } yield Map("result" -> result)
}

object SolutionLayer {
  def live = ZLayer.succeed(SolutionLive(19))
}
