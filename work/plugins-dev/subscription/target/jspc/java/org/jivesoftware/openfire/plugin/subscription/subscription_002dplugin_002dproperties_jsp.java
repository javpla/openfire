package org.jivesoftware.openfire.plugin.subscription;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.user.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.plugin.SubscriptionPlugin;
import org.jivesoftware.util.*;

public final class subscription_002dplugin_002dproperties_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n");

   boolean save = request.getParameter("save") != null;
   boolean success = request.getParameter("success") != null;
   String type = ParamUtils.getParameter(request, "type");
   String level = ParamUtils.getParameter(request, type);
   
   String username = ParamUtils.getParameter(request, "username");
   boolean addUser = ParamUtils.getBooleanParameter(request, "addUser");
   boolean deleteUser = ParamUtils.getBooleanParameter(request, "deleteUser");   

   SubscriptionPlugin plugin = (SubscriptionPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("subscription");

   Map<String, String> errors = new HashMap<String, String>();
   
	if (addUser) {
      if (username == null) {
          errors.put("missingUser", "missingUser");
      }
      else {
         username = username.trim().toLowerCase();
      
         try  {
            XMPPServer.getInstance().getUserManager().getUser(username);
            plugin.addWhiteListUser(username);
            response.sendRedirect("subscription-plugin-properties.jsp?addSuccess=true");
            return;
         }
         catch (UserNotFoundException unfe) {
            errors.put("userNotFound", "userNotFound");
         }
      }
   }

   if (deleteUser) {
      plugin.removeWhiteListUser(username);
      response.sendRedirect("subscription-plugin-properties.jsp?deleteSuccess=true");
      return;
   }
   
   
   if (save) {      
      plugin.setSubscriptionType(type);
      
      if (level != null) {
         plugin.setSubscriptionLevel(level);
      }
            
      response.sendRedirect("subscription-plugin-properties.jsp?success=true");
      return;
   }
   
   type = plugin.getSubscriptionType();
   level = plugin.getSubscriptionLevel();

      out.write("\n\n<html>\n\t<head>\n\t  <title>Subscription Service Properties</title>\n\t  <meta name=\"pageID\" content=\"subscription-plugin-properties\"/>\n   </head>\n   <body>\n   \n   <script language=\"JavaScript\" type=\"text/javascript\">\n      function addUsername() {\n         document.notifyform.addUser.value = 'true';\n         document.notifyform.submit();\n      }\n   </script>\n\n<p>Use the form below to set the subscription service properties.</p>\n\n");
 if (success) { 
      out.write("\n\n\t<div class=\"jive-success\">\n\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t<tbody>\n\t   <tr>\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n         <td class=\"jive-icon-label\">Service properties edited successfully.</td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   <br>\n    \n");
 } 
      out.write("\n\n<form action=\"subscription-plugin-properties.jsp?save\" name=\"notifyform\" method=\"post\">\n<input type=\"hidden\" name=\"addUser\" value=\"\">\n\n<fieldset>\n   <legend>Subscription Service Settings</legend>\n   <div>\n   <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n   <tbody>\n      <tr>\n         <td width=\"1%\">\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.DISABLED );
      out.write("\" id=\"rb01\"\n               ");
      out.print( (type.equals(SubscriptionPlugin.DISABLED) ? "checked" : "") );
      out.write(">\n         </td>\n         <td width=\"99%\">\n            <label for=\"rb01\"><strong>Disabled</strong></label> - No subscriptions requests will be intercepted.\n         </td>\n      </tr>      \n      <tr>\n         <td width=\"1%\">\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.ACCEPT );
      out.write("\" id=\"rb02\"\n               ");
      out.print( (type.equals(SubscriptionPlugin.ACCEPT) ? "checked" : "") );
      out.write(">\n         </td>\n         <td width=\"99%\">\n            <label for=\"rb02\"><strong>Accept</strong></label> - Subscription requests will be intercepted and accepted.\n         </td>\n      </tr>\n      <tr valign=\"top\">\n\t      <td width=\"1%\" nowrap>&nbsp;</td>\n         <td width=\"99%\">\n\n            <table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n               <tr>\n                  <td width=\"1%\">\n                     <input type=\"radio\" name=\"accept\" value=\"");
      out.print( SubscriptionPlugin.LOCAL );
      out.write("\" id=\"rb03\"\n                        ");
      out.print( (level.equals(SubscriptionPlugin.LOCAL) ? "checked" : "") );
      out.write(">\n                  </td>\n                  <td width=\"99%\">\n                     <label for=\"rb03\"><strong>Local</strong></label> - Only subscription requests sent by users <u>who have</u> an account on <i>");
      out.print(XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("</i> will be intercepted and accepted.\n                  </td>\n               </tr>\n               <tr>\n                  <td width=\"1%\">\n                     <input type=\"radio\" name=\"accept\" value=\"");
      out.print( SubscriptionPlugin.ALL );
      out.write("\" id=\"rb04\"\n                        ");
      out.print( (level.equals(SubscriptionPlugin.ALL) ? "checked" : "") );
      out.write(">\n                  </td>\n                  <td width=\"99%\">\n                     <label for=\"rb04\"><strong>All</strong></label> - All subscription requests will be intercepted and automatically accepted.\n                  </td>\n               </tr>\n             </table>\n         </td>\n      </tr>\n      <tr>\n         <td width=\"1%\">\n            <input type=\"radio\" name=\"type\" value=\"");
      out.print( SubscriptionPlugin.REJECT );
      out.write("\" id=\"rb05\"\n               ");
      out.print( (type.equals(SubscriptionPlugin.REJECT) ? "checked" : "") );
      out.write(">\n         </td>\n         <td width=\"99%\">\n            <label for=\"rb05\"><strong>Reject</strong></label> - Subscription requests will be intercepted and rejected.\n         </td>\n      </tr>\n      <tr valign=\"top\">\n         <td width=\"1%\" nowrap>&nbsp;</td>\n         <td width=\"99%\">\n            <table cellpadding=\"4\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n               <tr>\n                  <td width=\"1%\">\n                     <input type=\"radio\" name=\"reject\" value=\"");
      out.print( SubscriptionPlugin.LOCAL );
      out.write("\" id=\"rb06\"\n                        ");
      out.print( (level.equals(SubscriptionPlugin.LOCAL) ? "checked" : "") );
      out.write(">\n                  </td>\n                  <td width=\"99%\">\n                     <label for=\"rb06\"><strong>Local</strong></label> - Only subscription requests sent by users <u>who do not have</u> an account on <i>");
      out.print(XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("</i> will be intercepted and rejected.\n                  </td>\n               </tr>\n               <tr>\n                  <td width=\"1%\">\n                     <input type=\"radio\" name=\"reject\" value=\"");
      out.print( SubscriptionPlugin.ALL );
      out.write("\" id=\"rb07\"\n                        ");
      out.print( (level.equals(SubscriptionPlugin.ALL) ? "checked" : "") );
      out.write(">\n                  </td>\n                  <td width=\"99%\">\n                     <label for=\"rb07\"><strong>All</strong></label> - All subscription requests will be intercepted and rejected.\n                  </td>\n                </tr>\n             </table>\n         </td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   \n\t<br>\n   <input type=\"submit\" value=\"Save Settings\">\n</fieldset>\n\n<br><br>\n\n<fieldset>\n   <legend>White List</legend>\n   <div>\n   \n   <p>Any user specified in the list below will continue to have full control over manually accepting and rejecting subscription requests.</p>\n   \n   ");
 if (ParamUtils.getBooleanParameter(request, "deleteSuccess")) { 
      out.write("\n   \n   <div class=\"jive-success\">\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n   <tbody>\n      <tr>\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n         <td class=\"jive-icon-label\">User successfully removed.</td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   \n   ");
 } else if (ParamUtils.getBooleanParameter(request, "addSuccess")) { 
      out.write("\n   \n   <div class=\"jive-success\">\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n   <tbody>\n      <tr>\n         <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n         <td class=\"jive-icon-label\">User successfully added.</td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   \n   ");
 } else if (errors.containsKey("missingUser")) { 
      out.write("\n   \n   <div class=\"jive-error\">\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n   <tbody>\n      <tr>\n         <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n         <td class=\"jive-icon-label\">Missing user.</td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   \n   ");
 } else if (errors.containsKey("userNotFound")) { 
      out.write("\n   \n   <div class=\"jive-error\">\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n   <tbody>\n      <tr>\n         <td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n         <td class=\"jive-icon-label\">User not found.</td>\n      </tr>\n   </tbody>\n   </table>\n   </div>\n   \n   ");
 } 
      out.write("\n   \n   <div>\n   <label for=\"usertf\">Add user:</label>\n   <input type=\"text\" name=\"username\" size=\"30\" maxlength=\"100\" value=\"");
      out.print( (username != null ? username : "") );
      out.write("\" id=\"usertf\"/>\n   <input type=\"submit\" value=\"Add\" onclick=\"return addUsername();\"/>\n   \n   <br><br>\n   \n   <div class=\"jive-table\" style=\"width:400px;\">\n   <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n   <thead>\n      <tr>\n         <th width=\"99%\">User</th>\n         <th width=\"1%\" nowrap>Remove</th>\n      </tr>\n   </thead>\n   <tbody>\n   ");
 if (plugin.getWhiteListUsers().size() == 0) { 
      out.write("\n   \n   <tr>\n      <td width=\"100%\" colspan=\"2\" align=\"center\" nowrap>No users specified, use the form above to add one.</td>\n   </tr>\n   \n   ");
 } 
      out.write("\n   \n   ");
 for (String user : plugin.getWhiteListUsers()) { 
      out.write("\n   \n   <tr>\n      <td width=\"99%\">");
      out.print(user );
      out.write("</td>\n      <td width=\"1%\" align=\"center\"><a\n                     href=\"subscription-plugin-properties.jsp?deleteUser=true&username=");
      out.print(user );
      out.write("\"\n                     title=\"Delete User?\"\n                     onclick=\"return confirm('Are you sure you want to delete this user?');\"><img\n                     src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\"\n                     border=\"0\" alt=\"\"></a>\n      </td>\n   </tr>\n   \n   ");
 } 
      out.write("\n   </tbody>\n   </table>\n   </div>\n   </div>\n</fieldset>\n\n</form>\n\n</body>\n</html>");
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
