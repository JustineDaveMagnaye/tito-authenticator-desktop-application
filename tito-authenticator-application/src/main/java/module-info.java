module prefect.user.management {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires jbcrypt;

    opens com.system.demo to javafx.fxml;
    opens com.system.demo.controllers.dashboard to javafx.fxml;
    opens com.system.demo.controllers.main to javafx.fxml;
    opens com.system.demo.controllers to javafx.fxml;

    exports com.system.demo.controllers;
    exports com.system.demo;
    exports com.system.demo.controllers.main;
    exports com.system.demo.controllers.dashboard;
}