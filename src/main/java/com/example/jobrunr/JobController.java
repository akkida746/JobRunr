package com.example.jobrunr;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobScheduler jobScheduler;
    @Autowired
    private JobService jobService;

    @GetMapping(value = "/simple-job")
    public String simpleJob(@RequestParam(value ="name", defaultValue = "simple") String name){
        final JobId enqueuedJobId = jobScheduler.enqueue(() -> jobService.doSimpleJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId;
    }

    @GetMapping(value = "/long-running-job", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String longRunningJob(@RequestParam(value = "name", defaultValue = "World") String name) {
        final JobId enqueuedJobId = jobScheduler.<JobService>enqueue(myService -> myService.doLongRunningJob("Hello " + name));
        return "Job Enqueued: " + enqueuedJobId;
    }
}
