package com.jerrylin.myhouse.web.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springside.modules.utils.Encodes;

import com.jerrylin.myhouse.service.AppConfigService;

public class FilepathFilter implements Filter {
	
	private Pattern houseImagePattern = Pattern.compile("^/ht\\d+/(.*?)\\.(jpg|png|zip)$", Pattern.CASE_INSENSITIVE);
	private Pattern bannerImagePattern = Pattern.compile("^/b/\\d+\\.jpg$", Pattern.CASE_INSENSITIVE);
	private Pattern uploadImagePattern = Pattern.compile("^/\\d+(/d2|/d3)?/[\\da-z]+\\.(jpg|zip)$", Pattern.CASE_INSENSITIVE);
	
	private AppConfigService appConfigService;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestUri = httpRequest.getRequestURI();
//		System.out.println(httpRequest.getRequestURI());
//		System.out.println(httpRequest.getRequestURL());
		Matcher matcher = null;
		matcher = houseImagePattern.matcher(requestUri);
		if (matcher.matches()) {
			if (requestUri.endsWith("zip"))
				httpRequest.getRequestDispatcher(appConfigService.getPackagePrefix() + Encodes.urlDecode(requestUri)).forward(request, response);
			else
				httpRequest.getRequestDispatcher(appConfigService.getUploadPrefix() + Encodes.urlDecode(requestUri)).forward(request, response);
			return;
		}
		matcher = uploadImagePattern.matcher(requestUri);
		if (matcher.matches()) {
			if (requestUri.endsWith("jpg"))
				httpRequest.getRequestDispatcher(appConfigService.getUploadPrefix() + requestUri).forward(request, response);
			else
				httpRequest.getRequestDispatcher(appConfigService.getPackagePrefix() + requestUri).forward(request, response);
			return;
		}
		matcher = bannerImagePattern.matcher(requestUri);
		if (matcher.matches()) {
			httpRequest.getRequestDispatcher(appConfigService.getBannerPrefix() + requestUri).forward(request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		appConfigService = ctx.getBean("appConfigService", AppConfigService.class);
	}

}
