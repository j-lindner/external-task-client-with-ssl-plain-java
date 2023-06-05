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
        client.subscribe("invoiceCreator")
                .handler((externalTask, externalTaskService) -> {

                    // instantiate an invoice object
                    Invoice invoice = new Invoice("A123");

                    // create an object typed variable with the serialization format XML
                    ObjectValue invoiceValue = ClientValues
                            .objectValue(invoice)
                            .serializationDataFormat("application/xml")
                            .create();

                    // add the invoice object and its id to a map
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("invoiceId", invoice.id);
                    variables.put("invoice", invoiceValue);

                    // select the scope of the variables
                    boolean isRandomSample = Math.random() <= 0.5;
                    if (isRandomSample) {
                        externalTaskService.complete(externalTask, variables);
                    } else {
                        externalTaskService.complete(externalTask, null, variables);
                    }

                    System.out.println("The External Task " + externalTask.getId() +
                            " has been completed!");

                }).open();

        client.subscribe("invoiceArchiver")
                .handler((externalTask, externalTaskService) -> {
                    TypedValue typedInvoice = externalTask.getVariableTyped("invoice");
                    Invoice invoice = (Invoice) typedInvoice.getValue();
                    System.out.println("Invoice on process scope archived: " + invoice);
                    externalTaskService.complete(externalTask);
                }).open();
    }
}
