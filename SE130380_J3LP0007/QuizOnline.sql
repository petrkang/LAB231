USE [master]
GO
/****** Object:  Database [QuizOnline]    Script Date: 1/9/2021 11:12:59 AM ******/
CREATE DATABASE [QuizOnline]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuizOnline', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\QuizOnline.mdf' , SIZE = 4096KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'QuizOnline_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\QuizOnline_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [QuizOnline] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuizOnline].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuizOnline] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuizOnline] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuizOnline] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuizOnline] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuizOnline] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuizOnline] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QuizOnline] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuizOnline] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuizOnline] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuizOnline] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuizOnline] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuizOnline] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuizOnline] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuizOnline] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuizOnline] SET  DISABLE_BROKER 
GO
ALTER DATABASE [QuizOnline] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuizOnline] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuizOnline] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuizOnline] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuizOnline] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuizOnline] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuizOnline] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuizOnline] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [QuizOnline] SET  MULTI_USER 
GO
ALTER DATABASE [QuizOnline] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuizOnline] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuizOnline] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuizOnline] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [QuizOnline] SET DELAYED_DURABILITY = DISABLED 
GO
USE [QuizOnline]
GO
/****** Object:  Table [dbo].[Answers]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Answers](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Cont] [nvarchar](255) NULL,
	[QuestionId] [int] NULL,
	[Correctly] [bit] NULL,
 CONSTRAINT [PK_Answer] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Histories]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Histories](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[QuestionId] [int] NOT NULL,
	[AnswerId] [int] NOT NULL,
	[SubmitId] [int] NULL,
 CONSTRAINT [PK_Histories] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Questions]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Questions](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Cont] [nvarchar](255) NOT NULL,
	[Status] [varchar](50) NOT NULL,
	[Subject] [varchar](50) NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[UpdateDate] [datetime] NULL,
	[UserUpdate] [varchar](50) NULL,
 CONSTRAINT [PK_Question] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Roles](
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Role] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Status]    Script Date: 1/9/2021 11:12:59 AM ******/
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
/****** Object:  Table [dbo].[Subjects]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Subjects](
	[Id] [varchar](50) NOT NULL,
	[Name] [varchar](50) NULL,
	[QuestionQuantity] [int] NULL,
	[TestTime] [int] NULL,
 CONSTRAINT [PK_Subject] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Submits]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Submits](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserEmail] [varchar](50) NULL,
	[Point] [float] NULL,
	[SubjectId] [varchar](50) NULL,
	[StartTime] [datetime] NULL,
	[EndTime] [datetime] NULL,
 CONSTRAINT [PK_Submits] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Users]    Script Date: 1/9/2021 11:12:59 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Users](
	[Email] [varchar](50) NOT NULL,
	[FirstName] [nvarchar](50) NOT NULL,
	[LastName] [nvarchar](50) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[Role] [varchar](50) NOT NULL,
	[Status] [varchar](50) NOT NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Index [IX_Histories_Answers]    Script Date: 1/9/2021 11:12:59 AM ******/
CREATE NONCLUSTERED INDEX [IX_Histories_Answers] ON [dbo].[Histories]
(
	[AnswerId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Histories_Questions]    Script Date: 1/9/2021 11:12:59 AM ******/
CREATE NONCLUSTERED INDEX [IX_Histories_Questions] ON [dbo].[Histories]
(
	[QuestionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_Histories_Submits]    Script Date: 1/9/2021 11:12:59 AM ******/
CREATE NONCLUSTERED INDEX [IX_Histories_Submits] ON [dbo].[Histories]
(
	[SubmitId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Answers]  WITH CHECK ADD  CONSTRAINT [FK_Answer_Question] FOREIGN KEY([QuestionId])
REFERENCES [dbo].[Questions] ([Id])
GO
ALTER TABLE [dbo].[Answers] CHECK CONSTRAINT [FK_Answer_Question]
GO
ALTER TABLE [dbo].[Histories]  WITH CHECK ADD  CONSTRAINT [FK_Histories_Submits] FOREIGN KEY([SubmitId])
REFERENCES [dbo].[Submits] ([Id])
GO
ALTER TABLE [dbo].[Histories] CHECK CONSTRAINT [FK_Histories_Submits]
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD  CONSTRAINT [FK_Question_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Status] ([Name])
GO
ALTER TABLE [dbo].[Questions] CHECK CONSTRAINT [FK_Question_Status]
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD  CONSTRAINT [FK_Question_Subject] FOREIGN KEY([Subject])
REFERENCES [dbo].[Subjects] ([Id])
GO
ALTER TABLE [dbo].[Questions] CHECK CONSTRAINT [FK_Question_Subject]
GO
ALTER TABLE [dbo].[Questions]  WITH CHECK ADD  CONSTRAINT [FK_Questions_Users] FOREIGN KEY([UserUpdate])
REFERENCES [dbo].[Users] ([Email])
GO
ALTER TABLE [dbo].[Questions] CHECK CONSTRAINT [FK_Questions_Users]
GO
ALTER TABLE [dbo].[Submits]  WITH CHECK ADD  CONSTRAINT [FK_Submits_Subjects] FOREIGN KEY([SubjectId])
REFERENCES [dbo].[Subjects] ([Id])
GO
ALTER TABLE [dbo].[Submits] CHECK CONSTRAINT [FK_Submits_Subjects]
GO
ALTER TABLE [dbo].[Submits]  WITH CHECK ADD  CONSTRAINT [FK_Submits_Users] FOREIGN KEY([UserEmail])
REFERENCES [dbo].[Users] ([Email])
GO
ALTER TABLE [dbo].[Submits] CHECK CONSTRAINT [FK_Submits_Users]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_User_Role] FOREIGN KEY([Role])
REFERENCES [dbo].[Roles] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_User_Role]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_User_Status] FOREIGN KEY([Status])
REFERENCES [dbo].[Status] ([Name])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_User_Status]
GO
USE [master]
GO
ALTER DATABASE [QuizOnline] SET  READ_WRITE 
GO
