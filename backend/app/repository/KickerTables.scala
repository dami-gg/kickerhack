package repository

import model.{Color, KickerTable, User}
import org.joda.time.DateTime
import slick.dbio.Effect.Read
import slick.lifted.Tag
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current
import slick.profile.FixedSqlStreamingAction
import scala.concurrent.Future

class KickerTables(tag: Tag) extends Table[KickerTable](tag, Some("kicker"), "kickerTable") {
  implicit val colorColumnType = MappedColumnType.base[Color, String](
    { c => c match { case Color(s) => s}},
    { s => Color(s) }
  )

  implicit val dateTimeColumnType = MappedColumnType.base[DateTime, Long](
    { d => d.getMillis() },
    { l => new DateTime(l)}
  )

  def id = column[Long]("kt_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("kt_name")
  def building = column[String]("kt_building")
  def floor = column[String]("kt_floor")
  def colorHome = column[Color]("kt_color_home")
  def colorAway = column[Color]("kt_color_away")
  def lastGoalScored = column[DateTime]("kt_last_goal_scored")

  override def * = (id.?, name.?, building, floor, colorHome, colorAway, lastGoalScored.?) <>
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

  def findById(id: Long): Future[Option[KickerTable]] = {
    try db.run(filterQuery(id).result.headOption)
    finally db.close
  }
  def updateLastGoal(id: Long) = {
    //val q = for { c <- kickerTables if c.id === id} yield c.lastGoalScored
    //val updateAction = q.update(None)
  }
  private def filterQuery(id: Long): Query[KickerTables, KickerTable, Seq] =
    kickerTables.filter(_.id === id)
}
