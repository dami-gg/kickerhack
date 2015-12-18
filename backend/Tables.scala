package anypackage
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.PostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Game.schema ++ Kickertable.schema ++ Player.schema ++ PlayEvolutions.schema ++ User.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Game
   *  @param gId Database column g_id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param gTableId Database column g_table_id SqlType(int8)
   *  @param gGoalsHome Database column g_goals_home SqlType(int2)
   *  @param gGoalsAway Database column g_goals_away SqlType(int2)
   *  @param gStartedOn Database column g_started_on SqlType(timestamp)
   *  @param gFinishedOn Database column g_finished_on SqlType(timestamp), Default(None) */
  case class GameRow(gId: Long, gTableId: Long, gGoalsHome: Short, gGoalsAway: Short, gStartedOn: java.sql.Timestamp, gFinishedOn: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching GameRow objects using plain SQL queries */
  implicit def GetResultGameRow(implicit e0: GR[Long], e1: GR[Short], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]]): GR[GameRow] = GR{
    prs => import prs._
    GameRow.tupled((<<[Long], <<[Long], <<[Short], <<[Short], <<[java.sql.Timestamp], <<?[java.sql.Timestamp]))
  }
  /** Table description of table game. Objects of this class serve as prototypes for rows in queries. */
  class Game(_tableTag: Tag) extends Table[GameRow](_tableTag, Some("kicker"), "game") {
    def * = (gId, gTableId, gGoalsHome, gGoalsAway, gStartedOn, gFinishedOn) <> (GameRow.tupled, GameRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(gId), Rep.Some(gTableId), Rep.Some(gGoalsHome), Rep.Some(gGoalsAway), Rep.Some(gStartedOn), gFinishedOn).shaped.<>({r=>import r._; _1.map(_=> GameRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column g_id SqlType(bigserial), AutoInc, PrimaryKey */
    val gId: Rep[Long] = column[Long]("g_id", O.AutoInc, O.PrimaryKey)
    /** Database column g_table_id SqlType(int8) */
    val gTableId: Rep[Long] = column[Long]("g_table_id")
    /** Database column g_goals_home SqlType(int2) */
    val gGoalsHome: Rep[Short] = column[Short]("g_goals_home")
    /** Database column g_goals_away SqlType(int2) */
    val gGoalsAway: Rep[Short] = column[Short]("g_goals_away")
    /** Database column g_started_on SqlType(timestamp) */
    val gStartedOn: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("g_started_on")
    /** Database column g_finished_on SqlType(timestamp), Default(None) */
    val gFinishedOn: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("g_finished_on", O.Default(None))

    /** Foreign key referencing Kickertable (database name game_g_table_id_fkey) */
    lazy val kickertableFk = foreignKey("game_g_table_id_fkey", gTableId, Kickertable)(r => r.ktId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Game */
  lazy val Game = new TableQuery(tag => new Game(tag))

  /** Entity class storing rows of table Kickertable
   *  @param ktId Database column kt_id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param ktName Database column kt_name SqlType(text), Default(None)
   *  @param ktBuilding Database column kt_building SqlType(text)
   *  @param ktFloor Database column kt_floor SqlType(text)
   *  @param ktColorHome Database column kt_color_home SqlType(text)
   *  @param ktColorAway Database column kt_color_away SqlType(text)
   *  @param ktLastGoalScored Database column kt_last_goal_scored SqlType(timestamptz), Default(None) */
  case class KickertableRow(ktId: Long, ktName: Option[String] = None, ktBuilding: String, ktFloor: String, ktColorHome: String, ktColorAway: String, ktLastGoalScored: Option[java.sql.Timestamp] = None)
  /** GetResult implicit for fetching KickertableRow objects using plain SQL queries */
  implicit def GetResultKickertableRow(implicit e0: GR[Long], e1: GR[Option[String]], e2: GR[String], e3: GR[Option[java.sql.Timestamp]]): GR[KickertableRow] = GR{
    prs => import prs._
    KickertableRow.tupled((<<[Long], <<?[String], <<[String], <<[String], <<[String], <<[String], <<?[java.sql.Timestamp]))
  }
  /** Table description of table kickerTable. Objects of this class serve as prototypes for rows in queries. */
  class Kickertable(_tableTag: Tag) extends Table[KickertableRow](_tableTag, Some("kicker"), "kickerTable") {
    def * = (ktId, ktName, ktBuilding, ktFloor, ktColorHome, ktColorAway, ktLastGoalScored) <> (KickertableRow.tupled, KickertableRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(ktId), ktName, Rep.Some(ktBuilding), Rep.Some(ktFloor), Rep.Some(ktColorHome), Rep.Some(ktColorAway), ktLastGoalScored).shaped.<>({r=>import r._; _1.map(_=> KickertableRow.tupled((_1.get, _2, _3.get, _4.get, _5.get, _6.get, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column kt_id SqlType(bigserial), AutoInc, PrimaryKey */
    val ktId: Rep[Long] = column[Long]("kt_id", O.AutoInc, O.PrimaryKey)
    /** Database column kt_name SqlType(text), Default(None) */
    val ktName: Rep[Option[String]] = column[Option[String]]("kt_name", O.Default(None))
    /** Database column kt_building SqlType(text) */
    val ktBuilding: Rep[String] = column[String]("kt_building")
    /** Database column kt_floor SqlType(text) */
    val ktFloor: Rep[String] = column[String]("kt_floor")
    /** Database column kt_color_home SqlType(text) */
    val ktColorHome: Rep[String] = column[String]("kt_color_home")
    /** Database column kt_color_away SqlType(text) */
    val ktColorAway: Rep[String] = column[String]("kt_color_away")
    /** Database column kt_last_goal_scored SqlType(timestamptz), Default(None) */
    val ktLastGoalScored: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("kt_last_goal_scored", O.Default(None))
  }
  /** Collection-like TableQuery object for table Kickertable */
  lazy val Kickertable = new TableQuery(tag => new Kickertable(tag))

  /** Entity class storing rows of table Player
   *  @param pId Database column p_id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param pUserId Database column p_user_id SqlType(int8)
   *  @param pGameId Database column p_game_id SqlType(int8)
   *  @param pPosition Database column p_position SqlType(varchar), Length(254,true)
   *  @param pSide Database column p_side SqlType(varchar), Length(254,true) */
  case class PlayerRow(pId: Long, pUserId: Long, pGameId: Long, pPosition: String, pSide: String)
  /** GetResult implicit for fetching PlayerRow objects using plain SQL queries */
  implicit def GetResultPlayerRow(implicit e0: GR[Long], e1: GR[String]): GR[PlayerRow] = GR{
    prs => import prs._
    PlayerRow.tupled((<<[Long], <<[Long], <<[Long], <<[String], <<[String]))
  }
  /** Table description of table player. Objects of this class serve as prototypes for rows in queries. */
  class Player(_tableTag: Tag) extends Table[PlayerRow](_tableTag, Some("kicker"), "player") {
    def * = (pId, pUserId, pGameId, pPosition, pSide) <> (PlayerRow.tupled, PlayerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(pId), Rep.Some(pUserId), Rep.Some(pGameId), Rep.Some(pPosition), Rep.Some(pSide)).shaped.<>({r=>import r._; _1.map(_=> PlayerRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column p_id SqlType(bigserial), AutoInc, PrimaryKey */
    val pId: Rep[Long] = column[Long]("p_id", O.AutoInc, O.PrimaryKey)
    /** Database column p_user_id SqlType(int8) */
    val pUserId: Rep[Long] = column[Long]("p_user_id")
    /** Database column p_game_id SqlType(int8) */
    val pGameId: Rep[Long] = column[Long]("p_game_id")
    /** Database column p_position SqlType(varchar), Length(254,true) */
    val pPosition: Rep[String] = column[String]("p_position", O.Length(254,varying=true))
    /** Database column p_side SqlType(varchar), Length(254,true) */
    val pSide: Rep[String] = column[String]("p_side", O.Length(254,varying=true))

    /** Foreign key referencing Game (database name player_p_game_id_fkey) */
    lazy val gameFk = foreignKey("player_p_game_id_fkey", pGameId, Game)(r => r.gId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing User (database name player_p_user_id_fkey) */
    lazy val userFk = foreignKey("player_p_user_id_fkey", pUserId, User)(r => r.uId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Player */
  lazy val Player = new TableQuery(tag => new Player(tag))

  /** Entity class storing rows of table PlayEvolutions
   *  @param id Database column id SqlType(int4), PrimaryKey
   *  @param hash Database column hash SqlType(varchar), Length(255,true)
   *  @param appliedAt Database column applied_at SqlType(timestamp)
   *  @param applyScript Database column apply_script SqlType(text), Default(None)
   *  @param revertScript Database column revert_script SqlType(text), Default(None)
   *  @param state Database column state SqlType(varchar), Length(255,true), Default(None)
   *  @param lastProblem Database column last_problem SqlType(text), Default(None) */
  case class PlayEvolutionsRow(id: Int, hash: String, appliedAt: java.sql.Timestamp, applyScript: Option[String] = None, revertScript: Option[String] = None, state: Option[String] = None, lastProblem: Option[String] = None)
  /** GetResult implicit for fetching PlayEvolutionsRow objects using plain SQL queries */
  implicit def GetResultPlayEvolutionsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[String]]): GR[PlayEvolutionsRow] = GR{
    prs => import prs._
    PlayEvolutionsRow.tupled((<<[Int], <<[String], <<[java.sql.Timestamp], <<?[String], <<?[String], <<?[String], <<?[String]))
  }
  /** Table description of table play_evolutions. Objects of this class serve as prototypes for rows in queries. */
  class PlayEvolutions(_tableTag: Tag) extends Table[PlayEvolutionsRow](_tableTag, "play_evolutions") {
    def * = (id, hash, appliedAt, applyScript, revertScript, state, lastProblem) <> (PlayEvolutionsRow.tupled, PlayEvolutionsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(hash), Rep.Some(appliedAt), applyScript, revertScript, state, lastProblem).shaped.<>({r=>import r._; _1.map(_=> PlayEvolutionsRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6, _7)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(int4), PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    /** Database column hash SqlType(varchar), Length(255,true) */
    val hash: Rep[String] = column[String]("hash", O.Length(255,varying=true))
    /** Database column applied_at SqlType(timestamp) */
    val appliedAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("applied_at")
    /** Database column apply_script SqlType(text), Default(None) */
    val applyScript: Rep[Option[String]] = column[Option[String]]("apply_script", O.Default(None))
    /** Database column revert_script SqlType(text), Default(None) */
    val revertScript: Rep[Option[String]] = column[Option[String]]("revert_script", O.Default(None))
    /** Database column state SqlType(varchar), Length(255,true), Default(None) */
    val state: Rep[Option[String]] = column[Option[String]]("state", O.Length(255,varying=true), O.Default(None))
    /** Database column last_problem SqlType(text), Default(None) */
    val lastProblem: Rep[Option[String]] = column[Option[String]]("last_problem", O.Default(None))
  }
  /** Collection-like TableQuery object for table PlayEvolutions */
  lazy val PlayEvolutions = new TableQuery(tag => new PlayEvolutions(tag))

  /** Entity class storing rows of table User
   *  @param uId Database column u_id SqlType(bigserial), AutoInc, PrimaryKey
   *  @param uName Database column u_name SqlType(varchar), Length(254,true) */
  case class UserRow(uId: Long, uName: String)
  /** GetResult implicit for fetching UserRow objects using plain SQL queries */
  implicit def GetResultUserRow(implicit e0: GR[Long], e1: GR[String]): GR[UserRow] = GR{
    prs => import prs._
    UserRow.tupled((<<[Long], <<[String]))
  }
  /** Table description of table user. Objects of this class serve as prototypes for rows in queries. */
  class User(_tableTag: Tag) extends Table[UserRow](_tableTag, Some("kicker"), "user") {
    def * = (uId, uName) <> (UserRow.tupled, UserRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(uId), Rep.Some(uName)).shaped.<>({r=>import r._; _1.map(_=> UserRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column u_id SqlType(bigserial), AutoInc, PrimaryKey */
    val uId: Rep[Long] = column[Long]("u_id", O.AutoInc, O.PrimaryKey)
    /** Database column u_name SqlType(varchar), Length(254,true) */
    val uName: Rep[String] = column[String]("u_name", O.Length(254,varying=true))
  }
  /** Collection-like TableQuery object for table User */
  lazy val User = new TableQuery(tag => new User(tag))
}
