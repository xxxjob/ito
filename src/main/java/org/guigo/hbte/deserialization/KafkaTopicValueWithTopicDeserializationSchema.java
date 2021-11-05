package org.guigo.hbte.deserialization;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.typeutils.TupleTypeInfo;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 反序列化kafka topic key和 message 数据
 */
public class KafkaTopicValueWithTopicDeserializationSchema implements KafkaDeserializationSchema<Tuple3<String, String, String>> {

    @Override
    public boolean isEndOfStream(Tuple3<String, String, String> nextElement) {
        return false;
    }

    @Override
    public Tuple3<String, String, String> deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
        String key = null;
        if (Objects.nonNull(record.key())) {
            key = new String(record.key(), Charset.defaultCharset());
        }
        String value = null;
        if (Objects.nonNull(record.value())) {
            value = new String(record.value(), Charset.defaultCharset());
        }

        return new Tuple3<>(record.topic(), key, value);
    }

    @Override
    public TypeInformation<Tuple3<String, String, String>> getProducedType() {
        return new TupleTypeInfo<>(BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO);

    }
}
