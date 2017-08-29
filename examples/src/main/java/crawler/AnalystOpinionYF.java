/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.googlecode.jcsv.*;
import com.googlecode.jcsv.writer.CSVWriter;
import com.googlecode.jcsv.writer.internal.CSVWriterBuilder;
import java.io.FileWriter;
import java.io.Writer;
/*
 * It crawls the website and get expert opinions
 */
public class AnalystOpinionYF {
    private final String base_URL = "http://finance.yahoo.com/q/ud?s=";
    private Document doc;
    
    public static void main(String[] args){
    	CSVReader ticker = new CSVReader();
        ArrayList<String> tickers;
        tickers = ticker.get_tickers();
    	AnalystOpinionYF aopi = new AnalystOpinionYF();
        try {
			Map<String, String> r = aopi.get_analyst_opinion(tickers);
			System.out.println(r);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public Map<String, String> get_analyst_opinion(ArrayList<String> tickers) throws IOException{
    
    	Map<String, String> recommend = new HashMap<String, String>();
        for(String s: tickers){
        	
            String target_URL = base_URL + s;
            try {
                doc = Jsoup.connect(target_URL).get();
                Elements ele = doc.select("div[id=content] table[class=yfnc_datamodoutline1] table tbody tr");
               
                Elements td = ele.select("td");
                ArrayList<String>  data_temp;
                data_temp = new ArrayList<>();
           
                int i = 0;
                for(Element e: td){
                    ++i;                  
                    if(e.select("th").isEmpty()){
                        data_temp.add(e.text());               
                        if(i%5 == 0){                           
                            recommend.put(s, data_temp.get(0)+","+data_temp.get(4));                          
                            data_temp = new ArrayList<>();                            
                            break;
                        }
                    }
                }
             
            } catch (IOException ex) {
                Logger.getLogger(AnalystOpinionYF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return recommend;
    }
}

