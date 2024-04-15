package com.digitalclash.geoclient.gradle;

import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.ServiceReference;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.gradle.workers.WorkAction;
import org.gradle.workers.WorkParameters;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

import javax.inject.Inject;
import java.net.URI;

public abstract class ServiceRunner extends DefaultTask {
    public abstract class StartServiceAction implements WorkAction<StartServiceAction.Parameters> {
        interface Parameters extends WorkParameters {
            abstract Property<ServiceRunner> getServiceRunner();
        }

        @Override
        public void execute() {
            getLogger().quiet("Starting service...");
        }
    }

    @Inject
    abstract public ListProperty<String> getActiveProfiles();

    @Inject
    abstract ExecOperations getExecOperations();

    @Inject
    abstract public RegularFileProperty getJarFile();

    @ServiceReference("serviceRunner")
    abstract Property<ServiceRunner> getServiceRunner();

    @Inject
    abstract public Property<String> getUri();

    @Inject
    abstract public WorkerExecutor getWorkerExecutor();

    static final String ACTIVE_PROFILES_SWITCH = "--spring.profiles.active=";
    static final String SHUTDOWN_ENDPOINT = "/actuator/shutdown";


    @TaskAction
    public void startService() {
        WorkQueue workQueue = getWorkerExecutor().noIsolation();
        workQueue.submit(StartServiceAction.class, parameter -> {
            parameter.getServiceRunner().set(getServiceRunner());
        });
    }
}
