package org.jivesoftware.openfire.plugin.fastpath;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import org.jivesoftware.util.*;
import java.util.*;
import org.jivesoftware.xmpp.workgroup.WorkgroupManager;
import org.jivesoftware.xmpp.workgroup.Workgroup;
import org.jivesoftware.xmpp.workgroup.WorkgroupAdminManager;
import org.xmpp.packet.JID;
import org.jivesoftware.xmpp.workgroup.utils.ModelUtil;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettings;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSetting;

public final class workgroup_002dchatbot_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write('\n');

    // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupAdminManager workgroupAdminManager = new WorkgroupAdminManager();
    workgroupAdminManager.init(pageContext);
    boolean updated = ParamUtils.getBooleanParameter(request, "updated", false);
    boolean enabled = ParamUtils.getBooleanParameter(request, "enabled", false);
    long timeout = ParamUtils.getIntParameter(request, "timeout", 30);

    JID workgroupJID = new JID(wgID);
    String restore = request.getParameter("restore");

    final ChatSettingsManager chatSettingsManager = ChatSettingsManager.getInstance();
    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);
    ChatSettings chatSettings = chatSettingsManager.getChatSettings(workgroup);

    String saveText = request.getParameter("saveText");
    if (ModelUtil.hasLength(saveText)) {
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = (String)en.nextElement();
            String value = request.getParameter(key);
            ChatSetting setting = chatSettings.getChatSetting(key);
            if (setting != null && ModelUtil.hasLength(value)) {
                setting.setValue(value);
                chatSettingsManager.updateChatSetting(setting);
            }
        }
    }

    String keys = request.getParameter("_key");
    if (ModelUtil.hasLength(keys)) {
        ChatSetting setting = chatSettings.getChatSetting(keys);
        String defaultValue = setting.getDefaultValue();
        setting.setValue(defaultValue);
        chatSettingsManager.updateChatSetting(setting);
    }

    String enabledText = request.getParameter("enabled");
    String timeoutText = request.getParameter("timeout");
    if (ModelUtil.hasLength(enabledText)) {
        workgroup.chatbotEnabled(enabled);
    }
    else if (ModelUtil.hasLength(timeoutText)) {
        if (workgroup.isChatbotEnabled()) {
            workgroup.getChatBot().setIdleTimeout(timeout * 60 * 1000);
        }
    }
    else {
        enabled = workgroup.isChatbotEnabled();
        if (enabled) {
            timeout = workgroup.getChatBot().getIdleTimeout() / (60 * 1000);
        }
    }

      out.write("\n<html>\n    <head>\n        <title>");
      out.print( "Chatbot Configuration for "+wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-chatbot\"/>\n        <meta name=\"extraParams\" content=\"");
      out.print( "wgID="+wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"configure_chatbot_settings.html\"/>-->\n\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n        <script>\n        function restoreKey(name){\n            document.text3._key.value = name;\n            document.text3.submit();\n        }\n        </script>\n        <script language=\"javascript\">\n            function changeImage(image, img) {\n                img.src = image;\n            }\n        </script>\n    </head>\n    <body>\n\n      ");

          if(updated){
      
      out.write("\n       <div class=\"jive-success\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\n                    border=\"0\"></td>\n                        <td class=\"jive-icon-label\">\n                           Web Chat images have been updated successfully\n                        </td></tr>\n                </tbody>\n            </table>\n        </div><br/>\n      ");
 } 
      out.write("\n\n      <!-- Create HTML Code Snippet Table -->\n    <p>Use the form below to configure the messages that the chatbot will send to users using standard XMPP clients.</p>\n\n        <form name=\"text\" action=\"workgroup-chatbot.jsp\" method=\"post\">\n        <fieldset>\n            <legend>Chatbot activation</legend>\n            <div>\n            <p>\n            Enable or disable the chatbot for this workgroup.\n            </p>\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n            <tbody>\n                <tr>\n                    <td width=\"1%\">\n                        <input type=\"radio\" name=\"enabled\" value=\"true\" id=\"rb01\" ");
      out.print( ((enabled) ? "checked" : "") );
      out.write(">\n                    </td>\n                    <td width=\"99%\">\n                        <label for=\"rb01\"><b>Enabled</b></label> - Users will be attended by the chatbot.\n                    </td>\n                </tr>\n                <tr>\n                    <td width=\"1%\">\n                        <input type=\"radio\" name=\"enabled\" value=\"false\" id=\"rb02\" ");
      out.print( ((!enabled) ? "checked" : "") );
      out.write(">\n                    </td>\n                    <td width=\"99%\">\n                        <label for=\"rb02\"><b>Disabled</b></label> - Messages sent to the workgroup will be ignored.\n                    </td>\n                </tr>\n            </tbody>\n            </table>\n            </div>\n        </fieldset>\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n          <br>\n          <input type=\"submit\" value=\"Save Settings\" />\n        </form>\n        <br>\n\n        <form name=\"text2\" action=\"workgroup-chatbot.jsp\" method=\"post\">\n        <fieldset>\n            <legend>Idle Session Settings</legend>\n            <div>\n            <p>\n            Sessions that haven't been used for a while will be removed.\n            </p>\n            <table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n            <tbody>\n                <tr>\n                    <td width=\"30%\" nowrap>\n                        Remove sessions after they have been idle for\n                    </td>\n                    <td width=\"70%\">\n                        <input type=\"text\" name=\"timeout\" size=\"15\" maxlength=\"50\" value=\"");
      out.print( timeout );
      out.write("\"> minutes\n                    </td>\n                </tr>\n            </tbody>\n            </table>\n            </div>\n        </fieldset>\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n          <br>\n          <input type=\"submit\" value=\"Save Settings\" />\n        </form>\n        <br>\n\n        <!-- Text Settings -->\n        <form name=\"text3\" action=\"workgroup-chatbot.jsp\" method=\"post\">\n        <fieldset>\n            <legend>Chatbot Text Settings</legend>\n            <div>\n            <table  class=\"jive-table\"  width=\"100%\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n            <tr>\n            <th>Event</th><th>Current Message</th><th colspan=\"2\">Default Message</th>\n            </tr>\n            ");

                Iterator iter = chatSettings.getChatSettingsByType(ChatSettings.SettingType.bot_settings).iterator();
                while(iter.hasNext()){
                    ChatSetting setting = (ChatSetting)iter.next();
            
      out.write("\n            <tr valign=\"top\">\n                 <td width=\"25%\">");
      out.print( setting.getLabel() );
      out.write("</td>\n                 <td><textarea cols=\"25\" rows=\"5\" name=\"");
      out.print( setting.getKey() );
      out.write('"');
      out.write('>');
      out.print( setting.getValue() );
      out.write("</textarea></td>\n                 <td>");
      out.print( setting.getDefaultValue() );
      out.write("</td>\n                 <td><input type=\"submit\" name=\"restore\" value=\"Restore Defaults\" onClick=\"restoreKey('");
      out.print(setting.getKey());
      out.write("');\"></td>\n                 <input type=\"hidden\" name=\"key\" value=\"");
      out.print( setting.getKey() );
      out.write("\" />\n            </tr>\n            ");
 } 
      out.write("\n            </table>\n            </div>\n        </fieldset>\n            <br>\n          <input type=\"hidden\" name=\"_key\" />\n          <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n          <input type=\"submit\" name=\"saveText\" value=\"Update Text Settings\" />\n        </form>\n\n    </body>\n</html>\n");

    session.setAttribute("workgroup", wgID);

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
