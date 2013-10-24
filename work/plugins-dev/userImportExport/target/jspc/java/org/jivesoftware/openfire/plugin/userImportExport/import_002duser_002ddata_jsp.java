package org.jivesoftware.openfire.plugin.userImportExport;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.net.MalformedURLException;
import java.util.*;
import org.dom4j.DocumentException;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.jivesoftware.openfire.plugin.ImportExportPlugin;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.ParamUtils;

public final class import_002duser_002ddata_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 
public boolean isEmpty(String s) {
    if (s == null) {
        return true;
    }
    
    if (s.trim().length() == 0) {
        return true;
    }
    
    return false;
}

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

    boolean importUsers = request.getParameter("importUsers") != null;
   
    ImportExportPlugin plugin = (ImportExportPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("userimportexport");
    List<String> duplicateUsers = new ArrayList<String>();
   
    Map<String, String> errors = new HashMap<String, String>();
    if (importUsers) {
        DiskFileUpload dfu = new DiskFileUpload();
      
        List fileItems = dfu.parseRequest(request);
        Iterator i = fileItems.iterator();
        FileItem fi = (FileItem) i.next();
        FileItem pd = (FileItem) i.next();
        String previousDomain = pd.getString();
        
        if (plugin.validateImportFile(fi)) {
            try {
                if (isEmpty(previousDomain)) {
                    duplicateUsers.addAll(plugin.importUserData(fi, null));
                }
                else if (!isEmpty(previousDomain)) {
                    duplicateUsers.addAll(plugin.importUserData(fi, previousDomain));
                }
                else {
                    errors.put("missingDomain", "missingDomain");
                }
              
                if (duplicateUsers.size() == 0) {
                    response.sendRedirect("import-user-data.jsp?success=true");
                    return;
                }
                
                errors.put("invalidUser", "invalidUser");
            }
            catch (MalformedURLException e) {
                errors.put("IOException", "IOException");
            }
            catch (DocumentException e) {
                errors.put("DocumentException", "DocumentException");
            }
        }
        else {
            errors.put("invalidUserFile", "invalidUserFile");
        }
    }

      out.write("\n\n<html>\n    <head>\n        <title>Import User Data</title>\n        <meta name=\"pageID\" content=\"import-export-selection\"/>\n    </head>\n    <body>\n\n");
 if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n        <tbody>\n        <tr>\n            <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n            <td class=\"jive-icon-label\">\n            ");
 if (errors.containsKey("missingDomain")) { 
      out.write("\n                You must supply both a existing and new domain name.\n            ");
 } else if (errors.containsKey("IOException")) { 
      out.write("\n                Missing or bad file name.\n            ");
 } else if (errors.containsKey("DocumentException")) { 
      out.write("\n                Import failed.\n            ");
 } else if (errors.containsKey("invalidUserFile")) { 
      out.write("\n                The import file does not match the user schema.\n            ");
 } else if (errors.containsKey("invalidUser")) { 
      out.write("\n                \n                ");
 if (plugin.isUserProviderReadOnly()) { 
      out.write("\n                   The following users did not exist in the system or have invalid username so their roster was not loaded:<br>\n                ");
 } else { 
      out.write("\n                   The following users already exist in the system or have invalid username and were not loaded:<br>\n               ");
 } 
      out.write("\n            ");

                Iterator iter = duplicateUsers.iterator();
                while (iter.hasNext()) {
                    String username = (String) iter.next();
                    
      out.print( username );

                    if (iter.hasNext()) {
                        
      out.write(",&nbsp;");

                    } else {
                        
      out.write('.');

                    }
                }
            } 
      out.write("\n            </td>\n        </tr>\n        </tbody>\n    </table>\n    </div>\n    <br>\n\n");
 } else if (ParamUtils.getBooleanParameter(request, "success")) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n        <tbody>\n        <tr>\n            <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n            ");
 if (plugin.isUserProviderReadOnly()) { 
      out.write("\n               <td class=\"jive-icon-label\">User roster data added successfully.</td>\n            ");
 } else { 
      out.write("\n               <td class=\"jive-icon-label\">All users added successfully.</td>\n            ");
 } 
      out.write("\n        </tr>\n        </tbody>\n    </table>\n    </div>\n    <br>\n\n");
 } 
      out.write("\n\nUse the form below to import a user data XML file.\n\n\n<form action=\"import-user-data.jsp?importUsers\" method=\"post\" enctype=\"multipart/form-data\">\n\n<div class=\"jive-contentBoxHeader\">Import</div>\n<div class=\"jive-contentBox\">\n    <p>\n    Choose a file to import:</p>\n    <input type=\"file\" name=\"thefile\">\n\n    <br><br><br>\n   \n    <p>\n    <b>Optional</b> - Use the field below to replace the domain name of user roster entries with the current hostname.\n    See the migration section of the <a href=\"../../plugin-admin.jsp?plugin=userimportexport&showReadme=true&decorator=none\">readme</a> for details.\n    </p>\n    Replace Domain: <input type=\"text\" size=\"20\" maxlength=\"150\" name=\"previousDomain\" value=\"\"/>\n</div>\n<input type=\"submit\" value=\"Import\">\n\n</form>\n\n</body>\n</html>\n\n");
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
