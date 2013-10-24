package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.RequestQueue;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;
import java.util.HashMap;
import java.util.Map;
import org.xmpp.packet.JID;
import org.xmpp.component.ComponentManagerFactory;

public final class workgroup_002dqueue_002dcreate_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');
      out.write('\n');
      out.write('\n');
 // Get parameters //
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean createQueue = request.getParameter("createQueue") != null;
    String name = ParamUtils.getParameter(request, "name");
    String description = ParamUtils.getParameter(request, "description");
    String agents = ParamUtils.getParameter(request, "agents");

    // Load the workgroup

    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    Map errors = new HashMap();
    if (createQueue) {
        if (name == null) {
            errors.put("name", "");
        }
        if (errors.size() == 0) {
            RequestQueue queue = workgroup.createRequestQueue(name);
            if (description != null) {
                queue.setDescription(description);
            }

            if (ModelUtil.hasLength(agents)) {
                WorkgroupUtils.addAgents(queue, agents);
            }
            response.sendRedirect("workgroup-queues.jsp?wgID=" + wgID + "&queueaddsuccess=true");
            return;
        }


    }

      out.write("\n<html>\n    <head>\n        <title>Workgroup Queue Creation</title>\n        <meta name=\"subPageID\" content=\"workgroup-queues\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n         <!--<meta name=\"helpPage\" content=\"create_a_workgroup.html\"/>-->\n        <script>\n            function openWin(el) {\n                var win = window.open(\n                        'user-browser.jsp?formName=f&elName=agents', 'newWin',\n                        'width=500,height=550,menubar=yes,location=no,personalbar=no,scrollbars=yes,resize=yes');\n            }\n        </script>\n    </head>\n    <body>\n    <p>\n        Use the form below to create a new queue in the workgroup <b>");
      out.print( workgroup.getJID() );
      out.write("</b>.\n    </p>\n    <form name=\"f\" action=\"workgroup-queue-create.jsp\" method=\"post\">\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\">\n            <div class=\"jive-contentBoxHeader\">\n        Create New Queue\n        </div>\n           <table width=\"100%\"  class=\"jive-contentBox\" cellpadding=\"3\" cellspacing=\"3\" border=\"0\">\n\n                <tr valign=\"top\">\n                    <td width=\"1%\">\n                    Workgroup\n                    </td>\n                    <td colspan=\"2\">\n                        ");
      out.print( workgroup.getJID() );
      out.write("\n                    </td>\n                </tr>\n                <tr valign=\"top\">\n                    <td width=\"1%\" nowrap>\n                        Name Of Queue: *\n");

    if (errors.get("name") != null) {

      out.write("\n                            <span class=\"jive-error-text\">\n                            <br>\n                            Please enter a valid name. </span>\n");

    }

      out.write("\n                    </td>\n                    <td colspan=\"2\">\n                        <input type=\"text\" name=\"name\" size=\"30\" value=\"");
      out.print( ((name != null) ? name : "") );
      out.write("\">\n                        <br/><span class=\"jive-description\">Specify a name for the queue. (ex. product1)</span>\n                    </td>\n                </tr>\n                  <tr valign=\"top\">\n                    <td width=\"1%\" nowrap>\n                       Members:\n                    </td>\n                    <td width=\"1%\">\n                        <textarea name=\"agents\" cols=\"30\" rows=\"3\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea>\n                        <span class=\"jive-description\">\n                        <br/>Comma delimited list of initial members. ex. bob,mary,suzy </span>\n                    </td>\n                    ");
 if (!ComponentManagerFactory.getComponentManager().isExternalMode()) { 
      out.write("\n                    <td nowrap valign=\"top\">\n                        <table>\n                            <tr>\n                                <td> <a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\n                                        title=\"Click to browse available agents...\"> <img src=\"images/user.gif\" border=\"0\"/></a></td>\n                                <td><a href=\"#\" onclick=\"openWin(document.f.agents);return false;\"\n                                       title=\"Click to browse available agents...\">Browse Agents</a></td>\n                            </tr>\n                        </table>\n                    </td>\n                    ");
 } 
      out.write("\n                </tr>\n                <tr valign=\"top\">\n                    <td width=\"1%\" nowrap>\n                        Description:\n                    </td>\n                    <td>\n                        <textarea name=\"description\" cols=\"30\"\n                                  rows=\"5\">");
      out.print( ((description != null) ? description : "") );
      out.write("</textarea>\n                                <br/><span class=\"jive-description\">Specify a description for the queue.</span>\n                    </td>\n                </tr>\n            </table>\n\n    <p>\n            * Required fields\n    </p>\n        <input type=\"submit\" name=\"createQueue\" value=\"Create Queue\">\n    </form>\n  </body>\n  </html>\n");
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
