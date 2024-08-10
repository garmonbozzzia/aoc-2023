package dev.gbz.aoc2023

sealed trait Error 

object Error {
  final case class BadTestData() extends Error
  final case class FileNotFound(filename: String) extends Error
}