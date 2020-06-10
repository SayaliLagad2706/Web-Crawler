import java.io.FileWriter;
import java.io.IOException;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;

public class Controller {
	public static FileWriter fetch_csv;
	public static FileWriter visit_csv;
	public static FileWriter urls_csv;
	public static FileWriter crawlReport;

	public static void main(String[] args) {
		 String crawlStorageFolder = "/data/crawl";
		 int numberOfCrawlers = 7;
		 CrawlConfig config = new CrawlConfig();
		 config.setCrawlStorageFolder(crawlStorageFolder);
		 config.setMaxDepthOfCrawling(16);
		 config.setMaxPagesToFetch(28000);
		 config.setPolitenessDelay(1000);
		 config.setIncludeBinaryContentInCrawling(true);

		 /*
		 * Instantiate the controller for this crawl.
		 */
		 PageFetcher pageFetcher = new PageFetcher(config);
		 RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		 RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		 CrawlController controller = null;

		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			fetch_csv = new FileWriter("fetch_FoxNews.csv");
			visit_csv = new FileWriter("visit_FoxNews.csv");
			urls_csv = new FileWriter("urls_FoxNews.csv");
			crawlReport = new FileWriter("CrawlReport_FoxNews.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 /*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		 controller.addSeed("https://www.foxnews.com/");
		 controller.start(MyCrawler.class, numberOfCrawlers);
		 try {
			 fetch_csv.flush();
			 fetch_csv.close();
			 visit_csv.flush();
			 visit_csv.close();
			 urls_csv.flush();
			 urls_csv.close();
			 crawlReport.flush();
			 crawlReport.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
