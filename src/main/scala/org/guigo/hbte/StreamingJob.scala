package org.guigo.hbte

import org.apache.flink.streaming.api.scala.{OutputTag, _}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.guigo.hbte.util.ConfigProperties
import org.colloh.flink.kudu.connector.internal.KuduTableInfo
import org.colloh.flink.kudu.connector.internal.writer.KuduWriterConfig
import org.colloh.flink.kudu.connector.table.sink.KuduSink
import org.guigo.hbte.bean.DeviceInstance
import org.guigo.hbte.deserialization.KafkaTopicValueWithTopicDeserializationSchema
import org.guigo.hbte.mapper.CustomerAnnotationOperationMapper
import org.guigo.hbte.process.{CustomerAlertProcessFunction, CustomerCopyDataStreamFunction, CustomerFilterExceptionFlatMapFunction, CustomerFilterFunction, CustomerMessageToPojoProcessFunction}

import java.util.Properties
import java.util.regex.Pattern
import scala.language.postfixOps

object StreamingJob {

  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = new Properties()
    props.setProperty("bootstrap.servers", ConfigProperties.kafkaBootStrapServers())
    props.setProperty("group.id", ConfigProperties.kafkaGroupId())
    props.setProperty("key.serializer", ConfigProperties.kafkaKeySerializer())
    props.setProperty("value.serializer", ConfigProperties.kafkaValueSerializer())
    props.setProperty("out.offset.reset", ConfigProperties.kafkaOffsetReset())

    val consumer = new FlinkKafkaConsumer(
      Pattern.compile(ConfigProperties.kafkaTopic()),
      new KafkaTopicValueWithTopicDeserializationSchema(),
      props
    )

    //开启Checkpoint
    env.enableCheckpointing(5000)
    //开启将偏移量提交到checkpoint地址
    consumer.setCommitOffsetsOnCheckpoints(true)
    //设置从分组偏移量开始拉取
    consumer.setStartFromGroupOffsets()
    //解析kafka的message成pojo
    val deserialization = env.addSource(consumer)
      .map(_.f2)
      .flatMap(new CustomerFilterExceptionFlatMapFunction())
      .keyBy(_._1)
      .process(new CustomerMessageToPojoProcessFunction())
    //处理乱序时间的水位线 乱序程序需根据行业经验判断
    //      .assignTimestampsAndWatermarks(WatermarkStrategy
    //        .forBoundedOutOfOrderness[KuduData](Duration.ofMillis(30))
    //        .withTimestampAssigner(
    //          new SerializableTimestampAssigner[KuduData] {
    //            override def extractTimestamp(event: KuduData, recordTimestamp: Long): Long = event.getTime
    //          }
    //        ))
    //定义测输出流 将数据流复制成三条流
    val thresholdOutputTag = new OutputTag[(String, DeviceInstance)]("thresholdOutputTag")
    val continuityOutputTag = new OutputTag[(String, DeviceInstance)]("continuityOutputTag")
    val copyAntiSerialization = deserialization.process(new CustomerCopyDataStreamFunction(thresholdOutputTag, continuityOutputTag))
    val threshold = copyAntiSerialization.getSideOutput(thresholdOutputTag)
    val continuity = copyAntiSerialization.getSideOutput(continuityOutputTag)

    threshold.filter(new CustomerFilterFunction)
    continuity.keyBy(_._1).process(new CustomerAlertProcessFunction)
    //保存kudu的设备实例表中
    val sink = new KuduSink[DeviceInstance](
      KuduWriterConfig.Builder.setMasters(ConfigProperties.kuduWriterConfigHost()).build,
      KuduTableInfo.forTable(ConfigProperties.kuduWriterConfigTable()),
      new CustomerAnnotationOperationMapper(classOf[DeviceInstance]))
    copyAntiSerialization.map(_._2).addSink(sink)

    env.execute()
  }
}


