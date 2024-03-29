package models

import play.api.db.DB
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import controllers.routes

/**
 * Created with IntelliJ IDEA.
 * User: TIS301728
 * Date: 13/07/21
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 */
case class Task(id: Long, label: String)

object Task {
  def all():List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
        DB.withConnection { implicit c =>
          SQL("delete from task where id={id}").on(
            'id -> id
          ).executeUpdate()
        }
  }

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id~label => Task(id, label)
    }
  }

}
