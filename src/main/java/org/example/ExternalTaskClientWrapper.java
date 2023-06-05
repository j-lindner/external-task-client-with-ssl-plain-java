package org.example;

import org.camunda.bpm.client.impl.ExternalTaskClientBuilderImpl;

public class ExternalTaskClientWrapper extends ExternalTaskClientBuilderImpl {
    @Override
    protected void initEngineClient() {
        super.initEngineClient();
    }
}
