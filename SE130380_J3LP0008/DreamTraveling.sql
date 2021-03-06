USE [master]
GO
/****** Object:  Database [DreamTraveling]    Script Date: 1/9/2021 11:12:33 AM ******/
CREATE DATABASE [DreamTraveling]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DreamTravelling', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\DreamTravelling.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'DreamTravelling_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\DreamTravelling_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [DreamTraveling] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DreamTraveling].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DreamTraveling] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DreamTraveling] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DreamTraveling] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DreamTraveling] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DreamTraveling] SET ARITHABORT OFF 
GO
ALTER DATABASE [DreamTraveling] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DreamTraveling] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DreamTraveling] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DreamTraveling] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DreamTraveling] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DreamTraveling] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DreamTraveling] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DreamTraveling] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DreamTraveling] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DreamTraveling] SET  DISABLE_BROKER 
GO
ALTER DATABASE [DreamTraveling] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DreamTraveling] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DreamTraveling] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DreamTraveling] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DreamTraveling] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DreamTraveling] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DreamTraveling] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DreamTraveling] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DreamTraveling] SET  MULTI_USER 
GO
ALTER DATABASE [DreamTraveling] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DreamTraveling] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DreamTraveling] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DreamTraveling] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [DreamTraveling] SET DELAYED_DURABILITY = DISABLED 
GO
USE [DreamTraveling]
GO
/****** Object:  Table [dbo].[BookingDetails]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[BookingDetails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[BookingId] [varchar](255) NULL,
	[TourId] [int] NULL,
	[Amount] [int] NULL,
	[Price] [int] NULL,
 CONSTRAINT [PK_BookingDetails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Bookings]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Bookings](
	[Id] [varchar](255) NOT NULL,
	[UserBook] [varchar](50) NOT NULL,
	[TotalPrice] [int] NOT NULL,
	[BookingDate] [datetime] NOT NULL,
	[DiscountCode] [varchar](50) NULL,
 CONSTRAINT [PK_Bookings] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Discounts]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Discounts](
	[Code] [varchar](50) NOT NULL,
	[Description] [varchar](255) NULL,
	[DiscountPercent] [int] NULL,
	[ApplyDate] [datetime] NULL,
	[ExpiredDate] [datetime] NULL,
 CONSTRAINT [PK_Discounts] PRIMARY KEY CLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [IX_Discounts] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Places]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Places](
	[Name] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Places] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Roles]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Roles](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Status]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Status](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Tours]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Tours](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NULL,
	[FromDate] [datetime] NULL,
	[ToDate] [datetime] NULL,
	[Price] [int] NULL,
	[Quota] [int] NULL,
	[Image] [varchar](max) NULL,
	[Departure] [nvarchar](50) NULL,
	[Destination] [nvarchar](50) NULL,
	[DateImport] [datetime] NULL,
	[Status] [varchar](50) NULL,
 CONSTRAINT [PK_Tours] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Users]    Script Date: 1/9/2021 11:12:33 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [varchar](50) NOT NULL,
	[Password] [varchar](255) NULL,
	[Firstname] [varchar](50) NULL,
	[Lastname] [varchar](50) NULL,
	[Role] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[FacebookId] [varchar](50) NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[BookingDetails]  WITH CHECK ADD  CONSTRAINT [FK_BookingDetails_Bookings] FOREIGN KEY([BookingId])
REFERENCES [dbo].[Bookings] ([Id])
GO
ALTER TABLE [dbo].[BookingDetails] CHECK CONSTRAINT [FK_BookingDetails_Bookings]
GO
ALTER TABLE [dbo].[BookingDetails]  WITH CHECK ADD  CONSTRAINT [FK_BookingDetails_Tours] FOREIGN KEY([TourId])
REFERENCES [dbo].[Tours] ([Id])
GO
ALTER TABLE [dbo].[BookingDetails] CHECK CONSTRAINT [FK_BookingDetails_Tours]
GO
ALTER TABLE [dbo].[Bookings]  WITH CHECK ADD  CONSTRAINT [FK_Bookings_Discounts] FOREIGN KEY([DiscountCode])
REFERENCES [dbo].[Discounts] ([Code])
GO
ALTER TABLE [dbo].[Bookings] CHECK CONSTRAINT [FK_Bookings_Discounts]
GO
ALTER TABLE [dbo].[Bookings]  WITH CHECK ADD  CONSTRAINT [FK_Bookings_Users] FOREIGN KEY([UserBook])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Bookings] CHECK CONSTRAINT [FK_Bookings_Users]
GO
ALTER TABLE [dbo].[Tours]  WITH CHECK ADD  CONSTRAINT [FK_Tours_Places] FOREIGN KEY([Departure])
REFERENCES [dbo].[Places] ([Name])
GO
ALTER TABLE [dbo].[Tours] CHECK CONSTRAINT [FK_Tours_Places]
GO
ALTER TABLE [dbo].[Tours]  WITH CHECK ADD  CONSTRAINT [FK_Tours_Places1] FOREIGN KEY([Destination])
REFERENCES [dbo].[Places] ([Name])
GO
ALTER TABLE [dbo].[Tours] CHECK CONSTRAINT [FK_Tours_Places1]
GO
ALTER TABLE [dbo].[Tours]  WITH CHECK ADD  CONSTRAINT [FK_Tours_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Status] ([Name])
GO
ALTER TABLE [dbo].[Tours] CHECK CONSTRAINT [FK_Tours_Status]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([Role])
REFERENCES [dbo].[Roles] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Roles]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Status] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Status]
GO
USE [master]
GO
ALTER DATABASE [DreamTraveling] SET  READ_WRITE 
GO
