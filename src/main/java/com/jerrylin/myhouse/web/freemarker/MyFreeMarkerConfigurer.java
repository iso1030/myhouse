package com.jerrylin.myhouse.web.freemarker;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * 扩展Spring FreemarkerView的内置配置类，重写了返回Configuration的方法，<br>
 * 处理其默认的异常处理机制
 * 
 * @author user
 *
 */
public class MyFreeMarkerConfigurer extends FreeMarkerConfigurer {

	protected Configuration newConfiguration() throws IOException, TemplateException {
		Configuration cfg = super.newConfiguration();
//		cfg.setTemplateExceptionHandler(new MusicTemplateExceptionHandler());
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
//		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
//		System.out.println(cfg.getTemplateExceptionHandler() == TemplateExceptionHandler.DEBUG_HANDLER);
		return cfg;
	}

}
