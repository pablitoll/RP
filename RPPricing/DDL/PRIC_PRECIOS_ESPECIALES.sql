USE [RPAPER]
GO

/****** Object:  Table [dbo].[PRIC_PRECIOS_ESPECIALES]    Script Date: 28/5/2018 15:58:22 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE [dbo].[PRIC_PRECIOS_ESPECIALES] 
GO

CREATE TABLE [dbo].[PRIC_PRECIOS_ESPECIALES](
	[PRIC_PRECIOS_ESPECIALES_ID] [int] NOT NULL identity(1,1) ,
	[PRIC_CLIENTE] [int] NOT NULL,
	[PRIC_LISTA_PRECVTA] [int] NOT NULL,
	[PRIC_ARTICULO] [int] NOT NULL,
	[PRIC_DESCUENTO_1] [decimal](14, 2) NOT NULL,
	[PRIC_DESCUENTO_2] [decimal](14, 2) NOT NULL,
	[PRIC_MONEDA] [varchar](3) NOT NULL,
	[PRIC_PRECIO] [decimal](14, 2) NOT NULL,
	[PRIC_FECHA_DESDE] [datetime] NOT NULL,
	[PRIC_FECHA_HASTA] [datetime] NOT NULL,
	[PRIC_REFERENCIA] [varchar](100) NULL,
 CONSTRAINT [PRIC_PK1] PRIMARY KEY CLUSTERED 
(
	[PRIC_PRECIOS_ESPECIALES_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


