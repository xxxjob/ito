package org.guigo.hbte.process

import org.apache.flink.api.common.functions.RichFlatMapFunction
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.util.Collector
import org.guigo.hbte.util.ConfigProperties
import org.slf4j.LoggerFactory

import java.util.{HashMap, Objects}

/**
 * 滤掉发生异常和devId为空的数据 保留正常数据
 */
class CustomerFilterExceptionFlatMapFunction extends RichFlatMapFunction[String, (String, HashMap[String, Object])] {
  private val log = LoggerFactory.getLogger(this.getClass)
  val mapper = new ObjectMapper()

  override def flatMap(in: String, out: Collector[(String, HashMap[String, Object])]): Unit = {
    try {
      val slice = mapper.readValue(in, classOf[HashMap[String, Object]])
      val id = slice.get(ConfigProperties.deviceInstancePropertyId()).toString
      if (Objects.nonNull(id)) {
        out.collect((id, slice))
      }
    } catch {
      case _: Exception => {
        log.error(s"数据必须是标准json格式并且必须包含设备ID=>$in")
      }
    }
  }
}