package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import java.util.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.muc.MultiUserChatService;

public final class workgroup_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n<html>\n    <head>\n        <title>Workgroup Settings</title>\n        <meta name=\"pageID\" content=\"workgroup-settings\"/>\n        <!--<meta name=\"helpPage\" content=\"edit_global_workgroup_settings.html\"/>-->\n    </head>\n    <body>\n");


    // Get a workgroup manager
    WorkgroupManager wgManager = WorkgroupManager.getInstance();

    // If the workgroup manager is null, service is down so redirect:
    if (wgManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write('\n');
  // Get parameters
    int maxChats = ParamUtils.getIntParameter(request,"maxChats",0);
    int minChats = ParamUtils.getIntParameter(request,"minChats",0);
    long requestTimeout = ParamUtils.getLongParameter(request,"requestTimeout",0);
    long offerTimeout = ParamUtils.getLongParameter(request,"offerTimeout",0);
    int rejectionTimeout = ParamUtils.getIntParameter(request,"rejectionTimeout",0);
    int maxOverflows = ParamUtils.getIntParameter(request,"maxOverflows",3);
    boolean canChangeName = ParamUtils.getBooleanParameter(request, "canChangeName");
    boolean save = request.getParameter("save") != null;

    Map errors = new HashMap();
    if (save) {
        if (maxChats <= 0) {
            errors.put("maxChats","");
        }
        if (minChats <= 0) {
            errors.put("minChats","");
        }
        if (minChats > maxChats) {
            errors.put("minChatsGreater","");
        }
        if (requestTimeout <= 0) {
            errors.put("requestTimeout","");
        }
        if (offerTimeout <= 0) {
            errors.put("offerTimeout","");
        }
        if (offerTimeout > requestTimeout) {
            errors.put("offerGreater","");
        }
        if (rejectionTimeout <= 0) {
            errors.put("rejectionTimeout","");
        }
        if (rejectionTimeout > requestTimeout) {
            errors.put("rejectionGreater","");
        }
        if (maxOverflows < 0) {
            errors.put("maxOverflows","");
        }

        if (errors.size() == 0) {
            wgManager.setDefaultMaxChats(maxChats);
            wgManager.setDefaultMinChats(minChats);
            wgManager.setDefaultRequestTimeout(requestTimeout * 1000);
            wgManager.setDefaultOfferTimeout(offerTimeout * 1000);
            JiveGlobals.setProperty("xmpp.live.rejection.timeout", Integer.toString(rejectionTimeout * 1000));
            JiveGlobals.setProperty("xmpp.live.request.overflow", Integer.toString(maxOverflows));
            JiveGlobals.setProperty("xmpp.live.agent.change-properties", canChangeName ? "true" : "false");
            // done, so redirect
            response.sendRedirect("workgroup-settings.jsp?success=true");
            return;
        }
    }

    if (errors.size() == 0) {
        maxChats = wgManager.getDefaultMaxChats();
        minChats = wgManager.getDefaultMinChats();
        requestTimeout = wgManager.getDefaultRequestTimeout() / 1000;
        offerTimeout = wgManager.getDefaultOfferTimeout() / 1000;
        rejectionTimeout = JiveGlobals.getIntProperty("xmpp.live.rejection.timeout", 20000) / 1000;
        maxOverflows = JiveGlobals.getIntProperty("xmpp.live.request.overflow", 3);
        canChangeName = JiveGlobals.getBooleanProperty("xmpp.live.agent.change-properties", true);
    }

      out.write("\n<style type=\"text/css\">\n    @import \"style/style.css\";\n</style>\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" >\n<tr><td colspan=\"2\">\nUse the form below to set properties that are global to all workgroups. The current set of\nproperties below only affect the default settings of newly created workgroups.\n</td></tr></table>\n<br>\n\n");
  if (errors.get("general") != null) { 
      out.write("\n\n    <p class=\"jive-error-text\">\n    Error saving settings.\n    </p>\n\n");
  } 
      out.write('\n');
      out.write('\n');
  if ("true".equals(request.getParameter("success"))) { 
      out.write("\n\n    <p class=\"jive-success-text\">\n    Settings updated successfully.\n    </p>\n\n");
  } 
      out.write("\n\n<form name=\"f\" action=\"workgroup-settings.jsp\" method=\"post\">\n\n<table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"600\">\n<tr>\n    <th colspan=\"3\">Global Settings</th>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\" nowrap>\n        <b>Default maximum chat sessions per agent: *</b>\n\n        ");
  if (errors.get("maxChats") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } 
      out.write("\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"maxChats\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( maxChats );
      out.write("\">\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\" nowrap>\n        <b>Default minimum chat sessions per agent: *</b>\n\n        ");
  if (errors.get("minChats") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } else if (errors.get("minChatsGreater") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Min chats must be less than max chats.\n            </span>\n\n        ");
  } 
      out.write("\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"minChats\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( minChats );
      out.write("\">\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Request timeout: *</b>\n\n        ");
  if (errors.get("requestTimeout") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } 
      out.write("\n        <br>\n        <span class=\"jive-description\">\n        The total time before an individual request will timeout if no agent accepts it.\n        </span>\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"requestTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( requestTimeout );
      out.write("\"> seconds\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\" nowrap>\n        <b>Agent timeout to accept an offer: *</b>\n\n        ");
  if (errors.get("offerTimeout") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } else if (errors.get("offerGreater") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Offer timeout must be less than request timeout.\n            </span>\n\n        ");
  } 
      out.write("\n        <br>\n        <span class=\"jive-description\">\n        The time each agent will be given to accept a chat request.\n        </span>\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"offerTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( offerTimeout );
      out.write("\"> seconds\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Expire agent rejection: *</b>\n\n        ");
  if (errors.get("rejectionTimeout") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } else if (errors.get("rejectionGreater") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Rejection timeout must be less than request timeout.\n            </span>\n\n        ");
  } 
      out.write("\n        <br>\n        <span class=\"jive-description\">\n        The time each rejection will last. Once expired new offers for the rejected request may be sent again.\n        </span>\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"rejectionTimeout\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( rejectionTimeout );
      out.write("\"> seconds\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\">\n        <b>Times to overflow before canceling request: *</b>\n\n        ");
  if (errors.get("maxOverflows") != null) { 
      out.write("\n\n            <span class=\"jive-error-text\">\n            <br>Invalid number.\n            </span>\n\n        ");
  } 
      out.write("\n        <br/>\n        <span class=\"jive-description\">\n        Number of times a request may be moved to other queues before giving up and canceling the request.\n        </span>\n    </td>\n    <td class=\"c2\">\n        <input type=\"text\" name=\"maxOverflows\" size=\"5\" maxlength=\"5\" value=\"");
      out.print( maxOverflows );
      out.write("\">\n    </td>\n</tr>\n<tr valign=\"top\">\n    <td class=\"c1\" nowrap>\n        <b>Agents are allowed to change their names: *</b>\n    </td>\n    <td class=\"c2\">\n        <input type=\"checkbox\" name=\"canChangeName\" ");
      out.print( (canChangeName ? "checked" : "") );
      out.write(">\n    </td>\n</tr>\n</table>\n<br>\n\n* Required field.\n\n<br><br>\n\n<input type=\"submit\" name=\"save\" value=\"Save Settings\">\n\n</form>\n\n</body>\n</html>");
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
