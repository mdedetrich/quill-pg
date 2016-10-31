package quill.pg.async

import com.github.mauricio.async.db.RowData
import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import quill.pg.PostgresTypeConverter

trait ArrayExtensions extends io.getquill.context.async.Encoders { this: AsyncContext[PostgresDialect, _, _] =>

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

  implicit def dbTraversableEncoder[T]: Encoder[Traversable[T]] =
    encoder[Traversable[T]] { (value: Traversable[T]) =>
      value.map(_.asInstanceOf[Any]).toSeq
    }

  implicit def dbTraversableDecoder[T]: Decoder[Traversable[T]] =
    new Decoder[Traversable[T]] {
      def apply(index: Int, row: RowData) = {
        row(index) match {
          case value: IndexedSeq[Any] =>
            value.map(_.asInstanceOf[T])
        }
      }
    }

}
