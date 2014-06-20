package com.edhkle.vaadinrx;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;

import com.edhkle.pocketrx.controller.Pharmacist;
import com.edhkle.pocketrx.model.Pill;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
			//p = Pharmacist.getPharmacist(JDBC_URL);			
			initLayout();
		} catch (Exception e) {
			showError(e);
		}
	}

	protected void initLayout() throws Exception {
		final VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);
		setContent(mainLayout);
		
		Panel mainPanel = new Panel("VaadinRX Demo Application");
		mainPanel.setIcon(new ThemeResource("../images/pill.png"));
//		mainPanel.setHeight("800");
//		mainPanel.setWidth("600");
		mainLayout.addComponent(mainPanel);
		mainPanel.setContent(new MainUI());
//		HorizontalSplitPanel split = new HorizontalSplitPanel();
//		mainPanel.setContent(split);
//		final VerticalLayout leftPanel = new VerticalLayout();
//		final VerticalLayout rightPanel = new VerticalLayout();
//		split.setSplitPosition(15, Sizeable.UNITS_PERCENTAGE);
//		split.setLocked(true);
//		split.setWidth("100%");
//		split.setFirstComponent(leftPanel);
//		split.setSecondComponent(rightPanel);
//		
//		initTabsheet();
//		mainPanel.setContent(tabsheet);
//		Button button = new Button("Load Records");
//		button.setWidth("100%");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				try {
//					log.info("Accordian caption=" + tabsheet.getCaption() + " selected tab=" + tabsheet.getSelectedTab() + " selectedTabCaption=" + tabsheet.getSelectedTab().getCaption() + " tabDesc=" + tabsheet.getSelectedTab().getDescription());
//					updateResultsPanel();
//				} catch (Exception e) {
//					Notification n = new Notification(e.getClass().getSimpleName(), "<br/><i>" + e.getMessage() + "</i>", Notification.TYPE_ERROR_MESSAGE, true);
//					n.show(Page.getCurrent());					
//				}
//			}
//		});

//		leftPanel.addComponent(button);		
//		leftPanel.setComponentAlignment(button, Alignment.BOTTOM_CENTER);
//		
//		Panel resultsPanel = new Panel("Selected Results");
//		rightPanel.addComponent(resultsPanel);
//		updateResultsPanel();
//		resultsPanel.setContent(resultsTable);
	}
	
//	protected void initTabsheet() throws SQLException {
//		tabsheet.setSizeFull();
//		Map<String,Integer> aMap = p.getAlphaList();
//		for(Map.Entry<String,Integer> entry : aMap.entrySet()) {
//			log.info("Adding tabsheet tab: Key[" + entry.getKey() + "] = " + entry.getValue());
//			tabsheet.addTab(new Label(entry.getKey() + ": (" + entry.getValue() + " records)"), entry.getKey());
//		}
//	}
//	

	public static void showError(Throwable t) {
		Notification n = new Notification(t.getClass().getSimpleName(), "<br/><i>" + t.getMessage() + "</i>", Notification.TYPE_ERROR_MESSAGE, true);
		n.show(Page.getCurrent());		
	}	
	public static void showWarning(String caption, String description) {
		Notification n = new Notification(caption, description, Notification.TYPE_WARNING_MESSAGE, true);
	}
}