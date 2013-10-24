package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.sessions.TransportSessionManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GojaraAdminManager;
import org.jivesoftware.openfire.plugin.gojara.database.SessionEntry;
import org.jivesoftware.openfire.plugin.gojara.utils.JspHelper;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import org.jivesoftware.openfire.XMPPServer;

public final class gojara_002dRegistrationsOverview_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n");

	TransportSessionManager transportManager = TransportSessionManager.getInstance();
	GojaraAdminManager gojaraAdminManager = GojaraAdminManager.getInstance();

	//Helper object for generation of sorting links, column restriction is done in DatabaseManager
	// we need this object so we cann pass the parameters around to our functions
	Map<String, String> sortParams = new HashMap<String, String>();
	if (request.getParameter("sortby") != null && request.getParameter("sortorder") != null) {
		sortParams.put("sortby", request.getParameter("sortby"));
		sortParams.put("sortorder", request.getParameter("sortorder"));
	} else {
		sortParams.put("sortby", "username");
		sortParams.put("sortorder", "ASC");
	}

      out.write("\n\n<html>\n<head>\n<title>Overview of existing Registrations</title>\n<meta name=\"pageID\" content=\"gojaraRegistrationAdministration\" />\n</head>\n<body>\n\t<div align=\"center\">\n\t<ul style=\"list-style: none;padding:0;margin:0;\">\n\t");

		//do unregisters if supplied, we do them here because we generate output that should be displayed
		if (request.getParameterMap() != null) {
			String uninteresting_params = "sortorder sortby page";
			for (Object key : request.getParameterMap().keySet()) {
				if (uninteresting_params.contains(key.toString())) {
					continue;
				}
				String[] uservalues = request.getParameterValues(key.toString());
				for (String transport : uservalues) {
	
      out.write("\n\t<li>");
      out.print(transportManager.removeRegistrationOfUser(transport, key.toString()));
      out.write("</li>\n\t");

		}
			}
		}
	
      out.write("\n\t</ul>\n\t</div>\n\n\n\t<div align=\"center\">\n\t");
 if (!gojaraAdminManager.areGatewaysConfigured()) {
      out.write("\n\t\t<h2><a href=\"gojara-gatewayStatistics.jsp\">Warning: Not all Gateways are configured for admin usage. This means unregistrations will not be properly executed.<br/>\n\t\t Please configure admin_jid = gojaraadmin@");
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("  in Spectrum2 transport configuration.</a></h2>\n\t ");
 } 
      out.write("\n\t\t<h5>Logintime 1970 means User did register but never logged in,\n\t\t\tpropably because of invalid credentials.</h5>\n\t\t\t<br>\n\t\t\t<br>\n\t\tRegistrations total: <b style=\"font-size:150%\">");
      out.print(transportManager.getNumberOfRegistrations());
      out.write("</b><br>\n\t</div>\n\t<br>\n\t");

		//pagination logic
		//get all records, we limit these later. Not all databes support limiting queries so we need to do it the bad way
		ArrayList<SessionEntry> registrations = transportManager.getAllRegistrations(sortParams.get("sortby"),
				sortParams.get("sortorder"));
		
		int numOfSessions = registrations.size();
		// 100 entries is exactly 1 page, 101 entries is 2 pages
		int numOfPages = numOfSessions % 100 == 0 ? (numOfSessions / 100) : (1 + (numOfSessions / 100));
		//lets check for validity if page parameter is supplied, set it to 1 if not in valid range 
		int current_page = 1;
		if (request.getParameter("page") != null) {
			try {
				current_page = Integer.parseInt(request.getParameter("page"));
				if (current_page < 1 || current_page > numOfPages)
					current_page = 1;
			} catch (Exception e) {
			}
		}
		// we now know current_page is in valid range from supplied parameter or standard.
		// this will be our sublist starting index, 0, 100, 200 ... 
		int current_index = (current_page -1)* 100;
		//ending index, 100, 200 etc, when next items > numOfSessions we have reached last page, set proper index so we have no out of bounds
		//ending index is excluded, so 0-100 is 0-99, e.g. item 1-100
		int next_items = current_index + 100;
		if (next_items > numOfSessions)
			next_items = numOfSessions;
	
      out.write("\n\t<p>\n\t\t<br> Pages: [\n\t\t");

			for (int i = 1; i <= numOfPages; i++) {
		
      out.write('\n');
      out.write('	');
      out.write('	');
      out.print("<a href=\"gojara-RegistrationsOverview.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page  == i ? "jive-current" : "") + "\">" + i
						+ "</a>" );
      out.write('\n');
      out.write('	');
      out.write('	');

			}
		
      out.write("\n\t\t]\n\t</p>\n\t<form name=\"unregister-form\" id=\"gojara-RegOverviewUnregister\"\n\t\tmethod=\"POST\">\n\t\t<div class=\"jive-table\">\n\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t<thead>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<th nowrap>#</th>\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("username", sortParams));
      out.write("</th>\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("transport", sortParams));
      out.write("</th>\n\t\t\t\t\t\t<th nowrap>Active?</th>\n\t\t\t\t\t\t<th nowrap>Admin Configured?</th>\n\t\t\t\t\t\t<th nowrap>");
      out.print(JspHelper.sortingHelperRegistrations("lastActivity", sortParams));
      out.write("</th>\n\t\t\t\t\t\t<th nowrap>Unregister?</th>\n\t\t\t\t\t</tr>\n\t\t\t\t</thead>\n\t\t\t\t<tbody>\n\t\t\t\t\t");
	
						int show_number = 1 + current_index;
						for (SessionEntry registration : registrations.subList(current_index, next_items)) {
					
      out.write("\n\t\t\t\t\t<tr class=\"jive-odd\">\n\t\t\t\t\t\t<td>");
      out.print( show_number);
      out.write("</td>\n\t\t\t\t\t\t<td><a\n\t\t\t\t\t\t\thref=\"gojara-sessionDetails.jsp?username=");
      out.print(registration.getUsername());
      out.write("\"\n\t\t\t\t\t\t\ttitle=\"Session Details for ");
      out.print(registration.getUsername());
      out.write('"');
      out.write('>');
      out.print(registration.getUsername());
      out.write("</a></td>\n\t\t\t\t\t\t<td>");
      out.print(registration.getTransport());
      out.write("</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t");

								if (transportManager.isTransportActive(registration.getTransport())) {
							
      out.write(" <img alt=\"Yes\" src=\"/images/success-16x16.gif\"> ");

							 	} else {
							 
      out.write(" <img alt=\"No\" src=\"/images/error-16x16.gif\" title=\"Sending unregister to inactive transport will result in NOT UNREGISTERING the registration.\"> ");

							 	}
							 
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t");
 if (gojaraAdminManager.isGatewayConfigured(registration.getTransport())) { 
      out.write("\n\t\t\t\t\t\t<img alt=\"Yes\" src=\"/images/success-16x16.gif\"> \n\t\t\t\t\t\t");
 	} else { 
      out.write("\n\t\t\t\t\t\t <img alt=\"No\" src=\"/images/error-16x16.gif\" title=\"Sending unregister to unconfigured transport will result in NOT UNREGISTERING the registration.\">\n\t\t\t\t\t\t  ");
 }
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td\n\t\t\t\t\t\t\ttitle=\"");
      out.print(JspHelper.dateDifferenceHelper(registration.getLast_activityAsDate()));
      out.write('"');
      out.write('>');
      out.print(registration.getLast_activityAsDate());
      out.write("</td>\n\t\t\t\t\t\t<td><input type=\"checkbox\"\n\t\t\t\t\t\t\tname=\"");
      out.print(registration.getUsername());
      out.write("\"\n\t\t\t\t\t\t\tvalue=\"");
      out.print(registration.getTransport());
      out.write("\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");

						show_number++;
						}
					
      out.write("\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t</div>\n\t\t<p>\n\t\t\tPages: [\n\t\t\t");

			for (int i = 1; i <= numOfPages; i++) {
		
      out.write("\n\t\t\t");
      out.print("<a href=\"gojara-RegistrationsOverview.jsp?page=" + i + "&sortby=" + sortParams.get("sortby") + "&sortorder="
						+ sortParams.get("sortorder") + "\" class=\"" + (current_page == i ? "jive-current" : "") + "\">" + i
						+ "</a>");
      out.write("\n\t\t\t");

				}
			
      out.write("\n\t\t\t]\n\t\t</p>\n\t\t<br>\n\t\t<div align=\"center\">\n\t\t\t<input type=\"submit\" value=\"Unregister\">\n\t\t</div>\n\t</form>\n</body>\n</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
