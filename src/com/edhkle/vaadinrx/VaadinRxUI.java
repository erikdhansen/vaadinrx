package com.edhkle.vaadinrx;

import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import java.util.logging.Level;

@SuppressWarnings("serial")
@Theme("vaadinrx")
public class VaadinRxUI extends UI {
	final static Logger log = Logger.getLogger(VaadinRxUI.class.getName());
	//Pharmacist p;
	TabSheet tabsheet = new TabSheet();
	Table resultsTable;
	
	String JDBC_URL = "jdbc:mysql://pillbox:p1llb0x@localhost:3306/pillbox?zeroDateTime=convertToNull";
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinRxUI.class, widgetset = "com.edhkle.vaadinrx.widgetset.VaadinrxWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		try {
			initLayout();
		} catch (Exception e) {
			showError(e);
		}
	}

	protected void initLayout() throws Exception {
            Window mainWindow = new Window("VaadinRX Demo Application");
            mainWindow.setContent(new NewUI());
            mainWindow.setWidth("100.0%");
	    addWindow(mainWindow);
    }

	public static void showError(Throwable t) {
		Notification n = new Notification(t.getClass().getSimpleName(), "<br/><i>" + t.getMessage() + "</i>", Notification.TYPE_ERROR_MESSAGE, true);
		n.show(Page.getCurrent());
                log.log(Level.SEVERE, t.getClass().getName() + ": " + t.getMessage(), t);
	}	
	public static void showWarning(String caption, String description) {
		Notification n = new Notification(caption, description, Notification.TYPE_WARNING_MESSAGE, true);
	}
}