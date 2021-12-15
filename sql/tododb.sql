-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 15, 2021 at 11:05 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tododb`
--

-- --------------------------------------------------------

--
-- Table structure for table `todo_items`
--

CREATE TABLE `todo_items` (
  `item_id` int(11) NOT NULL,
  `creation_date_time` datetime DEFAULT NULL,
  `description` text DEFAULT NULL,
  `item_title` varchar(255) NOT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `todo_items`
--

INSERT INTO `todo_items` (`item_id`, `creation_date_time`, `description`, `item_title`, `priority`, `status`, `user_id`) VALUES
(2, '2021-12-15 09:55:49', 'Metting with Bob.', 'Meeting ', '1', 'IN_PROGRESS', 1),
(3, '2021-12-15 09:56:56', 'Glossary Shopping.', 'Sopping', '2', 'COMPLETED', 1),
(4, '2021-12-15 10:00:27', 'Need to email Tina.', 'Email', '3', 'TODO', 1);

-- --------------------------------------------------------

--
-- Table structure for table `todo_items_status_change_log`
--

CREATE TABLE `todo_items_status_change_log` (
  `change_log_id` int(11) NOT NULL,
  `changed_status` varchar(255) DEFAULT NULL,
  `creation_date_time` datetime DEFAULT NULL,
  `prev_status` varchar(255) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `todo_items_status_change_log`
--

INSERT INTO `todo_items_status_change_log` (`change_log_id`, `changed_status`, `creation_date_time`, `prev_status`, `item_id`) VALUES
(1, 'COMPLETED', '2021-12-15 10:00:57', 'TODO', 2),
(2, 'COMPLETED', '2021-12-15 10:01:18', 'TODO', 3),
(3, 'IN_PROGRESS', '2021-12-15 10:01:27', 'COMPLETED', 2);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `creation_date_time` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(64) DEFAULT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `creation_date_time`, `enabled`, `first_name`, `last_name`, `password`, `username`) VALUES
(1, '2021-12-07 06:56:56', b'1', 'Muradul', 'Mostafa', '$2a$10$mieR/xp7pXAcg7Mprv8Cruxsq7JekzrSnXU/0iVTQJiKr970vCFuG', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `todo_items`
--
ALTER TABLE `todo_items`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `FKav4ha4dkkk09oss5h6qdtnggv` (`user_id`);

--
-- Indexes for table `todo_items_status_change_log`
--
ALTER TABLE `todo_items_status_change_log`
  ADD PRIMARY KEY (`change_log_id`),
  ADD KEY `FKb6wbfcyy2lhg70ptoasaavfa0` (`item_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `todo_items`
--
ALTER TABLE `todo_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `todo_items_status_change_log`
--
ALTER TABLE `todo_items_status_change_log`
  MODIFY `change_log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `todo_items`
--
ALTER TABLE `todo_items`
  ADD CONSTRAINT `FKav4ha4dkkk09oss5h6qdtnggv` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `todo_items_status_change_log`
--
ALTER TABLE `todo_items_status_change_log`
  ADD CONSTRAINT `FKb6wbfcyy2lhg70ptoasaavfa0` FOREIGN KEY (`item_id`) REFERENCES `todo_items` (`item_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
