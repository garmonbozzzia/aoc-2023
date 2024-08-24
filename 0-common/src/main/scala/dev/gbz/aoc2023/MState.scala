package dev.gbz
package aoc2023

import scala.reflect.ClassTag


case class MState[A](array: Array[Array[A]]) {

  val (m, n) = (array.size, array.head.size)

  def withIndices = Array.tabulate(m, n)((i, j) => (i, j, array(i)(j))).flatten

  def set(i: Int, j: Int, v: A) =  
    array(i)(j) = v

  def get(i: Int, j: Int) = getOpt(i,j).get

  def getOpt(i: Int, j: Int) = 
    array.lift(i).flatMap(_.lift(j))

  def last = array.last.last
}

object MState {

  def apply(arr: Array[String]) = 
    new MState(arr.map(_.toArray))

  def apply(s: String) = 
    new MState(s.split("\n").map(_.toArray))

  def apply[A: ClassTag](m: Int, n: Int, v: A) = 
    new MState(Array.fill[A](m, n)(v))

  def apply[A: ClassTag](m: Int, n: Int)(f: (Int, Int) => A) = 
    new MState(Array.tabulate(m, n)(f))

  def apply[A, B: ClassTag](other: MState[A])(f: A => B) = 
    new MState(Array.tabulate(other.m, other.n)((i, j) => f(other.array(i)(j))))
}
