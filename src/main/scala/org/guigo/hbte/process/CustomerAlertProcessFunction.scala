package org.guigo.hbte.process

import org.apache.flink.api.common.state.{State, ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.util.Collector
import org.guigo.hbte.bean.DeviceInstance
import org.guigo.hbte.operation.OperationFactory
import org.slf4j.LoggerFactory

import java.util.Objects

/**
 * 处理连续问题 （上升,下降, ==）
 *
 * 如果数据量间隔太长 预警将不会提示
 * 逻辑流程图
 * 第一次  10 保存到状态管理里面
 * 第二次  11 时间超过设定时间 上一次的状态管理里面的值（10）将被替换更新成 11 不会开启闹钟
 * 第二次  11 时间未超过设定时间 上一次的状态管理里面的值（10）将被替换更新成 11 但是会开启闹钟
 * 第三次  12 时间超过设定时间 闹钟响起
 * 第三次  12 时间未超过设定时间 上一次的状态管理里面的值（11）将被替换成 12 等待闹钟响起
 * 第三次  10 时间未超过设定时间 删除闹钟 清空所有数据
 */
class CustomerAlertProcessFunction extends KeyedProcessFunction[String, (String, DeviceInstance), DeviceInstance] {

  private val log = LoggerFactory.getLogger(this.getClass)

  lazy val last: ValueState[DeviceInstance] = getRuntimeContext.getState(new ValueStateDescriptor[DeviceInstance]("last-state", classOf[DeviceInstance]))
  lazy val onTimerTs: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("time-ts", classOf[Long]))
  lazy val lastProcessTime: ValueState[Long] = getRuntimeContext.getState(new ValueStateDescriptor[Long]("last-process-time", classOf[Long]))

  override def open(parameters: Configuration): Unit = {

  }

  override def close(): Unit = {

  }

  override def processElement(input: (String, DeviceInstance), ctx: KeyedProcessFunction[String, (String, DeviceInstance), DeviceInstance]#Context, out: Collector[DeviceInstance]): Unit = {
    val lastValue = last.value()
    val timeTs = onTimerTs.value()
    val processTime = ctx.timerService().currentProcessingTime()
    //时间长度时间参数
    val howLongTime = 60 * 1000;
    if (Objects.isNull(lastValue)) {
      last.update(input._2)
      lastProcessTime.update(processTime)
    } else {
      if (processTime - lastProcessTime.value() > howLongTime) {
        last.update(input._2)
        lastProcessTime.update(processTime)
      } else if (Objects.nonNull(input._2)) {
        val bool = OperationFactory.newOperation("<").operator(lastValue.getDynamicAttribute1.toDouble, input._2.getDynamicAttribute1.toDouble)
        if (timeTs == 0) {
          if (bool) {
            ctx.timerService().registerProcessingTimeTimer(processTime + howLongTime)
            onTimerTs.update(processTime + howLongTime)
            last.update(input._2)
            lastProcessTime.update(processTime)
          }
        } else {
          if (bool) {
            last.update(input._2)
            lastProcessTime.update(processTime)
          } else {
            ctx.timerService().registerProcessingTimeTimer(timeTs)
            clearAllState(last, lastProcessTime, onTimerTs)
          }
        }
      }
    }
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, (String, DeviceInstance), DeviceInstance]#OnTimerContext, out: Collector[DeviceInstance]): Unit = {
    out.collect(last.value())
    clearAllState(last, lastProcessTime, onTimerTs)
  }

  def clearAllState(states: State*): Unit = {
    for (state <- states) {
      if (Objects.nonNull(state)) {
        state.clear()
      }
    }
  }
}

