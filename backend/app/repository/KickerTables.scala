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

  def id = column[Long]("kt_id", O.PrimaryKey)
  def name = column[String]("kt_name")
  def building = column[String]("kt_building")
  def floor = column[String]("kt_floor")
  def colorHome = column[Color]("kt_color_home")
  def colorAway = column[Color]("kt_color_away")
  def lastGoalScored = column[DateTime]("kt_last_goal_scored")

  override def * = (id.?, name.?, building, floor, colorHome, colorAway, lastGoalScored.?) <>
    ((KickerTable.apply _).tupled, KickerTable.unapply)
}

trait KickerTableComponent {
  val kickerTableRepository: KickerTableRepository
  trait KickerTableRepository {
    def createKickerTable(kickerTable: KickerTable): Future[Int]
    def getAll(): Future[Seq[KickerTable]]
    def findById(id: Long): Future[Option[KickerTable]]
  }
}

trait KickerTableComponentImpl extends KickerTableComponent {
  override val kickerTableRepository = new KickerTableRepositoryImpl

  class KickerTableRepositoryImpl extends KickerTableRepository{
    private val kickerTables = TableQuery[KickerTables]

    private def db = Database.forDataSource(DB.getDataSource())

    override def createKickerTable(kickerTable: KickerTable): Future[Int] = {
      val insertAction = kickerTables += kickerTable
      try db.run(insertAction)
      finally db.close
    }

    override def getAll(): Future[Seq[KickerTable]] = {
      try db.run(kickerTables.result)
      finally db.close
    }

    override def findById(id: Long): Future[Option[KickerTable]] = {
      try {
        val query = kickerTables.filter(_.id === id)
        db.run(query.result.headOption)
      }
      finally db.close
    }


  }

}
