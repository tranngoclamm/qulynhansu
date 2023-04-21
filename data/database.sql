-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 21, 2023 lúc 07:50 AM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `database`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employeedata`
--

CREATE TABLE `employeedata` (
  `id` varchar(20) NOT NULL,
  `ho_ten` varchar(50) NOT NULL,
  `ngay_sinh` varchar(10) NOT NULL,
  `gioi_tinh` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `chuc_vu` varchar(50) NOT NULL,
  `he_so_luong` double NOT NULL,
  `ngay_lam_viec` int(11) NOT NULL,
  `tong_luong` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employeedata`
--

INSERT INTO `employeedata` (`id`, `ho_ten`, `ngay_sinh`, `gioi_tinh`, `email`, `chuc_vu`, `he_so_luong`, `ngay_lam_viec`, `tong_luong`) VALUES
('NV001', 'Nguyen Van An', '1990-01-01', 'Male', 'nva@gmail.com', 'Director', 6.2, 20, 14584615),
('NV002', 'Tran Thi Hue', '1995-05-10', 'Female', 'tth@gmail.com', 'Manager', 5.6, 18, 11673846),
('NV003', 'Le Van Cuong', '1992-09-20', 'Male', 'lvc@gmail.com', 'Accountant', 4.9, 22, 12854615),
('NV004', 'Nguyen Thi Dung', '1994-03-15', 'Female', 'ntd@gmail.com', 'Accountant', 4.9, 20, 11523846),
('NV005', 'Pham Van Tuan', '1998-12-30', 'Male', 'pvt@gmail.com', 'Accountant', 4.9, 18, 10384615),
('NV006', 'Tran Van Chien', '1993-06-05', 'Male', 'tvc@gmail.com', 'Employee', 2.5, 22, 5432692),
('NV007', 'Le Thi Giang', '1996-11-25', 'Female', 'ltg@gmail.com', 'Employee', 2.5, 20, 4913461),
('NV008', 'Pham Van Hung', '1991-01-02', 'Male', 'pvh@gmail.com', 'Employee', 2.5, 18, 4394231);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `positiondata`
--

CREATE TABLE `positiondata` (
  `id` int(11) NOT NULL,
  `chuc_vu` varchar(50) NOT NULL,
  `he_so_luong` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `positiondata`
--

INSERT INTO `positiondata` (`id`, `chuc_vu`, `he_so_luong`) VALUES
(1, 'Accountant', 4.9),
(2, 'Director', 6.2),
(3, 'Manager', 5.6),
(8, 'Employee', 2.5);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`username`, `password`) VALUES
('admin', '12345'),
('nhom8', 'nhom812345');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `positiondata`
--
ALTER TABLE `positiondata`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `positiondata` ADD FULLTEXT KEY `chuc_vu` (`chuc_vu`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `positiondata`
--
ALTER TABLE `positiondata`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
