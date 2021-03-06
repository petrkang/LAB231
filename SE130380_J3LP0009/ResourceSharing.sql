USE [master]
GO
/****** Object:  Database [ResourceSharing]    Script Date: 1/9/2021 11:13:27 AM ******/
CREATE DATABASE [ResourceSharing]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ResourceSharing', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\ResourceSharing.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ResourceSharing_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\ResourceSharing_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ResourceSharing] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ResourceSharing].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ResourceSharing] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ResourceSharing] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ResourceSharing] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ResourceSharing] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ResourceSharing] SET ARITHABORT OFF 
GO
ALTER DATABASE [ResourceSharing] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ResourceSharing] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ResourceSharing] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ResourceSharing] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ResourceSharing] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ResourceSharing] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ResourceSharing] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ResourceSharing] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ResourceSharing] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ResourceSharing] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ResourceSharing] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ResourceSharing] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ResourceSharing] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ResourceSharing] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ResourceSharing] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ResourceSharing] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ResourceSharing] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ResourceSharing] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ResourceSharing] SET  MULTI_USER 
GO
ALTER DATABASE [ResourceSharing] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ResourceSharing] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ResourceSharing] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ResourceSharing] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [ResourceSharing] SET DELAYED_DURABILITY = DISABLED 
GO
USE [ResourceSharing]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Categories](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Request_Status]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Request_Status](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Request_Status] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[RequestDetails]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[RequestDetails](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[RequestId] [varchar](255) NULL,
	[ResourceId] [int] NULL,
	[Amount] [int] NULL,
 CONSTRAINT [PK_RequestDetails] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Requests]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Requests](
	[Id] [varchar](255) NOT NULL,
	[UserRequest] [varchar](50) NULL,
	[RequestDate] [datetime] NULL,
	[Status] [varchar](50) NULL,
 CONSTRAINT [PK_Requests] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Resource_Status]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Resource_Status](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Resource_Status] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Resources]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Resources](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Quota] [int] NOT NULL,
	[Status] [varchar](50) NOT NULL,
	[UsingDate] [date] NOT NULL,
	[Category] [int] NOT NULL,
 CONSTRAINT [PK_Resources] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 1/9/2021 11:13:27 AM ******/
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
/****** Object:  Table [dbo].[User_Status]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User_Status](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_User_Status] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Users]    Script Date: 1/9/2021 11:13:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[Email] [varchar](50) NOT NULL,
	[Firstname] [varchar](50) NOT NULL,
	[Lastname] [varchar](50) NOT NULL,
	[Password] [varchar](50) NOT NULL,
	[Role] [varchar](50) NOT NULL,
	[Status] [varchar](50) NOT NULL,
	[Code] [varchar](50) NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[RequestDetails]  WITH CHECK ADD  CONSTRAINT [FK_RequestDetails_Requests] FOREIGN KEY([RequestId])
REFERENCES [dbo].[Requests] ([Id])
GO
ALTER TABLE [dbo].[RequestDetails] CHECK CONSTRAINT [FK_RequestDetails_Requests]
GO
ALTER TABLE [dbo].[RequestDetails]  WITH CHECK ADD  CONSTRAINT [FK_RequestDetails_Resources] FOREIGN KEY([ResourceId])
REFERENCES [dbo].[Resources] ([Id])
GO
ALTER TABLE [dbo].[RequestDetails] CHECK CONSTRAINT [FK_RequestDetails_Resources]
GO
ALTER TABLE [dbo].[Requests]  WITH CHECK ADD  CONSTRAINT [FK_Requests_Request_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Request_Status] ([Name])
GO
ALTER TABLE [dbo].[Requests] CHECK CONSTRAINT [FK_Requests_Request_Status]
GO
ALTER TABLE [dbo].[Requests]  WITH CHECK ADD  CONSTRAINT [FK_Requests_Users] FOREIGN KEY([UserRequest])
REFERENCES [dbo].[Users] ([Email])
GO
ALTER TABLE [dbo].[Requests] CHECK CONSTRAINT [FK_Requests_Users]
GO
ALTER TABLE [dbo].[Resources]  WITH CHECK ADD  CONSTRAINT [FK_Resources_Categories] FOREIGN KEY([Category])
REFERENCES [dbo].[Categories] ([Id])
GO
ALTER TABLE [dbo].[Resources] CHECK CONSTRAINT [FK_Resources_Categories]
GO
ALTER TABLE [dbo].[Resources]  WITH CHECK ADD  CONSTRAINT [FK_Resources_Resource_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Resource_Status] ([Name])
GO
ALTER TABLE [dbo].[Resources] CHECK CONSTRAINT [FK_Resources_Resource_Status]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([Role])
REFERENCES [dbo].[Roles] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Roles]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_User_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[User_Status] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_User_Status]
GO
USE [master]
GO
ALTER DATABASE [ResourceSharing] SET  READ_WRITE 
GO
