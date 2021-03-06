package atto
import Atto._

import cats.implicits._
import org.scalacheck._

@SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Any", "org.wartremover.warts.Equals"))
object NumericTest extends Properties("Numeric") {
  import Prop._

  property("international characters") = forAll { (c: Char) =>
    bigInt.parseOnly(c.toString) // may fail on JS but should never throw
    true
  }

  property("bigDecimal") = forAll { (b: BigInt) =>
    bigInt.parseOnly(b.toString).option === Some(b)
  }

  property("long") = forAll { (b: BigInt) =>
    long.parseOnly(b.toString).option == Some(b).filter { _ =>
      b >= Long.MinValue && b <= Long.MaxValue
    }
  }

  property("int") = forAll { (b: BigInt) =>
    int.parseOnly(b.toString).option == Some(b).filter { _ =>
      b >= Int.MinValue && b <= Int.MaxValue
    }
  }

  property("short") = forAll { (b: BigInt) =>
    short.parseOnly(b.toString).option == Some(b).filter { _ =>
      b >= Short.MinValue && b <= Short.MaxValue
    }
  }

  property("byte") = forAll { (b: BigInt) =>
    byte.parseOnly(b.toString).option == Some(b).filter { _ =>
      b >= Byte.MinValue && b <= Byte.MaxValue
    }
  }

  // property("bigDecimal") = forAll { (b: BigDecimal) =>
  //   bigDecimal.parseOnly(b.toString).option === Some(b)
  // }

  // property("double") = forAll { (b: BigDecimal) =>
  //   double.parseOnly(b.toString).option === Some(b.toDouble)
  // }

  // property("float") = forAll { (b: BigDecimal) =>
  //   float.parseOnly(b.toString).option === Some(b.toFloat)
  // }

  property("signum") = forAll { (s: String) =>
    !(s.startsWith("-") || s.startsWith("+")) ==> {
      (signum.parseOnly("+" + s) sameAs ParseResult.Done(s,  1)) &&
      (signum.parseOnly("-" + s) sameAs ParseResult.Done(s, -1)) &&
      (signum.parseOnly(s)       sameAs ParseResult.Done(s,  1))
    }
  }

}
