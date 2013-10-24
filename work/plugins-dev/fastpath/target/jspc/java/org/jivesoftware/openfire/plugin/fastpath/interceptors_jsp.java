package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.beans.*;
import java.util.*;
import java.lang.reflect.Method;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.interceptor.*;
import org.jivesoftware.util.*;

public final class interceptors_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

 // Global variables/methods for this page

    private boolean isInstalledInterceptor(Workgroup workgroup,
            InterceptorManager interceptorManager, PacketInterceptor interceptor) {
        try {
            if (interceptor == null) {
                return false;
            }
            String interceptorClassname = interceptor.getClass().getName();
            List<PacketInterceptor> interceptors = workgroup == null ? interceptorManager
                    .getInterceptors()
                    : interceptorManager.getInterceptors(workgroup.getJID().toBareJID());
            for (PacketInterceptor installedInterceptor : interceptors) {
                if (interceptorClassname.equals(installedInterceptor.getClass().getName())) {
                    return true;
                }
            }
        }
        catch (Exception ignored) {
        }
        return false;
    }

    private String getHTML(PacketInterceptor interceptor, PropertyDescriptor descriptor) {
        // HTML of the customizer for this property
        StringBuffer html = new StringBuffer(50);
        // Get the name of the property (this becomes the name of the form element)
        String propName = descriptor.getName();
        // Get the current value of the property
        Object propValue = null;
        try {
            propValue = descriptor.getReadMethod().invoke(interceptor, (Object[])null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Get the classname of this property
        String className = descriptor.getPropertyType().getName();

        // HTML form elements for number values (rendered as small textfields)
        if ("int".equals(className)
                || "double".equals(className)
                || "long".equals(className)) {
            html.append("<input type=\"text\" name=\"").append(propName)
                    .append("\" size=\"6\" maxlength=\"10\"");
            if (propValue != null) {
                html.append(" value=\"").append(propValue.toString()).append("\"");
            }
            html.append(">");
        }
        // HTML form elements for boolean values (rendered as Yes/No radio buttons)
        else if ("boolean".equals(className)) {
            boolean value = false;
            if ("true".equals(propValue.toString())) {
                value = true;
            }
            html.append("<input type=\"radio\" name=\"").append(propName).append("\" id=\"rb")
                    .append(propName).append("1\" ");
            html.append("value=\"true\"");
            html.append((value) ? " checked" : "");
            html.append("> <label for=\"rb").append(propName).append("1\">Yes</label> ");
            html.append("<input type=\"radio\" name=\"").append(propName).append("\" id=\"rb")
                    .append(propName).append("2\" ");
            html.append("value=\"false\"");
            html.append((!value) ? " checked" : "");
            html.append("> <label for=\"rb").append(propName).append("2\">No</label> ");
        }
        else if ("java.lang.String".equals(className)) {
            // Indicates we should print a textarea if the large text field is specified to be used
            boolean useLarge = ("true".equals(descriptor.getValue("useLargeTextField")));

            // HTML elements for a String or String[] (rendered as a single-line textarea)
            if (descriptor.getPropertyType().isArray()) {
                // Print out a customizer for a String array:
                String[] valArray = (String[])propValue;
                for (int i = 0; i < valArray.length; i++) {
                    html.append(printStringHTML(propName + i, valArray[i], useLarge));
                    html.append("<input type=\"submit\" name=\"deletePropEntry")
                            .append(i).append("\" value=\"Delete\">")
                            .append("<br>");
                }
                html.append("<br>");

                html.append(printStringHTML(propName, null, useLarge));

                html.append("<input type=\"hidden\" name=\"addNewPropName");
                html.append("\" value=\"").append(propName).append("\">");
                html.append("<input type=\"submit\" name=\"addNewProp\" ");
                html.append("value=\"Add\">");
                html.append("<input type=\"hidden\" name=\"deletePropertyName");
                html.append("\" value=\"").append(propName).append("\">");
            }
            // Else, it's just a POS (plain old String) :)
            else {
                if (propName.toLowerCase().equals("password")) {
                    html.append("<input type=\"password\"").append(" name=\"").append(propName);
                    html.append("\" size=\"30\" maxlength=\"150\"");
                    if (propValue != null) {
                        html.append(" value=\"").append(escapeHTML(propValue.toString()))
                                .append("\"");
                    }
                    html.append(">");
                }
                else {
                    String value = null;
                    if (propValue != null) {
                        value = propValue.toString();
                    }
                    html.append(printStringHTML(propName, value, useLarge));
                }
            }
        }
        if (html.length() == 0) {
            html.append("&nbsp;");
        }
        return html.toString();
    }

    // Handles printing a string text field either as a textfield or a textarea.
    private String printStringHTML(String name, String value, boolean useLarge) {
        StringBuffer buf = new StringBuffer(50);
        if (useLarge) {
            buf.append("<textarea name=\"").append(name).append("\" cols=\"40\" rows=\"3\">");
            if (value != null) {
                buf.append(escapeHTML(value));
            }
            buf.append("</textarea>");
        }
        else {
            buf.append("<input type=\"text\" name=\"").append(name)
                    .append("\" size=\"40\" maxlength=\"255\" ");
            if (value != null) {
                buf.append("value=\"").append(escapeHTML(value)).append("\"");
            }
            buf.append(">");
        }
        return buf.toString();
    }

    private Map getInterceptorPropertyValues(HttpServletRequest request,
            PacketInterceptor interceptor) {
        // Map of interceptor property name/value pairs
        Map map = new HashMap();
        try {
            // Property descriptors
            PropertyDescriptor[] descriptors = BeanUtils
                    .getPropertyDescriptors(interceptor.getClass());
            // Loop through the properties, get the value of the property as a
            // parameter from the HttpRequest object
            for (int i = 0; i < descriptors.length; i++) {
                // Don't set any array properties:
                if (!descriptors[i].getPropertyType().isArray()) {
                    String propName = descriptors[i].getName();
                    String propValue = ParamUtils.getParameter(request, propName);
                    map.put(propName, propValue);
                }
            }
        }
        catch (Exception e) {
        }
        return map;
    }

    private String escapeHTML(String html) {
        html = StringUtils.replace(html, "\"", "&quot;");
        return StringUtils.escapeHTMLTags(html);
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
      out.write('\n');
      out.write('\n');
      out.write('\n');
 // Get parameters
    String workgroupID = ParamUtils.getParameter(request, "wgID");
    String managerType = ParamUtils.getParameter(request, "managerType");
    String classname = ParamUtils.getParameter(request, "interceptors");
    boolean install = ParamUtils.getBooleanParameter(request, "install");
    boolean remove = ParamUtils.getBooleanParameter(request, "remove");
    int position = ParamUtils.getIntParameter(request, "pos", -1);
    boolean edit = ParamUtils.getBooleanParameter(request, "edit");
    boolean addInterceptor = ParamUtils.getBooleanParameter(request, "addInterceptor");
    String newClassname = ParamUtils.getParameter(request, "newClassname");
    boolean saveProperties = ParamUtils.getBooleanParameter(request, "saveProperties");
    int interceptorIndex = ParamUtils.getIntParameter(request, "interceptorIndex", -1);
    boolean changePosition = ParamUtils.getBooleanParameter(request, "changePos");
    boolean up = ParamUtils.getBooleanParameter(request, "up");
    boolean down = ParamUtils.getBooleanParameter(request, "down");
    String deletePropertyName = ParamUtils.getParameter(request, "deletePropertyName");
    boolean addNewProp = request.getParameter("addNewProp") != null;

    String error = "";
    String errorMessage = ParamUtils.getParameter(request, "errorMessage");

    // Determine if we need to delete a String[] property entry
    boolean deletePropEntry = false;
    int deleteIndex = -1;

    for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
        String name = (String)e.nextElement();
        if (name.startsWith("deletePropEntry")) {
            try {
                int pos = "deletePropEntry".length();
                deleteIndex = Integer.parseInt(name.substring(pos, name.length()));
            }
            catch (Exception ignored) {
            }
            if (deleteIndex > -1) {
                deletePropEntry = true;
                break;
            }
        }
    }

    // Indicate if we're doing global interceptors
    boolean isGlobal = (workgroupID == null);

    WorkgroupManager workgroupManager = WorkgroupManager.getInstance();

    // Load the workgroup
    Workgroup workgroup = isGlobal ? null : workgroupManager.getWorkgroup(new JID(workgroupID));

    // Get the interceptor manager
    InterceptorManager interceptorManager = null;
    if (managerType == null) {
        managerType = "workgroup";
    }
    if ("workgroup".equals(managerType)) {
        interceptorManager = WorkgroupInterceptorManager.getInstance();
    }
    else if ("agent".equals(managerType)) {
        interceptorManager = AgentInterceptorManager.getInstance();
    }
    else if ("chatbot".equals(managerType)) {
        interceptorManager = ChatbotInterceptorManager.getInstance();
    }
    else if ("queue".equals(managerType)) {
        interceptorManager = QueueInterceptorManager.getInstance();
    }
    else if ("room".equals(managerType)) {
        interceptorManager = RoomInterceptorManager.getInstance();
    }
    else if ("offer".equals(managerType)) {
        interceptorManager = OfferInterceptorManager.getInstance();
    }

    // Add a new property for a String[] property type:
    if (addNewProp) {
        // Get the name of the interceptor for the new property:
        String newPropName = ParamUtils.getParameter(request, "addNewPropName");
        if (newPropName != null) {
            // Get the value of the new property:
            String newPropValue = ParamUtils.getParameter(request, "addNewProp" + newPropName);
            if (newPropValue != null) {
                // The interceptor we're working with:
                PacketInterceptor interceptor = (isGlobal ? interceptorManager
                        .getInterceptor(interceptorIndex) : interceptorManager
                        .getInterceptor(workgroup.getJID().toBareJID(), interceptorIndex));
                PropertyDescriptor[] descriptors = (Introspector.getBeanInfo(
                        interceptor.getClass())).getPropertyDescriptors();
                PropertyDescriptor propDescriptor = null;
                // Look for the property specified
                for (int i = 0; i < descriptors.length; i++) {
                    if (descriptors[i].getName().equals(newPropName)) {
                        propDescriptor = descriptors[i];
                        break;
                    }
                }
                if (propDescriptor != null) {
                    // Get both the read and write methods:
                    Method readMethod = propDescriptor.getReadMethod();
                    Method writeMethod = propDescriptor.getWriteMethod();
                    // Get the String[] via the read method:
                    String[] entries = (String[])readMethod.invoke(interceptor, (Object[])null);
                    // Make a new entry array of entries.length+1 because we're
                    // adding one more entry to the property
                    String[] newEntries = new String[entries.length + 1];
                    for (int i = 0; i < entries.length; i++) {
                        newEntries[i] = entries[i];
                    }
                    // The new prop value goes in the last spot of newEntries:
                    newEntries[newEntries.length - 1] = newPropValue;
                    // Use the write method to save the new entries:
                    writeMethod.invoke(interceptor, new Object[]{newEntries});
                    // Save interceptors
                    interceptorManager.saveInterceptors();
                    // Done, so redirect
                    StringBuffer url = new StringBuffer();
                    url.append("interceptors.jsp?edit=true&pos=").append(interceptorIndex)
                            .append("&managerType=").append(managerType);
                    if (!isGlobal) {
                        url.append("&wgID=" + workgroupID);
                    }
                    response.sendRedirect(url.toString());
                    return;
                }
            }
        }
    }

    // Remove one of the String[] prop entries:
    if (deletePropEntry) {
        if (deletePropertyName != null) {
            // The interceptor we're working with:
            PacketInterceptor interceptor = (isGlobal ? interceptorManager
                    .getInterceptor(interceptorIndex) : interceptorManager
                    .getInterceptor(workgroup.getJID().toBareJID(), interceptorIndex));
            PropertyDescriptor[] descriptors = (Introspector.getBeanInfo(interceptor.getClass()))
                    .getPropertyDescriptors();
            PropertyDescriptor propDescriptor = null;
            // Look for the property specified
            for (int i = 0; i < descriptors.length; i++) {
                if (descriptors[i].getName().equals(deletePropertyName)) {
                    propDescriptor = descriptors[i];
                    break;
                }
            }
            if (propDescriptor != null) {
                // Get both the read and write methods:
                Method readMethod = propDescriptor.getReadMethod();
                Method writeMethod = propDescriptor.getWriteMethod();
                // Get the String[] via the read method:
                String[] entries = (String[])readMethod.invoke(interceptor, (Object[])null);
                // Make a new entry array of entries.length+1 because we're
                // adding one more entry to the property
                String[] newEntries = new String[entries.length - 1];
                int offset = 0;
                for (int i = 0; i < newEntries.length; i++) {
                    // Skip the index of the item we want to delete
                    if (i == deleteIndex) {
                        offset++;
                    }
                    newEntries[i] = entries[i + offset];
                }
                // Use the write method to save the new entries:
                writeMethod.invoke(interceptor, new Object[]{newEntries});
                // Save interceptors
                interceptorManager.saveInterceptors();
                // Done, so redirect
                StringBuffer url = new StringBuffer();
                url.append("interceptors.jsp?edit=true&pos=").append(interceptorIndex)
                        .append("&managerType=").append(managerType);
                if (!isGlobal) {
                    url.append("&wgID=" + workgroupID);
                }
                response.sendRedirect(url.toString());
                return;
            }
        }
    }

    // Save interceptor properties
    if (saveProperties) {
        if (interceptorIndex >= 0) {
            // The interceptor we're working with
            PacketInterceptor interceptor = (isGlobal ? interceptorManager
                    .getInterceptor(interceptorIndex) : interceptorManager
                    .getInterceptor(workgroup.getJID().toBareJID(), interceptorIndex));
            // A map of name/value pairs. The names are the names of the bean
            // properties and the values come as parameters to this page
            Map properties = getInterceptorPropertyValues(request, interceptor);
            // Set the properties
            BeanUtils.setProperties(interceptor, properties);
            // Save the interceptors
            interceptorManager.saveInterceptors();

            // Add the new Queue
            //String queueID = request.getParameter("queues");
            //try {
            //long qID = Long.valueOf(queueID).longValue();
            //requestRouter.setRoutingQueue(qID);
            //}
            //catch (NumberFormatException e) {
            //e.printStackTrace();
            //}
            //routingUtils.saveWorkgroupRouters(workgroup);

            // Done, so redirect to this page
            String redirect = "interceptors.jsp?managerType=" + managerType;
            if (!isGlobal) {
                redirect += "&wgID=" + workgroupID;
            }
            response.sendRedirect(redirect);
            return;
        }
    }

    // Add a new interceptor to the list of installable interceptors
    if (addInterceptor) {
        try {
            if (newClassname != null) {
                // Load the specified class, make sure it's an insance of the interceptor class:
                Class c = ClassUtils.forName(newClassname.trim());
                Object obj = c.newInstance();
                if (obj instanceof PacketInterceptor) {
                    interceptorManager.addInterceptorClass(c);
                }
                else {
                    error = newClassname.trim() + " is not a Packet Interceptor";
                }
            }
            else {
                error = "You must specify a Packet Interceptorr class to load.";
            }
        }
        catch (ClassNotFoundException cnfe) {
            error = newClassname.trim() + " is not a valid classname";
        }

        catch (InstantiationException ie) {
            error = newClassname.trim() + " must have a valid constructor";
        }
        catch (Exception e) {
            Log.error(e);
            error = "Could not load class " + newClassname.trim();
        }
        String redirect = "interceptors.jsp?errorMessage=" + error + "&managerType=" + managerType;
        if (!isGlobal) {
            redirect += "&wgID=" + workgroupID;
        }
        response.sendRedirect(redirect);
        return;
    }

    // Change the position of an interceptor
    if (changePosition) {
        if (interceptorIndex >= 0) {
            // Get the interceptor at the specified interceptor position
            if (isGlobal) {
                PacketInterceptor interceptor = interceptorManager.getInterceptor(interceptorIndex);
                // Re-add it based on the "direction" we're doing. First, remove it:
                interceptorManager.removeInterceptor(interceptor);
                if (up) {
                    interceptorManager.addInterceptor(interceptorIndex - 1, interceptor);
                }
                if (down) {
                    interceptorManager.addInterceptor(interceptorIndex + 1, interceptor);
                }
            }
            else {
                PacketInterceptor interceptor = interceptorManager
                        .getInterceptor(workgroup.getJID().toBareJID(), interceptorIndex);
                // Re-add it based on the "direction" we're doing. First, remove it:
                interceptorManager.removeInterceptor(workgroup.getJID().toBareJID(), interceptor);
                if (up) {
                    interceptorManager.addInterceptor(workgroup.getJID().toBareJID(),
                            interceptorIndex - 1, interceptor);
                }
                if (down) {
                    interceptorManager.addInterceptor(workgroup.getJID().toBareJID(),
                            interceptorIndex + 1, interceptor);
                }
            }
            // done, so redirect
            String redirect = "interceptors.jsp?managerType=" + managerType;
            if (!isGlobal) {
                redirect += "&wgID=" + workgroupID;
            }
            response.sendRedirect(redirect);
            return;
        }
    }

    // Number of installed interceptors
    List<PacketInterceptor> activeInterceptors = isGlobal ? interceptorManager.getInterceptors()
            : interceptorManager.getInterceptors(workgroup.getJID().toBareJID());
    int interceptorCount = activeInterceptors.size();
    // All interceptor classes

    if (install && classname != null) {
        try {
            Class interceptorClass = ClassUtils.forName(classname);
            PacketInterceptor newInterceptor = (PacketInterceptor)interceptorClass.newInstance();
            if (isGlobal) {
                interceptorManager.addInterceptor(0, newInterceptor);
            }
            else {
                interceptorManager
                        .addInterceptor(workgroup.getJID().toBareJID(), 0, newInterceptor);
            }
            String redirect = "interceptors.jsp?managerType=" + managerType;
            if (!isGlobal) {
                redirect += "&wgID=" + workgroupID;
            }
            response.sendRedirect(redirect);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (remove && position > -1) {
        if (isGlobal) {
            PacketInterceptor interceptor = interceptorManager.getInterceptor(position);
            interceptorManager.removeInterceptor(interceptor);
        }
        else {
            PacketInterceptor interceptor = interceptorManager
                    .getInterceptor(workgroup.getJID().toBareJID(), position);
            interceptorManager.removeInterceptor(workgroup.getJID().toBareJID(), interceptor);
        }
        String redirect = "interceptors.jsp?managerType=" + managerType;
        if (!isGlobal) {
            redirect += "&wgID=" + workgroupID;
        }
        response.sendRedirect(redirect);
        return;
    }


      out.write("\n\n<html>\n    <head>\n");

    if (isGlobal) { 
      out.write("\n        <title>Global Packet Interceptors</title>\n        <meta name=\"pageID\" content=\"settings-interceptors\"/>\n    ");
  } else { 
      out.write("\n        <title>");
      out.print( "Packet Interceptors for " + workgroupID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-interceptors\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+workgroupID );
      out.write("\"/>\n\n    ");
 } 
      out.write("\n        <!--<meta name=\"helpPage\" content=\"edit_or_uninstall_global_interceptors.html\"/>-->\n    </head>\n    <body>\n\n<span>\n\n<p>Interceptors examine packets before they enter the system and can modify or reject them. Use\nthe forms below to install and customize ");
      out.print( isGlobal ? "global" : "local");
      out.write(" interceptors.\n");
 if ("workgroup".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked every time a packet is sent to ");
      out.print( isGlobal ? "a" : "the");
      out.write("\nworkgroup or ");
      out.print( isGlobal ? "a" : "the");
      out.write(" workgroup is sending a packet to a user or an agent.\n    ");
 }
    else if ("agent".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked every time an agent sends a presence to\n");
      out.print( isGlobal ? "a" : "the");
      out.write(" workgroup.\n    ");
 }
    else if ("chatbot".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked every time the chatbot of ");
      out.print( isGlobal ? "a" : "the");
      out.write("\nworkgroup receives or sends a packet.\n    ");
 }
    else if ("queue".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked every time a user tries to join a queue of\n");
      out.print( isGlobal ? "a" : "the");
      out.write(" workgroup.\n    ");
 }
    else if ("room".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked when sending packets for creating a room, configuring a room\nor sending room invitations to an agent or a user.\n    ");
 }
    else if ("offer".equals(managerType)) { 
      out.write("\nCurrent interceptors will be invoked when sending an offer to an agent or when an agent accepts\nor rejects an offer of ");
      out.print( isGlobal ? "a" : "the");
      out.write(" workgroup.\n    ");
 } 
      out.write("\n</p>\n\n</span>\n\n<p>\n\n<script language=\"JavaScript\" type=\"text/javascript\">\nvar routerInfo = new Array(\n");
	int i = 0;
    for(PacketInterceptor interceptor : interceptorManager.getAvailableInterceptors()){

        try {
            BeanDescriptor descriptor = (Introspector.getBeanInfo(interceptor.getClass())).getBeanDescriptor();

      out.write("\n    new Array(\n        \"");
      out.print( descriptor.getBeanClass().getName() );
      out.write("\",\n        \"");
      out.print( descriptor.getValue("version") );
      out.write("\",\n        \"");
      out.print( descriptor.getValue("author") );
      out.write("\",\n        \"");
      out.print( StringUtils.replace(descriptor.getShortDescription(), "\"", "\\\"") );
      out.write("\"\n    )\n");
          if ((interceptorManager.getAvailableInterceptors().size() - i) > 1) { 
      out.write("\n\t\t,\n");
	        }
        } catch (Exception e) {}
         i++;
    }

      out.write("\n);\nfunction properties(theForm) {\n    var className = theForm.interceptors.options[theForm.interceptors.selectedIndex].value;\n    var selected = 0;\n    for (selected=0; selected<routerInfo.length; selected++) {\n        if (routerInfo[selected][0] == className) {\n            var version = routerInfo[selected][1];\n            var author = routerInfo[selected][2];\n            var description = routerInfo[selected][3];\n            theForm.version.value = ((version==\"null\")?\"\":version);\n            theForm.author.value = ((author==\"null\")?\"\":author);\n            theForm.description.value = ((description==\"null\")?\"\":description);\n            break;\n        }\n    }\n}\n</script>\n\n<form action=\"interceptors.jsp\">\n");
 if (!isGlobal) { 
      out.write("\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( workgroupID );
      out.write('"');
      out.write('>');
      out.write('\n');
 } 
      out.write("\nConfigure interceptors of the realm:\n<select name=\"managerType\" onchange=\"this.form.submit();\">\n        <option value=\"workgroup\" ");
 if ("workgroup".equals(managerType)) out.write("selected");
      out.write(">Workgroup</option>\n        <option value=\"queue\" ");
 if ("queue".equals(managerType)) out.write("selected");
      out.write(">Queue</option>\n        <option value=\"offer\" ");
 if ("offer".equals(managerType)) out.write("selected");
      out.write(">Offer</option>\n        <option value=\"room\" ");
 if ("room".equals(managerType)) out.write("selected");
      out.write(">Room</option>\n        <option value=\"chatbot\" ");
 if ("chatbot".equals(managerType)) out.write("selected");
      out.write(">Chatbot</option>\n        <option value=\"agent\" ");
 if ("agent".equals(managerType)) out.write("selected");
      out.write(">Agent</option>\n    </select>\n</form>\n\n");
  // Print out a message if one exists
    String oneTimeMessage = errorMessage;
    if (oneTimeMessage != null) {

      out.write("\n    <font size=\"-1\" color=\"#ff0000\">\n    <p><i>");
      out.print( oneTimeMessage );
      out.write("</i></p>\n\n");
  }

      out.write('\n');
      out.write('\n');
  // Colors
    String red = "#ffeeee";
    String yellow = "#ffffee";
    i=0;

    if (interceptorCount > 0) {

      out.write("\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr><td>\n    <b>Current Interceptors</b>\n    </td>\n    <td>\n    <a href=\"#\" onclick=\"helpwin('interceptors','current_interceptors');return false;\"\n     title=\"Click for help\"\n     ><img src=\"images/help-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" hspace=\"8\" alt=\"\" /></a>\n    </td>\n</tr>\n</table><br>\n<ul>\n\t<table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n    <tr><td>\n    <table cellpadding=\"4\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n\t<tr bgcolor=\"#eeeeee\">\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>ORDER</b></font></td>\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>NAME</b></font></td>\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>DESCRIPTION</b></font></td>\n    ");
  if (interceptorCount > 1) { 
      out.write("\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>MOVE</b></font></td>\n    ");
  } 
      out.write("\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>EDIT</b></font></td>\n\t<td align=\"center\"><font size=\"-2\" face=\"verdana\"><b>DELETE</b></font></td>\n    </tr>\n");
  // Loop through all interceptors
    for (PacketInterceptor interceptor : activeInterceptors) {
        try {
            // Descriptor for this interceptor
            BeanDescriptor descriptor = (Introspector.getBeanInfo(interceptor.getClass())).getBeanDescriptor();
            // Properties for this interceptor
            PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(interceptor.getClass());
            // Version of this interceptor
            String version = (String)descriptor.getValue("version");
            // Description of this interceptor
            String description = StringUtils.escapeHTMLTags(descriptor.getShortDescription());

      out.write("\n    <tr bgcolor=\"#ffffff\">\n        <td>");
      out.print( (i+1) );
      out.write("</td>\n        <td nowrap alt=\"dd\">");
      out.print( descriptor.getDisplayName() );
      out.write("</td>\n        <td>");
      out.print( (description!=null)?description:"&nbsp;" );
      out.write("</td>\n        ");
  if (interceptorCount > 1) { 
      out.write("\n        <td nowrap>\n            ");
  if ((i+1)<interceptorCount) { 
      out.write("\n                <a href=\"interceptors.jsp?changePos=true&down=true&interceptorIndex=");
      out.print( i );
      out.write("&managerType=");
      out.print(managerType);
      out.print( isGlobal ? "" : "&wgID="+workgroupID );
      out.write("\"\n                ><img src=\"images/arrow_down.gif\" width=\"16\" height=\"16\" alt=\"Move this interceptor down.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n                <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\" />\n            ");
  } 
      out.write("\n\n            ");
  if (i != 0) { 
      out.write("\n                <a href=\"interceptors.jsp?changePos=true&up=true&interceptorIndex=");
      out.print( i );
      out.write("&managerType=");
      out.print(managerType);
      out.print( isGlobal ? "" : "&wgID="+workgroupID );
      out.write("\"\n                ><img src=\"images/arrow_up.gif\" width=\"16\" height=\"16\" alt=\"Move this interceptor up.\" border=\"0\"></a>\n            ");
  } else { 
      out.write("\n                <img src=\"images/blank.gif\" width=\"16\" height=\"16\" border=\"0\" alt=\"\" />\n            ");
  } 
      out.write("\n        </td>\n        ");
  } 
      out.write("\n        <td align=\"center\">\n            <a href=\"interceptors.jsp?edit=true&pos=");
      out.print( i );
      out.write("&managerType=");
      out.print(managerType);
      out.print( isGlobal ? "" : "&wgID="+workgroupID );
      out.write("\"\n            ><img src=\"images/edit-16x16.gif\" width=\"16\" height=\"16\" alt=\"Edit the properties of this interceptor\" border=\"0\"\n            ></a>\n        </td>\n        <td align=\"center\">\n            <a href=\"interceptors.jsp?remove=true&pos=");
      out.print( i );
      out.write("&managerType=");
      out.print(managerType);
      out.print( isGlobal ? "" : "&wgID="+workgroupID );
      out.write("\"\n            ><img src=\"images/delete-16x16.gif\" width=\"16\" height=\"16\" alt=\"Delete this interceptor\" border=\"0\"\n            ></a>\n        </td>\n    </tr>\n");
  if (position == i && edit) { 
      out.write("\n    <form action=\"interceptors.jsp\" method=\"post\">\n    ");
 if (!isGlobal) { 
      out.write("\n        <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( workgroupID );
      out.write("\">\n    ");
 } 
      out.write("\n    <input type=\"hidden\" name=\"managerType\" value=\"");
      out.print( managerType );
      out.write("\">\n    <input type=\"hidden\" name=\"saveProperties\" value=\"true\">\n    <input type=\"hidden\" name=\"interceptorIndex\" value=\"");
      out.print( i );
      out.write("\">\n    <tr bgcolor=\"#ffffff\">\n        <td>&nbsp;</td>\n        <td colspan=\"");
      out.print( (interceptorCount > 1)?"5":"4" );
      out.write("\">\n            <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n            ");
  int color = 1;
                for (int j=0; j<descriptors.length; j++) {
                    color ++;
                    boolean isString = "java.lang.String".equals(descriptors[j].getPropertyType().getName());
                    if (isString) {
            
      out.write("\n                    <tr bgcolor=");
      out.print( (color%2==0)?"#f4f5f7":"#ffffff" );
      out.write(">\n                        <td colspan=\"3\">\n                            ");
      out.print( descriptors[j].getDisplayName() );
      out.write("\n                            <br>\n                            <font size=\"-2\">");
      out.print( descriptors[j].getShortDescription() );
      out.write("</font>\n                        </td>\n                    </tr>\n                    <tr bgcolor=");
      out.print( (color%2==0)?"#f4f5f7":"#ffffff" );
      out.write(">\n                        <td colspan=\"3\">\n                            ");
      out.print( getHTML(interceptor, descriptors[j]) );
      out.write("\n                        </td>\n                    </tr>\n                ");
  } else { 
      out.write("\n                    <tr bgcolor=");
      out.print( (color%2==0)?"#f4f5f7":"#ffffff" );
      out.write(">\n                        <td width=\"70%\">\n                            ");
      out.print( descriptors[j].getDisplayName() );
      out.write("\n                            <br>\n                            <font size=\"-2\">");
      out.print( descriptors[j].getShortDescription() );
      out.write("</font>\n                        </td>\n                        <td width=\"10%\">&nbsp;</td>\n                        <td width=\"10%\" nowrap>\n                            ");
      out.print( getHTML(interceptor, descriptors[j]) );
      out.write("\n                        </td>\n                    </tr>\n            ");
      }
                }
            
      out.write("\n            <tr>\n                <td colspan=\"4\" align=\"right\">\n\n                    <input type=\"submit\" value=\"Save Properties\">\n\n                </td>\n            </tr>\n            </table>\n        </td>\n    </tr>\n    </form>\n");
  } 
      out.write('\n');
      } catch (Exception e) { }
         i++;
    }

      out.write("\n    </table>\n    </td></tr>\n    </table>\n    <br>\n\n");
  } 
      out.write("\n</ul>\n\n<p>\n\n<form action=\"interceptors.jsp\" method=\"post\">\n");
 if (!isGlobal) { 
      out.write("\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( workgroupID );
      out.write('"');
      out.write('>');
      out.write('\n');
 } 
      out.write("\n<input type=\"hidden\" name=\"managerType\" value=\"");
      out.print( managerType );
      out.write("\">\n<input type=\"hidden\" name=\"install\" value=\"true\">\n\n<span class=\"jive-install-interceptor\">\n\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr><td>\n    <b>Install Interceptor</b>\n    </td>\n    <td>\n    <a href=\"#\" onclick=\"helpwin('interceptors','install_interceptor');return false;\"\n     title=\"Click for help\"\n     ><img src=\"images/help-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" hspace=\"8\" alt=\"\" /></a>\n    </td>\n</tr>\n</table><br>\n\n<ul>\n\t<table bgcolor=\"#aaaaaa\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"1%\">\n    <tr><td>\n        <table cellpadding=\"4\" cellspacing=\"1\" border=\"0\" width=\"100%\">\n        <tr bgcolor=\"#eeeeee\">\n            <td align=\"center\">\n                <font size=\"-2\" face=\"verdana\"><b>AVAILABLE INTERCEPTORS</b></font>\n            </td>\n        </tr>\n        <tr bgcolor=\"#ffffff\">\n            <td>\n                <table cellpadding=\"1\" cellspacing=\"0\" border=\"0\">\n                <tr>\n                    <td width=\"48%\" valign=\"top\">\n                        <select size=\"8\" name=\"interceptors\" onchange=\"properties(this.form);\">\n");
      out.write("                        ");
  for(PacketInterceptor interceptor : interceptorManager.getAvailableInterceptors()) {
                            boolean isInstalled = isInstalledInterceptor(workgroup, interceptorManager, interceptor);
                            BeanDescriptor descriptor = (Introspector.getBeanInfo(interceptor.getClass())).getBeanDescriptor();
                        
      out.write("\n                            <option value=\"");
      out.print( descriptor.getBeanClass().getName() );
      out.write("\"\n                             >");
      out.print( descriptor.getDisplayName() );
      out.write("\n\n                            ");
  if (isInstalled) { 
      out.write("\n\n                                *\n\n                            ");
  } 
      out.write("\n\n                        ");
  } 
      out.write("\n                        </select>\n                        <br>\n                        (A * denotes the interceptor is already installed. You can install the\n                        same interceptor more than once.)\n                    </td>\n                    <td width=\"2%\"><img src=\"images/blank.gif\" width=\"5\" height=\"1\" border=\"0\" alt=\"\" /></td>\n                    <td width=\"48%\" valign=\"top\">\n\n                        <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n                        <tr>\n                            <td><font size=\"-2\">VERSION</font></td>\n                            <td><input type=\"text\" size=\"20\" name=\"version\" style=\"width:100%\"></td>\n                        </tr>\n                        <tr>\n                            <td><font size=\"-2\">AUTHOR</font></td>\n                            <td><input type=\"text\" size=\"20\" name=\"author\" style=\"width:100%\"></td>\n                        </tr>\n                        <tr>\n                            <td valign=\"top\"><font size=\"-2\">DESCRIPTION</font></td>\n");
      out.write("                            <td><textarea name=\"description\" cols=\"20\" rows=\"5\" wrap=\"virtual\"></textarea></td>\n                        </tr>\n                        </table>\n\n                    </td>\n                </tr>\n                <tr>\n                    <td colspan=\"3\" align=\"center\">\n\n                        <input type=\"submit\" value=\"Install\">\n\n                    </td>\n                </tr>\n                </table>\n            </td>\n        </tr>\n        </table>\n    </td></tr>\n    </table>\n</ul>\n\n</span>\n\n</form>\n\n<form action=\"interceptors.jsp\">\n<input type=\"hidden\" name=\"addInterceptor\" value=\"true\">\n");
 if (!isGlobal) { 
      out.write("\n    <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( workgroupID );
      out.write('"');
      out.write('>');
      out.write('\n');
 } 
      out.write("\n<input type=\"hidden\" name=\"managerType\" value=\"");
      out.print( managerType );
      out.write("\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n<tr><td>\n    <b>Add Interceptor Class</b>\n    </td>\n    <td>\n    <a href=\"#\" onclick=\"helpwin('interceptors','add_interceptor_class');return false;\"\n     title=\"Click for help\"\n     ><img src=\"images/help-16x16.gif\" width=\"16\" height=\"16\" border=\"0\" hspace=\"8\" alt=\"\" /></a>\n    </td>\n</tr>\n</table><br>\n<ul>\n    <table cellpadding=\"2\" cellspacing=\"0\" border=\"0\">\n    <tr>\n    \t<td>Class Name:</td>\n    \t<td><input type=\"text\" name=\"newClassname\" value=\"\" size=\"30\" maxlength=\"100\"></td>\n    \t<td><input type=\"submit\" value=\"Add Interceptor\"></td>\n    </tr>\n    </table>\n</ul>\n</form>\n\n<p>\n\n\n</body>\n</html>\n");
 } catch(Exception ex){ex.printStackTrace(); } 
      out.write('\n');
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
