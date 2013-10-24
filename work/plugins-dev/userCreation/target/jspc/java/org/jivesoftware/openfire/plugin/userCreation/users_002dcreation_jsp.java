package org.jivesoftware.openfire.plugin.userCreation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.util.TaskEngine;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.UserCreationPlugin;
import java.util.HashMap;
import java.util.Map;

public final class users_002dcreation_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n<html>\n    <head>\n        <title>Quick Users Creation</title>\n        <meta name=\"pageID\" content=\"users-creation\"/>\n    </head>\n    <body>\n\n");

    String prefix = ParamUtils.getParameter(request, "prefix");
    String from = ParamUtils.getParameter(request, "from");
    String total = ParamUtils.getParameter(request, "total");
    String usersPerRoster = ParamUtils.getParameter(request, "usersPerRoster");

    Map<String, String> errors = new HashMap<String, String>();

    boolean running = false;

    if (prefix != null) {
        final String userPrefix = prefix;
        final int intFrom = Integer.parseInt(from);
        final int maxUsers = Integer.parseInt(total);
        final int usersRoster = Integer.parseInt(usersPerRoster) + 1;
        if (maxUsers % usersRoster != 0 || maxUsers <= usersRoster) {
            errors.put("arguments", "");
        }

        if (errors.isEmpty()) {
            final UserCreationPlugin plugin =
                    (UserCreationPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("usercreation");
            TaskEngine.getInstance().submit(new Runnable() {
                public void run() {
                    plugin.createUsers(userPrefix, intFrom, maxUsers);
                    plugin.populateRosters(userPrefix, intFrom, maxUsers, usersRoster);
                    plugin.createVCards(userPrefix, intFrom, maxUsers);
                }
            });
            running = true;
        }
    }

      out.write('\n');
      out.write('\n');
  if (!errors.isEmpty()) { 
      out.write("\n\n    <div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr>\n            <td class=\"jive-icon\"><img src=\"/images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"/></td>\n            <td class=\"jive-icon-label\">\n\n            ");
 if (errors.get("arguments") != null) { 
      out.write("\n                Number of users per roster should be greater than total number of users. Number of users per roster <b>plus one</b> should also be a multiple of total number of users. \n            ");
 } 
      out.write("\n            </td>\n        </tr>\n    </tbody>\n    </table>\n    </div>\n    <br>\n\n");
  } else if (running) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"/images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n        <td class=\"jive-icon-label\">\n        Users being created in background and getting their rosters populated. Check the stdout for more information.\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } 
      out.write("\n\n<form name=\"f\" action=\"users-creation.jsp\">\n    <fieldset>\n        <legend>Creation Form</legend>\n        <div>\n        <table cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"600\">\n        <tr class=\"c1\">\n            <td width=\"1%\" colspan=\"2\" nowrap>\n                User prefix:\n                &nbsp;<input type=\"text\" name=\"prefix\" value=\"");
      out.print((prefix != null ? prefix : "") );
      out.write("\" size=\"30\" maxlength=\"75\"/>\n\t        </td>\n        </tr>\n        <tr class=\"c1\">\n            <td width=\"1%\" colspan=\"2\" nowrap>\n                From index:\n                &nbsp;<input type=\"text\" name=\"from\" value=\"");
      out.print((from != null ? from : "0") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\n\t        </td>\n        </tr>\n        <tr class=\"c1\">\n            <td width=\"1%\" colspan=\"2\" nowrap>\n                Total users:\n                &nbsp;<input type=\"text\" name=\"total\" value=\"");
      out.print((total != null ? total : "1000") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\n\t        </td>\n        </tr>\n        <tr class=\"c1\">\n            <td width=\"1%\" colspan=\"2\" nowrap>\n                Contacts in roster:\n                &nbsp;<input type=\"text\" name=\"usersPerRoster\" value=\"");
      out.print((usersPerRoster != null ? usersPerRoster : "30") );
      out.write("\" size=\"5\" maxlength=\"15\"/>\n\t        </td>\n        </tr>\n            <tr class=\"c1\">\n                <td width=\"1%\" colspan=\"2\" nowrap>\n                    <input type=\"submit\" name=\"Create\"/>\n                </td>\n            </tr>\n        </table>\n        </div>\n    </fieldset>\n</form>\n\n</body>\n</html>");
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
