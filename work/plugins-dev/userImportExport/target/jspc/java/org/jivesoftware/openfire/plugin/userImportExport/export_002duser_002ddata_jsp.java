package org.jivesoftware.openfire.plugin.userImportExport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.IOException;
import java.util.*;
import org.jivesoftware.openfire.plugin.ImportExportPlugin;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.ParamUtils;

public final class export_002duser_002ddata_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    boolean exportUsers = request.getParameter("exportUsers") != null;
    boolean success = request.getParameter("success") != null;
    boolean exportToFile = ParamUtils.getBooleanParameter(request, "exporttofile", true);
    
    ImportExportPlugin plugin = (ImportExportPlugin)XMPPServer.getInstance().getPluginManager(
            ).getPlugin("userimportexport");

    String exportText = "";
    
    Map<String, String> errors = new HashMap<String, String>();
    if (exportUsers) {
        if (exportToFile) {
            String file = ParamUtils.getParameter(request, "exportFile");
            if ((file == null) || (file.length() <= 0)) {
                errors.put("missingFile","missingFile");
            }
            else {
                response.sendRedirect("export-file.jsp?fileName="+file);
            }
        }
        else {
            try {
                exportText = plugin.exportUsersToString();
            }
            catch (IOException e) {
                errors.put("IOException","IOException");
            }
        }
    }

      out.write("\n\n<html>\n    <head>\n        <title>Export User Data</title>\n        <meta name=\"pageID\" content=\"import-export-selection\"/>\n    </head>\n    <body>\n\n");
 if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr>\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n            <td class=\"jive-icon-label\">\n            ");
 if (errors.containsKey("missingFile")) { 
      out.write("\n                Missing or bad file name.\n            ");
 } else if (errors.containsKey("IOException") || errors.containsKey("fileNotCreated")) { 
      out.write("\n                Couldn't create export file.\n            ");
 } 
      out.write("\n            </td>\n        </tr>\n    </tbody>\n    </table>\n    </div>\n    <br>\n\n");
 } else if (ParamUtils.getBooleanParameter(request, "success")) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr>\n            <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n            <td class=\"jive-icon-label\">User data successfully exported.</td>\n        </tr>\n    </tbody>\n    </table>\n    </div>\n    <br>\n    \n");
 } 
      out.write("\n\n<form action=\"export-user-data.jsp?exportUsers\" method=\"post\">\n\n<div class=\"jive-contentBoxHeader\">Export Options</div>\n<div class=\"jive-contentBox\">\n    <p>\n    Select the radio button next to the desired export option and then click on the Export button.\n    </p>\n    \n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n        <tr>\n            <td width=\"1%\"><input type=\"radio\" name=\"exporttofile\" value=\"true\" ");
      out.print( exportToFile ? "checked" : "" );
      out.write(" id=\"rb01\"></td>\n            <td width=\"99%\"><label for=\"rb01\"><b>To File</b></label> - Save user data to the specified file location.</td>\n        </tr>\n        <tr>\n            <td width=\"1%\">&nbsp;</td>\n            <td width=\"99%\">Export File Name:&nbsp;<input type=\"text\" size=\"30\" maxlength=\"150\" name=\"exportFile\"></td>\n        </tr>\n        <tr>\n            <td width=\"1%\"><input type=\"radio\" name=\"exporttofile\" value=\"false\" ");
      out.print( !exportToFile ? "checked" : "" );
      out.write(" id=\"rb02\"></td>\n            <td width=\"99%\"><label for=\"rb02\"><b>To Screen</b></label> - Display user data in the text area below.</td>            \n        </tr>\n        <tr>\n            <td width=\"1%\">&nbsp;</td>\n            <td width=\"99%\"><textarea cols=\"80\" rows=\"20\" wrap=off>");
      out.print(exportText );
      out.write("</textarea></td>\n        </tr>\n    </tbody>\n    </table>\n</div>\n\n<input type=\"submit\" value=\"Export\">\n</form>\n\n</body>\n</html>");
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
