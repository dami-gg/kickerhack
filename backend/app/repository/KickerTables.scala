package repository

import model.{Color, KickerTable}
import slick.lifted.Tag
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current
import scala.concurrent.Future

class KickerTables(tag: Tag) extends Table[KickerTable](tag, Some("kicker"), "kickerTable") {
  implicit val colorColumnType = MappedColumnType.base[Color, String](
    { case Color(s) => s },
    { case s => Color(s) }
  )

//  implicit val dateTimeColumnType = MappedColumnType.base[DateTime, Long](
//    { case d => d.getMillis },
//    { case l => new DateTime(l)}
//  )

  def id = column[Long]("kt_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("kt_name")
  def building = column[String]("kt_building")
  def floor = column[String]("kt_floor")
  def colorHome = column[Color]("kt_color_home")
  def colorAway = column[Color]("kt_color_away")
  def lastGoalScored = column[Long]("kt_last_goal_scored")
  def password = column[String]("kt_password")

  override def * = (id.?, name.?, building, floor, colorHome, colorAway, lastGoalScored.?, password) <>
    ((KickerTable.apply _).tupled, KickerTable.unapply)
}

class KickerTableRepository {
  private val kickerTables = TableQuery[KickerTables]

  private def db = Database.forDataSource(DB.getDataSource())

  def createKickerTable(kickerTable: KickerTable): Future[Long] = {
    val insertAction = (kickerTables returning kickerTables.map(_.id)) += kickerTable
    try db.run(insertAction)
    finally db.close
  }

  def getAll(): Future[Seq[KickerTable]] = {
    try db.run(kickerTables.result)
    finally db.close
  }

  def updateLastGoal(id: Long) = {
    val q = for {c <- kickerTables if c.id === id} yield c.lastGoalScored
    db.run(q.update(System.currentTimeMillis()))
  }

  def findById(id: Long): Future[Option[KickerTable]] = {
    try {
      val query = kickerTables.filter(_.id === id)
      db.run(query.result.headOption)
    }
    finally db.close
  }

  def findByIdAndPassword(id: Long, password: String): Future[Option[KickerTable]] = {
    try db.run(kickerTables.filter(_.id === id).filter(_.password === password).result.headOption)
    finally db.close
  }

}
