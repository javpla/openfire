package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.sessions.TransportSessionManager;
import org.jivesoftware.openfire.plugin.gojara.sessions.GojaraAdminManager;
import java.util.Map;
import java.util.Set;
import org.jivesoftware.openfire.XMPPServer;

public final class gojara_002dgatewayStatistics_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n");

	TransportSessionManager transportSessionManager = TransportSessionManager.getInstance();
	GojaraAdminManager gojaraAdminManager = GojaraAdminManager.getInstance();
	gojaraAdminManager.gatherGatewayStatistics();
	String domain = XMPPServer.getInstance().getServerInfo().getXMPPDomain();

      out.write("\n<html>\n<head>\n<title>Spectrum2 gateway stats</title>\n<meta name=\"pageID\" content=\"gojaraGatewayStatistics\" />\n</head>\n<body>\n\n\t");

		if (!gojaraAdminManager.areGatewaysConfigured()) {
	
      out.write("\n\t\t<h2 style=\"color: red\" align=\"center\">\n\t\t\tWarning: Not all Gateways are configured for admin usage. Affected\n\t\t\tgateways will not show spectrum2 data.<br /> Please configure admin_jid =\n\t\t\tgojaraadmin@");
      out.print(domain );
      out.write(" in Spectrum2 transport configuration.\n\t\t</h2>\n\t<hr />\n\t");

		}
	
      out.write("\n\n\t<br/><br/>\n\t<div class=\"jive-table\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t<thead>\n\t\t\t\t<tr>\n\t\t\t\t\t<th nowrap>Name</th>\n\t\t\t\t\t<th nowrap>Admin Configured?</th>\n\t\t\t\t\t<th nowrap># Online Users</th>\n\t\t\t\t\t<th nowrap># Registrations</th>\n\t\t\t\t\t<th nowrap>Uptime</th>\n\t\t\t\t\t<th nowrap># Messages received</th>\n\t\t\t\t\t<th nowrap># Messages sent</th>\n\t\t\t\t\t<th nowrap>Used Memory</th>\n\t\t\t\t\t<th nowrap>Avg. Mem. per User</th>\n\t\t\t\t</tr>\n\t\t\t</thead>\n\t\t\t<tbody>\n\t\t\t\t");

				Set<String> gateways = transportSessionManager.getActiveGateways();
				for (String gateway : gateways) {
				
      out.write("\t\n\t\t\t\t\t<tr class=\"jive-odd\">\n\t\t\t\t\t<td>");
      out.print(gateway );
      out.write(' ');
 if (!gateway.contains(domain)) { 
      out.write("\n\t\t\t\t\t\t<img alt=\"gateway configuration info\" src=\"/images/header-help_new.gif\" title=\"Component name does not include server name: ");
      out.print(domain);
      out.write(". \n\t\t\t\t\t\tIt should probably be configured like this: transport.server-name, e.g.: icq.");
      out.print(domain);
      out.write("\">\n\t\t\t\t\t");
 } 
      out.write(" \n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t");
 if (gojaraAdminManager.isGatewayConfigured(gateway)) { 
      out.write("\n\t\t\t\t\t\t<img alt=\"Yes\" src=\"/images/success-16x16.gif\"> \n\t\t\t\t\t\t");
 	} else { 
      out.write("\n\t\t\t\t\t\t <img alt=\"No\" src=\"/images/error-16x16.gif\" title=\"Will probably not show correct # of online users and not do unregister properly.\">\n\t\t\t\t\t\t  ");
 }
      out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t<td><div style=\"font-size:140%\">");
      out.print(transportSessionManager.getNumberOfActiveSessionsFor(gateway));
      out.write("</div></td>\n\t\t\t\t\t<td><div style=\"font-size:140%\">");
      out.print(transportSessionManager.getNumberOfRegistrationsForTransport(gateway));
      out.write("</div></td>\n\t\t\t\t\t<td>");
      out.print(gojaraAdminManager.getStatisticsPresentationString(gateway, "uptime"));
      out.write("</td>\n\t\t\t\t\t<td>");
      out.print(gojaraAdminManager.getStatisticsPresentationString(gateway, "messages_from_xmpp"));
      out.write("</td>\n\t\t\t\t\t<td>");
      out.print(gojaraAdminManager.getStatisticsPresentationString(gateway, "messages_to_xmpp") );
      out.write("</td>\n\t\t\t\t\t<td>");
      out.print(gojaraAdminManager.getStatisticsPresentationString(gateway, "used_memory") );
      out.write("</td>\n\t\t\t\t\t<td>");
      out.print(gojaraAdminManager.getStatisticsPresentationString(gateway, "average_memory_per_user") );
      out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
 } 
      out.write("\n\t\t\t</tbody>\n\t\t</table>\n\t</div>\n</body>\n</html>");
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
