package quill.pg.async

import com.github.mauricio.async.db.RowData
import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import jawn.support.json4s.Parser
import org.json4s.native.JsonMethods._

import scala.util.{Failure, Success}

trait Json4sJsonbExtensions extends JsonbExtensions { this: AsyncContext[PostgresDialect, _, _] =>

  override type JsValue = org.json4s.JsonAST.JValue

  override def jsValueEncoder(implicit stringEncoder: Encoder[String]): Encoder[JsValue] = {
    new Encoder[JsValue] {
      override def apply(index: Int, value: JsValue, row: PrepareRow): PrepareRow = {

        val string = compact(render(value))
        stringEncoder.apply(index, string, row)
      }
    }
  }

  override def jsValueDecoder(implicit stringDecoder: Decoder[String]): Decoder[JsValue] = {
    new Decoder[JsValue] {
      override def apply(index: Int, row: RowData) = {
        val string = stringDecoder.apply(index, row)
        Parser.parseFromString(string) match {
          case Success(jsValue) => jsValue
          case Failure(e)       => throw e
        }
      }
    }
  }
}
