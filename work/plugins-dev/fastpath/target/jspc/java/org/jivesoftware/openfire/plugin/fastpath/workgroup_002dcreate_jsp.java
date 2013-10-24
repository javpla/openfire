package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.HashMap;
import java.util.Map;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.openfire.XMPPServer;
import org.xmpp.component.ComponentManagerFactory;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;

public final class workgroup_002dcreate_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n<!-- Define Administration Bean -->\n");

    final XMPPServer xmppServer = XMPPServer.getInstance();
    Map errors = new HashMap();
    // Get a workgroup manager
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    // If the workgroup manager is null, service is down so redirect:
    if (workgroupManager == null) {
        response.sendRedirect("error-serverdown.jsp");
        return;
    }

      out.write('\n');
      out.write('\n');
 // Get parameters //
    boolean create = request.getParameter("create") != null;
    boolean cancel = request.getParameter("cancel") != null;
    String wgName = ParamUtils.getParameter(request, "wgName");
    String description = ParamUtils.getParameter(request, "description");
    String queueName = ParamUtils.getParameter(request, "queueName");
    String agents = ParamUtils.getParameter(request, "agents");
    int maxChats = ParamUtils.getIntParameter(request, "maxChats", 0);
    int minChats = ParamUtils.getIntParameter(request, "minChats", 0);

    // Handle a cancel
    if (cancel) {
        response.sendRedirect("workgroup-summary.jsp");
        return;
    }

    if (create) {
        errors = WorkgroupUtils.createWorkgroup(wgName, description, agents);
        if (errors.size() == 0) {
            Workgroup workgroup = workgroupManager.getWorkgroup(wgName);
            response.sendRedirect(
                    "workgroup-create-success.jsp?wgID=" + workgroup.getJID().toString());
            return;
        }
    }

      out.write("\n<html>\n    <head>\n        <title>Create Workgroup</title>\n        <meta name=\"pageID\" content=\"workgroup-create\"/>\n        <!--<meta name=\"helpPage\" content=\"create_a_workgroup.html\"/>-->\n        <script>\n            function openWin(el) {\n                var win = window.open(\n                              'user-browser.jsp?formName=f&elName=agents', 'newWin',\n                              'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n            }\n        </script>\n    </head>\n    <body>\n    <style type=\"text/css\">\n        @import \"style/style.css\";\n    </style>\n        <p>\n        Use the form below to create a new workgroup in the system.</p>\n");

    if (!errors.isEmpty()) {

      out.write("\n        <div class=\"jive-error\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr>\n                        <td class=\"jive-icon\">\n                            <img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"/>\n                        </td>\n                        <td class=\"jive-icon-label\">\n");

                            if (errors.get("general") != null) {

      out.write("\n                                    Unable to create the workgroup.\n");

                            }
                            else if (errors.get("exists") != null) {

      out.write("\n                               The workgroup name is already in use. Please try another.\n");

                            }
                            else if (errors.get("wgName") != null) {

      out.write("\n                                Supply a valid name for the workgroup.\n");

                            }
                            else if (errors.get("maxChats") != null) {

      out.write("\n                                Invalid maximum number of chat sessions.\n");

                            }
                            else if (errors.get("minChats") != null) {

      out.write("\n                                Invalid minimum number of chat sessions.\n");

                            }
                            else if (errors.get("minChatsGreaterThanMax") != null) {

      out.write("\n                                Minimum chat sessions can not be greater than maximum.\n");

                            }

      out.write("\n                        </td>\n                    </tr>\n                </tbody>\n            </table>\n        </div>\n        <br>\n");

    }

      out.write("\n    <form name=\"f\" action=\"workgroup-create.jsp\" method=\"post\">\n        <div>\n             <div class=\"jive-contentBoxHeader\">\n        Create New Workgroup\n        </div>\n            <table cellpadding=\"3\" cellspacing=\"3\" border=\"0\"  class=\"jive-contentBox\">\n\n                <tr valign=\"top\">\n                    <td width=\"30%\">\n                       Workgroup Name: *\n                        <br/>\n                    </td>\n                    <td colspan=\"2\" style=\"border-right:1px #ccc solid;\">\n                        <input type=\"text\" name=\"wgName\" size=\"40\" maxlength=\"45\"\n                               value=\"");
      out.print( ((wgName != null) ? wgName : "") );
      out.write("\"/>\n                        @workgroup.");
      out.print( xmppServer.getServerInfo().getXMPPDomain() );
      out.write("<br/><span class=\"jive-description\">e.g. sales, marketing, bizdev, support.</span>\n                    </td>\n                </tr>\n                <tr valign=\"top\">\n                    <td>\n                        Members:\n                    </td>\n                    <td width=\"1%\">\n                        <textarea name=\"agents\" cols=\"30\" rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea><br/>\n                        <span class=\"jive-description\">Comma delimited list of initial members of the workgroup.</span>\n                    </td>\n                    ");
 if (!ComponentManagerFactory.getComponentManager().isExternalMode()) { 
      out.write("\n                    <td nowrap valign=\"top\" style=\"border-right:1px #ccc solid;\">\n                        <table>\n                            <tr>\n                                <td> <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\n                           title=\"Click to browse available agents...\"> <img src=\"images/user.gif\" border=\"0\" alt=\"\"/></a></td>\n                                <td><a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\n                           title=\"Click to browse available agents...\">Browse...</a></td>\n                            </tr>\n                        </table>\n                    </td>\n                    ");
 } 
      out.write("\n                </tr>\n                <tr  valign=\"top\">\n                    <td>\n                       Description:\n\n                    </td>\n                    <td colspan=\"2\" width=\"1%\" style=\"border-right:1px #ccc solid;\">\n                        <textarea name=\"description\" cols=\"30\"\n                                  rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea> <br/>\n                        <span class=\"jive-description\">General description of the workgroup.</span>\n                    </td>\n                </tr>\n            </table>\n        </div>\n\n   <span class=\"jive-description\">\n    * Required fields\n    </span><br/><br/>\n        <input type=\"submit\" name=\"create\" value=\"Create Workgroup\"/>\n        <input type=\"submit\" name=\"cancel\" value=\"Cancel\"/>\n        <input type=\"hidden\" name=\"queueName\" size=\"40\" maxlength=\"75\"\n               value=\"");
      out.print( ((queueName != null) ? queueName : "") );
      out.write("\"/>\n        <input type=\"hidden\" name=\"maxChats\" size=\"5\" maxlength=\"10\"\n               value=\"");
      out.print( ((maxChats > 0) ? "" + maxChats : "") );
      out.write("\"/>\n        <input type=\"hidden\" name=\"minChats\" size=\"5\" maxlength=\"10\"\n               value=\"");
      out.print( ((minChats > 0) ? "" + minChats : "") );
      out.write("\"/>\n    </form>\n\n\n    <script language=\"JavaScript\" type=\"text/javascript\">\n        document.f.wgName.focus();\n    </script>\n</body>\n</html>\n");
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
