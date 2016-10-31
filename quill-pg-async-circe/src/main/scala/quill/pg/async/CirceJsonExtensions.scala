package quill.pg.async

import com.github.mauricio.async.db.RowData
import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import quill.pg.async.JsonExtensions

trait CirceJsonExtensions extends JsonExtensions { this: AsyncContext[PostgresDialect, _, _] =>

  override type JsValue = io.circe.Json

  override def jsValueEncoder(implicit stringEncoder: Encoder[String]): Encoder[JsValue] = {
    new Encoder[JsValue] {
      override def apply(index: Int, value: JsValue, row: PrepareRow): PrepareRow = {

        val string = value.noSpaces
        stringEncoder.apply(index, string, row)
      }
    }
  }

  override def jsValueDecoder(implicit stringDecoder: Decoder[String]): Decoder[JsValue] = {
    new Decoder[JsValue] {
      def apply(index: Int, row: RowData) = {
        val string = stringDecoder.apply(index, row)
        io.circe.jawn.parse(string).fold(error => throw error, jsValue => jsValue)
      }
    }
  }

}
