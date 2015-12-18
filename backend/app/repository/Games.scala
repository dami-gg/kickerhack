package repository

import java.sql.Timestamp

import model.Game
import org.joda.time.DateTime
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current

import scala.concurrent.Future

class Games(tag: Tag) extends Table[Game](tag, Some("kicker"), "game") {

  val id = column[Long]("g_id", O.AutoInc, O.PrimaryKey)
  val table = column[Long]("g_table_id")
  val goalsHome = column[Int]("g_goals_home")
  val goalsAway = column[Int]("g_goals_away")
  val startedOn = column[Long]("g_started_on")
  val finishedOn = column[Option[Long]]("g_finished_on", O.Default(None))

  lazy val kickertableFk = foreignKey("game_g_table_id_fkey", table, kickertable)(_.id)
  lazy val kickertable = TableQuery[KickerTables]

  override def * = (id.?, table, goalsHome, goalsAway, startedOn, finishedOn) <> (Game.tupled, Game.unapply)
}

class GamesRepository {
  private val games = TableQuery[Games]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  def insert(game: Game): Future[Game] = {
    try db.run((games returning games) += game)
    finally db.close
  }

  def list(): Future[Seq[Game]] =
    try db.run(games.result)
    finally db.close

  def findById(id: Long): Future[Option[Game]] =
    try db.run(games.filter(_.id === id).result.headOption)
    finally db.close()

  def findCurrentGameForTable(tableId: Long): Future[Option[Game]] =
    try db.run(games.filter(_.table === tableId).filter(_.finishedOn.isEmpty).result.headOption)
    finally db.close()

  def updateGoalAway(gameId: Long, goalsAway: Int) = {
    val q = for {c <- games if c.id === gameId} yield c.goalsAway
    db.run(q.update(goalsAway))
  }

  def updateGoalHome(gameId: Long, goalsHome: Int) = {
    val q = for {c <- games if c.id === gameId} yield c.goalsHome
    db.run(q.update(goalsHome))
  }

  def startNewGame(tableId: Long): Future[Game] = {
    insert(Game(None, tableId, 0, 0, DateTime.now().getMillis, None))
  }

  def finishGame(gameId: Long) = {
    val q = for {g <- games if g.id === gameId } yield g.finishedOn
    db.run(q.update(Some(DateTime.now().getMillis)))
  }

}
