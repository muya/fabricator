package fabricator

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test
import sun.misc.BASE64Decoder

class MobileTestSuite extends TestNGSuite with LazyLogging {

  val fabr = new Fabricator
  val mobile = fabr.mobile()

  @Test
  def testAndroidId() = {
    val androidId = mobile.androidGsmId()
    logger.info("Testing random android ID : "+ androidId)
    val expectedString = "APA910123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
    assert(androidId.length == 183)
    for (symbol <- androidId) assert(expectedString.contains(symbol))
  }

  @Test
  def testAppleId() = {
    val appleId = mobile.applePushToken()
    logger.info("Testing random apple ID : "+ appleId)
    val expectedString = "abcdef1234567890"
    assert(appleId.length == 64)
    for (symbol <- appleId) assert(expectedString.contains(symbol))
  }

  @Test
  def testWindows8d() = {
    val windows8Id = mobile.wp8_anid2()
    logger.info("Testing random windows 8 ID : "+ windows8Id)
    val expectedString = "0123456789abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
    val decodedString = new BASE64Decoder().decodeBuffer(windows8Id)
    for (symbol <- decodedString) assert(expectedString.contains(symbol))
  }

  @Test
  def testWindows7Id() = {
    val windows7Id = mobile.wp7_anid()
    logger.info("Testing random windows 7 ID : "+ windows7Id)
    assert(windows7Id.matches("A=\\w(.+?)&E=\\w{3}&W=\\d{1}"))
  }
}
