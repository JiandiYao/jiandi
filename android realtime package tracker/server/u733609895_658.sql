
-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 22, 2013 at 10:32 AM
-- Server version: 5.1.61
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `u733609895_658`
--

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE IF NOT EXISTS `Customer` (
  `customerID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `firstname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `lastname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `postcode` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `phone` int(11) NOT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- Dumping data for table `Customer`
--

INSERT INTO `Customer` (`customerID`, `username`, `password`, `firstname`, `lastname`, `address`, `postcode`, `phone`) VALUES
(1, 'rack', '123', 'Jiandi', 'Yao', 'jdklsafjldfjal', 'N2L3W6', 2147483647),
(2, 'google', '123', 'G', 'H', 'sdfasdaaf', 'N2L3E4', 1254879568),
(3, 'Ming', '123', 'Ming', 'Tan', 'ksflajflaf', 'N2L0A1', 1234567895),
(4, 'good', '123', 'GOod', 'boy', 'sfa', 'N2L3C5', 7845464),
(5, 'god', '123', '', '', '', '', 0),
(6, 'new', '123', '', '', '', '', 0),
(7, 'user', '123', '', '', '', '', 0),
(8, 'user1', '123', '', '', '', '', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Order`
--

CREATE TABLE IF NOT EXISTS `Order` (
  `orderID` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `status` int(10) NOT NULL,
  `link` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `description` text COLLATE utf8_unicode_ci NOT NULL,
  `proNum` int(100) NOT NULL,
  `customerID` int(10) NOT NULL,
  `postID` int(10) NOT NULL,
  `estimatedArrivalTime` datetime NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='used to store order information' AUTO_INCREMENT=7 ;

--
-- Dumping data for table `Order`
--

INSERT INTO `Order` (`orderID`, `productName`, `status`, `link`, `description`, `proNum`, `customerID`, `postID`, `estimatedArrivalTime`) VALUES
(1, 'soap', 3, 'www.amazon.com', 'A dozen soaps', 12634564, 1, 1, '2013-11-21 11:13:42'),
(2, 'kindle', 3, 'www.ebay.com', 'a new kindle', 2, 4, 1, '2013-11-13 18:08:22'),
(3, 'ipad', 3, 'www.360buy.com', 'A new Ipad', 1, 3, 1, '2013-11-28 04:26:42'),
(4, 'mouse', 1, 'www.taobao.com', 'a logitech mouse', 1, 2, 1, '2013-12-11 11:11:41'),
(5, 'Iphone', 1, 'www.apple.com', 'a beautiful phone', 2, 1, 1, '2013-11-14 09:19:07'),
(6, 'IBM Laptop', 1, 'www.ibm.com', 'a black computer', 1, 2, 1, '2013-11-15 08:38:32');

-- --------------------------------------------------------

--
-- Table structure for table `Postman`
--

CREATE TABLE IF NOT EXISTS `Postman` (
  `postID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `firstname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `lastname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `phone` int(11) NOT NULL,
  PRIMARY KEY (`postID`),
  UNIQUE KEY `username` (`username`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `Postman`
--

INSERT INTO `Postman` (`postID`, `username`, `password`, `firstname`, `lastname`, `phone`) VALUES
(1, 'judy', '123', 'jdsfl', 'O', 124565778),
(2, 'post', '123', 'Jone', 'Freeman', 21234431);

-- --------------------------------------------------------

--
-- Table structure for table `PostmanLocation`
--

CREATE TABLE IF NOT EXISTS `PostmanLocation` (
  `No` int(10) NOT NULL AUTO_INCREMENT,
  `postID` int(10) NOT NULL,
  `longitude` double NOT NULL,
  `latitude` double NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`No`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `PostmanLocation`
--

INSERT INTO `PostmanLocation` (`No`, `postID`, `longitude`, `latitude`, `time`) VALUES
(1, 1, 43.656877, -70.656877, '2013-11-14 11:43:19');

-- --------------------------------------------------------

--
-- Table structure for table `PostmanOrder`
--

CREATE TABLE IF NOT EXISTS `PostmanOrder` (
  `postID` int(10) NOT NULL,
  `orderID` int(10) NOT NULL,
  `estimatedTime` datetime NOT NULL,
  PRIMARY KEY (`orderID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `PostmanOrder`
--

INSERT INTO `PostmanOrder` (`postID`, `orderID`, `estimatedTime`) VALUES
(1, 2, '2013-11-20 12:00:00'),
(1, 1, '2013-12-02 14:00:00'),
(1, 3, '2013-11-21 20:00:00'),
(1, 5, '2013-11-22 19:00:00'),
(1, 6, '2013-11-23 14:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`id`, `username`, `password`) VALUES
(1, 'rack', '123');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
