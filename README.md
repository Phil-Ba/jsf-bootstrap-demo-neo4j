Hello,

this application is a demo project, showing of the use of Spring, JSF and Bootstrap together. We chose Spring, because we
wanted a lightweight framework which is as application server independent as possible. Nevertheless the main concern of this
application is the integration of JSF and Bootstrap, and the application could also have been realized with JEE.
The Spring scopes were extended with a "view" scope, which you can find in the de.bit.spring.scope package.
Further in the faces-config.xml the SpringBeanFacesELResolver is configured as EL resolver to make the Spring beans available
the JSF.
For validation Bean Validation is used. To validate that the startTime is greater than the endTime, a custom JSF validator
is used(BeforeValidator).Persistence is achieved by using Spring Data and neo4j. You need to configure the path where the database
should be created in the applicationContext.xml. To reset the database( droppAll ;) ), just delete that directory.

MyFaces was chosen as JSF implementation, since at the time of writing Mojarra had a rednering bug which impacted 
the application(https://java.net/jira/browse/JAVASERVERFACES-3169).
Since this is a rather basic demo application, we forewent the classic MVC pattern for simplicity's sake. Instead most
of the logic is handled by the controllers, even persistence which should  ideally handled in the service layer.

The main page, index.xhtml, consists of two tabs. The tab controller holds the information which tab is currently active.
Only the active tab is renders. Clicking a tab will trigger an AJAX request which will repaint the tab contents. The content
of the tabs is inserted with <ui:include/> from the dayView.xhtml and eventsOverviewView.xhtml files.
The dayView.xhtml shows all the events for a day. For the popup calendar the bootstrap-datepicker plugin is used. The events
fired by it, are caught with a <f:ajax event="change"/> tag.
The eventsOverView.xhtml shows all events in a table with pagination. The table is a standard JSF table. The pagination controls 
are implemented by the pagination composite component which relies on an implementation of the PaginationController interface. 
The pagination composite component should be useable with any kind of table(or JSF component). 

In the main.less file you can tweak the css of the application. We used this a single colour layout, and by changing the 
@brand-primary variable, you can change the colour of the UI. Give it a try and see the results for yourself. You can also try
to tweak the percentage values of the lighten/darken functions. The main.less file is compiled by maven and then placed in
in the src/main/webapp/resources/css directory.
