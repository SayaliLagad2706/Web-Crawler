import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(html|doc|pdf|jpg|png|jpeg|gif|tiff|raw))$");
	
			 /**
			 * This method receives two parameters. The first parameter is the page
			 * in which we have discovered this new url and the second parameter is
			 * the new url. You should implement this function to specify whether
			 * the given url should be crawled or not (based on your crawling logic).
			 */
			 @Override
			 public boolean shouldVisit(Page referringPage, WebURL url) {
				 String href = url.getURL().toLowerCase();
				 try {
					  Controller.urls_csv.append(href);
					  Controller.urls_csv.append(",");
					  if(href.startsWith("https://www.foxnews.com") || href.startsWith("http://www.foxnews.com") 
							  || href.startsWith("https://foxnews.com") || href.startsWith("http://foxnews.com")) {
						  Controller.urls_csv.append("OK");
					  }
					  else {
						  Controller.urls_csv.append("N_OK");
					  }
					  Controller.urls_csv.append("\n");
				  }
				  catch(IOException e) {
					  e.printStackTrace();
				  }

				 return (((FILTERS.matcher(href).matches() /*&& !FILTERS1.matcher(href).matches()*/)
						 || (referringPage.getContentType().contains("image") 
						 || (referringPage.getContentType().contains("html") && (!referringPage.getContentType().contains("javascript")) && (!referringPage.getContentType().contains("css"))) 
						 || referringPage.getContentType().contains("doc") || (referringPage.getContentType().contains("docx") || referringPage.getContentType().contains("pdf"))))
				 && (href.startsWith("https://www.foxnews.com") || href.startsWith("http://www.foxnews.com")));
			 }
			 
			 /**
			  * This function is called when a page is fetched and ready
			  * to be processed by your program.
			  */
			  @Override
			  public void visit(Page page) {
				  String url = page.getWebURL().getURL();
				  Set<WebURL> links1 = new HashSet<WebURL>();
				  Set<WebURL> links2 = new HashSet<WebURL>();
				  int statusCode = page.getStatusCode();
				  
				  if (page.getParseData() instanceof HtmlParseData) {
					  HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
					  String text = htmlParseData.getText();
					  String html = htmlParseData.getHtml();
					  links1 = htmlParseData.getOutgoingUrls();
				  }
				  else if(page.getParseData() instanceof BinaryParseData){
					  BinaryParseData binaryParseData = (BinaryParseData) page.getParseData();
				      links2 = binaryParseData.getOutgoingUrls();
				  }
				  
				  try {
					Controller.visit_csv.append(url);
					Controller.visit_csv.append(",");
					Controller.visit_csv.append(String.valueOf(page.getContentData().length));
					Controller.visit_csv.append(",");
					Controller.visit_csv.append(String.valueOf(links1.size() + links2.size()));
					Controller.visit_csv.append(",");
					Controller.visit_csv.append(String.valueOf(page.getContentType()));
					Controller.visit_csv.append("\n");
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			 }
			  
			  @Override
		        protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
				  try {
					  Controller.fetch_csv.append(webUrl.getURL());
					  Controller.fetch_csv.append(",");
					  Controller.fetch_csv.append(String.valueOf(statusCode));
					  Controller.fetch_csv.append("\n");
				  } catch(IOException e) {
					  e.printStackTrace();
				  }
		        }
			  
}
