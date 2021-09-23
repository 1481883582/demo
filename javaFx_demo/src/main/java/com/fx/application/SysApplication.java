package com.fx.application;
import com.javafx.JavaFxDemoApplication;
import com.utils.MySystemTray;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class SysApplication extends Application {

    /**
     * 项目favicon图标
     */
//    public static volatile String configFaviconIcon = FileUtil.getAppUpPath(FileConst.SYS_CONFIG, FileConst.SYS_FAVICON);


//    public static Image loadIcon(String configIcon, String defaultIcon) {
//        defaultIcon = FileUtil.isFileExist(configIcon) ? configIcon : defaultIcon;
//        return new Image(FileUtil.getUrIToString(defaultIcon));
//    }


    public Stage config(Stage primaryStage, String fxml, String icon, String title) throws Exception {
//        String defaultFxml = FXControllerConst.INDEX_FXML;
//        String defaultIcon = SysConst.SYS_APP_FAVICON;
//        String defaultTitle = SysConst.SYS_APP_TITLE;
//
//        if (Strings.isNotEmpty(fxml)) {
//            defaultFxml = fxml;
//        }
//
//        if (Strings.isNotEmpty(icon)) {
//            defaultIcon = icon;
//        }
//
//        if (Strings.isNotEmpty(title)) {
//            defaultTitle = title;
//        }

        // 设置stage的scen，然后显示我们的stage
//        primaryStage.setScene(new Scene(JavaFxDemoApplication.LoadFxml(defaultFxml).load()));

        //设置窗口的图标.
//        primaryStage.getIcons().add(new Image(FileUtil.getUrIToString(defaultIcon)));
//        primaryStage.getIcons().add(loadIcon(configFaviconIcon, defaultIcon));
        //名字
//        primaryStage.setTitle(defaultTitle);
	    //存储单例主stage
//	    BaseStage.setStage(primaryStage);
	    // 设置风格为 UTILITY
//	    primaryStage.initStyle(StageStyle.UTILITY);
//	    // 设置父级透明度为0
//	    primaryStage.setOpacity(0);
//
//	    Stage mainStage = new Stage();
//	    // 将 primaryStage 设置为归属对象，即父级窗口
//	    mainStage.initOwner(primaryStage);
//
//
//	    primaryStage.setScene(new Scene(JavaFxDemoApplication.LoadFxml(fxml).load()));

	    //循环计数
//        animation.setCycleCount(Timeline.INDEFINITE);
//        animation.play();

//        LineChart cpuChart = (LineChart) root.lookup("#cpuChart");
//        controller.printlnCpuChart(cpuChart);
//	    MySystemTray.getInstance(mainStage);
	    //显示窗口
	    primaryStage.show();
	    //自由改变大小
        primaryStage.setResizable(false);
        return primaryStage;
    }

    public Stage config(final Stage primaryStage) throws Exception {
        return config(primaryStage, null, null, null);
    }

    public Stage config(final Stage primaryStage, final String fxml) throws Exception {
        return config(primaryStage, fxml, null, null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }


    public void stop() throws Exception {
        System.exit(0);
        log.debug("正在clone（）。。。。");
        super.stop();
    }
}
