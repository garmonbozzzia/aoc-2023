package dev.gbz.aoc2023

case class D private (v: Char)

object D extends IShow[D] {
  val Seq(u,d,l,r) = "^v<>".map(D(_))
  val all = Set(u,d,l,r)
  def others(dir: D) = all - dir
  val opposite = Map(u -> d, l -> r, d -> u, r -> l)
  def side(dir: D) = all - dir - opposite(dir)
  override implicit val show: Show[D] = _.v.toString
}

// object DirOps {
//   def opp = Map(
//     'v' -> '^',
//     ''
//   )
// }