package com.javafx;

import com.fx.application.IndexApplication;
import com.fx.application.TestApplication;
import com.utils.MySystemTray;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class JavaFxDemoApplication {

	private static ApplicationContext applicationContext;



//	public static FXMLLoader LoadFxml(String fxmlPath) {
//		FXMLLoader fxmlLoader = new FXMLLoader();
//		fxmlLoader.setLocation(JavaFxDemoApplication.class.getResource(fxmlPath));
//		fxmlLoader.setControllerFactory(applicationContext::getBean);
//		return fxmlLoader;
//	}

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(JavaFxDemoApplication.class, args);
		Application.launch(TestApplication.class, args);
	}

}
