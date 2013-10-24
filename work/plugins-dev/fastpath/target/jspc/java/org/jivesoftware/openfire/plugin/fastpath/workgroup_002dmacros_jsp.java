package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.*;
import java.util.*;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.macros.MacroGroup;
import org.jivesoftware.openfire.fastpath.macros.WorkgroupMacros;

public final class workgroup_002dmacros_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    private void writeMacroGroup(MacroGroup group, MacroGroup parent, StringBuilder builder, int space, String wgID){
        builder.append("<tr>");

        builder.append("<td width=\"1%\"><table cellspacing=\"0\" cellpadding=\"0\"><tr><td width=\"1%\" nowrap>");

        String spaceString = "";
        for(int i=0; i<space; i++){
            spaceString += "&nbsp;";
        }

        builder.append(spaceString);
        try {
            builder.append("<img src=\"images/folder-16x16.gif\"></td><td nowrap><a href=\"workgroup-view-responses.jsp?macroGroupTitle="+URLEncoder.encode(group.getTitle(), "UTF-8")+"&wgID="+wgID+"\">"+group.getTitle()+"</a></td>");
            builder.append("</tr></table>");

            builder.append("</td> <td>[<a href=\"workgroup-add-category.jsp?macroGroupTitle="+URLEncoder.encode(group.getTitle(), "UTF-8")+"&wgID="+wgID+"\">Add Category</a> | <a href=\"workgroup-add-response.jsp?macroGroupTitle="+URLEncoder.encode(group.getTitle(), "UTF-8")+"&wgID="+wgID+"\">Add Response</a> | <a href=\"workgroup-add-category.jsp?edit=true&macroGroupTitle="+URLEncoder.encode(group.getTitle(), "UTF-8")+"&wgID="+wgID+"\">Edit Category</a> | <a href=\"workgroup-macros.jsp?parentGroupTitle="+URLEncoder.encode(parent.getTitle(), "UTF-8")+"&macroGroupTitle="+URLEncoder.encode(group.getTitle(), "UTF-8")+"&wgID="+wgID+"&delete=true\">Delete</a>]</td></tr>");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        space += 6;
        for(MacroGroup g : group.getMacroGroups()){
            writeMacroGroup(g, group, builder, space, wgID);
        }


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
      out.write("\n\n\n\n\n");
 // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean delete = request.getParameter("delete") != null;

    String success = request.getParameter("success");
    String failure = request.getParameter("failure");


    Map errors = new HashMap();

    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    WorkgroupMacros workgroupMacros = WorkgroupMacros.getInstance();
    MacroGroup rootGroup = workgroupMacros.getMacroGroup(workgroup);


    if (delete) {
        String macroGroup = request.getParameter("macroGroupTitle");
        String parentGroup = request.getParameter("parentGroupTitle");
        MacroGroup parent = workgroupMacros.getMacroGroup(workgroup, parentGroup);
        MacroGroup group = workgroupMacros.getMacroGroup(workgroup, macroGroup);
        if (group != null && parent != null) {
            parent.removeMacroGroup(group);
        }
        success = macroGroup + " has been deleted.";
        workgroupMacros.saveMacros(workgroup);
    }

      out.write("\n\n<html>\n    <head>\n        <title>");
      out.print( "Canned Responses for "+wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-macros\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"add_canned_responses_to_a_workgroup.html\"/>-->\n    </head>\n    <body>\n\n<p>\nBelow are the Canned Response Categories to create your own global canned responses. These global canned responses\nwill be available to all agents within the \"");
      out.print(wgID);
      out.write("\" workgroup to use in their chat sessions.\n</p>\n\n");
  if (ModelUtil.hasLength(failure)) { 
      out.write("\n\n    <p class=\"jive-error-text\">\n    ");
      out.print( failure);
      out.write("\n    </p>\n\n");
  } else if (ModelUtil.hasLength(success)) { 
      out.write("\n\n    <div class=\"jive-success\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\n            <tbody>\n                <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\" border=\"0\"></td>\n                <td class=\"jive-icon-label\">\n                ");
      out.print( success);
      out.write("\n                </td></tr>\n            </tbody>\n            </table>\n            </div><br>\n");
 } 
      out.write("\n\n<table class=\"jive-table\" cellspacing=\"0\" cellpadding=\"0\"  width=\"100%\">\n<th colspan=\"2\">Manage Canned Categories</th>\n  <tr>\n    <td width=\"1%\">\n        <table cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n        <td width=\"1%\"><img src=\"images/folder-16x16.gif\"></td><td nowrap><a href=\"workgroup-view-responses.jsp?macroGroupTitle=");
      out.print(URLEncoder.encode(rootGroup.getTitle(), "UTF-8") );
      out.write("&wgID=");
      out.print(wgID);
      out.write('"');
      out.write('>');
      out.print( rootGroup.getTitle());
      out.write("</a></td>\n        </tr>\n        </table>\n    </td>\n    <td>[<a href=\"workgroup-add-category.jsp?macroGroupTitle=");
      out.print( URLEncoder.encode(rootGroup.getTitle(), "UTF-8"));
      out.write("&wgID=");
      out.print( wgID);
      out.write("\">Add Category</a> | <a href=\"workgroup-add-response.jsp?macroGroupTitle=");
      out.print( URLEncoder.encode(rootGroup.getTitle(), "UTF-8"));
      out.write("&wgID=");
      out.print(wgID);
      out.write("\">Add Response</a>]</td>\n    </tr>\n");

    StringBuilder builder = new StringBuilder();

    List<MacroGroup> macroGroups = rootGroup.getMacroGroups();
    for(MacroGroup groups : macroGroups){
        writeMacroGroup(groups, rootGroup, builder, 6, wgID);
    }

      out.write('\n');
      out.print( builder.toString() );
      out.write("\n</table>\n</body>\n</html>\n\n");
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
