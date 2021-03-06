USE [master]
GO

/****** Object:  Database [GrinHouseDW]    Script Date: 27-04-2021 21:54:48 ******/
CREATE DATABASE [GrinHouseDW]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'GrinHouseDW', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\GrinHouseDW.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'GrinHouseDW_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\GrinHouseDW_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [GrinHouseDW].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [GrinHouseDW] SET ANSI_NULL_DEFAULT OFF 
GO

ALTER DATABASE [GrinHouseDW] SET ANSI_NULLS OFF 
GO

ALTER DATABASE [GrinHouseDW] SET ANSI_PADDING OFF 
GO

ALTER DATABASE [GrinHouseDW] SET ANSI_WARNINGS OFF 
GO

ALTER DATABASE [GrinHouseDW] SET ARITHABORT OFF 
GO

ALTER DATABASE [GrinHouseDW] SET AUTO_CLOSE OFF 
GO

ALTER DATABASE [GrinHouseDW] SET AUTO_SHRINK OFF 
GO

ALTER DATABASE [GrinHouseDW] SET AUTO_UPDATE_STATISTICS ON 
GO

ALTER DATABASE [GrinHouseDW] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO

ALTER DATABASE [GrinHouseDW] SET CURSOR_DEFAULT  GLOBAL 
GO

ALTER DATABASE [GrinHouseDW] SET CONCAT_NULL_YIELDS_NULL OFF 
GO

ALTER DATABASE [GrinHouseDW] SET NUMERIC_ROUNDABORT OFF 
GO

ALTER DATABASE [GrinHouseDW] SET QUOTED_IDENTIFIER OFF 
GO

ALTER DATABASE [GrinHouseDW] SET RECURSIVE_TRIGGERS OFF 
GO

ALTER DATABASE [GrinHouseDW] SET  DISABLE_BROKER 
GO

ALTER DATABASE [GrinHouseDW] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO

ALTER DATABASE [GrinHouseDW] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO

ALTER DATABASE [GrinHouseDW] SET TRUSTWORTHY OFF 
GO

ALTER DATABASE [GrinHouseDW] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO

ALTER DATABASE [GrinHouseDW] SET PARAMETERIZATION SIMPLE 
GO

ALTER DATABASE [GrinHouseDW] SET READ_COMMITTED_SNAPSHOT OFF 
GO

ALTER DATABASE [GrinHouseDW] SET HONOR_BROKER_PRIORITY OFF 
GO

ALTER DATABASE [GrinHouseDW] SET RECOVERY FULL 
GO

ALTER DATABASE [GrinHouseDW] SET  MULTI_USER 
GO

ALTER DATABASE [GrinHouseDW] SET PAGE_VERIFY CHECKSUM  
GO

ALTER DATABASE [GrinHouseDW] SET DB_CHAINING OFF 
GO

ALTER DATABASE [GrinHouseDW] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO

ALTER DATABASE [GrinHouseDW] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO

ALTER DATABASE [GrinHouseDW] SET DELAYED_DURABILITY = DISABLED 
GO

ALTER DATABASE [GrinHouseDW] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO

ALTER DATABASE [GrinHouseDW] SET QUERY_STORE = OFF
GO

ALTER DATABASE [GrinHouseDW] SET  READ_WRITE 
GO


USE [GrinHouseDW]
GO

/****** Object:  Schema [stage]    Script Date: 27-04-2021 21:56:02 ******/
CREATE SCHEMA [stage]
GO

/****** Object:  Schema [edw]    Script Date: 27-04-2021 21:56:02 ******/
CREATE SCHEMA [edw]
GO
