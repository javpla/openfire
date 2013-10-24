package org.jivesoftware.openfire.plugin.fastpath.forms;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.ParamUtils;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import java.util.StringTokenizer;
import java.util.List;
import java.util.Iterator;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.dataforms.FormManager;
import org.jivesoftware.openfire.fastpath.dataforms.WorkgroupForm;
import org.jivesoftware.openfire.fastpath.dataforms.FormElement;

public final class create_002delement_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


  private String getOption(WorkgroupForm.FormEnum form, String label, String answerType){
     String selected = form.toString().equals(answerType) ? "selected" : "";
     if(!ModelUtil.hasLength(answerType)){
         if(form == WorkgroupForm.FormEnum.textfield){
             selected = "selected";
         }
     }
     String returnStr = "<option value=\""+form.toString()+"\" "+selected+">"+label+"</option>";
     return returnStr;
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

    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();
    Workgroup workgroup = workgroupManager.getWorkgroup(new JID(wgID));

    FormManager formManager = FormManager.getInstance();

    WorkgroupForm workgroupForm = formManager.getWebForm(workgroup);
    if (workgroupForm == null) {
        workgroupForm = new WorkgroupForm();
        formManager.addWorkgroupForm(workgroup, workgroupForm);
    }

    boolean createElement = ParamUtils.getParameter(request, "createElement") != null;
    boolean edit = ParamUtils.getBooleanParameter(request, "edit", false);

    String label = ParamUtils.getParameter(request, "label");
    String variable = ParamUtils.getParameter(request, "variable");
    String answerType = ParamUtils.getParameter(request, "answer");
    boolean required = ParamUtils.getBooleanParameter(request, "required");
    String listItems = ParamUtils.getParameter(request, "items");
    String description = ParamUtils.getParameter(request, "description");

    boolean saveEdit = ParamUtils.getBooleanParameter(request, "saveEdit");
    int index = ParamUtils.getIntParameter(request, "index", -1);

    boolean hasCookie = false;

    if (createElement) {
        // Create Element
        FormElement formElement = new FormElement();
        if (saveEdit) {
            int saveIndex = ParamUtils.getIntParameter(request, "saveIndex", -1);
            formElement = workgroupForm.getFormElementAt(saveIndex);
            formElement.getAnswers().removeAll(formElement.getAnswers());
        }
        formElement.setLabel(label);
        formElement.setAnswerType(answerType);
        formElement.setRequired(required);
        formElement.setVisible(true);
        formElement.setVariable(variable);
        formElement.setDescription(description);

        if (listItems != null) {
            StringTokenizer tkn = new StringTokenizer(listItems, "\n");
            while (tkn.hasMoreTokens()) {
                String value = tkn.nextToken();
                value = value.replace('\r', ' ');
                formElement.getAnswers().add(value.trim());
            }
        }

        boolean prepopulate = ParamUtils.getBooleanParameter(request, "prepopulate");
        if (prepopulate) {
            String tag = "setCookie_" + variable;
            boolean containsTag = workgroupForm.containsHiddenTag(tag);
            if (!containsTag) {
                // Add new tag
                FormElement el = new FormElement();
                el.setAnswerType(WorkgroupForm.FormEnum.hidden);
                el.setVariable(tag);
                workgroupForm.addHiddenVar(el);
            }
        }
        else {
            String tag = "setCookie_" + variable;
            workgroupForm.removeHiddenVar(tag);
        }

        if (!saveEdit) {
            workgroupForm.addFormElement(formElement);
        }

        workgroup = workgroupManager.getWorkgroup(new JID(wgID));

        response.sendRedirect("workgroup-dataform.jsp?wgID=" + wgID);
        return;
    }

    String title = "Create Form Element";

    if (edit) {
        if (index != -1) {
            FormElement elem = workgroupForm.getFormElementAt(index);
            label = elem.getLabel();
            variable = elem.getVariable();
            description = elem.getDescription();
            answerType = elem.getAnswerType().toString();
            required = elem.isRequired();

            String tag = "setCookie_" + variable;
            hasCookie = workgroupForm.containsHiddenTag(tag);

            StringBuffer buf = new StringBuffer();
            List answers = elem.getAnswers();
            Iterator iter = answers.iterator();
            while (iter.hasNext()) {
                buf.append((String)iter.next());
                buf.append("\n");
            }
            listItems = buf.toString();
        }
        title = "Edit Form Element";
    }

    if (label == null) {
        label = "";
    }

    if (variable == null) {
        variable = "";
    }

    if (description == null) {
        description = "";
    }

    if (answerType == null) {
        answerType = "";
    }

    if (listItems == null) {
        listItems = "";
    }

      out.write("\n<html>\n    <head>\n        <title>");
      out.print( title );
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-forms\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"create_a_custom_form_field.html\"/>-->\n\n        <script>\n         function Jtrim(st) {\n            var len = st.length;\n            var begin = 0, end = len - 1;\n            while (st.charAt(begin) == \" \" && begin < len) {\n                begin++;\n            }\n            while (st.charAt(end) == \" \" && end > begin) {\n                end--;\n            }\n            return st.substring(begin, end + 1);\n         }\n\n         function validateForm(){\n             if(!Jtrim(document.f.label.value)){\n               alert(\"Please supply a label for this form element.\");\n               document.f.label.focus();\n               return false;\n             }\n\n             if(!Jtrim(document.f.variable.value)){\n               alert(\"Please supply a variable for this form element.\");\n               document.f.variable.focus();\n               return false;\n             }\n\n              if(document.f.variable.value.indexOf(\" \") != -1){\n               alert(\"Please supply a valid variable name for this form element.\");\n");
      out.write("               document.f.variable.focus();\n               return false;\n             }\n\n             var v = document.f.answer.value;\n             if(v == '");
      out.print( WorkgroupForm.FormEnum.dropdown_box);
      out.write("' || v == '");
      out.print( WorkgroupForm.FormEnum.radio_button);
      out.write("' || v == '");
      out.print( WorkgroupForm.FormEnum.checkbox);
      out.write("'){\n                if(!Jtrim(document.f.items.value)){\n                  alert(\"Please supply at least one item for a multi choice  element.\");\n                  return false;\n                }\n             }\n\n             return true;\n         }\n        </script>\n    </head>\n    <body>\n\n    <form name=\"f\" action=\"create-element.jsp\" method=\"post\" onsubmit=\"return validateForm(); return false;\"  >\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" width=\"600\">\n        <tr>\n            <th colspan=\"2\">New Form Element</th>\n        </tr>\n        <tr valign=\"top\">\n            <td>Variable Label:*</td><td><input type=\"text\" size=\"60\" name=\"label\" value=\"");
      out.print( label );
      out.write("\">\n            <br/><span class=\"jive-description\">The text to display on the HTML Form. e.g. Product:</span>\n            </td>\n        </tr>\n       <tr valign=\"top\">\n            <td>Variable Name:*</td><td><input type=\"text\" size=\"60\" name=\"variable\" value=\"");
      out.print( variable);
      out.write("\">\n            <br/><span class=\"jive-description\">The name of the html form element. e.g. product_name</span>\n            </td>\n        </tr>\n       <tr valign=\"top\">\n            <td>Description:</td><td><input type=\"text\" size=\"60\" name=\"description\" value=\"");
      out.print( description );
      out.write("\">\n             <br/><span class=\"jive-description\">A description of this form element.</span>\n            </td>\n        </tr>\n       <tr valign=\"top\">\n        <td>Answer Type:*</td>\n        <td>\n            <select name=\"answer\">\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.dropdown_box, "Dropdown Box", answerType) );
      out.write("\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.checkbox, "Checkbox", answerType) );
      out.write("\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.radio_button, "Radio Button", answerType) );
      out.write("\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.textfield, "TextField", answerType) );
      out.write("\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.textarea, "TextArea", answerType) );
      out.write("\n                ");
      out.print( getOption(WorkgroupForm.FormEnum.password, "Password", answerType) );
      out.write("\n            </select>\n        </td>\n        </tr>\n        <tr>\n            <td>&nbsp;</td>\n            <td><input type=\"checkbox\" name=\"required\" ");
      out.print( required ? "checked" : "");
      out.write(">&nbsp;<b>Required</b></td>\n        </tr>\n        <tr>\n        <td colspan=\"2\"><input type=\"checkbox\" name=\"prepopulate\" ");
      out.print( hasCookie ? "checked" : "");
      out.write(">Populate with user's previous choice.</td>\n        </tr>\n        </table>\n\n        <table class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" width=\"600\">\n        <tr>\n            <th colspan=\"2\">Add List Items</th>\n        </tr>\n        <tr>\n            <td colspan=\"2\"><i>Hit return after each list item.</i></td>\n        </tr>\n        <tr>\n        <td colspan=\"2\">\n            <textarea name=\"items\" cols=\"40\" rows=\"3\">");
      out.print( listItems );
      out.write("</textarea>\n        </td>\n        </tr>\n        <tr>\n           <td><input type=\"submit\" name=\"createElement\" value=\"Update\">&nbsp;\n           <input type=\"button\" name=\"cancel\" value=\"Cancel\" onclick=\"javascript:window.location.href='workgroup-dataform.jsp?wgID=");
      out.print(wgID);
      out.write("'\"></td>\n        </tr>\n        </table>\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID);
      out.write("\">\n        ");
 if(edit) { 
      out.write("\n        <input type=\"hidden\" name=\"saveEdit\" value=\"true\" />\n        <input type=\"hidden\" name=\"saveIndex\" value=\"");
      out.print( index );
      out.write("\" />\n        ");
 } 
      out.write("\n    </form>\n</body>\n</html>\n\n");
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
