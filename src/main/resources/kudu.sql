CREATE TABLE kudu.default.t_device_instance
(
    time timestamp with(primary_key= true),
    id varchar with(nullable=true),
    type_id varchar with(nullable=true),
    company_id varchar with(nullable=true),
    dynamic_attribute1 varchar with(nullable=false),
    dynamic_attribute2 varchar with(nullable=false),
    dynamic_attribute3 varchar with(nullable=false),
    dynamic_attribute4 varchar with(nullable=false),
    dynamic_attribute5 varchar with(nullable=false),
    dynamic_attribute6 varchar with(nullable=false),
    dynamic_attribute7 varchar with(nullable=false),
    dynamic_attribute8 varchar with(nullable=false),
    dynamic_attribute9 varchar with(nullable=false),
    dynamic_attribute10 varchar with(nullable=false),
    dynamic_attribute11 varchar with(nullable=false),
    dynamic_attribute12 varchar with(nullable=false),
    dynamic_attribute13 varchar with(nullable=false),
    dynamic_attribute14 varchar with(nullable=false),
    dynamic_attribute15 varchar with(nullable=false),
    dynamic_attribute16 varchar with(nullable=false),
    dynamic_attribute17 varchar with(nullable=false),
    dynamic_attribute18 varchar with(nullable=false),
    dynamic_attribute19 varchar with(nullable=false),
    dynamic_attribute20 varchar with(nullable=false),
    dynamic_attribute21 varchar with(nullable=false),
    dynamic_attribute22 varchar with(nullable=false),
    dynamic_attribute23 varchar with(nullable=false),
    dynamic_attribute24 varchar with(nullable=false),
    dynamic_attribute25 varchar with(nullable=false),
    dynamic_attribute26 varchar with(nullable=false)
) with (
    number_of_replicas = 3,
    partition_by_range_columns = ARRAY['time'],
    range_partitions = '[{"lower":"2021-10-16T00:00:00.000Z","upper":"2021-10-31T00:00:00.000Z"}]'
)

--查询建表语句
--SHOW create table kudu.default.t_device_instance
--删除默认时间戳范围分区
--CALL kudu.system.drop_range_partition('default', 't_device_instance', '{"lower":null,"upper":null}')
--添加时间戳范围分区
--CALL kudu.system.add_range_partition('default', 't_device_instance', '{"lower": "2021-10-16", "upper": "2021-10-31"}')
--presto客户端连接kudu数据库
--presto --server http://slave2:8080 --catalog kudu --schema default