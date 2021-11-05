package org.guigo.hbte.util

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import java.util.Objects

object HikariCPPool {

  @volatile
  @transient private[this] var mysql: HikariDataSource = _

  /**
   * 获取数据库连接池
   *
   * @return hikaricp连接池对象
   */
  def mysqlDataSource(): HikariDataSource = {
    this.synchronized {
      if (Objects.isNull(mysql)) {
        this.synchronized {
          val config = new HikariConfig
          config.setJdbcUrl(ConfigProperties.jdbcUrl())
          config.setUsername(ConfigProperties.jdbcUser())
          config.setPassword(ConfigProperties.jdbcPassword())
          config.setConnectionTimeout(ConfigProperties.jdbcConnectionTimeout())
          config.setMinimumIdle(ConfigProperties.jdbcMinimumIdle())
          config.setMaximumPoolSize(ConfigProperties.jdbcMaximumPoolSize())
          mysql = new HikariDataSource(config)
        }
      }
    }
    mysql
  }
}
