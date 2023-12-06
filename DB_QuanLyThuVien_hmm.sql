create database DB_QuanLyThuVien;
GO
USE DB_QuanLyThuVien
--drop database DB_QuanLyThuVien
Go
CREATE TABLE LoaiSach (
    MaLoaiSach VARCHAR(30) NOT NULL PRIMARY KEY,
    TenLoaiSach NVARCHAR(250) NOT NULL
);
 go
CREATE TABLE Sach (
    MaSach INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    TieuDe NVARCHAR(250) NOT NULL,
    NhaXuatBan NVARCHAR(250) NOT NULL,
    TacGia NVARCHAR(250) NOT NULL,
    SoTrang INT NULL,
    SoLuongSach INT  NOT NULL,
    GiaTien FLOAT NULL,
    NgayNhapKho DATE NULL,
    ViTriDatSach NVARCHAR(250)  NOT NULL,
    MaLoaiSach VARCHAR(30)  NOT NULL,
    CONSTRAINT fk_Sach_LoaiSach FOREIGN KEY (MaLoaiSach) REFERENCES LoaiSach(MaLoaiSach)
);

CREATE TABLE LoaiNguoiDung(
	MaLoaiNguoiDung VARCHAR(10)  NOT NULL PRIMARY KEY,
	TenLoaiNguoiDung NVARCHAR(150) NOT NULL
);
 go
 CREATE TABLE NguoiDung (
    MaNguoiDung VARCHAR(30)  NOT NULL PRIMARY KEY,
	MaLoaiNguoiDung  VARCHAR(10) NOT NULL,
    TenNguoiDung  NVARCHAR(150) NOT NULL,
    Email  VARCHAR(50) NOT NULL,
    SoDienThoai  VARCHAR(10) NOT NULL,
	MatKhau  VARCHAR(30) NOT NULL,
    CONSTRAINT fk_Nguoidung_LoaiNguoidung FOREIGN KEY (MaLoaiNguoiDung) REFERENCES LoaiNguoiDung(MaLoaiNguoiDung)

);

go
CREATE TABLE PhieuMuon (
    MaPhieuMuon INT IDENTITY(1,1)  NOT NULL PRIMARY KEY,
    NgayMuon DATE NOT NULL,
    NgayHenTra DATE NOT NULL,
    TongSoLuongSachMuon INT NULL,
    MaNguoiDung VARCHAR(30) NOT NULL,
    GhiChu NVARCHAR(250) NULL,
    CONSTRAINT fk_Phieumuon_Nguoidung FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung)
);
 go
CREATE TABLE ChiTietPhieuMuon (
    MaChiTietPhieuMuon INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
	MaPhieuMuon INT NOT NULL,
    MaSach INT NOT NULL,
    SoLuongSachMuonMoiLoai INT NOT NULL,
    CONSTRAINT fk_Chitietphieumuon_Phieumuon FOREIGN KEY (MaPhieuMuon) REFERENCES PhieuMuon(MaPhieuMuon),
    CONSTRAINT fk_Chitietphieumuon_Sach FOREIGN KEY (MaSach) REFERENCES Sach(MaSach)
);

CREATE TABLE PhieuTra (
    MaPhieuTra INT IDENTITY(1,1)  NOT NULL PRIMARY KEY,
	MaPhieuMuon INT NOT NULL,
    NgayTra DATE NULL,
    TrangThai bit NOT NULL,
    MaNguoiDung VARCHAR(30) NOT NULL,
    GhiChu NVARCHAR(250) NULL,
    CONSTRAINT fk_Phieutra_Nguoidung FOREIGN KEY (MaNguoiDung) REFERENCES NguoiDung(MaNguoiDung),
	CONSTRAINT FK_Phieutra_PhieuMuon FOREIGN KEY (MaPhieuMuon) REFERENCES PhieuMuon(MaPhieuMuon)
);
 go
-- Ràng buộc duy nhất 

alter table NguoiDung
add constraint uqEmail
unique (Email);
go
alter table NguoiDung
add constraint uqSDT
unique (SoDienThoai);
-- Ràng buộc kiểm tra
go
ALTER TABLE Sach
ADD CONSTRAINT CHK_sotrang 
CHECK (SoTrang > 5);
go
ALTER TABLE Sach
ADD CONSTRAINT CHK_SoLuongSach 
CHECK (SoLuongSach > 1);
go
ALTER TABLE Sach
ADD CONSTRAINT CHK_GiaTien
CHECK (GiaTien > 0);
go
ALTER TABLE PhieuMuon
ADD CONSTRAINT chk_NgayhenTra_NgayMuon
CHECK(NgayHenTra >= NgayMuon);
go

--insert data cho các table 

INSERT INTO LoaiSach (MaLoaiSach, TenLoaiSach)
VALUES
('LS001', N'Tiểu thuyết'),
('LS002', N'Khoa học'),
('LS003', N'Trinh thám'),
('LS004', N'Trẻ em'),
('LS005', N'Nghiên cứu'),
('LS006', N'Truyện tranh'),
('LS007', N'Nước ngoài'),
('LS008', N'Nghệ thuật'),
('LS009', N'Nấu ăn'),
('LS010', N'Ngoại ngữ');

INSERT INTO Sach (TieuDe, NhaXuatBan, TacGia, SoTrang, SoLuongSach, GiaTien, NgayNhapKho, ViTriDatSach, MaLoaiSach)
VALUES
(N'Harry Potter và hòa bình quán', N'NXB Hogwarts', N'J.K. Rowling', 420, 50, 25.99, '2023-11-07', N'Kệ A1', 'LS001'),
(N'Nguyên lý thiết kế hệ thống', N'NXB Khoa học', N'Donald D. Givone', 400, 30, 55.00, '2023-11-07', N'Kệ B2', 'LS002'),
(N'Bí mật trong căn phòng 13A', N'NXB Trinh thám', N'Dan Brown', 350, 20, 18.50, '2023-11-07', N'Kệ C3', 'LS003'),
(N'Chuyện kể bé con gà trống', N'NXB Trẻ em', N'Nguyễn Nhật Ánh', 60, 100, 7.99, '2023-11-07', N'Kệ D4', 'LS004'),
(N'Lập trình Python cho người mới bắt đầu', N'NXB Khoa học', N'John V. Guttag', 250, 40, 29.99, '2023-11-07', N'Kệ E5', 'LS005'),
(N'Doraemon tập 1', N'NXB Truyện tranh', N'Fujiko F. Fujio', 160, 80, 5.99, '2023-11-07', N'Kệ F6', 'LS006'),
(N'The Alchemist', N'HarperOne', N'Paulo Coelho', 190, 60, 15.00, '2023-11-07', N'Kệ G7', 'LS007'),
(N'The Starry Night', N'Art Publishing', N'Vincent van Gogh', 80, 10, 50.00, '2023-11-07', N'Kệ H8', 'LS008'),
(N'Mastering the Art of French Cooking', N'NXB Food', N'Julia Child', 724, 15, 40.00, '2023-11-07', N'Kệ I9', 'LS009'),
(N'English Grammar in Use', N'Cambridge University Press', N'Raymond Murphy', 380, 25, 20.00, '2023-11-07', N'Kệ J10', 'LS010');


INSERT INTO LoaiNguoiDung (MaLoaiNguoiDung, TenLoaiNguoiDung)
VALUES
('LND001', 'Admin'),
('LND002', 'Librarian'),
('LND003', 'User');


INSERT INTO NguoiDung (MaNguoiDung, MaLoaiNguoiDung, TenNguoiDung, Email, SoDienThoai, MatKhau)
VALUES
('AD001', 'LND001', N'Nguyễn Thành Thảo', 'bebun99111@gmail.com', '0336998554', 'admin'),
('LI001', 'LND002', N'HUỳnh Phương', 'admin1@gmail.com', '0898556447', '1234'),
('AD002', 'LND001', N'Trần Thị Minh Tâm', 'nd2@gmail.com', '0336552112', 'admin'),
('ND001', 'LND003', N'Nguyễn Đình Nhi', 'editor1@gmail.com', '0255665885', '1234'),
('ND002', 'LND003', N'Nguyễn Thị Chí Bình', 'nd3@gmail.com', '0945662589', '1234'),
('ND003', 'LND003', N'Võ Minh Thảo', 'nhanvien1@gmail.com', '0366588555', '1234'),
('LI002', 'LND002', N'Lê Thanh Tú', 'nd4@gmail.com', '0333221558', '1234'),
('ND004', 'LND003', N'Nguyễn Chí Bình', 'nd5@gmail.com', '0325669885', '1234'),
('ND005', 'LND003', N'HUỳnh Thị Ngọc Hoài Thương', 'admin2@gmail.com', '0455669885', '1234'),
('ND006', 'LND003', N'Lê Thanh Tâm', 'nd6@gmail.com', '0325668985', '1234');



INSERT INTO PhieuMuon (NgayMuon, NgayHenTra, TongSoLuongSachMuon, MaNguoiDung, GhiChu)
VALUES
('2022-10-07', '2022-10-10', 3, 'ND001', N'Mượn sách cho học tập'),
('2022-10-08', '2022-10-11', 2, 'ND002', N'Mượn sách cho giải trí'),
('2023-10-09', '2023-10-12', 4, 'ND006', NULL),
('2023-10-10', '2023-10-13', 1, 'ND005', NULL),
('2023-10-11', '2023-10-14', 5, 'ND004', N'Mượn sách cho biên tập'),
('2023-10-12', '2023-10-15', 2, 'ND002', N'Mượn sách cho kiểm tra'),
('2023-10-13', '2023-10-16', 3, 'ND002', NULL),
('2023-10-14', '2023-10-17', 2, 'ND005', N'Mượn sách cho việc công việc'),
('2023-10-15', '2023-10-18', 1, 'ND004', N'Mượn sách cho giải trí'),
('2023-10-16', '2023-10-19', 4, 'ND001', NULL),
('2023-10-17', '2023-10-20', 4, 'ND006', NULL);

INSERT INTO ChiTietPhieuMuon (MaPhieuMuon, MaSach, SoLuongSachMuonMoiLoai)
VALUES
(1, 1, 1),
(1, 3, 2),
(1, 5, 1),
(2, 2, 1),
(2, 7, 1),
(3, 4, 1),
(3, 6, 1),
(3, 8, 2),
(4, 9, 1),
(5, 10, 5),
(6, 2, 1),
(6, 3, 1),
(7, 1, 1),
(7, 4, 1),
(7, 6, 1),
(8, 5, 2),
(9, 7, 1),
(10, 10, 4);
select * from ChiTietPhieuMuon
INSERT INTO PhieuTra (MaPhieuMuon, NgayTra, TrangThai, MaNguoiDung, GhiChu)
VALUES
(1, '2022-11-10', 1, 'ND001', null),
(2, '2022-10-11', 1, 'ND002', null),
(3, null, 0, 'ND006', null),
(4, '2023-11-13', 1, 'ND005', null),
(5, null, 0, 'ND004', null),
(6, '2023-11-9', 1, 'ND002', NULL),
(7, null, 0, 'ND002', null),
(8, '2023-10-14', 1, 'ND005', null),
(9, null, 0, 'ND004', null),
(10, null, 0,'ND001', NULL),
(11, null, 0,'ND006', NULL)
GO 
-- select * from PhieuTra
-- select * from PhieuMuon
GO
-- sau khi thêm phiếu mượn
CREATE TRIGGER trg_SauKhiThemPhieuMuon
ON PhieuMuon
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON; -- không hiện thông báo số dòng bị thay đổi

    INSERT INTO PhieuTra (MaPhieuMuon, NgayTra, TrangThai, MaNguoiDung, GhiChu)
    SELECT
        inserted.MaPhieuMuon, NULL, 0, inserted.MaNguoiDung, NULL
    FROM inserted;
END;
go
-- sau khi sửa phiếu mượn thì nếu cột maNguoiDung bị update thì sẽ update luôn bên phiếu trả
CREATE TRIGGER trg_SauKhiCapNhatPhieuMuon
ON PhieuMuon
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF UPDATE(MaNguoiDung)
    BEGIN
        UPDATE pt
        SET pt.MaNguoiDung = i.MaNguoiDung
        FROM PhieuTra pt
        INNER JOIN inserted i ON pt.MaPhieuMuon = i.MaPhieuMuon
        INNER JOIN deleted d ON pt.MaPhieuMuon = d.MaPhieuMuon
        WHERE i.MaNguoiDung <> d.MaNguoiDung;
    END;
END;

go
-- INSTEAD sẽ sửa đổi thao tác delete theo các bước mà ta muốn
CREATE TRIGGER trg_KhiXoaPhieuMuon
ON PhieuMuon
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    -- Xóa phiếu trả của phiếu mượn bị xóa
    DELETE FROM PhieuTra
    WHERE MaPhieuMuon IN (SELECT MaPhieuMuon FROM deleted);

    -- Xóa chi tiết mượn của phiếu mượn bị xóa
    DELETE FROM ChiTietPhieuMuon
    WHERE MaPhieuMuon IN (SELECT MaPhieuMuon FROM deleted);

    -- Xóa phiếu mượn
    DELETE FROM PhieuMuon
    WHERE MaPhieuMuon IN (SELECT MaPhieuMuon FROM deleted);
END;
GO
-- STORE PROC
Create proc sp_muonTraTheoLoai(@Year int,@MonthStart int, @MonthEnd int, @TrangThai nvarchar(200), @MaDocGia varchar(10))
as begin
	SELECT
		pm.MaPhieuMuon,
		pm.MaNguoiDung,
		ngd.TenNguoiDung,
		pm.NgayMuon,
		pm.NgayHenTra,
		ptr.NgayTra,
		CASE
			WHEN DATEDIFF(DAY, NgayHenTra, NgayTra) > 0 THEN DATEDIFF(DAY, NgayHenTra, NgayTra)
			ELSE 0
		END AS SoNgayMuonQuaHan,
		CASE
			WHEN DATEDIFF(DAY, NgayHenTra, NgayTra) > 0 THEN DATEDIFF(DAY, NgayHenTra, NgayTra) * 1000
			ELSE 0
		END AS TienPhat,
		CASE
			WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) > 0 
			THEN N'Trả Quá hạn'
			WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) = 0
			THEN N'Đến hạn mà chưa trả sách'
			WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) > 0
			THEN N'Quá hạn nhưng Chưa trả sách'
			WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) < 0
			THEN N'Chưa đến hạn trả sách'
			ELSE N'Trả Đúng hạn'
		END AS TinhTrangTraSach
	FROM
		PhieuTra ptr
		INNER JOIN PhieuMuon pm ON pm.MaPhieuMuon = ptr.MaPhieuMuon
		INNER JOIN NguoiDung ngd ON ptr.MaNguoiDung = ngd.MaNguoiDung
	WHERE
		Year(pm.NgayMuon) = @Year
		AND (MONTH(pm.NgayMuon) BETWEEN @MonthStart AND @MonthEnd)
		AND CASE
				WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) > 0 
				THEN N'Trả Quá hạn'
				WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) = 0
				THEN N'Đến hạn mà chưa trả sách'
				WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) > 0
				THEN N'Quá hạn nhưng Chưa trả sách'
				WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) < 0
				THEN N'Chưa đến hạn trả sách'
				ELSE N'Trả Đúng hạn'
		    END like @TrangThai
		AND pm.MaNguoiDung like @MaDocGia
end
-- drop proc sp_muonTraTheoLoai;
-- tất cả trong năm đó theo tháng
-- exec sp_muonTraTheoLoai 2023, 10, 12, N'%%', 'ND001'; 
-- exec sp_muonTraTheoLoai 2023, 1, 12, N'%%', N'%%';
GO
-- Tất cả các năm
Create proc sp_muonTraALLYears
as begin
	SELECT
		pm.MaPhieuMuon,
		ngd.MaNguoiDung,
		ngd.TenNguoiDung,
		pm.NgayMuon,
		pm.NgayHenTra,
		ptr.NgayTra,
		CASE
			WHEN DATEDIFF(DAY, NgayHenTra, NgayTra) > 0 THEN DATEDIFF(DAY, NgayHenTra, NgayTra)
			ELSE 0
		END AS SoNgayMuonQuaHan,
		CASE
			WHEN DATEDIFF(DAY, NgayHenTra, NgayTra) > 0 THEN DATEDIFF(DAY, NgayHenTra, NgayTra) * 1000
			ELSE 0
		END AS TienPhat,
		
		CASE
			WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) > 0 THEN N'Trả Quá hạn'
			WHEN DATEDIFF(DAY, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) = 0
			THEN N'Đến hạn mà chưa trả sách'
			WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) > 0
			THEN N'Quá hạn nhưng Chưa trả sách'
			WHEN DATEDIFF(day, pm.NgayHenTra, ptr.NgayTra) is null and DateDiff(Day, pm.NgayHenTra, GETDATE()) < 0
			THEN N'Chưa đến hạn trả sách'
			ELSE N'Trả Đúng hạn'
		END AS TinhTrangTraSach
	FROM
		PhieuTra ptr
		INNER JOIN PhieuMuon pm ON pm.MaPhieuMuon = ptr.MaPhieuMuon
		INNER JOIN NguoiDung ngd ON ptr.MaNguoiDung = ngd.MaNguoiDung
end
--drop proc sp_muonTraALLYears;
-- exec sp_muonTraALLYears

-- select Distinct(Year(NgayMuon)) as Nam from PhieuMuon ORDER BY Nam DESC

--select * from PhieuMuon where MaNguoiDung = 'ND001' AND YEAR(NgayMuon) = 2023
-- select * from PhieuMuon where MaNguoiDung = 'ND001' AND YEAR(NgayMuon) = 2023 AND MONTH(NgayMuon) = 1


-- số lượt mượn sách tất cả các năm
go
create proc sp_soLuotMuonSachTongCacNam
as begin
	select MaSach, TieuDe, SUM(SoLuotMuon) as SLSM from 
		(select ctpm.MaSach, s.TieuDe,pm.NgayMuon, SUM(ctpm.SoLuongSachMuonMoiLoai) as SoLuotMuon 
		from ChiTietPhieuMuon ctpm 
		inner join Sach s on ctpm.MaSach = s.MaSach
		inner join PhieuMuon pm on ctpm.MaPhieuMuon = pm.MaPhieuMuon
		group by ctpm.MaSach, s.TieuDe, pm.NgayMuon) as tbl
	group by MaSach, TieuDe
end
exec sp_soLuotMuonSachTongCacNam

-- số lượt mượn sách của năm
--drop proc sp_soLuotMuonSachTheoNam;
go
create proc sp_soLuotMuonSachTrongNam(@year int)
as begin
	select MaSach, TieuDe, SUM(SoLuotMuon) as SLSM from 
		(select ctpm.MaSach, s.TieuDe,pm.NgayMuon, SUM(ctpm.SoLuongSachMuonMoiLoai) as SoLuotMuon 
		from ChiTietPhieuMuon ctpm 
		inner join Sach s on ctpm.MaSach = s.MaSach
		inner join PhieuMuon pm on ctpm.MaPhieuMuon = pm.MaPhieuMuon
		where Year(pm.NgayMuon) = @year
		group by ctpm.MaSach, s.TieuDe, pm.NgayMuon) as tbl
	group by MaSach, TieuDe
end
--exec sp_soLuotMuonSachTrongNam 2022
-- số lượt mượn sách theo tháng của năm
go
create proc sp_soLuotMuonSachTheoNamThang(@year int, @month int)
as begin
	select MaSach, TieuDe, SUM(SoLuotMuon) as SLSM from 
		(select ctpm.MaSach, s.TieuDe,pm.NgayMuon, SUM(ctpm.SoLuongSachMuonMoiLoai) as SoLuotMuon 
		from ChiTietPhieuMuon ctpm 
		inner join Sach s on ctpm.MaSach = s.MaSach
		inner join PhieuMuon pm on ctpm.MaPhieuMuon = pm.MaPhieuMuon
		where Year(pm.NgayMuon) = @year and MONTH(pm.NgayMuon) = @month
		group by ctpm.MaSach, s.TieuDe, pm.NgayMuon) as tbl
	group by MaSach, TieuDe
end
-- exec sp_soLuotMuonSachTheoNamThang 2022, 10
-- select * from ChiTietPhieuMuon
-- select * from Sach
-- số lượt mượn sách theo ngày trong tháng của năm
go
create proc sp_soLuotMuonSachTheoNamThangNgay(@year int, @month int, @day int)
as begin
	select MaSach, TieuDe, SUM(SoLuotMuon) as SLSM from 
		(select ctpm.MaSach, s.TieuDe,pm.NgayMuon, SUM(ctpm.SoLuongSachMuonMoiLoai) as SoLuotMuon 
		from ChiTietPhieuMuon ctpm 
		inner join Sach s on ctpm.MaSach = s.MaSach
		inner join PhieuMuon pm on ctpm.MaPhieuMuon = pm.MaPhieuMuon
		where Year(pm.NgayMuon) = @year and MONTH(pm.NgayMuon) = @month and DAY(pm.NgayMuon) = @day
		group by ctpm.MaSach, s.TieuDe, pm.NgayMuon) as tbl
	group by MaSach, TieuDe
end

