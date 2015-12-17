package repository

import java.sql.Timestamp

import model.{Game}
import org.joda.time.DateTime
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current

import scala.concurrent.Future

class Games(tag: Tag) extends Table[Game](tag, Some("kicker"), "game") {

  implicit def dateTime =
    MappedColumnType.base[DateTime, Timestamp](
      dt => new Timestamp(dt.getMillis),
      ts => new DateTime(ts.getTime)
    )

  val id = column[Long]("g_id", O.AutoInc, O.PrimaryKey)
  val table = column[Long]("g_table_id")
  val goalsHome = column[Int]("g_goals_home")
  val goalsAway = column[Int]("g_goals_away")
  val startedOn = column[DateTime]("g_started_on")
  val finishedOn = column[DateTime]("g_finished_on")

  lazy val kickertableFk = foreignKey("game_g_table_id_fkey", table, kickertable)(_.id)
  lazy val kickertable = TableQuery[KickerTables]

  override def * = (id.?, table, goalsHome, goalsAway, startedOn, finishedOn.?) <> (Game.tupled, Game.unapply)
}

class GamesRepository {
  private val games = TableQuery[Games]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  def insert(game: Game): Future[Int] =
    try db.run(games += game)
    finally db.close

  def list(): Future[Seq[Game]] =
    try db.run(games.result)
    finally db.close

  def findById(id: Long): Future[Game] =
    try db.run(filterQuery(id).result.head)
    finally db.close()

  private def filterQuery(id: Long): Query[Games, Game, Seq] =
    games.filter(_.id === id)

}
