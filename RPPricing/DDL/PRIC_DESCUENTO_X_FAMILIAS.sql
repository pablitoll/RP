USE [RPAPER]
GO

/****** Object:  Table [dbo].[PRIC_DESCUENTO_X_FAMILIAS]    Script Date: 24/5/2018 08:55:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE [dbo].[PRIC_DESCUENTO_X_FAMILIAS]
GO

CREATE TABLE [dbo].[PRIC_DESCUENTO_X_FAMILIAS](
	[PRIC_FAMILIA_ID] [int] NOT NULL identity(1,1) ,
	[PRIC_FAMILIA_CLIENTE] [int] NOT NULL,
	[PRIC_FAMILIA_LISTA_PRECVTA] [int] NOT NULL,
	[PRIC_CA01_CLASIF_1] [varchar](10) NOT NULL,
	[PRIC_FAMILIA_DESCUENTO_1] [decimal](14, 4) NOT NULL,
	[PRIC_FAMILIA_DESCUENTO_2] [decimal](14, 4),
	[PRIC_FAMILIA_FECHA_DESDE] [datetime] NOT NULL,
	[PRIC_FAMILIA_FECHA_HASTA] [datetime] NOT NULL,
	[PRIC_FAMILIA_COMISION] [decimal](14, 4),
	[PRIC_REFERENCIA] [varchar](100) NULL,
 CONSTRAINT [PRIC_PK2] PRIMARY KEY CLUSTERED 
(
	[PRIC_FAMILIA_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


