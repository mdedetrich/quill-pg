package quill.pg.async

import io.getquill.PostgresDialect
import io.getquill.context.async.AsyncContext
import quill.pg.PostgresTypeConverter

trait JsonExtensions extends io.getquill.context.async.Encoders { this: AsyncContext[PostgresDialect, _, _] =>

  type JsValue

  protected val isJsonB: Boolean = false

  protected implicit val jsonTypeConverter: PostgresTypeConverter[JsValue] =
    new PostgresTypeConverter[JsValue] {
      @inline override val apply: String = "json"
    }

  implicit def jsValueEncoder(implicit stringEncoder: Encoder[String]): Encoder[JsValue]

  implicit def jsValueDecoder(implicit stringDecoder: Decoder[String]): Decoder[JsValue]

  def ~> = quote { (json: JsValue, index: Int) =>
    infix"$json -> $index".as[JsValue]
  }

  def +> = quote { (json: JsValue, field: String) =>
    infix"$json -> $field".as[JsValue]
  }

  def ~>> = quote { (json: JsValue, index: Int) =>
    infix"$json ->> $index".as[String]
  }

  def #> = quote { (json: JsValue, fieldArray: Traversable[String]) =>
    infix"$json #> $fieldArray".as[JsValue]
  }

  def #>> = quote { (json: JsValue, fieldArray: Traversable[String]) =>
    infix"$json #>> $fieldArray".as[String]
  }

  def ++ = quote { (jsonLeft: JsValue, jsonRight: JsValue) =>
    infix"$jsonRight || $jsonRight".as[String]
  }

  def - = quote { (json: JsValue, index: Int) =>
    infix"$json - $index".as[JsValue]
  }

  def #- = quote { (json: JsValue, fields: Traversable[String]) =>
    infix"$json #- $fields".as[JsValue]
  }

  def arrayLength = quote { (json: JsValue) =>
    if (isJsonB)
      infix"jsonb_array_length($json)".as[Int]
    else
      infix"json_array_length($json)".as[Int]
  }

  def arrayElements = quote { (json: JsValue) =>
    if (isJsonB)
      infix"jsonb_array_elements($json)".as[Traversable[JsValue]]
    else
      infix"json_array_elements($json)".as[Traversable[JsValue]]
  }

  def objectKeys = quote { (json: JsValue) =>
    if (isJsonB)
      infix"jsonb_object_keys($json)".as[Traversable[String]]
    else
      infix"json_object_keys($json)".as[Traversable[String]]
  }

}
