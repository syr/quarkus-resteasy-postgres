package org.acme.quartz;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.quartz.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskBean {

    @Inject
    org.quartz.Scheduler quartz;

    @Inject
    SqsClient sqs;

    @ConfigProperty(name = "sqs.queue.url")
    String queueUrl;

    void onStart(@Observes StartupEvent event) throws SchedulerException {
//       JobDetail job = JobBuilder.newJob(SqsSendJob.class)
//                         .withIdentity("SqsSendJob", "myGroup")
//                         .build();
//       Trigger trigger = TriggerBuilder.newTrigger()
//                            .withIdentity("SqsSendJobTrigger", "myGroup")
//                            .startNow()
//                            .withSchedule(
//                               SimpleScheduleBuilder.simpleSchedule()
//                                  .withIntervalInSeconds(10)
//                                  .withRepeatCount(3))
//                            .build();
//       quartz.scheduleJob(job, trigger);

        JobDetail job = JobBuilder.newJob(SqsReceiveJob.class)
                .withIdentity("SqsReceiveJob", "myGroup")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("SqsReceiveJobTrigger", "myGroup")
                .startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(10)
                                .withRepeatCount(3))
                .build();
        quartz.scheduleJob(job, trigger);
    }

    @WithSpan
    void sendMessage() {
        SendMessageResponse response = sqs.sendMessage(m -> m
                .queueUrl(queueUrl)
                .messageBody("message" + UUID.randomUUID())
                .messageGroupId("a")
        );
        Log.info("message sent\tID=%s".formatted(response.messageId()));
    }

    void receiveMessage() {
        sqs.receiveMessage(m -> m.maxNumberOfMessages(1)
                        .queueUrl(queueUrl)
                        .attributeNames(QueueAttributeName.ALL)
                        .messageAttributeNames("AWSTraceHeader")
        ).messages().forEach(m -> {
            Log.info("message received\tID=%s".formatted(m.messageId()));
            Log.info("message system attributes: %s".formatted(m.messageAttributes().entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                    .collect(Collectors.joining(", "))));
            Log.info("message attributes: %s".formatted(m.messageAttributes().entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=\"" + e.getValue() + "\"")
                    .collect(Collectors.joining(", "))));
            Log.info("AWSTraceHeader: " + m.messageAttributes().get("AWSTraceHeader"));
            sqs.deleteMessage(DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(m.receiptHandle()).build());
        });
        Log.info("message received");
    }

    // A new instance of MyJob is created by Quartz for every job execution
    public static class SqsSendJob implements Job {

       @Inject
       TaskBean taskBean;

       public void execute(JobExecutionContext context) throws JobExecutionException {
          taskBean.sendMessage();
       }
    }

    public static class SqsReceiveJob implements Job {

        @Inject
        TaskBean taskBean;

        public void execute(JobExecutionContext context) throws JobExecutionException {
            taskBean.receiveMessage();
        }
    }
}