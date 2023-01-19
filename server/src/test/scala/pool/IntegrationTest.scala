package pool

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.sys.process.Process

class IntegrationTest extends AnyFunSuite with Matchers:
  val exitCode = Process("psql -d poolbalance -f ddl.sql").run().exitValue()
