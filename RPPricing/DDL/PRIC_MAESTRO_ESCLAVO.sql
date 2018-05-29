USE [RPAPER]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO					

DROP TABLE [dbo].[PRIC_MAESTRO_ESCLAVO]
GO

CREATE TABLE [dbo].[PRIC_MAESTRO_ESCLAVO](
	[PRIC_MAESTRO_ESCLAVO_ID] [int] NOT NULL identity(1,1) ,
	[PRIC_MAESTRO_CLIENTE] [int] NOT NULL,
	[PRIC_M_E_LISTA_PRECVTA] [int] NOT NULL,
	[PRIC_ESCLAVO_CLIENTE]  [int] NOT NULL
 CONSTRAINT [PRIC_PK3] PRIMARY KEY CLUSTERED 
(
	[PRIC_MAESTRO_ESCLAVO_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]  
GO