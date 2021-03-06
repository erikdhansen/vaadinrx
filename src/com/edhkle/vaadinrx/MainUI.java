package com.edhkle.vaadinrx;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.edhkle.pocketrx.controller.Pharmacist;
import com.edhkle.pocketrx.dao.UnknownColorException;
import com.edhkle.pocketrx.dao.UnknownScheduleException;
import com.edhkle.pocketrx.dao.UnknownShapeException;
import com.edhkle.pocketrx.model.Pill;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class MainUI extends CustomComponent {
	final static Logger log = Logger.getLogger(MainUI.class.getName());
	String JDBC_URL = "jdbc:mysql://pillbox:p1llb0x@localhost:3306/pillbox?zeroDateTime=convertToNull";
	Pharmacist p = Pharmacist.getPharmacist(JDBC_URL);
	
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Panel mainUIPanel;
	@AutoGenerated
	private VerticalLayout verticalLayout_1;
	@AutoGenerated
	private Accordion mainAccordian;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public MainUI() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		try {
			initAccordian();
			mainAccordian.setSelectedTab(0);
			updateResultsPanel(mainAccordian.getSelectedTab());
		} catch (Throwable e) {
			VaadinRxUI.showError(e);
			log.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage(), (Exception)e);
		}
		// TODO add user code here
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// mainUIPanel
		mainUIPanel = buildMainUIPanel();
		mainLayout.addComponent(mainUIPanel);
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildMainUIPanel() {
		// common part: create layout
		mainUIPanel = new Panel();
//		mainUIPanel.setCaption("VaadinRX Demo Application");
		mainUIPanel.setImmediate(false);
		mainUIPanel.setWidth("100.0%");
		mainUIPanel.setHeight("100.0%");
		
		// verticalLayout_1
		verticalLayout_1 = buildVerticalLayout_1();
		mainUIPanel.setContent(verticalLayout_1);
		
		return mainUIPanel;
	}

	@AutoGenerated
	private VerticalLayout buildVerticalLayout_1() {
		// common part: create layout
		verticalLayout_1 = new VerticalLayout();
		verticalLayout_1.setCaption("layout-caption");
		verticalLayout_1.setImmediate(false);
		verticalLayout_1.setWidth("100.0%");
		verticalLayout_1.setHeight("100.0%");
		verticalLayout_1.setMargin(false);
		
		// mainAccordian
		mainAccordian = new Accordion();
		mainAccordian.setImmediate(false);
		mainAccordian.setWidth("100.0%");
		mainAccordian.setHeight("100.0%");
		verticalLayout_1.addComponent(mainAccordian);
		return verticalLayout_1;
	}
	
	@SuppressWarnings("serial")
	private void initAccordian() throws SQLException {
		mainAccordian.setSizeFull();
		Map<String,Integer> aMap = p.getAlphaList();
		for(Map.Entry<String,Integer> entry : aMap.entrySet()) {
			log.info("Adding tabsheet tab: Key[" + entry.getKey() + "] = " + entry.getValue());
			Table tabTable = new Table();
			tabTable.setCaption(entry.getKey());
			tabTable.setIcon(new ThemeResource("images/pills-3-32.png"));
			tabTable.setColumnIcon("medicineName", new ThemeResource("../images/pills-3-32.png"));
			tabTable.setSizeUndefined();
			tabTable.setWidth("100%");
			//VerticalLayout tabContainer = new VerticalLayout(tabTable);
			//tabContainer.setCaption(entry.getKey() + " [" + entry.getValue() + " records]");
			mainAccordian.addTab(tabTable, entry.getKey() + " [" + entry.getValue() + " records]");
		}
		
		mainAccordian.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
			
			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				updateResultsPanel(event.getTabSheet().getSelectedTab());
			}
		});
	}

	protected void updateResultsPanel(Component selectedTab) {
		log.info("Selected=" + selectedTab.toString() + " Caption=" + selectedTab.getCaption());
		Collection<Pill> pillsToDisplay = null;
		boolean error = true;
		String errorMessage = "";
		try {
			pillsToDisplay = p.getPillsByNameStartingWith(selectedTab.getCaption().substring(0,1));
			error = false;
		} catch (SQLException e) {
			log.log(Level.SEVERE, e.getClass().getName() + e.getMessage(), e);
			errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
		} catch (UnknownColorException e) {
			log.log(Level.SEVERE, e.getClass().getName() + e.getMessage(), e);
			errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
		} catch (UnknownShapeException e) {
			log.log(Level.SEVERE, e.getClass().getName() + e.getMessage(), e);
			errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
		} catch (UnknownScheduleException e) {
			log.log(Level.SEVERE, e.getClass().getName() + e.getMessage(), e);
			errorMessage = e.getClass().getSimpleName() + ": " + e.getMessage();
		}
		if(error) {
			VaadinRxUI.showWarning("Warning: Error building list","<br/><b><i>errorMessage</i></b>");
		}
		Table resultsTable = (Table)selectedTab;
		BeanItemContainer<Pill> pillBeans = new BeanItemContainer<Pill>(Pill.class);
		pillBeans.addAll(pillsToDisplay);
		//pillBeans.addContainerProperty("iconPath", Resource.class, new ThemeResource("../images/thumbnails/no_pill.jpg"));
		resultsTable.setContainerDataSource(pillBeans);
		resultsTable.setColumnHeader("medicineName", "Medicine");
		resultsTable.setColumnHeader("SPLimprint", "Imprint");
		resultsTable.setColumnHeader("rxString", "Description");
		resultsTable.setColumnHeader("author", "Company");
		resultsTable.addItemClickListener(new ItemClickListener() {
			
			@Override
			public void itemClick(ItemClickEvent event) {
				if(event.getItemId() != null) {
					log.info("TableItem selected -- Item: " + event.getItemId().toString());
					if(event.getItemId() instanceof Pill) {
						Pill pill = (Pill)event.getItemId();
						final Window pillDialog = new Window(pill.getMedicineName() + " information");
						pillDialog.setContent(new PillWindowComponent(pill, pillDialog));
						pillDialog.setModal(true);
						mainUIPanel.getUI().addWindow(pillDialog);
					}
				} else {
					log.info("TableItem selected -- NULL");
				}
			}
		});
		resultsTable.setVisibleColumns(new Object[] {"medicineName", "SPLimprint", "rxString", "author" });
		resultsTable.setPageLength((pillBeans.size() > 10) ? 10 : pillBeans.size());
	}

}
