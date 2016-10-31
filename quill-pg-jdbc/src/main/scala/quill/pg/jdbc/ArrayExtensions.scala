package quill.pg.jdbc

import io.getquill.{JdbcContext, PostgresDialect}

trait ArrayExtensions extends io.getquill.context.jdbc.JdbcEncoders with io.getquill.context.jdbc.JdbcDecoders {
  this: JdbcContext[PostgresDialect, _] =>

  def any = quote { (traversable: Traversable[_]) =>
    infix"any($traversable)".as[Boolean]
  }

  def all = quote { (traversable: Traversable[_]) =>
    infix"all($traversable)".as[Boolean]
  }

  def >@ = quote { (traversable: Traversable[_]) =>
    infix"@>($traversable)".as[Boolean]
  }

  def <@ = quote { (traversable: Traversable[_]) =>
    infix"<@($traversable)".as[Boolean]
  }

  @inline def length = <@

  implicit def dbTraversableEncoder[T](implicit baseJDBCTypeEncoder: JdbcEncoder[T],
                                       mappedEncoding: MappedEncoding[T, AnyRef]): Encoder[Traversable[T]] =
    encoder[Traversable[T]](row =>
                              (idx, traversable) => {
                                val array: Array[AnyRef] = Array.ofDim(traversable.size)
                                var i                    = 0
                                val iterator             = traversable.toIterator
                                while (iterator.hasNext) {
                                  val x = iterator.next()
                                  array.update(i, mappedEncoding.f(x))
                                  i += 1
                                }

                                val sqlType =
                                  java.sql.JDBCType.valueOf(baseJDBCTypeEncoder.sqlType).getName
                                val sqlArray =
                                  withConnection(conn => conn.createArrayOf(sqlType, array))
                                row.setArray(idx, sqlArray)
                            },
                            java.sql.Types.ARRAY)

  implicit def dbTraversableDecoder[T](baseJDBCTypeDecoder: Decoder[T],
                                       mappedDecoding: MappedEncoding[AnyRef, T]): Decoder[Traversable[T]] =
    decoder[Traversable[T]] { row => index =>
      {
        val s = row.getArray(index)
        s.getArray.asInstanceOf[Array[T]].to[Traversable]
      }
    }
}
