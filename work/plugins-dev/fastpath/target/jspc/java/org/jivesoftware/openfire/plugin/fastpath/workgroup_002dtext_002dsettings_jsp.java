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
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettingsManager;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSettings;
import org.jivesoftware.openfire.fastpath.settings.chat.ChatSetting;
import org.jivesoftware.openfire.fastpath.settings.chat.KeyEnum;

public final class workgroup_002dtext_002dsettings_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


   List<InternalModel> pageList = new ArrayList<InternalModel>();


    private void addToPage(String page, ChatSetting chatSetting){
        List list = null;
        for(InternalModel model : pageList){
            if(model.getPageName().equals(page)){
                list = model.getChatSettings();
                list.add(chatSetting);
                return;
            }
        }

        if(list == null){
            InternalModel model = new InternalModel();
            model.setPageName(page);
            model.addChatSetting(chatSetting);
            pageList.add(model);
            return;
        }
    }

    private class InternalModel {
        private String pageName;
        private List<ChatSetting> list = new ArrayList<ChatSetting>();

        public void setPageName(String pageName){
            this.pageName = pageName;
        }

        public String getPageName(){
            return pageName;
        }

        public void addChatSetting(ChatSetting chatSetting){
            list.add(chatSetting);
        }

        public List<ChatSetting> getChatSettings(){
            return list;
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
      out.write('\n');
      out.write('\n');

    pageList = new ArrayList<InternalModel>();

    // Get parameters
    String wgID = ParamUtils.getParameter(request, "wgID");
    WorkgroupAdminManager workgroupAdminManager = new WorkgroupAdminManager();
    workgroupAdminManager.init(pageContext);
    boolean updated = ParamUtils.getBooleanParameter(request, "updated", false);

    JID workgroupJID = new JID(wgID);
    String restore = request.getParameter("restore");

    final ChatSettingsManager chatSettingsManager = ChatSettingsManager.getInstance();
    Workgroup workgroup = WorkgroupManager.getInstance().getWorkgroup(workgroupJID);

    ChatSettings chatSettings = chatSettingsManager.getChatSettings(workgroup);

    // Notify the workgroup
    workgroup.imagesChanged();

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
        updated = true;
    }

    String keys = request.getParameter("_key");
    if (ModelUtil.hasLength(keys)) {
        ChatSetting setting = chatSettings.getChatSetting(keys);
        String defaultValue = setting.getDefaultValue();
        setting.setValue(defaultValue);
        chatSettingsManager.updateChatSetting(setting);
        updated = true;
    }

      out.write("\n<html>\n    <head>\n        <title>");
      out.print( "Web Chat Text Settings for "+wgID);
      out.write("</title>\n        <meta name=\"subPageID\" content=\"workgroup-text-settings\"/>\n        <meta name=\"extraParams\" content=\"wgID=");
      out.print( wgID );
      out.write("\"/>\n        <!--<meta name=\"helpPage\" content=\"edit_form_text.html\"/>-->\n\n        <link rel=\"stylesheet\" type=\"text/css\" href=\"/style/global.css\">\n        <script>\n        function restoreKey(name){\n            document.text._key.value = name;\n            document.text.submit();\n        }\n        </script>\n        <script language=\"javascript\">\n            function changeImage(image, img) {\n                img.src = image;\n            }\n        </script>\n    </head>\n    <body>\n\n      ");

          if(updated){
      
      out.write("\n       <div class=\"jive-success\">\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n                <tbody>\n                    <tr><td class=\"jive-icon\"><img src=\"images/success-16x16.gif\" width=\"16\" height=\"16\"\n                    border=\"0\"></td>\n                        <td class=\"jive-icon-label\">\n                           Web UI Text Settings have been updated successfully\n                        </td></tr>\n                </tbody>\n            </table>\n        </div><br/>\n      ");
 } 
      out.write("\n\n      <!-- Create HTML Code Snippet Table -->\n        <table width=\"100%\" class=\"jive-table\" cellpadding=\"3\" cellspacing=\"0\" border=\"0\">\n        <tr valign=\"top\">\n        <th colspan=\"3\">HTML Code Snippet</th>\n        </tr>\n        <tr valign=\"middle\">\n        <td><b>HTML Code</b><br/>\n        <span class=\"jive-description\">Copy this HTML Code wherever you would like to place a \"Chat with me\" button. This code will\n        display the correct presence information for this workgroup by either being online or offline. <i>(You must replace the url to the actual jivelive.jsp page)</i>\n    </span>\n        </td>\n        <td width=\"60%\" style=\"font-size:11px;\">\n       <font color=\"green\">&lt;!-- Insert this snippet where you would like the Chat button image to show up --></font><br/>\n        &lt;script language=\"JavaScript\" type=\"text/javascript\" src=\"url to jivelive.jsp\">&lt;/script><br/>\n        &lt;script>showChatButton('");
      out.print( wgID );
      out.write("');&lt;/script><br/>\n        <font color=\"green\">&lt;!-- End Of Spark Fastpath Snippet --></font>\n        </td>\n        </tr>\n        </table>\n        <br/><br/>\n");

    // User Input Page
    addToPage("User Input Page", chatSettings.getChatSetting(KeyEnum.user_input_page_title));
    addToPage("User Input Page", chatSettings.getChatSetting(KeyEnum.start_chat_button));

    // Queue
    addToPage("Queue", chatSettings.getChatSetting(KeyEnum.queue_title_text));
    addToPage("Queue", chatSettings.getChatSetting(KeyEnum.queue_description_text));
    addToPage("Queue", chatSettings.getChatSetting(
            org.jivesoftware.openfire.fastpath.settings.chat.KeyEnum.queue_footer_text));
    addToPage("Queue", chatSettings.getChatSetting(KeyEnum.no_agent_text));

    // Chat Room
    addToPage("Chat Room", chatSettings.getChatSetting(KeyEnum.accepted_chat_text));
    addToPage("Chat Room", chatSettings.getChatSetting(KeyEnum.transferred_chat_text));
    addToPage("Chat Room", chatSettings.getChatSetting(KeyEnum.agent_invite_text));
    addToPage("Chat Room", chatSettings.getChatSetting(KeyEnum.agent_ends_chat_text));

    // Transcript Page
    addToPage("Email Transcript Page", chatSettings.getChatSetting(KeyEnum.transcript_text));
    addToPage("Email Transcript Page", chatSettings.getChatSetting(KeyEnum.transcript_sent_text));
    addToPage("Email Transcript Page",
            chatSettings.getChatSetting(KeyEnum.transcript_not_sent_text));

    // Offline Page
    addToPage("No Help Page", chatSettings.getChatSetting(KeyEnum.no_help));

      out.write("\n\n        <!-- Text Settings -->\n        <form name=\"text\" action=\"workgroup-text-settings.jsp\" method=\"post\">\n        ");

            for(InternalModel model : pageList){
        
      out.write("\n          <table  class=\"jive-table\"  cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n               <tr>\n               <th colspan=\"2\">");
      out.print( model.getPageName());
      out.write("</th>\n               </tr>\n\n               ");

                  List list = model.getChatSettings();
                  Iterator iter = list.iterator();
                  while(iter.hasNext()){
                      ChatSetting chatSetting = (ChatSetting)iter.next();
               
      out.write("\n               <tr valign=\"top\">\n                    <td  style=\"border:0px;\" width=\"25%\"><b>");
      out.print( chatSetting.getLabel() );
      out.write("</b><br/>\n                    <span class=\"jive-description\">");
      out.print( chatSetting.getDescription() );
      out.write("</span>\n                    </td>\n                    <td rowspan=\"2\" align=\"left\"><textarea cols=\"60\" rows=\"6\" name=\"");
      out.print( chatSetting.getKey() );
      out.write('"');
      out.write('>');
      out.print( chatSetting.getValue() );
      out.write("</textarea></td>\n                    <input type=\"hidden\" name=\"key\" value=\"");
      out.print( chatSetting.getKey() );
      out.write("\" />\n               </tr>\n               <tr valign=\"bottom\">\n                 <td ><input type=\"submit\" name=\"restore\" value=\"Restore Defaults\" onClick=\"restoreKey('");
      out.print(chatSetting.getKey());
      out.write("');\"></td>\n                </tr>\n              ");
 } 
      out.write("\n          </table><br/>\n        ");
 } 
      out.write("\n\n\n\n\n              <table>\n               <tr><td colspan=\"4\">\n                <input type=\"hidden\" name=\"_key\" />\n                <input type=\"hidden\" name=\"wgID\" value=\"");
      out.print( wgID );
      out.write("\" />\n                <input type=\"submit\" name=\"saveText\" value=\"Update Text Settings\" />\n            </td>\n           </tr>\n        </table>\n        </form>\n    </body>\n</html>\n\n");
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
