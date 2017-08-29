<?php
/**
 * A simple API to retrieve current stock market data
 * 
 * This PHP class uses the Yahoo! Finanace API to get current stock market data.
 * 
 * PHP version 5
 * 
 * @package   StockMarketAPI
 * @author    Ben Marshall <me@benmarshall.me>
 * @license   http://www.opensource.org/licenses/mit-license.html  MIT License
 * @version   1.1
 * @link      http://www.benmarshall.me/php-stock-market-api
 * @since     Class available since Release 1.0
 */
 
 
class StockMarketAPI
{
  /*
   * The stock symbol to request data for
   * 
   * @var string
   * @access public
   */
  public $symbol;
  
  /*
   * The type of data to request
   * 
   * @var string
   * @access public
   */
  public $stat;
  
  /*
   * Initializes the MarketWatchAPI
   * 
   * @param string  $symbol   the stock symbol
   * @param string  $stat     the type of data to retrieve, default grabs all data
   * 
   * @access public
   * @since Method available since Release 1.0
   */
  public function __construct($symbol = '', $stat = 'all')
  {
    if($symbol) $this->_setParam('symbol', $symbol);
    $this->_setParam('stat', $stat);
  }
  
  /*
   * Sets the class parameters
   * 
   * @param string  $param  the parameter to set
   * @param string  $val    the value of the parameter
   * 
   * @access private
   * @since Method available since Release 1.0
   */
  private function _setParam($param, $val) {
    switch($param)
    {
      case 'symbol':
        $this->symbol = $val;
        break;
      case 'stat':
        $this->stat = $val;
        break;
    }
  }
   
  /*
   * Makes a request to the Yahoo! Finance API
   * 
   * @access private
   * @since Method available since Release 1.0
   */
  private function _request()
  {
    $file = 'http://download.finance.yahoo.com/d/quotes.csv?s='.$this->symbol.'&f='.$this->_convertStat($this->stat).'=.csv';
    $handle = fopen($file, "r");
    $data = fgetcsv($handle, 4096, ',');
    fclose($handle);
    return $data;
  }

  /*
   * Retrieve's stock market data
   * 
   * @param string  $symbol   the stock symbol
   * @param string  $stat     the type of data to retrieve, default grabs all data
   * 
   * @return  array the requested data
   * 
   * @access public
   * @since Method available since Release 1.0
   */
  public function getData($symbol='', $stat='') {
    
    if($symbol) $this->_setParam('symbol', $symbol);
    if($stat) $this->_setParam('stat', $stat);
    
    $data = $this->_request();
    
    if ($this->stat === 'all') {
		/*$response["price"]=strip_tags($data[0]);
		$response["change"]=strip_tags($data[1]);
		$return = json_encode($response);*/
		
	
      $return = array(
        'price'                       =>  strip_tags($data[0]),
        'change'                      =>  strip_tags($data[1]),
        'volume'                      =>  strip_tags($data[2]),
        'avg_daily_volume'            =>  strip_tags($data[3]),
        'stock_exchange'              =>  strip_tags($data[4]),
        'market_cap'                  =>  strip_tags($data[5]),
        'book_value'                  =>  strip_tags($data[6]),
        'ebitda'                      =>  strip_tags($data[7]),
        'dividend_per_share'          =>  strip_tags($data[8]),
        'dividend_yield'              =>  strip_tags($data[9]),
        'earnings_per_share'          =>  strip_tags($data[10]),
        'fiftytwo_week_high'          =>  strip_tags($data[11]),
        'fiftytwo_week_low'           =>  strip_tags($data[12]),
        'fiftyday_moving_avg'         =>  strip_tags($data[13]),
        'twohundredday_moving_avg'    =>  strip_tags($data[14]),
        'price_earnings_ratio'        =>  strip_tags($data[15]),
        'price_earnings_growth_ratio' =>  strip_tags($data[16]),
        'price_sales_ratio'           =>  strip_tags($data[17]),
        'price_book_ratio'            =>  strip_tags($data[18]),
        'short_ratio'                 =>  strip_tags($data[19])
      );
	
    } else {
      //$return = array($this->stat => strip_tags($data[0]));
	  //$return = $this->stat => strip_tags($data[0]);
	 // $return = json_encode($return);
    }
    
    return $return;
  }


  
  
  /*
   * Converts the string stat to Yahoo! value
   * 
   * @param string  $stat   the text value stat
   * 
   * @return  string the Yahoo! stat value
   * 
   * @access private
   * @since Method available since Release 1.0
   */
  private function _convertStat($stat) {
    switch($stat) {
      case 'all':
        return 'l1c1va2xj1b4j4dyekjm3m4rr5p5p6s7';
        break;
      case 'price':
        return 'l1';
        break;
      case 'change':
        return 'c1';
        break;
      case 'volume':
        return 'v';
        break;
      case 'avgDailyVolume':
        return 'a2';
        break;
      case 'stockExchange':
        return 'x';
        break;
      case 'marketCap':
        return 'j1';
        break;
      case 'bookValue':
        return 'b4';
        break;
      case 'ebitda':
        return 'j4';
        break;
      case 'dividendPerShare':
        return 'd';
        break;
      case 'dividendYield':
        return 'y';
        break;
      case 'eps':
        return 'e';
        break;
      case '52WeekHigh':
        return 'k';
        break;
      case '52WeekLow':
        return 'j';
        break;
      case '50DayMovingAvg':
        return 'm4';
        break;
      case '200DayMovingAvg':
        return 'm3';
        break;
      case 'peRatio':
        return 'r';
        break;
      case 'peGrowthRatio':
        return 'r5';
        break;
      case 'priceSalesRatio':
        return 'p5';
        break;
      case 'priceBookRatio':
        return 'p6';
        break;
      case 'shortRatio':
        return 's7';
        break;
    }
  }
}
