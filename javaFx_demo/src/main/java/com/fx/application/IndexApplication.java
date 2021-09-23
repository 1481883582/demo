package com.fx.application;

import com.utils.MySystemTray;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IndexApplication extends SysApplication {

    @Override
    public void init() throws Exception {
        log.debug("正在init（）。。。。");
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.debug("正在start（）。。。。");
	    MySystemTray.getInstance().listen(primaryStage);
//        config(primaryStage, "/App.fxml").show();
    }

    @Override
    public void stop() throws Exception {
        log.debug("正在stop（）。。。。");
        super.stop();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        log.debug("正在clone（）。。。。");
        return super.clone();
    }
}
