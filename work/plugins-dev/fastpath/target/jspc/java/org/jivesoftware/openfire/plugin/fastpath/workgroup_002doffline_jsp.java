package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettings;
import org.jivesoftware.openfire.fastpath.settings.offline.OfflineSettingsNotFound;
import org.jivesoftware.openfire.fastpath.util.WorkgroupUtils;
import org.jivesoftware.util.JiveGlobals;

public final class workgroup_002doffline_jsp extends org.apache.jasper.runtime.HttpJspBase
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
 try { 
      out.write('\n');
      out.write('\n');
      out.write(' ');

     String wgID = request.getParameter("wgID");
     final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
     Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

     OfflineSettingsManager offlineManager = new OfflineSettingsManager();
     String redirectValue = request.getParameter("redirectToPage");
     String statusMessage = "";

     OfflineSettings offlineSettings = null;

     boolean emailConfigured = false;

     String property = JiveGlobals.getProperty("mail.configured");
     if (ModelUtil.hasLength(property)) {
         emailConfigured = true;
     }

     boolean delete = request.getParameter("delete") != null;
     boolean save = request.getParameter("save") != null && !delete;

     if (save){
         String emailAddress = request.getParameter("email");
         String subject = request.getParameter("subject");
         String header = request.getParameter("headerField");
         offlineSettings = offlineManager.saveOfflineSettings(workgroup, redirectValue, emailAddress, subject, header);
         if (offlineSettings != null) {
             statusMessage = "Offline settings have been saved.";
         }
     }
     else if(delete){
        statusMessage = "Offline settings have been deleted.";
        offlineSettings = offlineManager.saveOfflineSettings(workgroup, redirectValue, "", "", "");
     }
     else {
         try {
             offlineSettings = offlineManager.getOfflineSettings(workgroup);
         }
         catch (OfflineSettingsNotFound offlineSettingsNotFound) {
             offlineSettings = new OfflineSettings();
         }

     }
 
      out.write("\n<html>\n    <head>\n        <title>");
      out.print( "Offline Settings for "+wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-offline\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"set_an_offline_policy_for_a_workgroup.html\"/>-->\n\n        <script>\n        function saveOfflineSettings(){\n             var todo = document.offline.todo;\n             if(todo[0].checked){\n                 var url = document.offline.redirectToPage.value;\n                 if(!Jtrim(url)){\n                   alert(\"Please specify the URL to forward to.\");\n                   document.offline.redirectToPage.focus();\n                   return;\n                 }\n                 document.offline.email.value = \"\";\n                 document.offline.subject.value = \"\";\n                 document.offline.headerField.value = \"\";\n\n                 document.offline.submit();\n             }\n             else if(todo[1].checked){\n               var email = document.offline.email.value;\n               var subject = document.offline.subject.value;\n               var message = document.offline.headerField.value;\n               document.offline.redirectToPage.value = '';\n\n               if(!Jtrim(email) || !Jtrim(subject) || !Jtrim(message)){\n");
      out.write("                 alert(\"All fields are required.\");\n                 return;\n               }\n                document.offline.submit();\n             }\n        }\n\n\n\n         function Jtrim(st) {\n             var len = st.length;\n             var begin = 0, end = len - 1;\n             while (st.charAt(begin) == \" \" && begin < len) {\n                 begin++;\n             }\n             while (st.charAt(end) == \" \" && end > begin) {\n                 end--;\n             }\n             return st.substring(begin, end + 1);\n         }\n        </script>\n    </head>\n    <body>\n    Specify action to take when this workgroup has no available agents to take incoming chat requests.\n    ");
 if(statusMessage != null && !statusMessage.equals("")) { 
      out.write("\n    <div class=\"success\">\n        ");
      out.print( statusMessage );
      out.write("\n    </div>\n    ");
 } 
      out.write("\n\n      ");
 if(!emailConfigured){ 
      out.write("\n            <div class=\"error\">\n                Email form will not be displayed until you configure your <a href=\"../../system-email.jsp\">email settings</a>.\n            </div>\n    ");
 } 
      out.write("\n\n    <div id=\"offline_message\">");
      out.print( statusMessage );
      out.write("</div>\n    <p/>\n    <form action=\"workgroup-offline.jsp\" method=\"get\" name=\"offline\">\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n    <div>\n        <div class=\"jive-contentBoxHeader\">\n        Offline Workgroup Action\n        </div>\n        <table width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\" class=\"jive-contentBox\">\n                <tr valign=\"top\">\n                ");
 String checked = offlineSettings.redirects() ? "checked" : ""; 
      out.write("\n                            <td width=\"1%\">\n                                <input type=\"radio\" name=\"todo\" value=\"redirectToPage\" ");
      out.print( checked );
      out.write(" />\n                            </td>\n                            <td nowrap><b>Redirect To Web Page</b>\n                               </td>\n                            <td class=\"c2\">\n                                <input type=\"text\" name=\"redirectToPage\" size=\"40\" value=\"");
      out.print( offlineSettings.getRedirectURL() );
      out.write("\" /><br/>\n                                 <span class=\"jive-description\">e.g. http://www.jivesoftware.com/contact.html</span>\n                            </td>\n                </tr>\n                <tr>\n                    <td nowrap width=\"1%\">\n                         <input type=\"radio\" name=\"todo\" value=\"showEmailPage\" ");
      out.print(!offlineSettings.redirects() ? "checked" :"" );
      out.write("/>\n                         <td><b>Display Email Form</b></td>\n                     </td>\n                     <td>&nbsp;</td>\n                </tr>\n                <!-- Email Address -->\n                <tr valign=\"top\">\n                    <td>&nbsp;</td>\n                    <td>Email Address:</td>\n                    <td>\n                        <input type=\"text\" size=\"40\" name=\"email\" value=\"");
      out.print( offlineSettings.getEmailAddress() );
      out.write("\" /><br/>\n                        <span class=\"jive-description\">Email address to send all offline messages to.</span>\n                    </td>\n                </tr>\n                <!-- End of Email Address -->\n                <!-- Subject Line -->\n                 <tr valign=\"top\">\n                    <td>&nbsp;</td>\n                    <td>Subject:</td>\n                    <td>\n                        <input type=\"text\" size=\"40\" name=\"subject\" value=\"");
      out.print( offlineSettings.getSubject() );
      out.write("\"/><br/>\n                        <span class=\"jive-description\">The subject of all offline email messages.</span>\n                    </td>\n                </tr>\n                <!--  End Of Subject Line -->\n                <tr valign=\"top\">\n                     <td>&nbsp;</td>\n                    <td>Offline Text:</td>\n                    <td>\n                        <textarea name=\"headerField\" cols=\"40\" rows=\"5\">");
      out.print( offlineSettings.getOfflineText()  );
      out.write("</textarea><br/>\n                        <span class=\"jive-description\">Text to display to the user in the email form.</span>\n                    </td>\n                </tr>\n                    <input type=\"hidden\" name=\"save\" value=\"save\">\n                 <tr>\n                </tr>\n            ");
      out.write("\n            </table>\n            <table><tr>\n                 <td colspan=\"1\"> <input type=\"button\" name=\"save\" value=\"Save Changes\" onclick=\"return saveOfflineSettings();\" /></td>\n                <td colspan=\"1\"> <input type=\"submit\" name=\"delete\" value=\"Delete Changes\" /></td>\n            </tr></table>\n       </div>\n    </form>\n\n</body>\n</html>\n\n");
 } catch(Exception ex){ex.printStackTrace();} 
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
