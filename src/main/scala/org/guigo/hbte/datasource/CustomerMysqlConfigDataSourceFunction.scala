package org.guigo.hbte.datasource

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import org.guigo.hbte.bean.Schema
import org.guigo.hbte.util.HikariCPPool
import org.slf4j.LoggerFactory

import java.sql.{Connection, PreparedStatement, ResultSet, SQLException}
import java.util
import java.util.Objects

/**
 * 定义mysql的流式数据来源信息
 * 主要获取对应的kafka接收到的消息数据格式描述信息
 */
class CustomerMysqlConfigDataSourceFunction extends RichParallelSourceFunction[util.ArrayList[Schema]] {

  private val log = LoggerFactory.getLogger(this.getClass)

  //查询结束标志
  var isRunFlag = true

  //全局变量数据库连接
  var connection: Connection = _

  val sql = "select id,company from meta"

  override def run(cxt: SourceFunction.SourceContext[util.ArrayList[Schema]]): Unit = {
    val metas = new util.ArrayList[Schema]()
    while (isRunFlag) {
      var statement: PreparedStatement = null
      var metaCustomRes: ResultSet = null
      try {
        statement = connection.prepareStatement(sql)
        metaCustomRes = statement.executeQuery()
        while (metaCustomRes.next()) {
          val id = metaCustomRes.getString(1)
          val company = metaCustomRes.getString(2)
          val property = metaCustomRes.getString(3)
          metas.add(new Schema(id, company, property))
        }
      } catch {
        case e: SQLException => {
          log.error(s"查询sql => $sql,错误 => ${e.getLocalizedMessage}")
        }
      } finally {
        if (Objects.nonNull(metaCustomRes)) {
          metaCustomRes.close()
        }
        if (Objects.nonNull(statement)) {
          statement.close()
        }
      }
    }
    cxt.collect(metas)
    Thread.sleep(3000)
  }

  /**
   * 取消轮询Mysql数据
   */
  override def cancel(): Unit = {
    isRunFlag = false
  }

  /**
   * 初始化数据源连接信息 方法只加载一次
   *
   * @param parameters
   */
  override def open(parameters: Configuration): Unit = {
    try {
      connection = HikariCPPool.mysqlDataSource().getConnection()
    } catch {
      case e: SQLException => {
        log.error("获取数据库连接错误 => {}", e.getLocalizedMessage)
      }
    }
  }

  /**
   * 关闭资源资源释放资源
   */
  override def close(): Unit = {
    connection.close()
  }

}
