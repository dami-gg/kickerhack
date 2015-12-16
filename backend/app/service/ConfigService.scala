package service

import com.typesafe.config.{Config, ConfigFactory}

trait ConfigService {

  implicit def conf: Config
  def clientId: String
  def clientSecret: String

}

trait ConfigServiceImpl extends ConfigService {

  override val conf = ConfigFactory.load()

  override val clientId: String = conf.getString("client.id")

  override val clientSecret: String = conf.getString("client.secret")
}
