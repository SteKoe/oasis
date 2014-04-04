-- phpMyAdmin SQL Dump
-- version 4.1.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 03, 2014 at 01:07 PM
-- Server version: 5.5.25
-- PHP Version: 5.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `idss`
--

--
-- Dumping data for table `Permission`
--

INSERT INTO `Permission` (`id`, `permissionObjectId`, `permissionObject`, `permissionType`) VALUES
('41304a42-7f18-4662-8bf7-16f4641ffeaf', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'UPDATE'),
('47164465-1f83-4ee5-8dbc-57bab4e90abf', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'PROJECT_ADD_MEMBER'),
('6a328c47-3b18-4b2c-bfe2-26a901e20835', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'DELETE'),
('94e2d743-3fa1-447d-b688-8deb4ce5e5e1', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'READ'),
('af7ba4c2-4f82-4431-babf-2cb7ba9fa9d0', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'PROJECT_UPLOAD_FILE'),
('c8772612-4ff2-48c5-8f5e-f6502daca719', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'PROJECT_ADD_ROLES'),
('d8fa1db5-7d6d-45fa-8f1a-c4314fabbf26', '3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'PROJECT', 'READ');

--
-- Dumping data for table `Project`
--

INSERT INTO `Project` (`id`, `description`, `name`, `projectEndDate`, `projectStartDate`, `projectStatus`) VALUES
('3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', 'Contrary to popular belief, Lorem Ipsum is not simply random text. <strong>It has roots</strong> in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of "de Finibus Bonorum et Malorum" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance.', 'Sample Name for Project', NULL, '2014-04-03', 'EDITING');

--
-- Dumping data for table `ProjectMember`
--

INSERT INTO `ProjectMember` (`id`, `projectRole_project_role_id`, `user_user_id`) VALUES
('8df6bba9-b8af-4107-84c1-c8d11cde9486', '139af811-0821-4357-929f-778ed21ff869', '2338f1b9-cdac-4fe1-becd-bc3e4e9a1080');

--
-- Dumping data for table `ProjectRole`
--

INSERT INTO `ProjectRole` (`id`, `name`) VALUES
('03e6f35b-b77e-4f2b-bc96-d5f9bd472400', 'Projektmitglied'),
('139af811-0821-4357-929f-778ed21ff869', 'Projektleiter');

--
-- Dumping data for table `ProjectRole_Permission`
--

INSERT INTO `ProjectRole_Permission` (`ProjectRole_id`, `permissions_id`) VALUES
('139af811-0821-4357-929f-778ed21ff869', '41304a42-7f18-4662-8bf7-16f4641ffeaf'),
('139af811-0821-4357-929f-778ed21ff869', '47164465-1f83-4ee5-8dbc-57bab4e90abf'),
('139af811-0821-4357-929f-778ed21ff869', '6a328c47-3b18-4b2c-bfe2-26a901e20835'),
('139af811-0821-4357-929f-778ed21ff869', '94e2d743-3fa1-447d-b688-8deb4ce5e5e1'),
('139af811-0821-4357-929f-778ed21ff869', 'af7ba4c2-4f82-4431-babf-2cb7ba9fa9d0'),
('139af811-0821-4357-929f-778ed21ff869', 'c8772612-4ff2-48c5-8f5e-f6502daca719'),
('03e6f35b-b77e-4f2b-bc96-d5f9bd472400', 'd8fa1db5-7d6d-45fa-8f1a-c4314fabbf26');

--
-- Dumping data for table `Project_ProjectMember`
--

INSERT INTO `Project_ProjectMember` (`Project_project_id`, `projectTeam_id`) VALUES
('3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', '8df6bba9-b8af-4107-84c1-c8d11cde9486');

--
-- Dumping data for table `Project_ProjectRole`
--

INSERT INTO `Project_ProjectRole` (`Project_project_id`, `projectRoles_project_role_id`) VALUES
('3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', '03e6f35b-b77e-4f2b-bc96-d5f9bd472400'),
('3bfca2dd-c0d4-4b24-9c6e-38fbf77e3051', '139af811-0821-4357-929f-778ed21ff869');

--
-- Dumping data for table `SystemRole`
--

INSERT INTO `SystemRole` (`id`, `name`) VALUES
('a75ae3a2-f0a7-4ac9-9394-a812f3563b18', 'ADMIN'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'USER');

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`id`, `activationKey`, `email`, `password`, `userStatus`, `username`, `profile_user_profile_id`) VALUES
('029cfff5-ac09-4088-abb0-7a630a46dee1', NULL, 'hans.wurst@example.com', '$2a$10$lvMULLc1px8RodNqmr7xDuylcZiwPGcVq6q0DEgRdib3h0VqiIYLO', 'ACTIVATED', 'hans.wurst', NULL),
('03fb657e-4c53-4ccb-8c33-1553c513b3af', NULL, 'jonas.sprenger@example.com', '$2a$10$fCEmUiytVWalbIlHwLvLUet4HJ3PnbJxg.zOYN5xAj4iqo5/XwutK', 'ACTIVATED', 'jonas.sprenger', NULL),
('1d6c065e-9d3c-4b6d-84b6-e6636702f420', NULL, 'rainer.zufall@example.com', '$2a$10$3TykqVSwMWkjSaKWTF4eHeWPcW2afXXyVwpNwPmOoOXcBxeDhx8J6', 'ACTIVATED', 'rainer.zufall', NULL),
('2338f1b9-cdac-4fe1-becd-bc3e4e9a1080', NULL, 'klara.fall@example.com', '$2a$10$M0rYM8ggZu/D35.Ek9dTaOx9K35EX0HyACXMIFWx7KUUJQbwOdej.', 'ACTIVATED', 'klara.fall', NULL),
('28b21d98-f596-4e69-aac8-c1da50778c65', NULL, 'administrator@example.com', 'admin', 'ACTIVATED', 'administrator', NULL),
('2b1b4a53-3caf-4a60-bb51-f684ce3b39d7', NULL, 'michael.hess@example.com', '$2a$10$sswRrBhTTYgokPljvczjleGsd1oJD2yOhWGjDNmgG/THkPGnee3QC', 'ACTIVATED', 'michael.hess', NULL),
('7c40f070-fa6d-4d5b-ab6f-8a209206abc3', NULL, 'anna.gramm@example.com', '$2a$10$cfRiBcU5R3.0gZnRgzVn8unKklPL61pymet7nksf/Il0OCyoiuWZW', 'ACTIVATED', 'anna.gramm', NULL),
('8df8d064-e814-42bf-88ef-0206a86b0b40', NULL, 'jeff.trainer@example.com', '$2a$10$OQuMtxYvB4woxF059wKD5e8tL.vJZiRYRLdlrbWRNsBj6MBR1ttIa', 'ACTIVATED', 'jeff.trainer', NULL),
('9a1f931c-24f8-4690-9361-8de56b239505', NULL, 'stephan.koeninger@example.com', '$2a$10$nHGNT6e3MGfbDAwNg1WcAe3DeZaz1YoOhXLCMBFAuMpvFuAjNwszy', 'ACTIVATED', 'stephan.koeninger', NULL),
('9cb8de40-82f5-445b-b18c-6e3124a3f061', NULL, 'benedikt.ritter@example.com', '$2a$10$ASJUrECOsrOvYoMIw42Xgumtm8MvpJfgNnU08QAlw/aHw1lZU7ZL.', 'ACTIVATED', 'benedikt.ritter', NULL),
('a7af0343-eaf5-4190-b7c7-873228407256', NULL, 'kai.ser@example.com', '$2a$10$k9F8a3BXF0nx9C7p07TqCOo2WIqqHR6KVQMQpZvP2rbxZ6Og5cvIi', 'ACTIVATED', 'kai.ser', NULL),
('ba1f0843-81a9-4bdd-82e5-ecdb25ae2b20', NULL, 'peer.manent@example.com', '$2a$10$TAXBJWuabGwaKJK4Vek9Yuj0M9Deu56/Y3/.JYGxuRi.OpdLwvkHK', 'ACTIVATED', 'peer.manent', NULL),
('be1b6bc0-56f5-46e0-be46-1d516e0c6688', NULL, 'anna.nass@example.com', '$2a$10$BbOFRgmI9akxDUHXprPYseIKIkJ8lWSFmPhw0Lua.gtHWIlt42iYO', 'ACTIVATED', 'anna.nass', NULL),
('c0d4f168-ca20-4d3f-b2ec-2b8ad0ac4061', NULL, 'melita.kaffee@example.com', '$2a$10$ceIfDTH7Va47p2NE9I3tJOXdDd0TxUMmKTkUCF38Ynz7Ivih20ANG', 'ACTIVATED', 'melita.kaffee', NULL),
('c63078b0-94f8-4e00-8f2a-54609793fe46', NULL, 'jo.kher@example.com', '$2a$10$tdAKk6RXTSkg83UT0m9zFegkL0bCD1TjFmVcP9WQPYIEux6v97OWK', 'ACTIVATED', 'jo.kher', NULL);

--
-- Dumping data for table `User_SystemRole`
--

INSERT INTO `User_SystemRole` (`system_role_id`, `user_id`) VALUES
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '029cfff5-ac09-4088-abb0-7a630a46dee1'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '03fb657e-4c53-4ccb-8c33-1553c513b3af'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '1d6c065e-9d3c-4b6d-84b6-e6636702f420'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '2338f1b9-cdac-4fe1-becd-bc3e4e9a1080'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '2b1b4a53-3caf-4a60-bb51-f684ce3b39d7'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '7c40f070-fa6d-4d5b-ab6f-8a209206abc3'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '8df8d064-e814-42bf-88ef-0206a86b0b40'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '9a1f931c-24f8-4690-9361-8de56b239505'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', '9cb8de40-82f5-445b-b18c-6e3124a3f061'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'a7af0343-eaf5-4190-b7c7-873228407256'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'ba1f0843-81a9-4bdd-82e5-ecdb25ae2b20'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'be1b6bc0-56f5-46e0-be46-1d516e0c6688'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'c0d4f168-ca20-4d3f-b2ec-2b8ad0ac4061'),
('4d8234ce-e643-48c2-9b89-497bf58f7ea7', 'c63078b0-94f8-4e00-8f2a-54609793fe46'),
('a75ae3a2-f0a7-4ac9-9394-a812f3563b18', '28b21d98-f596-4e69-aac8-c1da50778c65');
