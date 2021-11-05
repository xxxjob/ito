package org.guigo.hbte.process

import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector
import org.guigo.hbte.bean.DeviceInstance
import org.slf4j.LoggerFactory

import java.util


/**
 * 根据设备ID查询数据库
 * 找到message中key对应的设备名称和kudu中的字段对应关系
 * 通过映射关系将数据封装成pojo
 */
class CustomerMessageToPojoProcessFunction extends KeyedProcessFunction[String, (String, util.HashMap[String, Object]), DeviceInstance] {

  private val log = LoggerFactory.getLogger(this.getClass)

  override def open(config: Configuration): Unit = {
  }

  override def close(): Unit = {
  }

  override def processElement(input: (String, util.HashMap[String, Object]), context: KeyedProcessFunction[String, (String, util.HashMap[String, Object]), DeviceInstance]#Context, out: Collector[DeviceInstance]): Unit = {
    val data: DeviceInstance = new DeviceInstance()
    try {
      data.attribute("id", input._1)
      data.attribute("companyId", 1)
      data.attribute("typeId", 1)
      data.attribute("dynamicAttribute1", input._2.get("ax"))
      data.attribute("dynamicAttribute2", input._2.get("bx"))
    } catch {
      case _: Exception => {
        log.error(s"数据结构报错=>${input._2}")
      }
    }
    out.collect(data)
  }
}
