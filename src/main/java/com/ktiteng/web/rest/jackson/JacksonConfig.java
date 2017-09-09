package com.ktiteng.web.rest.jackson;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktiteng.cdi.Log;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("unused")
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    @Inject
    @Log
    private Logger log;

    @Override
    public ObjectMapper getContext(final Class<?> type) {
        final ObjectMapper mapper = new ObjectMapper();
//        log.info("Configured Jackson Json module");
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.findAndRegisterModules();
        return mapper;
    }

}