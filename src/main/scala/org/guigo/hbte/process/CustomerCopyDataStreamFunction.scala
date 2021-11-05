package org.guigo.hbte.process

import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.OutputTag
import org.apache.flink.util.Collector
import org.guigo.hbte.bean.DeviceInstance
import org.slf4j.LoggerFactory

/**
 * 侧输出流将流将主流copy N份
 *
 * @param outputs
 */
class CustomerCopyDataStreamFunction(outputs: OutputTag[(String, DeviceInstance)]*) extends ProcessFunction[DeviceInstance, (String, DeviceInstance)] {

  private val log = LoggerFactory.getLogger(this.getClass)

  override def processElement(value: DeviceInstance, ctx: ProcessFunction[DeviceInstance, (String, DeviceInstance)]#Context, out: Collector[(String, DeviceInstance)]): Unit = {
    for (output <- outputs) {
      ctx.output(output, (value.getId, value))
    }
    out.collect((value.getId, value))
  }
}
