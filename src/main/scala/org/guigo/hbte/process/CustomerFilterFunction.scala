package org.guigo.hbte.process

import org.apache.flink.api.common.functions.RichFilterFunction
import org.apache.flink.configuration.Configuration
import org.guigo.hbte.bean.DeviceInstance
import org.guigo.hbte.operation.OperationFactory
import org.slf4j.LoggerFactory

/**
 * 单次超过阈值则报警输出
 */
class CustomerFilterFunction extends RichFilterFunction[(String, DeviceInstance)] {

  private val log = LoggerFactory.getLogger(this.getClass)

  override def open(parameters: Configuration): Unit = {

  }

  override def close(): Unit = {

  }

  override def filter(t: (String, DeviceInstance)): Boolean = {
    val bool = OperationFactory.newOperation(">").operator(t._2.getDynamicAttribute1.toDouble, 20);
    if (bool) true else false
  }
}
