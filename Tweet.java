/**
 *@author Wei Zhang
 *@version build Feb 23, 2013 
 *this class get the most recent tweets match the hashtag
 */

import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

public class Tweet {
	/** This function parses the URLs, returns the unique URL set
	 * 
	 * @param line			URLs returned by the server of Twitter
	 * @return			    unique URL set
	 */
	public static Set<String> getUrls(String line) {
		int pos = 0;
		Set<String> ret = new HashSet<String>();
		while ((pos = line.indexOf("expanded", pos+1)) != -1) {
			String newUrl = line.substring(pos+15, line.indexOf('\"', pos+15));
			if (ret.contains(newUrl)) {
				ret.remove(newUrl);
			} else {
				ret.add(newUrl);
			}
		}
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		String hashtag;
		//if there is '#' in the hashtag, remove it
		if (args[0].charAt(0)=='#')		hashtag = args[0].substring(1);
		else							hashtag = args[0];
		URL address = new URL("http://search.twitter.com/search.json?q=%23"+hashtag+"&rpp=100&include_entities=true&result_type=recent");
		BufferedReader in = new BufferedReader( new InputStreamReader( address.openStream() ) );

		String line = in.readLine();
		if (line == null) {
			System.out.println("Error, the response from server is empty.");
		} else {
			Set<String> dataSet = getUrls( line );
			
			if (dataSet == null) {
				System.out.println("Error, the url set is null.");
			} else {
				for (String str : dataSet) {
					for (int i=0; i<str.length(); i++) {
						if (str.charAt(i)!='\\')
							System.out.print(str.charAt(i));
					}
					System.out.println();
				}
				System.out.println("There are "+dataSet.size()+" unique urls in total.");
			}
		}
		in.close();
	}
}
