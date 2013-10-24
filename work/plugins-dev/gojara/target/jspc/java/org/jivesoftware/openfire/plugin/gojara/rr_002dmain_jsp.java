package org.jivesoftware.openfire.plugin.gojara;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.openfire.plugin.gojara.database.DatabaseManager;
import org.jivesoftware.openfire.plugin.gojara.permissions.PermissionManager;
import org.dom4j.tree.DefaultElement;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.group.Group;
import org.jivesoftware.openfire.session.ComponentSession;
import java.util.Collection;
import org.jivesoftware.openfire.SessionManager;
import org.jivesoftware.util.JiveGlobals;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.jivesoftware.util.ParamUtils;

public final class rr_002dmain_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');

	webManager.init(request, response, session, application, out);
	boolean save = request.getParameter("save") != null;
	boolean success = request.getParameter("success") != null;
	String persistentRosterParam = request.getParameter("persistentEnabled");
	boolean persistentRoster = persistentRosterParam == null? false : persistentRosterParam.equals("true");

	String sparkdiscoParam = request.getParameter("sparkDiscoInfo");
	boolean sparkDiscoInfo = sparkdiscoParam == null ? false : sparkdiscoParam.equals("true");
	
	String iqLastFilterPram = request.getParameter("iqLastFilter");
	boolean iqLastFilter = iqLastFilterPram == null ? false : iqLastFilterPram.equals("true");
	
	String mucFilterParam = request.getParameter("mucFilter");
	boolean mucFilter = mucFilterParam == null ? false : mucFilterParam.equals("true");
	
	String[] componentsEnabled = request.getParameterValues("enabledComponents[]");
	PermissionManager _pmanager = new PermissionManager();
	DatabaseManager _db;

	Map<String, String> errors = new HashMap<String, String>();
	if (save) {
		for (String property : JiveGlobals.getPropertyNames("plugin.remoteroster.jids")) {
			JiveGlobals.deleteProperty(property);
		}
		if (componentsEnabled != null) {
			for (int i = 0; i < componentsEnabled.length; i++) {
				JiveGlobals.setProperty("plugin.remoteroster.jids." + componentsEnabled[i], "true");
				String group = request.getParameter("input_group." + componentsEnabled[i]);
				if (group != null) {
					_pmanager.setGroupForGateway(componentsEnabled[i], group);
				}
			}
		}
		JiveGlobals.setProperty("plugin.remoteroster.persistent", (persistentRoster ? "true" : "false"));
		JiveGlobals.setProperty("plugin.remoteroster.sparkDiscoInfo", (sparkDiscoInfo ? "true" : "false"));
		JiveGlobals.setProperty("plugin.remoteroster.iqLastFilter", (iqLastFilter ? "true" : "false"));
		JiveGlobals.setProperty("plugin.remoteroster.mucFilter", (mucFilter ? "true" : "false"));
		response.sendRedirect("rr-main.jsp?success=true");
		return;
	}

	// Get the session manager
	SessionManager sessionManager = webManager.getSessionManager();

	Collection<ComponentSession> sessions = sessionManager.getComponentSessions();

	_db = DatabaseManager.getInstance();

      out.write("\n\n<html>\n<head>\n<title>Gojara Settings</title>\n<link href=\"./css/rr.css\" rel=\"stylesheet\" type=\"text/css\">\n<script src=\"./js/http.js\" type=\"text/javascript\"></script>\n<script src=\"./js/jquery.js\" type=\"text/javascript\"></script>\n<script src=\"./js/rr.js\" type=\"text/javascript\"></script>\n<script src=\"./js/jquery.sparkline.js\" type=\"text/javascript\"></script>\n<script src=\"./js/jquery.horiz-bar-graph.js\" type=\"text/javascript\"></script>\n<!--[if lte IE 8]><script language=\"javascript\" type=\"text/javascript\" src=\"./js/excanvas.min.js\"></script><![endif]-->\n<script language=\"javascript\" type=\"text/javascript\" src=\"./js/jquery.flot.js\"></script>\n<script language=\"javascript\" type=\"text/javascript\" src=\"./js/jquery.flot.pie.js\"></script>\n\n<meta name=\"pageID\" content=\"remoteRoster\" />\n<meta name=\"helpPage\" content=\"\" />\n\n</head>\n<body>\n\n\t<p>Any components configured here will allow the external component associated with them full control over their\n\t\tdomain within any user's roster. Before enabling Remote Roster Management support for an external component, first\n");
      out.write("\t\tconnect it like you would any external component. Once it has connected and registered with Openfire, it's JID should\n\t\tshow up below and you can enable Remote Roster support.</p>\n\n\t");

		if (success) {
	
      out.write("\n\n\t<div class=\"jive-success\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n\t\t\t\t\t<td class=\"jive-icon-label\">Settings saved!</td>\n\t\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n\t</div>\n\t<br>\n\n\t");

		} else if (errors.size() > 0) {
	
      out.write("\n\n\t<div class=\"jive-error\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n\t\t\t\t\t<td class=\"jive-icon-label\">Error saving settings!</td>\n\t\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n\t</div>\n\t<br>\n\n\t");

		}
	
      out.write("\n\n\t<form action=\"rr-main.jsp?save\" method=\"post\">\n\n\t\t<div class=\"jive-contentBoxHeader\">Connected Gateway Components</div>\n\t\t<div class=\"jive-contentBox\">\n\n\t\t\t<p>Select which components you want to enable remote roster on:</p>\n\t\t\t");

				boolean gatewayFound = false;
				int i = 0;
				for (ComponentSession componentSession : sessions) {
					if (!componentSession.getExternalComponent().getCategory().equals("gateway")) {
						continue;
					}
					gatewayFound = true;

					long incoming = componentSession.getNumClientPackets();
					long outgoing = componentSession.getNumServerPackets();
					long both = incoming + outgoing;
					int incomingPercent = (int) (incoming * 100 / both);
					int outgoingPercent = (int) (outgoing * 100 / both);
			
      out.write("\n\t\t\t<table class=\"gatewayHeader\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"gatewayCheckbox\"><input type=\"checkbox\" name=\"enabledComponents[]\"\n\t\t\t\t\t\t\tvalue=\"");
      out.print(componentSession.getExternalComponent().getInitialSubdomain());
      out.write("\"\n\t\t\t\t\t\t\t");
      out.print(JiveGlobals.getBooleanProperty("plugin.remoteroster.jids."
						+ componentSession.getExternalComponent().getInitialSubdomain(), false) ? "checked=\"checked\""
						: "");
      out.write(" />\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"gatewayName\">");
      out.print(componentSession.getExternalComponent().getName());
      out.write("</td>\n\t\t\t\t\t\t<td class=\"gatewayIcons\"><img src=\"images/log-16x16.png\" onclick=\"slideToggle('#logs");
      out.print(i);
      out.write("')\"><img\n\t\t\t\t\t\t\tsrc=\"images/permissions-16x16.png\" id=\"showPermissions\" onclick=\"slideToggle('#permission");
      out.print(i);
      out.write("')\"><img\n\t\t\t\t\t\t\tsrc=\"images/info-16x16.png\" id=\"showConfig\" onclick=\"slideToggle('#config");
      out.print(i);
      out.write("')\"></td>\n\t\t\t\t\t</tr>\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t\t<div id=\"config");
      out.print(i);
      out.write("\" class=\"slider\">\n\t\t\t\t<div class=\"sildeHeader\">Information</div>\n\t\t\t\t<table class=\"configTable\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr id=\"logodd\">\n\t\t\t\t\t\t\t<td width=\"200px\">Domain:</td>\n\t\t\t\t\t\t\t<td>");
      out.print(componentSession.getExternalComponent().getInitialSubdomain());
      out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logeven\">\n\t\t\t\t\t\t\t<td>Status:</td>\n\t\t\t\t\t\t\t<td>Online</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logodd\">\n\t\t\t\t\t\t\t<td>Packages Send/Received:</td>\n\t\t\t\t\t\t\t<td><dl class=\"browser-data\" title=\"\">\n\t\t\t\t\t\t\t\t\t<dt>Incoming</dt>\n\t\t\t\t\t\t\t\t\t<dd>");
      out.print(incomingPercent);
      out.write("</dd>\n\t\t\t\t\t\t\t\t\t<dt>Outgoing</dt>\n\t\t\t\t\t\t\t\t\t<dd>");
      out.print(outgoingPercent);
      out.write("</dd>\n\t\t\t\t\t\t\t\t</dl></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t<div id=\"permission");
      out.print(i);
      out.write("\" class=\"slider\">\n\t\t\t\t<div class=\"sildeHeader\">Access control</div>\n\t\t\t\t<table class=\"groupTable\">\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr id=\"loghead\">\n\t\t\t\t\t\t\t<td colspan=\"3\">You can limit the access to the external component to an existing group</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"permissionTableColumn\">Groupname:</td>\n\t\t\t\t\t\t\t<td><input class=\"groupInput\" type=\"text\" id=\"groupSearch");
      out.print(i);
      out.write("\"\n\t\t\t\t\t\t\t\tname=\"input_group.");
      out.print(componentSession.getExternalComponent().getInitialSubdomain());
      out.write("\" alt=\"Find Groups\"\n\t\t\t\t\t\t\t\tonkeyup=\"searchSuggest('");
      out.print(i);
      out.write("');\" autocomplete=\"off\"\n\t\t\t\t\t\t\t\tvalue=\"");
      out.print(_pmanager.getGroupForGateway(componentSession.getExternalComponent().getInitialSubdomain()));
      out.write("\">\n\t\t\t\t\t\t\t\t<div id=\"search_suggest");
      out.print(i);
      out.write("\"></div></td>\n\t\t\t\t\t\t\t<td style=\"vertical-align: top;\">\n\t\t\t\t\t\t\t\t<div class=\"ajaxloading\" id=\"ajaxloading");
      out.print(i);
      out.write("\"></div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t\t<div id=\"logs");
      out.print(i);
      out.write("\" class=\"slider\">\n\t\t\t\t");

					int iqs = _db.getPacketCount(componentSession.getExternalComponent().getInitialSubdomain(),
								Class.forName("org.xmpp.packet.IQ"));
						int msgs = _db.getPacketCount(componentSession.getExternalComponent().getInitialSubdomain(),
								Class.forName("org.xmpp.packet.Message"));
						int rosters = _db.getPacketCount(componentSession.getExternalComponent().getInitialSubdomain(),
								Class.forName("org.xmpp.packet.Roster"));
						int presences = _db.getPacketCount(componentSession.getExternalComponent().getInitialSubdomain(),
								Class.forName("org.xmpp.packet.Presence"));
				
      out.write("\n\t\t\t\t<div class=\"sildeHeader\">Logs & Statistics</div>\n\n\t\t\t\t<table class=\"logtable\">\n\t\t\t\t\t<tfoot>\n\t\t\t\t\t\t<tr id=\"logfoot\">\n\t\t\t\t\t\t\t<td colspan=\"2\">Packages being logged for ");
      out.print(JiveGlobals.getIntProperty("plugin.remoteroster.log.cleaner.minutes", 60));
      out.write("\n\t\t\t\t\t\t\t\tminutes\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td><a style=\"float: right;\"\n\t\t\t\t\t\t\t\tonClick=\"window.open('liveStats.jsp?component=");
      out.print(componentSession.getExternalComponent().getInitialSubdomain());
      out.write("','mywindow','width=1200,height=700')\">Show\n\t\t\t\t\t\t\t\t\trealtime Log</a>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tfoot>\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr id=\"loghead\">\n\t\t\t\t\t\t\t<td width=\"200px\">Paket type</td>\n\t\t\t\t\t\t\t<td width=\"100px\">Number</td>\n\t\t\t\t\t\t\t<td></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logodd\">\n\t\t\t\t\t\t\t<td>IQ</td>\n\t\t\t\t\t\t\t<td id=\"logiq");
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(iqs);
      out.write("</td>\n\t\t\t\t\t\t\t<td rowspan=\"5\"><div id=\"pie");
      out.print(i);
      out.write("\" class=\"graph\"></div></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logeven\">\n\t\t\t\t\t\t\t<td>Messages</td>\n\t\t\t\t\t\t\t<td id=\"logmsg");
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(msgs);
      out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logodd\">\n\t\t\t\t\t\t\t<td>Roster</td>\n\t\t\t\t\t\t\t<td id=\"logroster");
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(rosters);
      out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logeven\">\n\t\t\t\t\t\t\t<td>Presence</td>\n\t\t\t\t\t\t\t<td id=\"logpresence");
      out.print(i);
      out.write('"');
      out.write('>');
      out.print(presences);
      out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr id=\"logodd\">\n\t\t\t\t\t\t\t<td><span style=\"font-weight: bold;\">Total:</span></td>\n\t\t\t\t\t\t\t<td><span style=\"font-weight: bold;\">");
      out.print(iqs + msgs + rosters + presences);
      out.write("</span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\n\t\t\t</div>\n\n\n\n\n\t\t\t");

				++i;
				}
			
      out.write("\n\t\t\t");

				if (!gatewayFound) {
			
      out.write("\n\t\t\t<span style=\"font-weight: bold\">No connected external gateway components found.</span>\n\t\t\t");

				}
			
      out.write("\n\t\t</div>\n\t\t\n\t\t\n<div class=\"jive-contentBoxHeader\">General Options</div>\n<div class=\"jive-contentBox\">\n   <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n   <tbody>\n   <tr valign=\"top\">\n       <td width=\"100%\">\n           <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n           <tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td><input type=\"checkbox\" name=\"persistentEnabled\" id=\"GO1\" value=\"true\"\n\t\t\t\t\t\t");
      out.print(JiveGlobals.getBooleanProperty("plugin.remoteroster.persistent", false) ? "checked=\"checked\"" : "");
      out.write(" />\n\n\t\t\t\t\t</td>\n\t\t\t\t\t<td><label for=\"GO1\">Enable persistent Roster</label></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td />\n\t\t\t\t\t<td align=\"left\" style=\"font-size: -3; color: grey\">When Persistent-Roster is enabled, contacts will be saved to database and\n\t\t\t\t\tno contacts will be deleted\tby GoJara automatically.<br>\t\t\t\t\t\n\t\t\t\t\tWhen Persistent-Roster is disabled, contacts will not be saved to databse and \n\t\t\t\t\tGoJara will automatically delete all Legacy-RosterItems from the OF-Roster of a User upon logout. </td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td><input type=\"checkbox\" name=\"mucFilter\" id=\"GO2\" value=\"true\"\n\t\t\t\t\t\t");
      out.print(JiveGlobals.getBooleanProperty("plugin.remoteroster.mucFilter", false) ? "checked=\"checked\"" : "");
      out.write(" />\n\t\t\t\t\t</td>\n\t\t\t\t\t<td><label for=\"GO2\">Only allow internal Jabber Conferences</label></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td />\n\t\t\t\t\t<td align=\"left\" style=\"font-size: -3; color: grey\">Spectrum might add MUC(Multi User Chat) to supported features\n\t\t\t\t\t of some Transports. If this should not be allowed, because only internal Jabber Conferences should be used, GoJara\n\t\t\t\t\t can remove these.</td>\n\t\t\t\t</tr>\n           </tbody>\n           </table>\n       </td>\n   </tr>\n   </tbody>\n   </table>\n</div>\n\n\t\t<br /> <br />\n\t\t<div class=\"jive-contentBoxHeader\">Client specific options</div>\n\t\t<div class=\"jive-contentBox\">\n\t\t\t<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr valign=\"top\">\n\t\t\t\t\t\t<td width=\"1%\" nowrap class=\"c1\">Spark:</td>\n\t\t\t\t\t\t<td width=\"99%\">\n\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"sparkDiscoInfo\" id=\"SDI\" value=\"true\"\n\t\t\t\t\t\t\t\t\t\t\t");
      out.print(JiveGlobals.getBooleanProperty("plugin.remoteroster.sparkDiscoInfo", false) ? "checked=\"checked\""
					: "");
      out.write(" />\n\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t<td><label for=\"SDI\"> Support jabber:iq:registered feature</label></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td />\n\t\t\t\t\t\t\t\t\t\t<td align=\"left\" style=\"font-size: -3; color: grey\">If you use Spark clients within your network, it\n\t\t\t\t\t\t\t\t\t\t\tmight be necessary to modify the service discovery packets between Spark and the external component. If you\n\t\t\t\t\t\t\t\t\t\t\tcheck this RemoteRoster will add the feature \"jabber:iq:registered\" to the disco#info to indicate that the\n\t\t\t\t\t\t\t\t\t\t\tClient is registered with the external component.</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td><input type=\"checkbox\" name=\"iqLastFilter\" id=\"SDI2\" value=\"true\"\n\t\t\t\t\t\t\t\t\t\t\t");
      out.print(JiveGlobals.getBooleanProperty("plugin.remoteroster.iqLastFilter", false) ? "checked=\"checked\""
					: "");
      out.write(" />\n\n\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t<td><label for=\"SDI\">Reply to jabber:iq:last </label></td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t<td />\n\t\t\t\t\t\t\t\t\t\t<td align=\"left\" style=\"font-size: -3; color: grey\">Some clients try to check how long a contact is already offline.\n\t\t\t\t\t\t\t\t\t\t This feature is not supported by spectrum so it won't response to this IQ stanza. To prevent the client from waiting\n\t\t\t\t\t\t\t\t\t\t for a response we could answer with a service-unavailable message as described in XEP-12.</td>\n\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t</tbody>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t</div>\n\n\n\t\t<input type=\"submit\" name=\"save\" value=\"Save Settings\" />\n\t</form>\n\n</body>\n</html>\n");
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
