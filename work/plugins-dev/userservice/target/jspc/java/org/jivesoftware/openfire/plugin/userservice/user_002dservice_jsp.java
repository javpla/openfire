package org.jivesoftware.openfire.plugin.userservice;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.util.*;
import org.jivesoftware.openfire.plugin.UserServicePlugin;

public final class user_002dservice_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
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
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n\n\n\n\n");
      out.write('\n');
      org.jivesoftware.util.WebManager admin = null;
      synchronized (_jspx_page_context) {
        admin = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("admin", PageContext.PAGE_SCOPE);
        if (admin == null){
          admin = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("admin", admin, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write('\n');
 admin.init(request, response, session, application, out ); 
      out.write('\n');
      out.write('\n');
  // Get parameters
    boolean save = request.getParameter("save") != null;
    boolean success = request.getParameter("success") != null;
    String secret = ParamUtils.getParameter(request, "secret");
    boolean enabled = ParamUtils.getBooleanParameter(request, "enabled");
    String allowedIPs = ParamUtils.getParameter(request, "allowedIPs");

    UserServicePlugin plugin = (UserServicePlugin) XMPPServer.getInstance().getPluginManager().getPlugin("userservice");

    // Handle a save
    Map errors = new HashMap();
    if (save) {
        if (errors.size() == 0) {
            plugin.setEnabled(enabled);
        	plugin.setSecret(secret);
            plugin.setAllowedIPs(StringUtils.stringToCollection(allowedIPs));
            response.sendRedirect("user-service.jsp?success=true");
            return;
        }
    }

    secret = plugin.getSecret();
    enabled = plugin.isEnabled();
    allowedIPs = StringUtils.collectionToString(plugin.getAllowedIPs());

      out.write("\n\n<html>\n    <head>\n        <title>User Service Properties</title>\n        <meta name=\"pageID\" content=\"user-service\"/>\n    </head>\n    <body>\n\n\n<p>\nUse the form below to enable or disable the User Service and configure the secret key.\nBy default the User Service plugin is <strong>disabled</strong>, which means that\nHTTP requests to the service will be ignored.\n</p>\n\n");
  if (success) { 
      out.write("\n\n    <div class=\"jive-success\">\n    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tbody>\n        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n        <td class=\"jive-icon-label\">\n            User service properties edited successfully.\n        </td></tr>\n    </tbody>\n    </table>\n    </div><br>\n");
 } 
      out.write("\n\n<form action=\"user-service.jsp?save\" method=\"post\">\n\n<fieldset>\n    <legend>User Service</legend>\n    <div>\n    <p>\n    The addition, deletion and editing of users is not normally available outside of the admin console.\n    This service lets those administration tasks be performed HTTP requests to provide\n    simple integration with other applications.</p>\n\n    <p>However, the presence of this service exposes a security risk. Therefore,\n    a secret key is used to validate legitimate requests to this service. Moreover,\n    for extra security you can specify the list of IP addresses that are allowed to\n    use this service. An empty list means that the service can be accessed from any\n    location. Addresses are delimited by commas.\n    </p>\n    <ul>\n        <input type=\"radio\" name=\"enabled\" value=\"true\" id=\"rb01\"\n        ");
      out.print( ((enabled) ? "checked" : "") );
      out.write(">\n        <label for=\"rb01\"><b>Enabled</b> - User service requests will be processed.</label>\n        <br>\n        <input type=\"radio\" name=\"enabled\" value=\"false\" id=\"rb02\"\n         ");
      out.print( ((!enabled) ? "checked" : "") );
      out.write(">\n        <label for=\"rb02\"><b>Disabled</b> - User service requests will be ignored.</label>\n        <br><br>\n\n        <label for=\"text_secret\">Secret key:</label>\n        <input type=\"text\" name=\"secret\" value=\"");
      out.print( secret );
      out.write("\" id=\"text_secret\">\n        <br><br>\n\n        <label for=\"text_secret\">Allowed IP Addresses:</label>\n        <textarea name=\"allowedIPs\" cols=\"40\" rows=\"3\" wrap=\"virtual\">");
      out.print( ((allowedIPs != null) ? allowedIPs : "") );
      out.write("</textarea>\n    </ul>\n    </div>\n</fieldset>\n\n<br><br>\n\n<input type=\"submit\" value=\"Save Settings\">\n</form>\n\n\n</body>\n</html>");
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

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("admin");
    _jspx_th_c_set_0.setValue(new String("${admin.manager}"));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }
}
