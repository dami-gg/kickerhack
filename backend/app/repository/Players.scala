package repository

import java.util.Date

import model.Side.Side
import model.{User, Side, Position, Player}
import play.api.db.DB
import slick.driver.PostgresDriver.api._
import play.api.Play.current
import scala.concurrent.Future
import model.Position.Position

class Players(tag: Tag) extends Table[Player](tag, Some("kicker"), "player") {
  implicit val positionColumnType = MappedColumnType.base[Position, String](d => d.toString, d => Position.withName(d))
  implicit val sideColumnType = MappedColumnType.base[Side, String](d => d.toString, d => Side.withName(d))

  def id = column[Long]("p_id", O.PrimaryKey, O.AutoInc)
  def user = column[Long]("p_user_id")
  def game = column[Long]("p_game_id")
  def position = column[Position]("p_position")
  def side = column[Side]("p_side")

  override def * = (id.?, user, game, position, side) <> ((Player.apply _).tupled, Player.unapply)
}

class PlayerRepository {
  private val players = TableQuery[Players]

  private def db: Database = Database.forDataSource(DB.getDataSource())

  def insert(player: Player): Future[Int] =
    try db.run(players += player)
    finally db.close

  def list(): Future[Seq[Player]] =
    try db.run(players.result)
    finally db.close

  def findById(id: Long): Future[Player] =
    try db.run(filterQuery(id).result.head)
    finally db.close

  private def filterQuery(id: Long): Query[Players, Player, Seq] =
    players.filter(_.id === id)
}

