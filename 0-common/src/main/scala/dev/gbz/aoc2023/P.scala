package dev.gbz
package aoc2023

import TraceOps._

case class P(i: Int, j: Int) {
  def u = P(i - 1, j)
  def d = P(i + 1, j)
  def l = P(i, j - 1)
  def r = P(i, j + 1)

  val pair = (i, j)

  def to(dir: Char) = dir match {
    case 'v' => d
    case '^' => u
    case '>' => r
    case '<' => l
  }
}

object P extends IShow[P] {
  implicit def ordering(O: Ordering[(Int, Int)]): Ordering[P] = (x, y) => O.compare(x.pair, y.pair)
  
  implicit val setShow: Show[Set[P]] = set => {
    val is = set.map(_.i)
    val js = set.map(_.j)
    val (i0, i1) = (is.min, is.max).trace
    val (j0, j1) = (js.min, js.max).trace
    Seq.tabulate(i1 - i0 + 1, j1 - j0 + 1) { (i, j) => 
      if(set.contains(P(i0 + i, j0 + j))) '#' else ' '
    }.map(_.mkString).mkString("\n")
  }

  object PointNeighborhood {
    val ul = P(-1, -1)
    val ur = P(-1,  1)
    val dl = P( 1, -1)
    val dr = P( 1,  1)
    val c  = P( 0,  0)
    val u  = P(-1,  0)
    val d  = P( 1,  0)
    val l  = P( 0, -1)
    val r  = P( 0,  1)
  }

  implicit class SetPointOps(set: Set[P]) {
    import PointNeighborhood._

    private def add: (P, P) => P = {
      case (P(a, b), P(c, d)) => P(a + c, b + d)
    }

    def square = {
      if(set.isEmpty) Set.empty
      else {
        val is = set.map(_.i)
        val js = set.map(_.j)
        val (i0, i1) = (is.min, is.max).trace
        val (j0, j1) = (js.min, js.max).trace
        
      }
    }

    def expanded5 = set.flatMap { 
      p => Set(u, d, l, r, c).map(add(p, _)) 
    }

    def expanded4 = set.flatMap { 
      p => Set(u, d, l, r).map(add(p, _)) 
    }

    def expanded9 = set.flatMap { 
      p => Set(u, d, l, r, c, ul, ur, dl, dr).map(add(p, _)) 
    }
  }

}

