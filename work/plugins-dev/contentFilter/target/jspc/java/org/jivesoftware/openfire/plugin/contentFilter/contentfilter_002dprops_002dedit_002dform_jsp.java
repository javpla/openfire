package org.jivesoftware.openfire.plugin.contentFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.user.*;
import org.jivesoftware.openfire.plugin.ContentFilterPlugin;
import org.jivesoftware.util.*;
import java.util.regex.Pattern;

public final class contentfilter_002dprops_002dedit_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
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

    boolean save = request.getParameter("save") != null;
    boolean reset = request.getParameter("reset") !=null;
    boolean success = request.getParameter("success") != null;
    
    //filter options
    boolean patternsEnabled = ParamUtils.getBooleanParameter(request, "patternsenabled");
    String patterns =  ParamUtils.getParameter(request, "patterns");
    String [] filterStatusChecked = ParamUtils.getParameters(request, "filterstatus");
    boolean filterStatusEnabled = filterStatusChecked.length > 0;
 
    //match options
    boolean allowOnMatch = ParamUtils.getBooleanParameter(request, "allowonmatch");
    String [] maskChecked = ParamUtils.getParameters(request, "maskenabled");
	boolean maskEnabled = maskChecked.length > 0;
   	String mask =  ParamUtils.getParameter(request, "mask");

    //rejection options
    boolean rejectionNotificationEnabled = ParamUtils.getBooleanParameter(request, "rejectionnotificationenabled");
    String rejectionMsg = ParamUtils.getParameter(request, "rejectionMsg");
  
    //notification options  
    boolean notificationEnabled = ParamUtils.getBooleanParameter(request, "notificationenabled");
    String contactName = ParamUtils.getParameter(request, "contactname");
    List<String> notificationOptions = Arrays.asList(ParamUtils.getParameters(request, "notificationcb"));
    boolean notificationByIMEnabled = notificationOptions.contains("notificationbyim");
    boolean notificationByEmailEnabled = notificationOptions.contains("notificationbyemail");
    boolean includeOriginalEnabled = notificationOptions.contains("notificationincludeoriginal");
    
    //get handle to plugin
	ContentFilterPlugin plugin = (ContentFilterPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("contentfilter");

    //input validation
    Map<String, String> errors = new HashMap<String, String>();
    if (save) {
    
        if (patterns == null) {
            errors.put("missingPatterns", "missingPatterns");
        } else {
            String[] data = patterns.split(",");
            try {
                for (String aData : data) {
                    Pattern.compile(aData);
                }
            } catch (java.util.regex.PatternSyntaxException e) {
    			    errors.put("patternSyntaxException", e.getMessage());
    			}
    		}
    		    	
	    	if (mask == null) {
	    	    errors.put("missingMask", "missingMask");
	    	}
    	
	    	if (rejectionMsg == null) {
	    	    errors.put("missingRejectionMsg", "missingRejectionMsg");
	    	}
    	
	    	if (contactName == null) {
		    errors.put("missingContactName", "missingContactName");
		} else {
		    contactName = contactName.trim().toLowerCase();
		    try  {
		        User user = UserManager.getInstance().getUser(contactName);
		        if (notificationByEmailEnabled) {
		            //verify that the user has an email address
		            if (user.getEmail() == null) {
		                errors.put("userEmailNotConfigured", "userEmailNotConfigured");
		            }
		            //verify that the email server is configured
		            if (!JiveGlobals.getBooleanProperty("mail.configured", false)) {
		                errors.put("mailServerNotConfigured", "mailServerNotConfigured");
		            }
		        }
			} catch (UserNotFoundException unfe) {
			    errors.put("userNotFound", "userNotFound");
			}
		}
		
		if (!notificationByIMEnabled && !notificationByEmailEnabled) {
		    errors.put("notificationFormatNotConfigured", "notificationFormatNotConfigured");
		}
	    	       	    	    
	    if (errors.size() == 0) {
		    plugin.setPatternsEnabled(patternsEnabled);
		    plugin.setPatterns(patterns);
		    plugin.setFilterStatusEnabled(filterStatusEnabled);
		    plugin.setAllowOnMatch(allowOnMatch);
		    plugin.setMaskEnabled(maskEnabled);
		    plugin.setMask(mask);
	        plugin.setViolationNotificationEnabled(notificationEnabled);
	        plugin.setViolationContact(contactName);
	        plugin.setViolationNotificationByIMEnabled(notificationByIMEnabled);
	        plugin.setViolationNotificationByEmailEnabled(notificationByEmailEnabled);
	        plugin.setViolationIncludeOriginalPacketEnabled(includeOriginalEnabled);           
	        plugin.setRejectionNotificationEnabled(rejectionNotificationEnabled);
	        plugin.setRejectionMessage(rejectionMsg);            
	        response.sendRedirect("contentfilter-props-edit-form.jsp?success=true");
	        return;
	    }
    } else if (reset) {
      plugin.reset();
      response.sendRedirect("contentfilter-props-edit-form.jsp?success=true");
    } else {
        patterns = plugin.getPatterns();
        mask = plugin.getMask();   
        contactName = plugin.getViolationContact();
        rejectionMsg = plugin.getRejectionMessage();
    }

    if (errors.size() == 0) {
        patterns = plugin.getPatterns();
        mask = plugin.getMask();   
        contactName = plugin.getViolationContact();
        rejectionMsg = plugin.getRejectionMessage();
        notificationByIMEnabled = plugin.isViolationNotificationByIMEnabled();
        notificationByEmailEnabled = plugin.isViolationNotificationByEmailEnabled();
        includeOriginalEnabled = plugin.isViolationIncludeOriginalPacketEnabled();
    }
    
    patternsEnabled = plugin.isPatternsEnabled();
    filterStatusEnabled = plugin.isFilterStatusEnabled();
    allowOnMatch = plugin.isAllowOnMatch();
    maskEnabled = plugin.isMaskEnabled();
    notificationEnabled = plugin.isViolationNotificationEnabled();
    rejectionNotificationEnabled = plugin.isRejectionNotificationEnabled();


      out.write("\n\n<html>\n    <head>\n        <title>Content Filter</title>\n        <meta name=\"pageID\" content=\"contentfilter-props-edit-form\"/>\n    </head>\n    <body>\n\n<p>\nUse the form below to edit content filter settings.<br>\n</p>\n\n");
  if (success) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr>\n\t        <td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n\t        <td class=\"jive-icon-label\">Settings updated successfully.</td>\n        </tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } else if (errors.size() > 0) { 
      out.write("\n\n    <div class=\"jive-error\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr>\n        \t<td class=\"jive-icon\"><img src=\"images/error-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\"></td>\n        \t<td class=\"jive-icon-label\">Error saving the settings.</td>\n        </tr>\n    </tbody>\n    </table>\n    </div><br>\n\n");
  } 
      out.write("\n\n<form action=\"contentfilter-props-edit-form.jsp\" method=\"post\">\n\n<fieldset>\n    <legend>Filter</legend>\n    <div>\n    \n    <p>\n    To enable the content filter you need to set up some regular expressions.\n    </p>\n    \n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n    \t<tr>\n    \t    <td width=\"1%\">\n            <input type=\"radio\" name=\"patternsenabled\" value=\"false\" id=\"not01\"\n             ");
      out.print( ((patternsEnabled) ? "" : "checked") );
      out.write(">\n        </td>\n        <td width=\"99%\">\n            <label for=\"not01\"><b>Disabled</b></label> - Packets will not be filtered.\n        </td>\n    </tr>\n    <tr>\n        <td width=\"1%\">\n            <input type=\"radio\" name=\"patternsenabled\" value=\"true\" id=\"not02\"\n             ");
      out.print( ((patternsEnabled) ? "checked" : "") );
      out.write(">\n        </td>\n        <td width=\"99%\">\n            <label for=\"not02\"><b>Enabled</b></label> - Packets will be filtered.\n        </td>\n    </tr>\n    \t<tr>\n        \t<td>&nbsp;</td>\n        \t<td align=\"left\">Patterns:&nbsp;</td>\n    </tr>\n    <tr>\n        <td>&nbsp;</td>\n\t    <td>\n\t        <textarea rows=\"10\" cols=\"100\" name=\"patterns\">");
      out.print( (patterns != null ? patterns : "") );
      out.write("</textarea>\n\t        \t");
 if (errors.containsKey("missingPatterns")) { 
      out.write("\n\t            <span class=\"jive-error-text\">\n\t                <br>Please enter comma separated, regular expressions.\n\t            </span>\n\t            ");
 } else if (errors.containsKey("patternSyntaxException")) { 
      out.write("\n\t            <span class=\"jive-error-text\">\n\t                <br>Invalid regular expression: ");
      out.print( errors.get("patternSyntaxException") );
      out.write(". Please try again.\n\t            </span>\n\t            ");
 } 
      out.write("\n\t    </td>\n\t</tr>\n\t<tr>\n\t\t<td>&nbsp;</td>\n        <td><input type=\"checkbox\" name=\"filterstatus\" value=\"filterstatus\" ");
      out.print( filterStatusEnabled ? "checked" : "" );
      out.write("/>Filter users presence status.</td>\n\t</tr>\n    </tbody>\n    </table>\n    </div>\n</fieldset>\n\n<br><br>\n\n<fieldset>\n    <legend>On Content Match</legend>\n    <div>\n    \n    <p>\n    Configure this feature to reject or allow (and optionally mask) packet content when there is a match.\n    </p>\n    \n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n    \t<tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"allowonmatch\" value=\"false\" id=\"not03\"\n             ");
      out.print( ((allowOnMatch) ? "" : "checked") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not01\"><b>Reject</b></label> - Packets will be rejected.\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"allowonmatch\" value=\"true\" id=\"not04\"\n             ");
      out.print( ((allowOnMatch) ? "checked" : "") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not02\"><b>Allow</b></label> - Packets will be allowed.\n            </td>\n        </tr>\n        <tr>\n        \t<td>&nbsp;</td>\n\t        <td align=\"left\">Mask:&nbsp;\n\t        <input type=\"text\" size=\"100\" maxlength=\"100\" name=\"mask\" \n\t        \tvalue=\"");
      out.print( (mask != null ? mask : "") );
      out.write("\">\n\t        \t");
 if (errors.containsKey("missingMask")) { 
      out.write("\n\t            <span class=\"jive-error-text\">\n\t                <br>Please enter a mask.\n\t            </span>\n\t            ");
 } 
      out.write("\n\t        </td>\n\t    </tr>\n\t    <tr>\n\t\t<td>&nbsp;</td>\n        <td><input type=\"checkbox\" name=\"maskenabled\" value=\"maskenabled\" ");
      out.print( maskEnabled ? "checked" : "" );
      out.write("/>Enable mask.</td>\n\t</tr>\n    </tbody>\n    </table>\n    </div>\n</fieldset>\n\n<br><br>\n\n<fieldset>\n    <legend>Rejection Notification</legend>\n    <div>\n    \n    <p>\n    Enable this feature to have the sender notified whenever a packet is rejected.\n    NB: This feature is only operational if \"On Content Match\" is set to reject packets.\n    </p>\n    \n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n    \t<tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"rejectionnotificationenabled\" value=\"false\" id=\"not05\"\n             ");
      out.print( ((rejectionNotificationEnabled) ? "" : "checked") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not01\"><b>Disabled</b></label> - Notifications will not be sent out.\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"rejectionnotificationenabled\" value=\"true\" id=\"not06\"\n             ");
      out.print( ((rejectionNotificationEnabled) ? "checked" : "") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not02\"><b>Enabled</b></label> - Notifications will be sent out.\n            </td>\n        </tr>\n         <tr>\n        \t<td>&nbsp;</td>\n\t        <td align=\"left\">Rejection message:&nbsp;\n\t        <input type=\"text\" size=\"100\" maxlength=\"100\" name=\"rejectionMsg\" \n\t        \tvalue=\"");
      out.print( (rejectionMsg != null ? rejectionMsg : "") );
      out.write("\">\n\t        \t");
 if (errors.containsKey("missingRejectionMsg")) { 
      out.write("\n\t            <span class=\"jive-error-text\">\n\t                <br>Please enter a rejection message.\n\t            </span>\n\t            ");
 } 
      out.write("\n\t        </td>\n\t    </tr>\n    </tbody>\n    </table>\n    </div>\n</fieldset>\n\n<br><br>\n\n<fieldset>\n    <legend>Content Match Notification</legend>\n    <div>\n    \n    <p>\n    Enable this feature to have the contact person notified whenever there is a content match.\n    </p>\n    \n    <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n    <tbody>\n    \t<tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"notificationenabled\" value=\"false\" id=\"not07\"\n             ");
      out.print( ((notificationEnabled) ? "" : "checked") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not01\"><b>Disabled</b></label> - Notifications will not be sent out.\n            </td>\n        </tr>\n        <tr>\n            <td width=\"1%\">\n            <input type=\"radio\" name=\"notificationenabled\" value=\"true\" id=\"not08\"\n             ");
      out.print( ((notificationEnabled) ? "checked" : "") );
      out.write(">\n            </td>\n            <td width=\"99%\">\n                <label for=\"not02\"><b>Enabled</b></label> - Notifications will be sent out.\n            </td>\n        </tr>        \n        <tr>\n        \t    <td>&nbsp;</td>\n\t        <td align=\"left\">Username:&nbsp\n                <input type=\"text\" size=\"20\" maxlength=\"100\" name=\"contactname\" value=\"");
      out.print( (contactName != null ? contactName : "") );
      out.write('"');
      out.write('>');
      out.write('@');
      out.print( XMPPServer.getInstance().getServerInfo().getXMPPDomain() );
      out.write("\n\t\t        ");
 if (errors.containsKey("missingContactName")) { 
      out.write("\n\t\t            <span class=\"jive-error-text\">\n\t\t            <br>Please enter a username.\n\t\t            </span>\n\t\t        ");
 } else if (errors.containsKey("userNotFound")) { 
      out.write("\n\t\t            <span class=\"jive-error-text\">\n\t\t            <br>Could not find user. Please try again.\n\t\t            </span>\n\t\t        ");
 } 
      out.write("\n\t        </td>\n\t    </tr>\n\t    <tr>\n\t        <td>&nbsp;</td>\n\t        <td>\n                <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationbyim\" ");
      out.print( notificationByIMEnabled ? "checked" : "" );
      out.write("/>Notify by IM.\n                <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationbyemail\" ");
      out.print( notificationByEmailEnabled ? "checked" : "" );
      out.write("/>Notify by Email.\n\t            <input type=\"checkbox\" name=\"notificationcb\" value=\"notificationincludeoriginal\" ");
      out.print( includeOriginalEnabled ? "checked" : "" );
      out.write("/>Include original packet.\n\t            ");
 if (errors.containsKey("mailServerNotConfigured")) { 
      out.write("\n\t\t            <span class=\"jive-error-text\">\n\t\t            <br>Error, sending an email will fail because the mail server is not setup. Please go to the <a href=\"/system-email.jsp\">mail settings page</a> and set the mail host.\n\t\t            </span>\n\t\t        ");
 } else if (errors.containsKey("userEmailNotConfigured")) { 
      out.write("\n\t\t            <span class=\"jive-error-text\">\n\t\t            <br>Please configure <a href=\"/user-properties.jsp?username=");
      out.print( contactName );
      out.write('"');
      out.write('>');
      out.print( contactName );
      out.write("'s</a> email address.\n\t\t            </span>\n\t\t        ");
 } else if (errors.containsKey("notificationFormatNotConfigured")) { 
      out.write("\n\t\t            <span class=\"jive-error-text\">\n\t\t            <br>Users must be notified by IM and/or Email.\n\t\t            </span>\n\t\t        ");
 } 
      out.write("\n\t        </td>\n\t    </tr>\n    </tbody>\n    </table>\n    </div>\n</fieldset>\n\n<br><br>\n\n<input type=\"submit\" name=\"save\" value=\"Save settings\">\n<input type=\"submit\" name=\"reset\" value=\"Restore factory settings*\">\n</form>\n\n<br><br>\n\n<em>*Restores the plugin to its factory state, you will lose all changes ever made to this plugin!</em>\n</body>\n</html>");
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
