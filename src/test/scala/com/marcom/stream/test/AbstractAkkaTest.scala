package com.marcom.stream.test

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}


abstract class AbstractStreamTest extends TestKit(ActorSystem("MySpec")) with ImplicitSender
       with WordSpecLike with Matchers with BeforeAndAfterAll {

  override protected def afterAll(): Unit = TestKit.shutdownActorSystem(system)
}
