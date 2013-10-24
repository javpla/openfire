package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import java.net.URL;
import java.net.MalformedURLException;

public final class workgroup_002drepos_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


      boolean success;

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

    final String wgID = request.getParameter("wgID");
    final WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

    String submit = request.getParameter("save");
    boolean hasSubmitted = ModelUtil.hasLength(submit);

    String forum = request.getParameter("forum");
    String kb = request.getParameter("kb");

    if(ModelUtil.hasLength(forum) && hasSubmitted){
        try {
            URL url = new URL(forum);
            workgroup.getProperties().setProperty("forums", forum);
            success = true;
        }
        catch (MalformedURLException e) {
            // Bad protocol
        }

    }
    else if(hasSubmitted) {
        workgroup.getProperties().deleteProperty("forums");
        success = true;
    }

    if(ModelUtil.hasLength(kb) && hasSubmitted){
        try {
            URL url = new URL(kb);
            workgroup.getProperties().setProperty("kb", kb);
            success = true;
        }
        catch (MalformedURLException e) {
           // Bad protocol
        }
    }
    else if(hasSubmitted){
        workgroup.getProperties().deleteProperty("kb");
        success = true;
    }


      out.write("\n\n\n\n<html>\n    <head>\n        <title>");
      out.print( "Search Settings for "+ wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-repos-settings\"/>\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"specify_search_settings_for_workgroup.html\"/>-->\n    </head>\n    <body>\n");


    String kbSetting = workgroup.getProperties().getProperty("kb");
    if(kbSetting == null){
        kbSetting = "";
    }

    String forumSetting = workgroup.getProperties().getProperty("forums");
    if(forumSetting == null){
        forumSetting = "";
    }

      out.write("\n    Use the form below to set the Jive Knowledge Base and/or Jive Forums you are using for this workgroup.\n    <br/><br/>\n");
 if (hasSubmitted && success){ 
      out.write("\n            <div class=\"jive-success\">\n                <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                    <tbody>\n                        <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\n                                                       border=\"0\"></td>\n                            <td class=\"jive-icon-label\">\n                                Repository Settings have been updated.\n                            </td></tr>\n                    </tbody>\n                </table>\n            </div>\n ");
 } else if(hasSubmitted && !success) { 
      out.write("\n\n        <p class=\"jive-error-text\">\n            An error occured. Please verify that you have filled out all required fields correctly and try again.\n        </p>\n");
 } 
      out.write("\n    <p>\n    <form action=\"workgroup-repos-settings.jsp\" method=\"post\" name=\"f\">\n        <fieldset><legend>Jive Repositories</legend>\n            <div>\n                <table class=\"box\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n                    <tr>\n                        <td width=\"30%\" >\n                            <b>Jive Forum Document Root:</b><br/><span class=\"jive-description\">The document root of your Jive Forums installation (ex. http://www.jivesoftware.com/forums). </span>\n                        </td>\n                        <td width=\"70%\">\n                            <input type=\"text\" name=\"forum\" value=\"");
      out.print( forumSetting);
      out.write("\" size=\"40\" maxlength=\"150\">\n                        </td>\n                    </tr>\n                    <tr>\n                        <td width=\"30%\">\n                            <b>Jive Knowledge Base Document Root:</b><br/><span class=\"jive-description\">The document root of your Jive Knowledge Base installation (ex. http://www.jivesoftware.com/kb)</span>\n                        </td>\n                        <td width=\"70%\">\n                            <input type=\"text\" name=\"kb\" value=\"");
      out.print( kbSetting );
      out.write("\" size=\"40\" maxlength=\"150\">\n                        </td>\n                    </tr>\n                    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\"/>\n                    ");
      out.write("\n                    <tr>\n                        <td colspan=\"2\">\n                            <input type=\"submit\" name=\"save\" value=\"Save Changes\">\n                        </td>\n                    </tr>\n                </table>\n            </div>\n        </fieldset>\n    </form>\n    </body>\n</html>");
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
