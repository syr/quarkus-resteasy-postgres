# quarkus-resteasy-postgres Project

Just execute quarkus app. Simple quartz trigger is automatically created and executed

```
...
18:12:16 INFO  traceId=, parentId=, spanId=, sampled= [or.fl.co.in.co.DbMigrate] (Quarkus Main Thread) Successfully applied 1 migration to schema "public", now at version v2.0.0 (execution time 00:00.132s)
18:12:16 INFO  traceId=, parentId=, spanId=, sampled= [io.qu.qu.ru.QuartzSchedulerImpl] (Quarkus Main Thread) No scheduled business methods found - Quartz scheduler will not be started
18:12:16 INFO  traceId=, parentId=, spanId=, sampled= [or.qu.si.RAMJobStore] (Quarkus Main Thread) RAMJobStore initialized.
18:12:16 INFO  traceId=, parentId=, spanId=, sampled= [or.ac.qu.TaskResource] (Quarkus Main Thread) going to start scheduler
```

traceId,spanId expected in following lines

```
18:12:16 INFO  traceId=, parentId=, spanId=, sampled= [or.ac.qu.TaskCreateJob] (DefaultQuartzScheduler_Worker-1) created task
18:12:17 INFO  traceId=, parentId=, spanId=, sampled= [or.ac.qu.TaskCreateJob] (DefaultQuartzScheduler_Worker-2) created task
18:12:18 INFO  traceId=, parentId=, spanId=, sampled= [or.ac.qu.TaskCreateJob] (DefaultQuartzScheduler_Worker-3) created task
18:12:19 INFO  traceId=, parentId=, spanId=, sampled= [or.ac.qu.TaskCreateJob] (DefaultQuartzScheduler_Worker-4) created task
```