package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import java.util.Iterator;
import org.jivesoftware.openfire.user.User;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.xmpp.packet.JID;

public final class user_002dbrowser_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write('\n');
      out.write('\n');
  // Get parameters
    int start = ParamUtils.getIntParameter(request,"start",0);
    int range = ParamUtils.getIntParameter(request,"range",10);
    String formName = ParamUtils.getParameter(request,"formName");
    String elName = ParamUtils.getParameter(request,"elName");

    String panel = ParamUtils.getParameter(request,"panel");
    if (panel == null) {
        panel = "frameset";
    }

      out.write('\n');
      out.write('\n');
  if ("frameset".equals(panel)) { 
      out.write("\n\n<html>\n    <head>\n\n        <title>User Browser</title>\n        <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\"/>\n        <meta name=\"decorator\" content=\"none\"/>\n\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n\n        <script language=\"JavaScript\" type=\"text/javascript\">\n            var users = new Array();\n            function getUserListDisplay() {\n                var display = \"\";\n                var sep = \", \";\n                for (var i=0; i<users.length; i++) {\n                    if ((i+1) == users.length) {\n                        sep = \"\";\n                    }\n                    display += (users[i] + sep);\n                }\n                return display;\n            }\n            function printUsers(theForm) {\n                theForm.users.value = getUserListDisplay();\n            }\n            function addUser(theForm, username) {\n                users[users.length] = username;\n                printUsers(theForm);\n            }\n            function closeWin() {\n");
      out.write("                window.close();\n            }\n            function done() {\n                closeWin();\n            }\n        </script>\n    </head>\n\n\n    <frameset rows=\"*,105\">\n        <frame name=\"main\" src=\"user-browser.jsp?panel=main\"\n                marginwidth=\"5\" marginheight=\"5\" scrolling=\"auto\" frameborder=\"0\">\n        <frame name=\"bottom\" src=\"user-browser.jsp?panel=bottom&formName=");
      out.print( formName );
      out.write("&elName=");
      out.print( elName );
      out.write("\"\n                marginwidth=\"5\" marginheight=\"5\" scrolling=\"no\" frameborder=\"0\">\n    </frameset>\n    </html>\n\n");
  } else if ("bottom".equals(panel)) { 
      out.write("\n\n    <html>\n    <head>\n        <title><fmt:message key=\"title\" /> <fmt:message key=\"header.admin\" /></title>\n        <meta http-equiv=\"content-type\" content=\"text/html; charset=\">\n        <meta name=\"decorator\" content=\"none\"/>\n\n        <link rel=\"stylesheet\" href=\"style/global.css\" type=\"text/css\">\n    </head>\n\n    <body>\n\n    <style type=\"text/css\">\n    .mybutton {\n        width : 100%;\n    }\n    </style>\n\n    <form name=\"f\" onsubmit=\"return false;\">\n\n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tr>\n        <td width=\"99%\">\n\n            <textarea rows=\"4\" cols=\"40\" style=\"width:100%;\" name=\"users\" wrap=\"virtual\"></textarea>\n\n        </td>\n        <td width=\"1%\" nowrap>\n\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"75\">\n            <tr>\n                <td>\n                <script language=\"javascript\">\n                  var currentValue = parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value;\n                  if(currentValue.length > 0){\n                    currentValue = \",\"+currentValue;\n                  }\n                </script>\n                    <input type=\"submit\" name=\"\" value=\"Done\" class=\"mybutton\"\n                     onclick=\"if(parent.getUserListDisplay()!=''){parent.opener.document.");
      out.print( formName );
      out.write('.');
      out.print( elName );
      out.write(".value=parent.getUserListDisplay()+currentValue;}parent.done();return false;\">\n                </td>\n            </tr>\n            <tr>\n                <td>\n                    <input type=\"submit\" name=\"\" value=\"Cancel\" class=\"mybutton\"\n                     onclick=\"parent.closeWin();return false;\">\n                </td>\n            </tr>\n            </table>\n\n        </td>\n    </tr>\n    </table>\n\n    </form>\n</body>\n</html>\n\n");
  } else if ("main".equals(panel)) { 
      out.write("\n\n    ");
  // Get the user manager
        int userCount = webManager.getUserManager().getUserCount();

        // paginator vars
        int numPages = (int)Math.ceil((double)userCount/(double)range);
        int curPage = (start/range) + 1;
    
      out.write("\n\n    <html>\n<head>\n\n    <title>User Browser</title>\n    <meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\n    <meta name=\"decorator\" content=\"none\"/>\n\n    <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n</head>\n<body class=\"jive-body\">\n\n    <p>\n    Total Users: ");
      out.print( webManager.getUserManager().getUserCount() );
      out.write(",\n    ");
  if (numPages > 1) { 
      out.write("\n\n        Showing ");
      out.print( (start+1) );
      out.write('-');
      out.print( (start+range) );
      out.write(",\n\n    ");
  } 
      out.write("\n    Sorted by User ID.\n    </p>\n\n    <p>\n    Viewing page ");
      out.print( curPage );
      out.write("\n    </p>\n\n    <p>Click \"Add User\" to add a user to the list box below. When you're done\n    click \"Done\".\n    </p>\n\n    ");
  if (numPages > 1) { 
      out.write("\n\n        <p>\n        Pages:\n        [\n        ");
  for (int i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\n            <a href=\"user-browser.jsp?panel=main&start=");
      out.print( (i*range) );
      out.write("\"\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\n\n        ");
  } 
      out.write("\n        ]\n        </p>\n\n    ");
  } 
      out.write("\n    <fieldset>\n    <legend>Possible Agents to Add</legend>\n    <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n\n        <th>&nbsp;</th>\n        <th>Username</th>\n        <th>Name</th>\n        <th align=\"center\">Add</th>\n\n    ");
  // Print the list of users
        Iterator users = webManager.getUserManager().getUsers(start, range).iterator();
        if (!users.hasNext()) {
    
      out.write("\n        <tr>\n            <td align=\"center\" colspan=\"4\">\n                No users in the system.\n            </td>\n        </tr>\n\n    ");

        }
        int i = start;
        while (users.hasNext()) {
            User user = (User)users.next();
            i++;
    
      out.write("\n        <tr class=\"jive-");
      out.print( (((i%2)==0) ? "even" : "odd") );
      out.write("\">\n            <td width=\"1%\">\n                ");
      out.print( i );
      out.write("\n            </td>\n            <td width=\"60%\">\n                ");
      out.print( JID.unescapeNode(user.getUsername()) );
      out.write("\n            </td>\n            <td width=\"50%\">\n            ");
 String name = user.getName();
               if(!ModelUtil.hasLength(name)){
                   name = "&nbsp;";
               }
           
      out.write("\n                ");
      out.print( name );
      out.write("\n            </td>\n            <td width=\"1%\" align=\"center\">\n                <input type=\"submit\" name=\"\" value=\"Add User\" class=\"jive-sm-button\"\n                 onclick=\"parent.addUser(parent.frames['bottom'].document.f,'");
      out.print( JID.unescapeNode(user.getUsername()) );
      out.write("');\">\n            </td>\n        </tr>\n\n    ");

        }
    
      out.write("\n    </table>\n    </fieldset>\n\n    ");
  if (numPages > 1) { 
      out.write("\n\n        <p>\n        Pages:\n        [\n        ");
  for (i=0; i<numPages; i++) {
                String sep = ((i+1)<numPages) ? " " : "";
                boolean isCurrent = (i+1) == curPage;
        
      out.write("\n            <a href=\"user-browser.jsp?panel=main&start=");
      out.print( (i*range) );
      out.write("\"\n             class=\"");
      out.print( ((isCurrent) ? "jive-current" : "") );
      out.write("\"\n             >");
      out.print( (i+1) );
      out.write("</a>");
      out.print( sep );
      out.write("\n\n        ");
  } 
      out.write("\n        ]\n        </p>\n\n    ");
  } 
      out.write("\n    </body>\n</html>\n\n");
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
