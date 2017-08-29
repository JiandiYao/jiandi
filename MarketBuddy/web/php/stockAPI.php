<?php
		require_once('class.stockMarketAPI.php');
		$StockMarketAPI = new StockMarketAPI;
		$StockMarketAPI->symbol = $name;
		$StockMarketAPI->stat = 'all';
		//print_r($StockMarketAPI->getData());

		$response = $StockMarketAPI->getData();
		$response = array_values($response);
		$result["symbol"]=$name;
		$result["price"]= $response[0];
		$result["change"]=$response[1];
		$result["volume"]=$response[2];
		$result["avg_daily_volume"]=$response[3];
		$result["stock_exchange"]=$response[4];
		$result["market_cap"]=$response[5];
		$result["book_value"]=$response[6];
		$result["ebitda"]=$response[7];
		$result["dividend_per_share"]=$response[8];
		$result["dividend_yield"]=$response[9];
		$result["earnings_per_share"]=$response[10];
		$result["fiftytwo_week_high"]=$response[11];
		$result["fiftytwo_week_low"]=$response[12];
		$result["fiftyday_moving_avg"]=$response[13];
		$result["twohundredday_moving_avg"]=$response[14];
		$result["price_earnings_ratio"]=$response[15];
		$result["price_earnings_growth_ratio"]=$response[16];
		$result["price_sales_ratio"]=$response[17];
		$result["price_book_ratio"]=$response[18];
		$result["short_ratio"]=$response[19];

		$result = json_encode($result);
		echo $result;
		?>