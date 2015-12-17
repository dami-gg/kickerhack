package repository

import model.{NfcData, Position, Side}
import slick.lifted.Tag
import slick.driver.PostgresDriver.api._
import model.Position._
import model.Side._
import play.api.db.DB
import play.api.Play.current

import scala.concurrent.Future

class NfcDataTable(tag: Tag) extends Table[NfcData](tag, Some("kicker"), "nfcData") {
  implicit val positionColumnType = MappedColumnType.base[Position, String](p => p.toString, s => Position.withName(s))
  implicit val sideColumnType = MappedColumnType.base[Side, String](s => s.toString, s => Side.withName(s))

  def nfcTag = column[String]("nd_tag", O.PrimaryKey)
  def tableId = column[Long]("nd_table_id")
  def position = column[Position]("nd_position")
  def side = column[Side]("nd_side")

  override def * = (nfcTag, tableId, side, position) <> ((NfcData.apply _).tupled, NfcData.unapply)
}

class NfcDataRepository {
  private val nfcDataTable = TableQuery[NfcDataTable]

  private def db = Database.forDataSource(DB.getDataSource())

  def insertNfcData(data: NfcData): Future[Int] = {
    try db.run(nfcDataTable += data)
    finally db.close
  }

  def getNfcData(tag: String): Future[Option[NfcData]] = {
    try {
      val query = nfcDataTable.filter(_.nfcTag === tag)
      db.run(query.result.headOption)
    } finally db.close
  }

  def getAll: Future[Seq[NfcData]] = {
    try db.run(nfcDataTable.result)
    finally db.close
  }
}
