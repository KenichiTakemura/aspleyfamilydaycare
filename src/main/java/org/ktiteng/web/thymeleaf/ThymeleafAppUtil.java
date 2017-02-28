package org.ktiteng.web.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ThymeleafAppUtil {
    private static TemplateEngine servletTemplateEngine;
    private static TemplateEngine fileTemplateEngine;

    public static TemplateEngine getServletTemplateEngine() {

        if (servletTemplateEngine == null) {
            ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
            templateResolver.setTemplateMode("XHTML");
            templateResolver.setPrefix("/templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCacheTTLMs(3600000L);
            servletTemplateEngine = new TemplateEngine();
            servletTemplateEngine.setTemplateResolver(templateResolver);
        }
        return servletTemplateEngine;
    }

    public static TemplateEngine getFileTemplateEngine() {

        if (fileTemplateEngine == null) {
            FileTemplateResolver templateResolver = new FileTemplateResolver();
            templateResolver.setTemplateMode("XHTML");
            templateResolver.setSuffix(".html");
            fileTemplateEngine = new TemplateEngine();
            fileTemplateEngine.setTemplateResolver(templateResolver);
        }
        return fileTemplateEngine;
    }
}