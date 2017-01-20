package com.example.dao

import com.example.model.Post
import org.joda.time.DateTime
import scalikejdbc._

class PostTable extends SQLSyntaxSupport[Post] {

  override val tableName = "post"

  val p = this.syntax("p")

  def apply(rs: WrappedResultSet): Post = autoConstruct(rs, p.resultName)

  def all(): Seq[Post] = {
    implicit val session = autoSession

    withSQL {
      selectFrom(this as p)
        .orderBy(p.postedAt.asc)
    }.map(this(_)).list().apply()
  }

  def insert(title: String, content: String): Post = {
    implicit val session = autoSession

    val now = DateTime.now()
    val id = applyUpdate {
      insertInto(this).namedValues(
        column.title -> title,
        column.content -> content,
        column.postedAt -> now)
    }

    Post(id, title, content, now)
  }
}

object PostTableImpl extends PostTable
