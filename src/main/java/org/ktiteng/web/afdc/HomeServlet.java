package org.ktiteng.web.afdc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ktiteng.web.thymeleaf.ThymeleafAppUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.WebContext;

@WebServlet("/")
public class HomeServlet extends BaseServlet {
	private static final Logger log = LoggerFactory.getLogger(HomeServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init();
		log.debug("Init done");
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("doGet starts");
		WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale());
		ThymeleafAppUtil.getServletTemplateEngine().process("home", ctx, response.getWriter());
		log.debug("doGet completes.");
	}

}
