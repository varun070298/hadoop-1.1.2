package org.apache.hadoop.mapred;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import org.apache.hadoop.http.HtmlQuoting;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

public final class jobtracker_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

	private static final long serialVersionUID = 1L;


  private static DecimalFormat percentFormat = new DecimalFormat("##0.00");
  
  public void generateSummaryTable(JspWriter out, ClusterMetrics metrics,
                                   JobTracker tracker) throws IOException {
    String tasksPerNode = metrics.getTaskTrackerCount() > 0 ?
      percentFormat.format(((double)(metrics.getMapSlotCapacity() +
      metrics.getReduceSlotCapacity())) / metrics.getTaskTrackerCount()):
      "-";
    out.print("<table border=\"1\" cellpadding=\"5\" cellspacing=\"0\">\n"+
              "<tr><th>Running Map Tasks</th><th>Running Reduce Tasks</th>" + 
              "<th>Total Submissions</th>" +
              "<th>Nodes</th>" + 
              "<th>Occupied Map Slots</th><th>Occupied Reduce Slots</th>" + 
              "<th>Reserved Map Slots</th><th>Reserved Reduce Slots</th>" + 
              "<th>Map Task Capacity</th>" +
              "<th>Reduce Task Capacity</th><th>Avg. Tasks/Node</th>" + 
              "<th>Blacklisted Nodes</th>" +
              "<th>Graylisted Nodes</th>" +
              "<th>Excluded Nodes</th></tr>\n");
    out.print("<tr><td>" + metrics.getRunningMaps() + "</td><td>" +
              metrics.getRunningReduces() + "</td><td>" + 
              metrics.getTotalJobSubmissions() +
              "</td><td><a href=\"machines.jsp?type=active\">" +
              metrics.getTaskTrackerCount() + "</a></td><td>" + 
              metrics.getOccupiedMapSlots() + "</td><td>" +
              metrics.getOccupiedReduceSlots() + "</td><td>" + 
              metrics.getReservedMapSlots() + "</td><td>" +
              metrics.getReservedReduceSlots() + "</td><td>" + 
              metrics.getMapSlotCapacity() +
              "</td><td>" + metrics.getReduceSlotCapacity() +
              "</td><td>" + tasksPerNode +
              "</td><td><a href=\"machines.jsp?type=blacklisted\">" +
              metrics.getBlackListedTaskTrackerCount() + "</a>" +
              "</td><td><a href=\"machines.jsp?type=graylisted\">" +
              metrics.getGrayListedTaskTrackerCount() + "</a>" +
              "</td><td><a href=\"machines.jsp?type=excluded\">" +
              metrics.getDecommissionedTaskTrackerCount() + "</a>" +
              "</td></tr></table>\n");

    out.print("<br>");
    if (tracker.hasRestarted()) {
      out.print("<span class=\"small\"><i>");
      if (tracker.hasRecovered()) {
        out.print("The JobTracker got restarted and recovered back in " );
        out.print(StringUtils.formatTime(tracker.getRecoveryDuration()));
      } else {
        out.print("The JobTracker got restarted and is still recovering");
      }
      out.print("</i></span>");
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
      response.setContentType("text/html; charset=UTF-8");
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

  JobTracker tracker = (JobTracker) application.getAttribute("job.tracker");
  ClusterStatus status = tracker.getClusterStatus();
  ClusterMetrics metrics = tracker.getClusterMetrics();
  String trackerName = 
           StringUtils.simpleHostname(tracker.getJobTrackerMachine());
  JobQueueInfo[] queues = tracker.getQueues();
  Vector<JobInProgress> runningJobs = tracker.runningJobs();
  Vector<JobInProgress> completedJobs = tracker.completedJobs();
  Vector<JobInProgress> failedJobs = tracker.failedJobs();

      out.write('\n');
      out.write("\n\n\n<html>\n<head>\n<title>");
      out.print( trackerName );
      out.write(" Hadoop Map/Reduce Administration</title>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/hadoop.css\">\n<script type=\"text/javascript\" src=\"/static/jobtracker.js\"></script>\n<script type='text/javascript' src='/static/sorttable.js'></script>\n</head>\n<body>\n\n");
 JSPUtil.processButtons(request, response, tracker); 
      out.write("\n\n<h1>");
      out.print( trackerName );
      out.write(" Hadoop Map/Reduce Administration</h1>\n\n<div id=\"quicklinks\">\n  <a href=\"#quicklinks\" onclick=\"toggle('quicklinks-list'); return false;\">Quick Links</a>\n  <ul id=\"quicklinks-list\">\n    <li><a href=\"#scheduling_info\">Scheduling Info</a></li>\n    <li><a href=\"#running_jobs\">Running Jobs</a></li>\n    <li><a href=\"#retired_jobs\">Retired Jobs</a></li>\n    <li><a href=\"#local_logs\">Local Logs</a></li>\n  </ul>\n</div>\n\n<b>State:</b> ");
      out.print( status.getJobTrackerState() );
      out.write("<br>\n<b>Started:</b> ");
      out.print( new Date(tracker.getStartTime()));
      out.write("<br>\n<b>Version:</b> ");
      out.print( VersionInfo.getVersion());
      out.write(",\n                r");
      out.print( VersionInfo.getRevision());
      out.write("<br>\n<b>Compiled:</b> ");
      out.print( VersionInfo.getDate());
      out.write(" by \n                 ");
      out.print( VersionInfo.getUser());
      out.write("<br>\n<b>Identifier:</b> ");
      out.print( tracker.getTrackerIdentifier());
      out.write("<br>                 \n<b>SafeMode:</b> ");
      out.print( tracker.getSafeModeText());
      out.write("<br>                    \n<hr>\n<h2>Cluster Summary (Heap Size is ");
      out.print( StringUtils.byteDesc(Runtime.getRuntime().totalMemory()) );
      out.write('/');
      out.print( StringUtils.byteDesc(Runtime.getRuntime().maxMemory()) );
      out.write(")</h2>\n");
 
 generateSummaryTable(out, metrics, tracker); 

      out.write("\n<hr>\n<h2 id=\"scheduling_info\">Scheduling Information</h2>\n<table border=\"2\" cellpadding=\"5\" cellspacing=\"2\" class=\"sortable\">\n<thead style=\"font-weight: bold\">\n<tr>\n<td> Queue Name </td>\n<td> State </td>\n<td> Scheduling Information</td>\n</tr>\n</thead>\n<tbody>\n");

for(JobQueueInfo queue: queues) {
  String queueName = queue.getQueueName();
  String state = queue.getQueueState();
  String schedulingInformation = queue.getSchedulingInfo();
  if(schedulingInformation == null || schedulingInformation.trim().equals("")) {
    schedulingInformation = "NA";
  }

      out.write("\n<tr>\n<td><a href=\"jobqueue_details.jsp?queueName=");
      out.print(queueName);
      out.write('"');
      out.write('>');
      out.print(queueName);
      out.write("</a></td>\n<td>");
      out.print(state);
      out.write("</td>\n<td>");
      out.print(HtmlQuoting.quoteHtmlChars(schedulingInformation).replaceAll("\n","<br/>") );
      out.write("\n</td>\n</tr>\n");

}

      out.write("\n</tbody>\n</table>\n<hr/>\n<b>Filter (Jobid, Priority, User, Name)</b> <input type=\"text\" id=\"filter\" onkeyup=\"applyfilter()\"> <br>\n<span class=\"small\">Example: 'user:smith 3200' will filter by 'smith' only in the user field and '3200' in all fields</span>\n<hr>\n\n<h2 id=\"running_jobs\">Running Jobs</h2>\n");
      out.print(JSPUtil.generateJobTable("Running", runningJobs, 30, 0, tracker.conf));
      out.write("\n<hr>\n\n");

if (completedJobs.size() > 0) {
  out.print("<h2 id=\"completed_jobs\">Completed Jobs</h2>");
  out.print(JSPUtil.generateJobTable("Completed", completedJobs, 0, 
    runningJobs.size(), tracker.conf));
  out.print("<hr>");
}

      out.write('\n');
      out.write('\n');

if (failedJobs.size() > 0) {
  out.print("<h2 id=\"failed_jobs\">Failed Jobs</h2>");
  out.print(JSPUtil.generateJobTable("Failed", failedJobs, 0, 
    (runningJobs.size()+completedJobs.size()), tracker.conf));
  out.print("<hr>");
}

      out.write("\n\n<h2 id=\"retired_jobs\">Retired Jobs</h2>\n");
      out.print(JSPUtil.generateRetiredJobTable(tracker, 
  (runningJobs.size()+completedJobs.size()+failedJobs.size())));
      out.write("\n<hr>\n\n<h2 id=\"local_logs\">Local Logs</h2>\n<a href=\"logs/\">Log</a> directory,\n<a href=\"");
      out.print(JobHistoryServer.getHistoryUrlPrefix(tracker.conf));
      out.write("/jobhistoryhome.jsp\">\nJob Tracker History</a>\n\n");

out.println(ServletUtil.htmlFooter());

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
