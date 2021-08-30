-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 30, 2021 lúc 10:40 AM
-- Phiên bản máy phục vụ: 10.4.14-MariaDB
-- Phiên bản PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `courseregister`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `courseregistration`
--

CREATE TABLE `courseregistration` (
  `id` int(11) NOT NULL,
  `masv` varchar(10) NOT NULL,
  `mamh` varchar(15) NOT NULL,
  `tenmh` varchar(100) NOT NULL,
  `malop` varchar(15) NOT NULL,
  `sotc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `courseregistration`
--

INSERT INTO `courseregistration` (`id`, `masv`, `mamh`, `tenmh`, `malop`, `sotc`) VALUES
(58, '637630', 'TH03111', 'lap trinh java', 'K63HTTT', 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `classcode` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `login`
--

INSERT INTO `login` (`id`, `username`, `password`, `classcode`) VALUES
(1, '637630', '123', 'K63ATTT'),
(2, '637648', '123', 'K63HTTT');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `subject`
--

CREATE TABLE `subject` (
  `id` int(11) NOT NULL,
  `mamh` varchar(10) NOT NULL,
  `tenmh` varchar(100) NOT NULL,
  `malop` varchar(15) NOT NULL,
  `sotc` int(5) NOT NULL,
  `sisomax` int(11) NOT NULL,
  `sisocl` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `subject`
--

INSERT INTO `subject` (`id`, `mamh`, `tenmh`, `malop`, `sotc`, `sisomax`, `sisocl`) VALUES
(1, 'TH03111', 'lap trinh java', 'K63ATTT', 3, 50, 1),
(2, 'KT01212', 'kinh te vi mo', 'K63KTA', 3, 45, 45),
(3, 'TH02106', 'cau truc du lieu giai thuat', 'K63ATTT', 3, 120, 120),
(4, 'TH03111', 'lap trinh java', 'K63HTTT', 3, 90, 89);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `courseregistration`
--
ALTER TABLE `courseregistration`
  ADD UNIQUE KEY `id` (`id`),
  ADD UNIQUE KEY `duplli` (`masv`,`mamh`,`malop`);

--
-- Chỉ mục cho bảng `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Chỉ mục cho bảng `subject`
--
ALTER TABLE `subject`
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `courseregistration`
--
ALTER TABLE `courseregistration`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT cho bảng `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `subject`
--
ALTER TABLE `subject`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
