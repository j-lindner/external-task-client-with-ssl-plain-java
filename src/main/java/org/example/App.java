package org.example;

import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.variable.ClientValues;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        // bootstrap the client
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .asyncResponseTimeout(1000)
                .build();

        // subscribe to the topic
        client.subscribe("infos")
                .handler((externalTask, externalTaskService) -> {
                    // add the invoice object and its id to a map
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("infos", "weitere infos zu den eheleuten");

                    // select the scope of the variables
                    boolean isRandomSample = Math.random() <= 0.5;
                    if (isRandomSample) {
                        externalTaskService.complete(externalTask, variables);
                    } else {
//                        externalTaskService.handleFailure(externalTask.getId(), "someError", "some error occurred", 0, 0);
                        externalTaskService.complete(externalTask, variables);
                    }

                    System.out.println("The External Task " + externalTask.getId() +
                            " has been completed!");

                }).open();
    }
}
