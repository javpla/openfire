package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.DbProperties;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.Log;

public final class workgroup_002dtranscript_002dconfig_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      			"workgroup-error.jsp", true, 8192, true);
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

     boolean delete = request.getParameter("removeChanges") != null;
     boolean save = request.getParameter("save") != null && !delete;

     boolean emailConfigured = false;

     String property = JiveGlobals.getProperty("mail.configured");
     if (ModelUtil.hasLength(property)) {
        emailConfigured = true;
     }

     DbProperties props = workgroup.getProperties();
     String context = "jive.transcript";
     String from = "";
     String subject = "";
     String message = "";
     String fromEmail = "";
     if (save) {
         from = request.getParameter("from");
         fromEmail = request.getParameter("fromEmail");
         subject = request.getParameter("subject");
         message = request.getParameter("message");
         if (ModelUtil.hasLength(from) && ModelUtil.hasLength(subject) && ModelUtil.hasLength(message) && ModelUtil.hasLength(fromEmail)) {
             props.setProperty(context + ".from", from);
             props.setProperty(context + ".fromEmail", fromEmail);
             props.setProperty(context + ".subject", subject);
             props.setProperty(context + ".message", message);
         }
     }
     else if(delete){
        props.deleteProperty(context + ".from");
        props.deleteProperty(context + ".fromEmail");
        props.deleteProperty(context + ".subject");
        props.deleteProperty(context + ".message");
         System.out.println("DELTED");
     }
     else {
         from = props.getProperty(context + ".from");
         subject = props.getProperty(context + ".subject");
         message = props.getProperty(context + ".message");
         fromEmail = props.getProperty(context + ".fromEmail");
         if (from == null) {
             from = "";
         }

         if (subject == null) {
             subject = "";
         }

         if (message == null) {
             message = "";
         }

         if (fromEmail == null) {
             fromEmail = "";
         }
     }
 
      out.write("\n<html>\n    <head>\n        <title>");
      out.print( "Transcript Settings for "+wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-transcript-config\"/>\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"set_header_and_message_text_for_transcript_emails.html\"/>-->\n    </head>\n    <body>\n\n    Specify specific configuration when a user requests a transcript from the system.\n    <br/><br/>\n\n    ");
 if(save) { 
      out.write("\n   <div class=\"success\">\n    Transcript Settings have been updated\n    </div>\n    ");
}
      out.write("\n\n\n       ");
 if(!emailConfigured){ 
      out.write("\n            <div class=\"error\">\n                Transcripts cannot be sent until you configure your <a href=\"../../system-email.jsp\">email settings</a>.\n            </div>\n    ");
 } 
      out.write("\n\n    <p/>\n    <form action=\"workgroup-transcript-config.jsp\" method=\"get\" name=\"offline\">\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n    <div>\n        <div class=\"jive-contentBoxHeader\">\n        Conversation Transcript Configuration\n        </div>\n        <table width=\"100%\" cellpadding=\"3\" cellspacing=\"3\" border=\"0\" class=\"jive-contentBox\">\n                <tr valign=\"top\">\n                    <td>From:</td>\n                    <td>\n                        <input type=\"text\" size=\"40\" name=\"from\" value=\"");
      out.print( from );
      out.write("\" /><br/><span class=\"jive-description\">Specify who the transcript is from, such as ACME Company.</span>\n                    </td>\n                </tr>\n\n                  <tr valign=\"top\">\n                    <td>Email Address:</td>\n                    <td>\n                        <input type=\"text\" size=\"40\" name=\"fromEmail\" value=\"");
      out.print( fromEmail );
      out.write("\" /><br/><span class=\"jive-description\">Specify the email address the message will be from. Ex. support@acme.com</span>\n                    </td>\n                </tr>\n\n               <tr valign=\"top\">\n\n                    <td>Subject:</td>\n                    <td>\n                        <input type=\"text\" size=\"40\" name=\"subject\" value=\"");
      out.print( subject );
      out.write("\"/><br/><span class=\"jive-description\">The subject that will appear to the user.</span>\n                    </td>\n                </tr>\n                <!--  End Of Subject Line -->\n                 <tr valign=\"top\">\n\n                    <td>Message:</td>\n                    <td>\n                        <textarea name=\"message\" cols=\"40\" rows=\"3\">");
      out.print( message );
      out.write("</textarea><br/><span class=\"jive-description\">Text to prepend to the transcript being sent.</span>\n                    </td>\n                </tr>\n                    <input type=\"hidden\" name=\"save\" value=\"save\">\n                 <tr>\n                <td colspan=\"1\"> <input type=\"button\" name=\"save\" value=\"Save Changes\" onclick=\"return saveSettings();\" /></td>\n                <td colspan=\"1\"> <input type=\"submit\" name=\"removeChanges\" value=\"Remove Changes\"  /></td>\n                <td>&nbsp;</td>\n                </tr>\n            ");
      out.write("\n            </table>\n       </div>\n       </form>\n       <script>\n       function saveSettings(){\n            var from = document.offline.from.value;\n            var subject = document.offline.subject.value;\n            var message = document.offline.message.value;\n\n            if(!Jtrim(from) || !Jtrim(subject) || !Jtrim(message)){\n                alert(\"All fields are required.\");\n                document.offline.from.focus();\n                return;\n            }\n               document.offline.submit();\n       }\n\n\n\n        function Jtrim(st) {\n            var len = st.length;\n            var begin = 0, end = len - 1;\n            while (st.charAt(begin) == \" \" && begin < len) {\n                begin++;\n            }\n            while (st.charAt(end) == \" \" && end > begin) {\n                end--;\n            }\n            return st.substring(begin, end + 1);\n        }\n       </script>\n\n</body>\n</html>\n\n");
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
