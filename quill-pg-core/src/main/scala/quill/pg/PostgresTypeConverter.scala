package quill.pg

import java.util.UUID

abstract class PostgresTypeConverter[T]() {
  val apply: String
}

trait PostgresTypeConverterDefaults {

  implicit val stringPostgresTypeConverter =
    new PostgresTypeConverter[String]() {
      @inline override val apply = "text"
    }

  implicit val intPostgresTypeConverter = new PostgresTypeConverter[Int]() {
    @inline override val apply = "int"
  }

  implicit val longPostgresTypeConverter = new PostgresTypeConverter[Long]() {
    @inline override val apply: String = "bigint"
  }

  implicit val uuidPostgresTypeConverter = new PostgresTypeConverter[UUID]() {
    @inline override val apply: String = "uuid"
  }

  implicit val booleanPostgresTypeConverter =
    new PostgresTypeConverter[Boolean]() {
      @inline override val apply: String = "boolean"
    }

  implicit val characterPostgresTypeConverter =
    new PostgresTypeConverter[Char] {
      @inline override val apply: String = "character"
    }

  implicit val arrayTypeConverter = new PostgresTypeConverter[Traversable[_]] {
    @inline override val apply: String = "array"
  }

}

object PostgresTypeConverterDefaults extends PostgresTypeConverterDefaults
