-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 20, 2013 at 05:10 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mysql`
--
CREATE DATABASE IF NOT EXISTS `mysql` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `mysql`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `clientID` int(11) NOT NULL,
  `accountType` varchar(45) NOT NULL,
  `balance` double NOT NULL,
  `accountID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`accountID`),
  KEY `clientID_idx` (`clientID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`clientID`, `accountType`, `balance`, `accountID`) VALUES
(1, 'traditional', 3112, 1),
(2, 'special', 200, 2),
(5, 'traditional', 7949.592000000001, 3),
(1, 'special', 4000, 4),
(5, 'special', 400000, 5),
(2, 'traditional', 1220, 6),
(3, 'traditional', 600000, 7),
(3, 'special', 4000, 8),
(4, 'traditional', 200, 9),
(4, 'special', 3000, 10);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `clientID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` int(11) NOT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `ClientID_UNIQUE` (`clientID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`clientID`, `username`, `password`, `firstname`, `lastname`, `address`, `email`, `phone`) VALUES
(1, 'rack', '123', 'Clint', 'Venturini', 'M4S2Y9', 'judy.yjd@gmail.com', 1657687788),
(2, 'judy', '123', 'Daron', 'Livesay', 'M4S2Y4', 'daronl@gmail.com', 1657687788),
(3, 'mark', '123', 'Tarsha', 'Burditt', 'M3S1Y0', 'tarshab@gmail.com', 1657687788),
(4, 'mike', '123', 'Cathy', 'Merkle', 'M2S1Y8', 'cathym@gmail.com', 1657687788),
(5, 'lisa', '123', 'Lisa', 'Simpson', 'M9E2Y2', 'lisas@gmail.com', 1657687788),
(6, 'homer', '123', 'Homer', 'Simpson', 'M2RL3S', 'homers@gmail.com', 1657687788);

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `stockSymbol` varchar(8) NOT NULL,
  `company` varchar(45) NOT NULL,
  PRIMARY KEY (`stockSymbol`),
  UNIQUE KEY `company_UNIQUE` (`company`),
  UNIQUE KEY `stockSymbol_UNIQUE` (`stockSymbol`),
  KEY `company` (`company`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stockSymbol`, `company`) VALUES
('AMZN', 'Amazon.com, Inc.'),
('AAPL', 'Apple Inc'),
('GOOG', 'Google Inc'),
('IBM', 'International Business Machines Corp'),
('MSFT', 'Microsoft Corporation'),
('YHOO', 'Yahoo! Inc');

-- --------------------------------------------------------

--
-- Table structure for table `stockinhold`
--

CREATE TABLE IF NOT EXISTS `stockinhold` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `clientID` int(10) NOT NULL,
  `stockSymbol` varchar(10) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `clientID` (`clientID`),
  KEY `clientID_2` (`clientID`),
  KEY `stockSymbol` (`stockSymbol`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Dumping data for table `stockinhold`
--

INSERT INTO `stockinhold` (`no`, `clientID`, `stockSymbol`) VALUES
(1, 1, 'GOOG'),
(2, 1, 'AAPL'),
(5, 1, 'YHOO'),
(6, 2, 'GOOG'),
(7, 2, 'AAPL'),
(8, 2, 'IBM'),
(9, 2, 'AMZN'),
(10, 2, 'YHOO'),
(11, 3, 'GOOG'),
(12, 3, 'AAPL'),
(13, 3, 'IBM'),
(14, 3, 'AMZN'),
(15, 3, 'YHOO'),
(16, 4, 'GOOG'),
(17, 4, 'AAPL'),
(18, 4, 'IBM'),
(19, 4, 'AMZN'),
(20, 4, 'YHOO'),
(21, 5, 'GOOG'),
(22, 5, 'AAPL'),
(23, 5, 'IBM'),
(24, 5, 'AMZN'),
(25, 5, 'YHOO'),
(26, 1, 'MSFT'),
(27, 3, 'MSF'),
(28, 5, 'MSFT'),
(29, 4, 'MSF');

-- --------------------------------------------------------

--
-- Table structure for table `stock_data`
--

CREATE TABLE IF NOT EXISTS `stock_data` (
  `stockSymbol` varchar(10) NOT NULL,
  `data` date NOT NULL,
  `open` float NOT NULL,
  `high` float NOT NULL,
  `low` float NOT NULL,
  `close` float NOT NULL,
  `volume` int(11) NOT NULL,
  `no` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `stock_order`
--

CREATE TABLE IF NOT EXISTS `stock_order` (
  `orderNo` int(11) NOT NULL AUTO_INCREMENT,
  `accountID` int(11) NOT NULL,
  `share` int(11) NOT NULL,
  `bidPrice` float NOT NULL,
  `bidTime` time NOT NULL,
  `bidDate` date NOT NULL COMMENT 'orderType: 1: buy\n0: sell\n\ntotal: means the stock shares in the account after buy/sell operations',
  `stockSymbol` varchar(10) NOT NULL,
  PRIMARY KEY (`orderNo`),
  KEY `accountNo_idx` (`accountID`),
  KEY `stockSymbol_idx` (`stockSymbol`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=29 ;

--
-- Dumping data for table `stock_order`
--

INSERT INTO `stock_order` (`orderNo`, `accountID`, `share`, `bidPrice`, `bidTime`, `bidDate`, `stockSymbol`) VALUES
(2, 1, 200, 887.2, '13:00:00', '2013-11-02', 'GOOG'),
(3, 1, 500, 32.06, '14:00:00', '2013-10-22', 'YHOO'),
(7, 4, 2100, 32.08, '14:00:00', '2013-01-02', 'YHOO'),
(10, 5, 0, 1025.2, '12:00:00', '2013-11-19', 'GOOG'),
(11, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(12, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(20, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(21, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(22, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(23, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(24, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(25, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(26, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(27, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG'),
(28, 1, 12303, 1230.9, '12:00:00', '2013-10-03', 'GOOG');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `clientID` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `stockinhold`
--
ALTER TABLE `stockinhold`
  ADD CONSTRAINT `stockinhold_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`);

--
-- Constraints for table `stock_order`
--
ALTER TABLE `stock_order`
  ADD CONSTRAINT `stock_order_ibfk_1` FOREIGN KEY (`accountID`) REFERENCES `account` (`accountID`),
  ADD CONSTRAINT `stock` FOREIGN KEY (`stockSymbol`) REFERENCES `stock` (`stockSymbol`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
