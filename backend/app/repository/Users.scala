package repository

import model.{User}
import play.api.db.DB

import slick.driver.PostgresDriver.api._
import slick.lifted.Tag

import play.api.Play.current

import scala.concurrent.Future

class Users(tag: Tag) extends Table[User](tag, Some("kicker"), "user") {
  def id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  override def * = (id.?, name) <> ((User.apply _).tupled, User.unapply)
}

class UserRepository {
  private val users = TableQuery[Users]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  def insert(user: User): Future[Int] =
    try db.run(users += user)
    finally db.close

  def list(): Future[Seq[User]] = {
    try {
      db.run(users.result)
    } finally { db.close() }
  }
}













