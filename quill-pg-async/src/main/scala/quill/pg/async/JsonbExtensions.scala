package quill.pg.async

import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import quill.pg.PostgresTypeConverter

trait JsonbExtensions extends JsonExtensions { this: AsyncContext[PostgresDialect, _, _] =>

  def jsonbSet = quote { (json: JsValue, path: Traversable[String], newValue: JsValue, createMissing: Boolean) =>
    infix"jsonb_set($json,$path,$newValue,$createMissing)".as[JsValue]
  }

  override val isJsonB = true

  override implicit val jsonTypeConverter: PostgresTypeConverter[JsValue] =
    new PostgresTypeConverter[JsValue] {
      @inline override val apply: String = "jsonb"
    }
}
