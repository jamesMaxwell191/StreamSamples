package com.marcom.stream.samples

import java.nio.file.Paths

import akka.stream.{ActorMaterializer, IOResult}
import akka.stream.scaladsl.{FileIO, Flow, Keep, RunnableGraph, Sink, Source}
import akka.util.ByteString
import com.marcom.stream.test.AbstractStreamTest

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by douglasm on 02/01/2017.
  */
class FirstSampleTest extends AbstractStreamTest{

  implicit val mat = ActorMaterializer()

  "a number stream" should {
    "print some values" in {
       Source(1 to 10).map(_+10).filter(_<15).runWith(Sink.foreach(println))
    }
    "produce a file" in {
      val factorials = Source(1 to 10).scan(BigInt(1))((acc, next) => acc * next)
      val result: Future[IOResult] =
        factorials
          .map(num => ByteString(s"$num\n"))
          .runWith(FileIO.toPath(Paths.get("factorials.txt")))
      Await.result(result,5 seconds)
    }
    "print some ints" in {
       val g:RunnableGraph[Future[Int]] = Source(1 to 10).via(Flow[Int].map(_*3)).toMat(Sink.fold[Int,Int](0){_+_})(Keep.right)
       val fut = g.run()
       val r = Await.result(fut,5 seconds)
       r should be(165)
    }
    "print some ints from an iterator" in {
      val source = Source.fromIterator(() => Iterator from 1)
      val g:RunnableGraph[Future[Int]] = source.take(10).via(Flow[Int].map(_*3)).toMat(Sink.fold[Int,Int](0){_+_})(Keep.right)
      val fut = g.run()
      val r = Await.result(fut,5 seconds)
      r should be(165)
    }
  }

}
