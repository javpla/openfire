package org.jivesoftware.openfire.plugin.hazelcast;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.jivesoftware.util.cluster.NodeRuntimeStats;
import org.jivesoftware.util.cache.CacheFactory;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Cluster;
import com.hazelcast.core.Member;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import org.jivesoftware.openfire.cluster.ClusterManager;
import org.jivesoftware.openfire.cluster.NodeID;
import org.jivesoftware.openfire.cluster.ClusterNodeInfo;
import org.jivesoftware.util.*;
import org.jivesoftware.util.cache.Cache;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.LinkedList;

public final class system_002dclustering_002dnode_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n\n\n\n\n\n\n\n");
      org.jivesoftware.util.WebManager webManager = null;
      synchronized (_jspx_page_context) {
        webManager = (org.jivesoftware.util.WebManager) _jspx_page_context.getAttribute("webManager", PageContext.PAGE_SCOPE);
        if (webManager == null){
          webManager = new org.jivesoftware.util.WebManager();
          _jspx_page_context.setAttribute("webManager", webManager, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
 webManager.init(request, response, session, application, out ); 
      out.write("\n\n\n\n\n\n<html>\n<head>\n    <title>Cluster Node Information</title>\n    <meta name=\"pageID\" content=\"system-clustering\"/>\n    <meta http-equiv=\"refresh\" content=\"10\" >\n    <style type=\"text/css\">\n    .warning {\n        color : #f00;\n        font-weight : bold;\n    }\n    .jive-stats .jive-table THEAD TH, .jive-stats .jive-table TBODY TD {\n        border-right : 1px #ccc solid;\n        text-align : center;\n    }\n    .jive-stats .jive-table .c6c7c8, .jive-stats .jive-table .c8, .jive-stats .jive-table TBODY .c8 {\n        border-right : 0px;\n    }\n    .jive-stats .jive-table TBODY TD TABLE TD {\n        border : 0px;\n    }\n\n    .jive-info .c1 {\n        width : 30%;\n    }\n    .jive-info .c2 {\n        width : 25%;\n    }\n    .jive-info .c3 {\n        width : 15%;\n        text-align : center;\n    }\n    .jive-info .c4 {\n        width : 30%;\n    }\n    </style>\n</head>\n\n<body>\n\n");
 // Is clustering enabled? If not, redirect back to the cache page
    if (!ClusterManager.isClusteringStarted()) {
        response.sendRedirect("../../system-clustering.jsp");
        return;
    }

    // get parameters
    boolean clear = request.getParameter("clear") != null;
    String uid = ParamUtils.getParameter(request, "UID");

    // Clear the cache stats if requested
    if (clear) {
        NodeRuntimeStats.clearCacheStats();
        response.sendRedirect("system-clustering-node.jsp?UID=" + uid);
        return;
    }

	List<ClusterNodeInfo> members = (List<ClusterNodeInfo>) CacheFactory.getClusterNodesInfo();
    Map<NodeID, NodeRuntimeStats.NodeInfo> nodeInfoMap = NodeRuntimeStats.getNodeInfo();

    // Sort it according to name
    Collections.sort(members, new Comparator<ClusterNodeInfo>() {
        public int compare(ClusterNodeInfo member1, ClusterNodeInfo member2) {
            String name1 = member1.getHostName() + " (" + member1.getNodeID() + ")";
            String name2 = member2.getHostName() + " (" + member2.getNodeID() + ")";
            return name1.toLowerCase().compareTo(name2.toLowerCase().toLowerCase());
        }
    });

    // If no UID was used, use the UID from the first member in the member list
    byte[] byteArray;
    if (uid == null) {
        byteArray = members.get(0).getNodeID().toByteArray();
    } else {
        byteArray = Base64.decode(uid, Base64.URL_SAFE);
    }

    // Get the specific member requested
    ClusterNodeInfo member = null;
    for (int i = 0; i < members.size(); i++) {
        ClusterNodeInfo m = members.get(i);
        if (Arrays.equals(byteArray, m.getNodeID().toByteArray())) {
            member = m;
            break;
        }
    }

    if (member == null) {
        Log.warn("Node not found: " + uid);
        for (int i = 0; i < members.size(); i++) {
            ClusterNodeInfo m = members.get(i);
            Log.warn("Available members: " + m.getNodeID().toString());
        }

        response.sendRedirect("../../system-clustering.jsp");
        return;
    }

    // Get the cache stats object:
    Map cacheStats = Hazelcast.getHazelcastInstanceByName("openfire").getMap("opt-$cacheStats");

    // Decimal formatter for numbers
    DecimalFormat decFormat = new DecimalFormat("#,##0.0");
    NumberFormat numFormat = NumberFormat.getInstance();
    DecimalFormat mbFormat = new DecimalFormat("#0.00");
    DecimalFormat percentFormat = new DecimalFormat("#0.0");

    // Get the list of existing caches
    Cache[] caches = webManager.getCaches();
    String[] cacheNames = new String[caches.length];
    for (int i = 0; i < caches.length; i++) {
        cacheNames[i] = caches[i].getName();
    }

      out.write("\n\n<p>\nBelow you will find statistic information for the selected node. This page will be automatically\nrefreshed every 10 seconds.\n</p>\n\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<tr>\n    <td width=\"99%\">\n        &nbsp;\n    </td>\n    <td width=\"1%\" nowrap=\"nowrap\">\n        <a href=\"../../system-clustering.jsp\">&laquo; Back to cluster summary</a>\n    </td>\n</tr>\n</table>\n\n<br />\n\n<div class=\"jive-stats\">\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<thead>\n    <tr>\n        <th rowspan=\"2\" class=\"c1\">Node</th>\n        <th rowspan=\"2\" class=\"c2\">Memory Usage</th>\n        <th colspan=\"3\" class=\"c3c4c5\">Incoming Packets</th>\n        <th colspan=\"3\" class=\"c6c7c8\">Outgoing Packets</th>\n    </tr>\n    <tr>\n        <th class=\"c3\" colspan=\"2\">Packets Received</th>\n        <th class=\"c5\">Success</th>\n        <th class=\"c6\">CPU</th>\n        <th class=\"c7\">Throughput</th>\n        <th class=\"c8\">Success</th>\n    </tr>\n</thead>\n\n<tbody>\n\n");
  for (int i=0; i<members.size(); i++) {
        ClusterNodeInfo m = members.get(i);
        if (member != m) {
            continue;
        }
        NodeRuntimeStats.NodeInfo nodeInfo = nodeInfoMap.get(m.getNodeID());

      out.write("\n    <tr bgcolor=\"#ffffcc\">\n\n        <td nowrap class=\"c1\">\n            ");
      out.print( m.getHostName() );
      out.write("<br/>");
      out.print( m.getNodeID() );
      out.write("\n        </td>\n\n        <td class=\"c2\" valign=\"middle\">\n            ");
  double freeMem = (double)nodeInfo.getFreeMem()/(1024.0*1024.0);
                double maxMem = (double)nodeInfo.getMaxMem()/(1024.0*1024.0);
                double totalMem = (double)nodeInfo.getTotalMem()/(1024.0*1024.0);
                double usedMem = totalMem - freeMem;
                double percentFree = ((maxMem - usedMem)/maxMem)*100.0;
                double percentUsed = 100.0 - percentFree;
                int percent = 100-(int)Math.round(percentFree);
            
      out.write("\n\n            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"250\">\n            <tr>\n                <td width=\"99%\">\n                    <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" style=\"border:1px #666 solid;\">\n                    <tr>\n                        ");
  if (percent == 0) { 
      out.write("\n\n                            <td width=\"100%\" style=\"padding:0px;\"><img src=\"../../images/percent-bar-left.gif\" width=\"100%\" height=\"4\" border=\"0\" alt=\"\"></td>\n\n                        ");
  } else { 
      out.write("\n\n                            ");
  if (percent >= 90) { 
      out.write("\n\n                                <td width=\"");
      out.print( percent );
      out.write("%\" background=\"../../images/percent-bar-used-high.gif\" style=\"padding:0px;\"\n                                    ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\n\n                            ");
  } else { 
      out.write("\n\n                                <td width=\"");
      out.print( percent );
      out.write("%\" background=\"../../images/percent-bar-used-low.gif\" style=\"padding:0px;\"\n                                    ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\n\n                            ");
  } 
      out.write("\n\n                            <td width=\"");
      out.print( (100-percent) );
      out.write("%\" background=\"../../images/percent-bar-left.gif\" style=\"padding:0px;\"\n                                ><img src=\"images/blank.gif\" width=\"1\" height=\"4\" border=\"0\" alt=\"\"></td>\n\n                        ");
  } 
      out.write("\n                    </tr>\n                    </table>\n                </td>\n                <td width=\"1%\" nowrap=\"nowrap\">\n                    ");
      out.print( mbFormat.format(totalMem) );
      out.write(" MB, ");
      out.print( decFormat.format(percentUsed) );
      out.write("% used\n                </td>\n           </tr>\n           </table>\n\n        </td>\n        <td class=\"c3\" colspan=\"2\">\n            \n        </td>\n\n        <td class=\"c5\">\n            \n        </td>\n        <td class=\"c6\">\n           \n        </td>\n        <td class=\"c7\">\n           \n        </td>\n        <td class=\"c8\">\n            \n        </td>\n    </tr>\n\n");
  } 
      out.write("\n\n</tbody>\n\n</table>\n</div>\n</div>\n\n\n<br/>\n\n[<a href=\"system-clustering-node.jsp?clear=true&UID=");
      out.print(uid);
      out.write("\">Clear Cache Stats</a>]\n\n<br /><br />\n\n<table cellpadding=\"3\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<tr>\n    <td width=\"1%\"><img src=\"images/server-network-48x48.gif\" width=\"48\" height=\"48\" border=\"0\" alt=\"\" hspace=\"10\"></td>\n    <td width=\"99%\">\n        <span style=\"font-size:1.1em;\"><b>Node Details: ");
      out.print( member.getHostName() );
      out.write(' ');
      out.write('(');
      out.print( member.getNodeID() );
      out.write(")</b></span>\n        <br />\n        <span style=\"font-size:0.9em;\">\n        Joined: ");
      out.print( JiveGlobals.formatDateTime(new Date(member.getJoinedTime())) );
      out.write("\n        </span>\n    </td>\n</tr>\n</table>\n\n<p>\nCache statistics for this cluster node appear below.\n</p>\n\n<div class=\"jive-info\">\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<thead>\n    <tr>\n        <th class=\"c1\">Cache Type</th>\n        <th class=\"c2\">Size</th>\n        <th class=\"c3\">Objects</th>\n        <th class=\"c4\">Effectiveness</th>\n    </tr>\n</thead>\n\n<tbody>\n\n");
 Map cNames = (Map) cacheStats.get(member.getNodeID().toString());
    if (cNames == null) {

      out.write("\n    <tr>\n    <td align=\"center\" colspan=\"4\"><i>No stats available</i></td>\n    </tr>\n\n");
 } else {
    // Iterate through the cache names,
    for (String cacheName : cacheNames) {
        long[] theStats = (long[]) cNames.get(cacheName);
        // Skip caches that are in this JVM but not in other nodes
        if (theStats == null) {
            continue;
        }
        long size = theStats[0];
        long maxSize = theStats[1];
        long numObjects = theStats[2];

        double memUsed = (double) size / (1024 * 1024);
        double totalMem = (double) maxSize / (1024 * 1024);
        double freeMem = 100 - 100 * memUsed / Math.max(1, totalMem);
        double usedMem = 100 * memUsed / Math.max(1, totalMem);
        long hits = theStats[3];
        long misses = theStats[4];
        double hitPercent = 0.0;
        if (hits + misses == 0) {
            hitPercent = 0.0;
        } else {
            hitPercent = 100 * (double) hits / (hits + misses);
        }
        boolean lowEffec = (hits > 500 && hitPercent < 85.0 && freeMem < 20.0);

      out.write("\n    <tr>\n        <td class=\"c1\">\n            ");
      out.print( cacheName );
      out.write("\n        </td>\n        <td class=\"c2\">\n\n            ");
 if (maxSize != -1 && maxSize != Integer.MAX_VALUE) { 
      out.write("\n            ");
      out.print( mbFormat.format(totalMem) );
      out.write(" MB,\n            ");
      out.print( percentFormat.format(usedMem));
      out.write("% used\n            ");
 } else { 
      out.write("\n            Unlimited\n            ");
 } 
      out.write("\n\n        </td>\n        <td class=\"c3\">\n\n            ");
      out.print( LocaleUtils.getLocalizedNumber(numObjects) );
      out.write("\n\n        </td>\n        <td class=\"c4\">\n\n            ");
 if (lowEffec) { 
      out.write("\n            <font color=\"#ff0000\"><b>");
      out.print( percentFormat.format(hitPercent));
      out.write("%</b>\n                ");
  } else { 
      out.write("\n                <b>");
      out.print( percentFormat.format(hitPercent));
      out.write("%</b>\n                ");
  } 
      out.write("\n                (");
      out.print( LocaleUtils.getLocalizedNumber(hits) );
      out.write("\n                hits, ");
      out.print( LocaleUtils.getLocalizedNumber(misses) );
      out.write(" misses)\n\n        </td>\n    </tr>\n    ");

        }
    }
    
      out.write("\n</tbody>\n\n</table>\n</div>\n</div>\n\n<br /><br />\n\n<div class=\"jive-table\">\n<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\">\n<thead>\n    <tr>\n        <th colspan=\"2\">\n            Openfire Cluster Details\n        </th>\n    </tr>\n</thead>\n<tbody>\n    <tr>\n        <td width=\"100%\">\n            Hazelcast Version ");
      out.print( NodeRuntimeStats.getProviderConfig("hazelcast.version") );
      out.write(" \n            Build ");
      out.print( NodeRuntimeStats.getProviderConfig("hazelcast.build") );
      out.write("\n        </td>\n    </tr>\n</tbody>\n</table>\n</div>\n\n<br/>\n\n</body>\n</html>");
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
