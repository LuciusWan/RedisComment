--查询锁的id
local id=redis.call('get',KEYS[1])
if(ARGV[1]==id) then
    --释放锁
    return redis.call('del',KEYS[1])
end
--不是自己的锁,不用释放锁
return 0