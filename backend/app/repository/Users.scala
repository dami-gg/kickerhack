package repository

import model.{User}
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current
import scala.concurrent.Future

class Users(tag: Tag) extends Table[User](tag, Some("kicker"), "user") {
  def id = column[Long]("u_id", O.PrimaryKey)
  def name = column[String]("u_name")

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

  def findById(id: Long): Future[User] = {
    try db.run(filterQuery(id).result.head)
    finally db.close
  }

  private def filterQuery(id: Long): Query[Users, User, Seq] =
    users.filter(_.id === id)
}













