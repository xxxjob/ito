CREATE TABLE kudu.default.t_device_instance1
(
    time timestamp with(primary_key= true),
    dev_id varchar with(nullable=true),
    type_id varchar with(nullable=true),
    company_id varchar with(nullable=true),
    ax varchar with(nullable=false),
    bx varchar with(nullable=false),
    cx varchar with(nullable=false),
    dx varchar with(nullable=false),
    ex varchar with(nullable=false),
    fx varchar with(nullable=false),
    gx varchar with(nullable=false),
    hx varchar with(nullable=false),
    ix varchar with(nullable=false),
    jx varchar with(nullable=false),
    kx varchar with(nullable=false),
    lx varchar with(nullable=false),
    mx varchar with(nullable=false),
    nx varchar with(nullable=false),
    ox varchar with(nullable=false),
    px varchar with(nullable=false),
    qx varchar with(nullable=false),
    rx varchar with(nullable=false),
    sx varchar with(nullable=false),
    tx varchar with(nullable=false),
    ux varchar with(nullable=false),
    vx varchar with(nullable=false),
    wx varchar with(nullable=false),
    xx varchar with(nullable=false),
    yx varchar with(nullable=false),
    zx varchar with(nullable=false)
) with (
    number_of_replicas = 3,
    partition_by_hash_columns = ARRAY['time'],
    partition_by_hash_buckets = 2
)

--查询建表语句
--SHOW create table kudu.default.t_device_instance1
--删除默认时间戳范围分区
--CALL kudu.system.drop_range_partition('default', 't_device_instance1', '{"lower":null,"upper":null}')
--添加时间戳范围分区
--CALL kudu.system.add_range_partition('default', 't_device_instance1', '{"lower": "2021-10-16", "upper": "2021-10-31"}')
