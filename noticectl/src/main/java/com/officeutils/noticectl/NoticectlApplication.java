package com.officeutils.noticectl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.officeutils.noticectl.xml.XSLUtils;

@SpringBootApplication
public class NoticectlApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NoticectlApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// throw new UnsupportedOperationException("Unimplemented method 'run'");
		String op = "format";
		String outerTag = "body_lines";
		String innerTag = "body_lines";

		if (args.length > 1) {
			String outerXML = args[0];
			String innerXML = args[1];
			//XMLUtil.formatEnclose(outerXML, outerTag, innerXML, innerTag);

			ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

			// get an instance of the XMLUtil bean
			XSLUtils xslUtils = context.getBean(XSLUtils.class, "firstTagName", "secondTagName");

			// process the XML files
			xslUtils.processXml();
			
		}
	}

}
