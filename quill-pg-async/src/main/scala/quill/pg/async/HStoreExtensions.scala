package quill.pg.async

import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import quill.pg.{PostgresTypeConverter, PostgresTypeConverterDefaults}

trait HStoreExtensions extends io.getquill.context.async.Encoders with PostgresTypeConverterDefaults {
  this: AsyncContext[PostgresDialect, _, _] =>

  def +> = quote { (hStore: Map[String, String], key: String) =>
    infix"$hStore -> $key".as[String]
  }

  def >>[T](implicit postgresTypeConverter: PostgresTypeConverter[T]) = quote {
    (hStore: Map[String, String], key: String) =>
      infix"cast($hStore -> $key as ${postgresTypeConverter.apply})".as[T]
  }

  def exists = quote { (hStore: Map[String, String], key: String) =>
    infix"exist($hStore,$key)".as[Boolean]
  }

  def defined = quote { (hStore: Map[String, String], key: String) =>
    infix"defined($hStore,$key)".as[Boolean]
  }

  def ?& = quote { (hStore: Map[String, String], keys: Traversable[String]) =>
    infix"$hStore ?& $keys".as[Boolean]
  }

  @inline def containsAll = ?&

  def ?| = quote { (hStore: Map[String, String], key: String) =>
    infix"$hStore ?| $key".as[Boolean]
  }

  @inline def contains = ?|

  def @> = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft @> $hStoreRight".as[Boolean]
  }

  def <@ = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft <@ $hStoreRight".as[Boolean]
  }

  def ++ = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft || $hStoreRight".as[Boolean]
  }

  def @- = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft @- $hStoreRight".as[Map[String, String]]
  }

  def -- = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft -- $hStoreRight".as[Map[String, String]]
  }

  def -/ = quote { (hStoreLeft: Map[String, String], hStoreRight: Map[String, String]) =>
    infix"$hStoreLeft -/ $hStoreRight".as[Map[String, String]]
  }

}
