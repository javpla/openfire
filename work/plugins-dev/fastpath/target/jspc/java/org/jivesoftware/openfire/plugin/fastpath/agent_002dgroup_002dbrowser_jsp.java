package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.openfire.group.GroupManager;
import org.jivesoftware.openfire.group.Group;

public final class agent_002dgroup_002dbrowser_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n");

    GroupManager groupManager = GroupManager.getInstance();

      out.write('\n');
      out.write('\n');
 // Get parameters
    int              start = ParamUtils.getIntParameter(request, "start", 0);
    int              range = ParamUtils.getIntParameter(request, "range", 10);
    String           formName = ParamUtils.getParameter(request, "formName");
    String           elName = ParamUtils.getParameter(request, "elName");

    String           panel = ParamUtils.getParameter(request, "panel");
    if (panel == null) {
        panel = "frameset";
    }

      out.write('\n');
      out.write('\n');

    if ("frameset".equals(panel)) {

      out.write("\n\n        <html>\n        <head>\n            <title>Group Browser</title>\n\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>\n            <meta name=\"decorator\" content=\"none\"/>\n\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n\n            <script language=\"JavaScript\" type=\"text/javascript\">\n                var users = new Array();\n\n                function getUserListDisplay() {\n                    var display = \"\";\n                    var sep = \", \";\n\n                    for (var i = 0; i < users.length; i++) {\n                        if ((i + 1) == users.length) {\n                            sep = \"\";\n                        }\n\n                        display += (users[i] + sep);\n                    }\n                    return display;\n                }\n\n                function printUsers(theForm) {\n                    theForm.users.value = getUserListDisplay();\n                }\n\n                function addUser(theForm, username) {\n\n                    users[users.length] = username;\n");
      out.write("                    printUsers(theForm);\n                }\n\n                function closeWin() {\n                    window.close();\n                }\n\n                function done() {\n                    closeWin();\n                }\n            </script>\n        </head>\n\n        <frameset rows=\"*,105\">\n            <frame name=\"main\"      src=\"agent-group-browser.jsp?panel=main\" marginwidth=\"5\" marginheight=\"5\"\n                   scrolling=\"auto\" frameborder=\"0\">\n                <frame name=\"bottom\"\n                       src=\"agent-group-browser.jsp?panel=bottom&formName=");
      out.print( formName );
      out.write("&elName=");
      out.print( elName );
      out.write("\"\n                       marginwidth=\"5\"\n                       marginheight=\"5\"\n                       scrolling=\"no\"\n                       frameborder=\"0\">\n        </frameset>\n        </html>\n\n");

    }
    else if ("bottom".equals(panel)) {

      out.write("\n\n        <html>\n        <head>\n            <title><fmt:message key=\"title\" /> <fmt:message key=\"header.admin\" /></title>\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=\">\n            <meta name=\"decorator\" content=\"none\"/>\n\n            <link rel=\"stylesheet\" href=\"style/global.css\" type=\"text/css\">\n        </head>\n\n        <body>\n        <style type=\"text/css\">\n            .mybutton\n            {\n             width: 100%;\n            }\n        </style>\n\n        <form name=\"f\" onsubmit=\"return false;\">\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                <tr>\n                    <td width=\"99%\">\n                        <textarea rows=\"4\" cols=\"40\" style=\"width:100%;\" name=\"users\" wrap=\"virtual\">\n                        </textarea>\n                    </td>\n\n                    <td width=\"1%\" nowrap>\n                        <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"75\">\n                            <tr>\n                                <td>\n");
      out.write("                                    <script language=\"javascript\">\n                                        var currentValue = parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value;\n\n                                        if (currentValue.length > 0) {\n                                            currentValue = \",\" + currentValue;\n                                        }\n                                    </script>\n\n                                    <input type=\"submit\"\n                                           name=\"\"\n                                           value=\"Done\"\n                                           class=\"mybutton\"\n                                           onclick=\"if(parent.getUserListDisplay()!=''){parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value=parent.getUserListDisplay()+currentValue;}parent.done();return false;\">\n                                </td>\n                            </tr>\n\n                            <tr>\n                                <td>\n                                    <input type=\"submit\" name=\"\" value=\"Cancel\" class=\"mybutton\"\n                                           onclick=\"parent.closeWin();return false;\">\n                                </td>\n                            </tr>\n                        </table>\n                    </td>\n                </tr>\n            </table>\n        </form>\n\n        </body>\n    </html>\n");

    }
    else if ("main".equals(panel)) {

      out.write('\n');
      out.write('\n');
 // Get the user manager
        int userCount = groupManager.getGroupCount();

        // paginator vars
        int numPages = (int) Math.ceil((double) userCount / (double) range);
        int curPage = (start / range) + 1;

      out.write("\n\n        <html>\n        <head>\n            <title>Agent Browser</title>\n\n            <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\n            <meta name=\"decorator\" content=\"none\"/>\n\n            <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n        </head>\n\n        <body class=\"jive-body\">\n            <p>\n                Total Groups: ");
      out.print( groupManager.getGroupCount() );
      out.write(',');
      out.write('\n');
      out.write('\n');

                if (numPages > 1) {

      out.write("\n\n                            Showing ");
      out.print( (start + 1) );
      out.write('-');
      out.print( (start + range) );
      out.write(',');
      out.write('\n');
      out.write('\n');

                }

      out.write("\n\n                        Sorted by Group ID\n            </p>\n\n            <p>\n                Viewing page ");
      out.print( curPage );
      out.write("\n            </p>\n\n            <p>\n                Click \"Add Group\" to add a group to the list box below. When you are finished, click \"Done\".\n            </p>\n\n");

            if (numPages > 1) {

      out.write("\n\n                    <p>\n                    Pages: [\n\n");

                    for (int i = 0; i < numPages; i++) {
                        String sep = ((i + 1) < numPages) ? " " : "";
                        boolean isCurrent = (i + 1) == curPage;

      out.write("\n\n                            <a href=\"agent-group-browser.jsp?panel=main&start=");
      out.print( (i * range) );
      out.write("\"\n                               class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write('"');
      out.write('>');
      out.print( (i + 1) );
      out.write("</a>");
      out.print( sep );
      out.write('\n');
      out.write('\n');

                            }

      out.write("\n\n                            ]\n                    </p>\n\n");

            }

      out.write("\n\n            <fieldset>\n                <legend>\n                    Possible Groups to Add\n                </legend>\n\n                <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n                    <th>\n                        &nbsp;\n                    </th>\n\n                    <th>\n                        Name/Description\n                    </th>\n\n                    <th align=\"center\">\n                        Add\n                    </th>\n\n");

                        if (groupManager.getGroupCount() == 0) {

      out.write("\n\n                            <tr>\n                                <td align=\"center\" colspan=\"3\">\n                                    No groups in the system.\n                                </td>\n                            </tr>\n\n");

                        }
                        else{

      out.write('\n');
      out.write('\n');

                            // Print the list of users
                            int i = start;
                            for(Group group : groupManager.getGroups()){
                                i++;

      out.write("\n\n                                <tr class=\"jive-");
      out.print( (((i % 2) == 0) ? "even" : "odd") );
      out.write("\">\n                                    <td width=\"1%\">\n                                        ");
      out.print( i );
      out.write("\n                                    </td>\n\n                                    <td>\n                                        ");
      out.print( group.getName() );
      out.write("\n                                    </td>\n\n                                    <td width=\"1%\" align=\"center\">\n                                        <input type=\"submit\"\n                                               name=\"\"\n                                               value=\"Add Group\"\n                                               class=\"jive-sm-button\"\n                                               onclick=\"parent.addUser(parent.frames['bottom'].document.f,'");
      out.print( group.getName() );
      out.write("');\">\n                                    </td>\n                                </tr>\n\n");

                            }
                        }

      out.write("\n                </table>\n            </fieldset>\n\n            </body>\n        </html>\n");

    }

      out.write('\n');
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
