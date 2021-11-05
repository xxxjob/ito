package org.guigo.hbte.util

import java.util.Properties

/**
 * 加载配置文件默认路径项目路径下config.properties
 *
 * environment 环境变量的选择环境 dev release alpha 等等
 */
object ConfigProperties {

  @transient private[this] val prop = new Properties

  prop.load(getClass.getClassLoader.getResourceAsStream("config.properties"))

  val env = prop.getProperty("environment")

  //拼接环境变量的和属性值后缀定位读取属性值
  private def environment(env: String)(property: String): String = {
    s"$env.$property"
  }

  def jdbcUrl() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.url"))
  }

  def jdbcUser() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.user"))
  }

  def jdbcPassword() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.password"))
  }

  def jdbcMaximumPoolSize() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.maximumPoolSize")).toInt
  }

  def jdbcMinimumIdle() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.minimumIdle")).toInt
  }

  def jdbcConnectionTimeout() = {
    prop.getProperty(environment(env)("data.mysql.jdbc.connectionTimeout")).toInt
  }

  def kafkaBootStrapServers() = {
    prop.getProperty(environment(env)("kafka.bootstrap.servers"))
  }

  def kafkaGroupId() = {
    prop.getProperty(environment(env)("kafka.group.id"))
  }

  def kafkaTopic() = {
    prop.getProperty(environment(env)("kafka.topic"))
  }

  def kafkaOffsetReset() = {
    prop.getProperty(environment(env)("kafka.auto.offset.reset"))
  }

  def kafkaKeySerializer() = {
    prop.getProperty(environment(env)("kafka.key.serializer"))
  }

  def kafkaValueSerializer() = {
    prop.getProperty(environment(env)("kafka.value.serializer"))
  }

  def kuduWriterConfigHost() = {
    prop.getProperty(environment(env)("kudu.writer.config.host"))
  }

  def kuduWriterConfigTable() = {
    prop.getProperty(environment(env)("kudu.writer.config.table"))
  }

  def deviceInstancePropertyId() = {
    prop.getProperty(environment(env)("device.instance.id"))
  }
}
