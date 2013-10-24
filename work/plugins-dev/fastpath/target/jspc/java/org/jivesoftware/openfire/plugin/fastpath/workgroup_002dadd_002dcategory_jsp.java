package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.util.*;
import java.util.*;
import java.net.URLEncoder;
import java.net.URLDecoder;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.macros.MacroGroup;
import org.jivesoftware.openfire.fastpath.macros.WorkgroupMacros;

public final class workgroup_002dadd_002dcategory_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n\n\n\n");
 // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    boolean add = request.getParameter("add") != null;
    boolean delete = request.getParameter("remove") != null;
    boolean edit = request.getParameter("edit") != null;

    Map errors = new HashMap();

    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));
    WorkgroupMacros workgroupMacros = WorkgroupMacros.getInstance();

    String groupTitle = URLDecoder
            .decode(ParamUtils.getParameter(request, "macroGroupTitle"), "UTF-8");


    MacroGroup rootGroup = workgroupMacros.getMacroGroup(workgroup, groupTitle);

    String categoryTitle = ParamUtils.getParameter(request, "categoryTitle");
    MacroGroup g = workgroupMacros.getMacroGroup(workgroup, categoryTitle);
    if (g != null) {
        response.sendRedirect("workgroup-macros.jsp?wgID=" + wgID
                + "&categoryExists=true&failure=Category name already exists. Please choose a unique category name.");
        return;
    }
    else if (add && !edit) {
        if (ModelUtil.hasLength(categoryTitle)) {
            // Create new MacroGroup and add
            MacroGroup group = new MacroGroup();
            group.setTitle(categoryTitle);
            rootGroup.addMacroGroup(group);
            workgroupMacros.saveMacros(workgroup);
            response.sendRedirect(
                    "workgroup-macros.jsp?success=New category has been added&wgID=" + wgID);
            return;
        }
    }
    else if (edit && ModelUtil.hasLength(categoryTitle)) {
        rootGroup.setTitle(categoryTitle);
        workgroupMacros.saveMacros(workgroup);
        response.sendRedirect("workgroup-macros.jsp?wgID=" + wgID + "&success=Category edited");
        return;
    }


      out.write("\n\n<html>\n    <head>\n        <title>Workgroup Add/Edit Category</title>\n        <meta name=\"subPageID\" content=\"workgroup-macros\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n    </head>\n    <body>\n<script language=\"javascript\">\n        function Jtrim(st) {\n            var len = st.length;\n            var begin = 0, end = len - 1;\n            while (st.charAt(begin) == \" \" && begin < len) {\n                begin++;\n            }\n            while (st.charAt(end) == \" \" && end > begin) {\n                end--;\n            }\n            return st.substring(begin, end + 1);\n        }\n\n        function validateForm(){\n           if(!Jtrim(document.f.categoryTitle.value)){\n              alert(\"You must specify a valid name for the new category.\");\n              document.f.categoryTitle.focus();\n              return false;\n           }\n           return true;\n        }\n</script>\n\n");
 if(!edit){ 
      out.write("\n<p>\nCreate a new Category as a sub-category to \"<b>");
      out.print( groupTitle);
      out.write("</b>\" in the Form below.\n</p>\n");
 } else { 
      out.write("\n<p>\nUse the form below to edit the \"<b>");
      out.print( groupTitle);
      out.write("</b>\" category.\n</p>\n");
 } 
      out.write("\n\n<form name=\"f\" action=\"workgroup-add-category.jsp\" method=\"post\" onsubmit=\"return validateForm(); return false;\">\n<input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\" />\n<input type=\"hidden\" name=\"macroGroupTitle\" value=\"");
      out.print( URLEncoder.encode(groupTitle, "UTF-8") );
      out.write("\" />\n<input type=\"hidden\" name=\"add\" value=\"true\" />\n");
 if(edit){ 
      out.write("\n<input type=\"hidden\" name=\"edit\" value=\"true\" />\n");
 }
      out.write("\n<table class=\"jive-table\" cellspacing=\"0\" cellpadding=\"0\"  width=\"100%\">\n<th colspan=\"2\">");
      out.print( edit ? "Edit Category" : "Add Category" );
      out.write("</th>\n  <tr>\n  <td>Category Title</td><td><input type=\"text\" name=\"categoryTitle\" size=\"40\" maxlength=\"40\"><br><span class=\"jive-description\">");
      out.print(edit ? "Edit category" : "Adding new category to ");
      out.write(" <b>");
      out.print( rootGroup.getTitle() );
      out.write("</b></span></td>\n  </tr>\n  <tr>\n  <td colspan=\"2\">\n  <input type=\"submit\" name=\"Add\" value=\"");
      out.print( edit ? "Edit Category" : "Add Category");
      out.write("\">\n  </td>\n  </tr>\n</table>\n</form>\n</body>\n</html>\n\n");
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
