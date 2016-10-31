package quill.pg.async

import com.github.mauricio.async.db.RowData
import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import jawn.support.spray.Parser

import scala.util.{Failure, Success}

trait SprayJsonbExtensions extends JsonbExtensions { this: AsyncContext[PostgresDialect, _, _] =>

  override type JsValue = spray.json.JsValue

  override def jsValueEncoder(implicit stringEncoder: Encoder[String]): Encoder[JsValue] = {
    new Encoder[JsValue] {
      override def apply(index: Int, value: JsValue, row: PrepareRow): PrepareRow = {

        val string = value.compactPrint
        stringEncoder.apply(index, string, row)
      }
    }
  }

  override def jsValueDecoder(implicit stringDecoder: Decoder[String]): Decoder[JsValue] = {
    new Decoder[JsValue] {
      def apply(index: Int, row: RowData) = {
        val string = stringDecoder.apply(index, row)
        Parser.parseFromString(string) match {
          case Success(jsValue) => jsValue
          case Failure(e)       => throw e
        }
      }
    }
  }

}
