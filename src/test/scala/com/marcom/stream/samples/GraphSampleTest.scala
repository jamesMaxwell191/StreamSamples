package com.marcom.stream.samples

import akka.stream.{ActorMaterializer, ClosedShape}
import akka.stream.scaladsl.{Broadcast, Flow, GraphDSL, RunnableGraph, Sink, Source}
import com.marcom.stream.test.AbstractStreamTest

/**
  * Created by douglasm on 02/01/2017.
  */
class GraphSampleTest extends AbstractStreamTest {

  implicit val mat = ActorMaterializer()

  "a test" should {
    "do the business" in {
       val g = createBroadcast
       g.run()
    }
  }

  def createBroadcast ={
    val source = Source(1 to 10)
    val writeVal = Sink.foreach[Int](println)
    val g = RunnableGraph.fromGraph(GraphDSL.create(){ implicit b =>
      import GraphDSL.Implicits._
      val bcast = b.add(Broadcast[Int](2))
      source ~> bcast.in
      bcast.out(0) ~> writeVal
      bcast.out(1) ~> Flow[Int].map(_*3) ~> Sink.foreach(println)
      ClosedShape
    })
    g
  }

}
