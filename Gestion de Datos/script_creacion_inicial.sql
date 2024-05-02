USE [GD2C2023]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[pago_alquiler]') AND type in (N'U'))
DROP TABLE [SQLITO].[pago_alquiler]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[detalle_importe_alquiler]') AND type in (N'U'))
DROP TABLE [SQLITO].[detalle_importe_alquiler]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[alquiler]') AND type in (N'U'))
DROP TABLE [SQLITO].[alquiler]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[estado_alquiler]') AND type in (N'U'))
DROP TABLE [SQLITO].[estado_alquiler]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[inquilino]') AND type in (N'U'))
DROP TABLE [SQLITO].[inquilino]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[pago_compra_inmueble]') AND type in (N'U'))
DROP TABLE [SQLITO].[pago_compra_inmueble]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[medio_pago]') AND type in (N'U'))
DROP TABLE [SQLITO].[medio_pago]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[venta]') AND type in (N'U'))
DROP TABLE [SQLITO].[venta]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[comprador]') AND type in (N'U'))
DROP TABLE [SQLITO].[comprador]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[anuncio]') AND type in (N'U'))
DROP TABLE [SQLITO].[anuncio]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[agente_inmobiliario]') AND type in (N'U'))
DROP TABLE [SQLITO].[agente_inmobiliario]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[agencia]') AND type in (N'U'))
DROP TABLE [SQLITO].[agencia]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[moneda]') AND type in (N'U'))
DROP TABLE [SQLITO].[moneda]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[tipo_operacion]') AND type in (N'U'))
DROP TABLE [SQLITO].[tipo_operacion]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[tipo_periodo]') AND type in (N'U'))
DROP TABLE [SQLITO].[tipo_periodo]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[estado_anuncio]') AND type in (N'U'))
DROP TABLE [SQLITO].[estado_anuncio]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[caracteristica_X_inmueble]') AND type in (N'U'))
DROP TABLE [SQLITO].[caracteristica_X_inmueble]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[inmueble]') AND type in (N'U'))
DROP TABLE [SQLITO].[inmueble]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[propietario]') AND type in (N'U'))
DROP TABLE [SQLITO].[propietario]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[caracteristica]') AND type in (N'U'))
DROP TABLE [SQLITO].[caracteristica]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[barrio]') AND type in (N'U'))
DROP TABLE [SQLITO].[barrio]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[localidad]') AND type in (N'U'))
DROP TABLE [SQLITO].[localidad]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[provincia]') AND type in (N'U'))
DROP TABLE [SQLITO].[provincia]
GO


IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[estado_inmueble]') AND type in (N'U'))
DROP TABLE [SQLITO].[estado_inmueble]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[orientacion]') AND type in (N'U'))
DROP TABLE [SQLITO].[orientacion]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[disposicion]') AND type in (N'U'))
DROP TABLE [SQLITO].[disposicion]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[tipo_ambiente]') AND type in (N'U'))
DROP TABLE [SQLITO].[tipo_ambiente]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[SQLITO].[tipo_inmueble]') AND type in (N'U'))
DROP TABLE [SQLITO].[tipo_inmueble]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND object_id = OBJECT_ID(N'[SQLITO].[MIGRACION]'))
DROP PROCEDURE [SQLITO].[MIGRACION]
GO

IF EXISTS (SELECT * FROM sys.schemas WHERE name = 'SQLITO')
DROP SCHEMA [SQLITO]
GO

IF NOT EXISTS (SELECT schema_name FROM information_schema.schemata WHERE schema_name = 'SQLITO')
BEGIN
    EXEC('CREATE SCHEMA SQLITO')
END
GO

/***************************************************************CREACION DE TABLAS****************************************************************************************/

CREATE PROCEDURE [SQLITO].[MIGRACION] AS
BEGIN

BEGIN TRANSACTION

CREATE TABLE [SQLITO].[tipo_inmueble](
	[tipo_inmueble_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[tipo_ambiente](
	[tipo_ambiente_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[disposicion](
	[disposicion_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[orientacion](
	[orientacion_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[estado_inmueble](
	[estado_inmueble_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[provincia](
	[provincia_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[localidad](
	[localidad_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[provincia] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[provincia] ([provincia_id]) ,
	[nombre] NVARCHAR(100) 
)

CREATE TABLE [SQLITO].[barrio](
	[barrio_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[localidad] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[localidad] ([localidad_id]) ,
	[nombre] VARCHAR(100) 
)

CREATE TABLE [SQLITO].[caracteristica](
	[caracteristica_id] NUMERIC(18,0) IDENTITY NOT NULL PRIMARY KEY,
	[nombre] VARCHAR(100) 
)

CREATE TABLE [SQLITO].[propietario](
	[propietario_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] VARCHAR(100),
	[apellido] VARCHAR(100),
	[dni] NUMERIC(18,0),
	[fecha_registro] DATETIME,
	[telefono] NUMERIC(18,0),
	[mail] VARCHAR(100),
	[fecha_nacimiento] DATETIME
)

CREATE TABLE [SQLITO].[inmueble] (
	[inmueble_id]  NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[codigo_inmueble] NUMERIC(18,0),
	[tipo_inmueble] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[tipo_inmueble] ([tipo_inmueble_id]) ,
	[descripcion] NVARCHAR(100),
	[direccion] NVARCHAR(100),
	[tipo_ambiente] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[tipo_ambiente] ([tipo_ambiente_id]) ,
	[barrio] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[barrio] ([barrio_id]) ,
	[supericie_total] NUMERIC(18,2),
	[disposicion] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[disposicion] ([disposicion_id]) ,
	[orientacion] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[orientacion] ([orientacion_id]) ,
	[estado_inmueble] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[estado_inmueble] ([estado_inmueble_id]) ,
	[antiguedad] NUMERIC(18,0),
	[expensas] NUMERIC(18,2),
	[propietario] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[propietario] ([propietario_id]) 
)

CREATE TABLE [SQLITO].[caracteristica_X_inmueble](
	[caracteristica] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[caracteristica] ([caracteristica_id]) ,
	[inmueble] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[inmueble] ([inmueble_id]) ,
	PRIMARY KEY ([inmueble], [caracteristica]),
)

CREATE TABLE [SQLITO].[estado_anuncio](
	[estado_anuncio_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100)
)

CREATE TABLE [SQLITO].[tipo_periodo](
	[tipo_periodo_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100)
)

CREATE TABLE [SQLITO].[tipo_operacion](
	[tipo_operacion_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100)
)

CREATE TABLE [SQLITO].[moneda](
	[moneda_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[tipo_moneda] VARCHAR(100)
)

CREATE TABLE [SQLITO].[agencia](
	[agencia_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] VARCHAR(100),
	[direccion] NVARCHAR(100),
	[localidad] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[localidad] ([localidad_id]) 
)

CREATE TABLE [SQLITO].[agente_inmobiliario](
	[agente_inmobiliario_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[agencia] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[agencia] ([agencia_id]) ,
	[nombre] VARCHAR(100),
	[apellido] VARCHAR(100),
	[dni] NUMERIC(18,0),
	[fecha_registro] DATETIME,
	[fecha_nacimiento] DATETIME,
	[mail] VARCHAR(100),
	[telefono] NUMERIC(18,0)
)

CREATE TABLE [SQLITO].[anuncio](
	[anuncio_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[codigo_anuncio] NUMERIC(19,0),
	[estado] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[estado_anuncio] ([estado_anuncio_id]) ,
	[agente] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[agente_inmobiliario] ([agente_inmobiliario_id]) ,
	[moneda] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[moneda] ([moneda_id]) ,
	[inmueble] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[inmueble] ([inmueble_id]),
	[tipo_periodo] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[tipo_periodo] ([tipo_periodo_id]) ,
	[tipo_operacion] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[tipo_operacion] ([tipo_operacion_id]) ,
	[precio_publicado_inmueble] NUMERIC(18,2),
	[fecha_publicacion] DATETIME,
	[fecha_finalizacion] DATETIME,
	[costo_anuncio] NUMERIC(18,2)
)

CREATE TABLE [SQLITO].[comprador](
	[comprador_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] VARCHAR(100),
	[apellido] VARCHAR(100),
	[dni] NUMERIC(18,0),
	[fecha_registro] DATETIME,
	[telefono] NUMERIC(18,0),
	[mail] VARCHAR(100),
	[fecha_nacimiento] DATETIME
)

CREATE TABLE [SQLITO].[venta](
	[venta_id]  NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[codigo_venta] NUMERIC(18,0),
	[anuncio] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[anuncio] ([anuncio_id]) ,
	[fecha_venta] DATETIME,
	[precio_venta] NUMERIC(18,0),
	[comision_inmobiliaria] NUMERIC(18,0),
	[comprador] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[comprador] ([comprador_id])
)

CREATE TABLE [SQLITO].[medio_pago](
	[medio_pago_id]  NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] VARCHAR(100)
)

CREATE TABLE [SQLITO].[pago_compra_inmueble](
	[pago_compra_inmueble_id]  NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[moneda] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[moneda] ([moneda_id]) ,
	[medio_pago]  NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[medio_pago] ([medio_pago_id]) ,
	[venta]  NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[venta] ([venta_id]) ,
	[cotizacion] NUMERIC(18,2)
)

CREATE TABLE [SQLITO].[inquilino](
	[inquilino_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] VARCHAR(100),
	[apellido] VARCHAR(100),
	[dni] NUMERIC(18,0),
	[fecha_registro] DATETIME,
	[telefono] NUMERIC(18,0),
	[mail] VARCHAR(100),
	[fecha_nacimiento] DATETIME
)

CREATE TABLE [SQLITO].[estado_alquiler](
	[estado_alquiler_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[nombre] NVARCHAR(100)
)

CREATE TABLE [SQLITO].[alquiler](
	[alquiler_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[codigo_alquiler]  NUMERIC(18,0),
	[anuncio] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[anuncio] ([anuncio_id]) ,
	[inquilino] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[inquilino] ([inquilino_id]) ,
	[estado_alquiler] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[estado_alquiler] ([estado_alquiler_id]) ,
	[fecha_inicio] DATETIME,
	[fecha_fin] DATETIME,
	[deposito]  NUMERIC(18,2),
	[comision] NUMERIC(18,2),
	[gastos_de_averiguacion] NUMERIC(18,2)
)

CREATE TABLE [SQLITO].[detalle_importe_alquiler](
	[detalle_importe_alquiler_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[alquiler] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[alquiler] ([alquiler_id]) ,
	[numero_periodo_inicio] NUMERIC(18,0),
	[numero_periodo_fin] NUMERIC(18,0),
	[precio] NUMERIC(18,2)
)

CREATE TABLE [SQLITO].[pago_alquiler](
	[pago_alquiler_id] NUMERIC(18,0) NOT NULL IDENTITY PRIMARY KEY,
	[codigo_pago]  NUMERIC(18,0),
	[alquiler] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[alquiler] ([alquiler_id]) ,
	[medio_pago] NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[medio_pago] ([medio_pago_id]) ,
	[fecha_pago] DATETIME,
	[numero_periodo] NUMERIC(18,0),
	[descripcion_periodo] VARCHAR(100),
	[fecha_inicio_periodo] DATETIME,
	[fecha_fin_periodo] DATETIME,
	[importe] NUMERIC(18,2),
	[fecha_vencimiento] DATETIME
)

/*************************************************INDICES****************************************************************************/

CREATE NONCLUSTERED INDEX IDX_AGENCIA ON [SQLITO].[agencia] (nombre);

CREATE NONCLUSTERED INDEX IDX_AGENTE ON [SQLITO].[agente_inmobiliario] (nombre);

CREATE NONCLUSTERED INDEX IDX_COMPRADOR_NOMBRE ON [SQLITO].[comprador] (nombre);

CREATE NONCLUSTERED INDEX IDX_COMPRADOR_DNI ON [SQLITO].[comprador] (dni);

CREATE NONCLUSTERED INDEX IDX_AGENTE_ID ON [SQLITO].[agente_inmobiliario] (agente_inmobiliario_id);

CREATE NONCLUSTERED INDEX IDX_AGENTE_DNI ON [SQLITO].[agente_inmobiliario] (dni);

CREATE NONCLUSTERED INDEX IDX_AGENTE_MAIL ON [SQLITO].[agente_inmobiliario] (mail);

CREATE NONCLUSTERED INDEX IDX_AGENTE_FECHA_REGISTRO ON [SQLITO].[agente_inmobiliario] (fecha_registro);

CREATE NONCLUSTERED INDEX IDX_AGENTE_TELEFONO ON [SQLITO].[agente_inmobiliario] (telefono);

CREATE NONCLUSTERED INDEX IDX_AGENTE_FECHA_NAC ON [SQLITO].[agente_inmobiliario] (fecha_nacimiento);

CREATE NONCLUSTERED INDEX IDX_INMUEBLE ON [SQLITO].[inmueble] (codigo_inmueble);

CREATE NONCLUSTERED INDEX IDX_PROVINCIA ON [SQLITO].[provincia] (nombre);

CREATE NONCLUSTERED INDEX IDX_LOCALIDAD ON [SQLITO].[localidad] (nombre);

CREATE NONCLUSTERED INDEX IDX_BARRIO ON [SQLITO].[barrio] (nombre);

CREATE NONCLUSTERED INDEX IDX_ALQUILER ON [SQLITO].[alquiler] (codigo_alquiler);

CREATE NONCLUSTERED INDEX IDX_ANUNCIO ON [SQLITO].[anuncio] (codigo_anuncio);

CREATE NONCLUSTERED INDEX IDX_propietario_dni ON [SQLITO].[propietario](dni);

CREATE NONCLUSTERED INDEX IDX_propietario_nombre ON [SQLITO].[propietario](nombre);
						  
CREATE NONCLUSTERED INDEX IDX_disposicion_nombre ON [SQLITO].[disposicion](nombre);
						
CREATE NONCLUSTERED INDEX IDX_estado_inmueble_nombre ON [SQLITO].[estado_inmueble](nombre);
					
CREATE NONCLUSTERED INDEX IDX_orientacion_nombre ON [SQLITO].[orientacion](nombre);
					
CREATE NONCLUSTERED INDEX IDX_tipo_inmueble_nombre ON [SQLITO].[tipo_inmueble](nombre);
				
CREATE NONCLUSTERED INDEX IDX_tipo_ambiente_nombre ON [SQLITO].[tipo_ambiente](nombre);
				
CREATE NONCLUSTERED INDEX IDX_estado_anuncio_nombre ON [SQLITO].[estado_anuncio](nombre);
					
CREATE NONCLUSTERED INDEX IDX_moneda_tipo_moneda ON [SQLITO].[moneda](tipo_moneda);
	   					
CREATE NONCLUSTERED INDEX IDX_tipo_operacion_nombre ON [SQLITO].[tipo_operacion](nombre);
						
CREATE NONCLUSTERED INDEX IDX_tipo_periodo_nombre ON [SQLITO].[tipo_periodo](nombre);
	 				
CREATE NONCLUSTERED INDEX IDX_estado_alquiler_nombre ON [SQLITO].[estado_alquiler](nombre);
	  					
CREATE NONCLUSTERED INDEX IDX_inquilino_dni ON [SQLITO].[inquilino](dni);
	 				
CREATE NONCLUSTERED INDEX IDX_inmueble_descripcion ON [SQLITO].[inmueble](descripcion);
					
CREATE NONCLUSTERED INDEX IDX_anuncio_fecha_publicacion ON [SQLITO].[anuncio](fecha_publicacion);


/**************************************************MIGRACION DE TABLAS********************************************************************************/


INSERT INTO [SQLITO].[propietario](apellido, dni, fecha_nacimiento, fecha_registro, mail, nombre, telefono)
	SELECT DISTINCT
		PROPIETARIO_APELLIDO,
		PROPIETARIO_DNI,
		PROPIETARIO_FECHA_NAC,
		PROPIETARIO_FECHA_REGISTRO,
		PROPIETARIO_MAIL,
		PROPIETARIO_NOMBRE, 
		PROPIETARIO_TELEFONO
	FROM gd_esquema.Maestra 
	WHERE
		PROPIETARIO_NOMBRE IS NOT NULL AND
		PROPIETARIO_APELLIDO IS NOT NULL AND
		PROPIETARIO_DNI IS NOT NULL AND
		PROPIETARIO_FECHA_NAC IS NOT NULL AND
		PROPIETARIO_FECHA_REGISTRO IS NOT NULL AND
		PROPIETARIO_MAIL IS NOT NULL AND
		PROPIETARIO_TELEFONO IS NOT NULL;


INSERT INTO [SQLITO].[comprador](apellido, dni, fecha_nacimiento, fecha_registro, mail, nombre, telefono)
	SELECT DISTINCT
		COMPRADOR_APELLIDO,
		COMPRADOR_DNI,
		COMPRADOR_FECHA_NAC,
		COMPRADOR_FECHA_REGISTRO,
		COMPRADOR_MAIL,
		COMPRADOR_NOMBRE, 
		COMPRADOR_TELEFONO
	FROM gd_esquema.Maestra 
	WHERE
		COMPRADOR_NOMBRE IS NOT NULL AND
		COMPRADOR_APELLIDO IS NOT NULL AND
		COMPRADOR_DNI IS NOT NULL AND
		COMPRADOR_FECHA_NAC IS NOT NULL AND
		COMPRADOR_FECHA_REGISTRO IS NOT NULL AND
		COMPRADOR_MAIL IS NOT NULL AND
		COMPRADOR_TELEFONO IS NOT NULL;

INSERT INTO [SQLITO].[tipo_inmueble](nombre)
	SELECT DISTINCT
		INMUEBLE_TIPO_INMUEBLE
	FROM gd_esquema.Maestra
	WHERE
	    INMUEBLE_TIPO_INMUEBLE IS NOT NULL


INSERT INTO [SQLITO].[disposicion](nombre)
	SELECT DISTINCT
		INMUEBLE_DISPOSICION
	FROM gd_esquema.Maestra
	WHERE
	    INMUEBLE_DISPOSICION IS NOT NULL


INSERT INTO [SQLITO].[moneda](tipo_moneda)
	SELECT DISTINCT
		PAGO_VENTA_MONEDA
	FROM gd_esquema.Maestra
	WHERE
	    PAGO_VENTA_MONEDA IS NOT NULL


INSERT INTO [SQLITO].[medio_pago](nombre)
	SELECT DISTINCT
		PAGO_VENTA_MEDIO_PAGO
	FROM gd_esquema.Maestra
	WHERE
	    PAGO_VENTA_MEDIO_PAGO IS NOT NULL


INSERT INTO [SQLITO].[orientacion](nombre)
	SELECT DISTINCT
		INMUEBLE_ORIENTACION
	FROM gd_esquema.Maestra
	WHERE
	    INMUEBLE_ORIENTACION IS NOT NULL


INSERT INTO [SQLITO].[tipo_operacion](nombre)
	SELECT DISTINCT
		ANUNCIO_TIPO_OPERACION
	FROM gd_esquema.Maestra
	WHERE
	    ANUNCIO_TIPO_OPERACION IS NOT NULL


INSERT INTO [SQLITO].[tipo_periodo](nombre)
	SELECT DISTINCT
		ANUNCIO_TIPO_PERIODO
	FROM gd_esquema.Maestra
	WHERE
	    ANUNCIO_TIPO_PERIODO IS NOT NULL


INSERT INTO [SQLITO].[tipo_ambiente](nombre)
	SELECT DISTINCT
		INMUEBLE_CANT_AMBIENTES
	FROM gd_esquema.Maestra
	WHERE
	    INMUEBLE_CANT_AMBIENTES IS NOT NULL


INSERT INTO [SQLITO].[provincia](nombre)
	SELECT DISTINCT 
		INMUEBLE_PROVINCIA
	FROM gd_esquema.Maestra
	WHERE
	    INMUEBLE_PROVINCIA IS NOT NULL


INSERT INTO [SQLITO].[localidad](nombre, provincia)
	SELECT DISTINCT
	    M.INMUEBLE_LOCALIDAD,
	    P.provincia_id
	FROM gd_esquema.Maestra M
	INNER JOIN [SQLITO].[provincia] AS P ON M.INMUEBLE_PROVINCIA = P.nombre
	WHERE 
		M.INMUEBLE_LOCALIDAD IS NOT NULL
	UNION
	SELECT DISTINCT
	    M.SUCURSAL_LOCALIDAD,
	    P.provincia_id
	FROM gd_esquema.Maestra M
	INNER JOIN [SQLITO].[provincia] AS P ON M.SUCURSAL_PROVINCIA = P.nombre
		WHERE 
			M.SUCURSAL_LOCALIDAD IS NOT NULL


INSERT INTO [SQLITO].[barrio](nombre, localidad)
	SELECT DISTINCT 
		M.INMUEBLE_BARRIO, 
		L.localidad_id
	FROM gd_esquema.Maestra AS M
	INNER JOIN Provincia AS P ON M.INMUEBLE_PROVINCIA = P.nombre   
	INNER JOIN Localidad AS L ON M.INMUEBLE_LOCALIDAD = L.nombre
		WHERE 
		M.INMUEBLE_BARRIO IS NOT NULL AND
		L.provincia = P.provincia_id;


INSERT INTO [SQLITO].[agencia](nombre, direccion, localidad)
	SELECT DISTINCT 
		M.SUCURSAL_NOMBRE,
		M.SUCURSAL_DIRECCION,
		L.localidad_id
	FROM gd_esquema.Maestra M
	INNER JOIN provincia AS P ON M.SUCURSAL_PROVINCIA = P.nombre
	INNER JOIN localidad AS L ON M.SUCURSAL_LOCALIDAD = L.nombre 
								AND L.provincia = P.provincia_id
	WHERE 
		M.SUCURSAL_NOMBRE IS NOT NULL AND
		M.SUCURSAL_DIRECCION IS NOT NULL AND
		M.SUCURSAL_LOCALIDAD IS NOT NULL


INSERT INTO [SQLITO].[agente_inmobiliario] (nombre, apellido, dni, fecha_registro, telefono, mail, fecha_nacimiento, agencia)
	SELECT DISTINCT
	    M.AGENTE_NOMBRE,
	    M.AGENTE_APELLIDO,
	    M.AGENTE_DNI,
	    M.AGENTE_FECHA_REGISTRO,
	    M.AGENTE_TELEFONO,
	    M.AGENTE_MAIL,
	    M.AGENTE_FECHA_NAC,
	    S.agencia_id
	FROM gd_esquema.Maestra M
	JOIN agencia S ON M.SUCURSAL_NOMBRE = S.nombre


INSERT INTO [SQLITO].[estado_alquiler](nombre)
	SELECT DISTINCT
		ALQUILER_ESTADO
	FROM gd_esquema.Maestra 
	WHERE
    ALQUILER_ESTADO IS NOT NULL


INSERT INTO [SQLITO].[estado_anuncio](nombre)
	SELECT DISTINCT
		ANUNCIO_ESTADO
	FROM gd_esquema.Maestra 
	WHERE
    ANUNCIO_ESTADO IS NOT NULL


INSERT INTO [SQLITO].[estado_inmueble](nombre)
	SELECT DISTINCT
	INMUEBLE_ESTADO
	FROM  gd_esquema.Maestra 
	WHERE
    INMUEBLE_ESTADO IS NOT NULL


INSERT INTO [SQLITO].[inquilino](nombre, apellido, dni, fecha_nacimiento, fecha_registro, mail, telefono)
	SELECT DISTINCT 
		INQUILINO_NOMBRE,
		INQUILINO_APELLIDO,
		INQUILINO_DNI,
		INQUILINO_FECHA_NAC,
		INQUILINO_FECHA_REGISTRO,
		INQUILINO_MAIL,
		INQUILINO_TELEFONO
	FROM gd_esquema.Maestra
	WHERE
    INQUILINO_NOMBRE IS NOT NULL AND
    INQUILINO_APELLIDO IS NOT NULL AND
    INQUILINO_DNI IS NOT NULL AND
    INQUILINO_FECHA_NAC IS NOT NULL AND
    INQUILINO_FECHA_REGISTRO IS NOT NULL AND
    INQUILINO_MAIL IS NOT NULL AND
    INQUILINO_TELEFONO IS NOT NULL;


INSERT INTO [SQLITO].[inmueble](descripcion, codigo_inmueble, antiguedad, direccion, barrio, disposicion, estado_inmueble, orientacion, supericie_total, expensas, tipo_inmueble, tipo_ambiente, propietario)
	SELECT DISTINCT
		M.INMUEBLE_DESCRIPCION,
		M.INMUEBLE_CODIGO,
		M.INMUEBLE_ANTIGUEDAD,
		M.INMUEBLE_DIRECCION,
		B.barrio_id,
		S.disposicion_id,
		E.estado_inmueble_id,
		O.orientacion_id,
		M.INMUEBLE_SUPERFICIETOTAL,
		M.INMUEBLE_EXPESAS,
		T.tipo_inmueble_id,
		A.tipo_ambiente_id,
		PR.propietario_id
	FROM gd_esquema.Maestra M
	JOIN provincia AS P ON (M.INMUEBLE_PROVINCIA = P.nombre)
	JOIN localidad AS L ON (M.INMUEBLE_LOCALIDAD = L.nombre 
							AND P.provincia_id = L.provincia)
	JOIN barrio AS B ON (M.INMUEBLE_BARRIO = B.nombre 
						AND L.localidad_id = B.localidad)
	JOIN estado_inmueble AS E ON (M.INMUEBLE_ESTADO = E.nombre)
	JOIN disposicion AS S ON (M.INMUEBLE_DISPOSICION = S.nombre)
	JOIN orientacion AS O ON (M.INMUEBLE_ORIENTACION = O.nombre)
	JOIN tipo_inmueble AS T ON (M.INMUEBLE_TIPO_INMUEBLE = T.nombre)
	JOIN tipo_ambiente AS A ON (M.INMUEBLE_CANT_AMBIENTES = A.nombre)
	JOIN propietario AS PR ON (M.PROPIETARIO_DNI = PR.dni 
							AND M.PROPIETARIO_NOMBRE = PR.nombre)
	WHERE
	M.INMUEBLE_DESCRIPCION IS NOT NULL AND
	M.INMUEBLE_CODIGO IS NOT NULL AND
	M.INMUEBLE_ANTIGUEDAD IS NOT NULL AND
	M.INMUEBLE_DIRECCION IS NOT NULL AND
	M.INMUEBLE_SUPERFICIETOTAL IS NOT NULL AND
	M.INMUEBLE_EXPESAS IS NOT NULL 


INSERT INTO [SQLITO].[anuncio](codigo_anuncio, estado,moneda,fecha_finalizacion,agente,fecha_publicacion, inmueble, costo_anuncio, precio_publicado_inmueble, tipo_operacion, tipo_periodo)
	SELECT DISTINCT 
		M.ANUNCIO_CODIGO,
		E.estado_anuncio_id,
		MO.moneda_id,
		M.ANUNCIO_FECHA_FINALIZACION,
		A.agente_inmobiliario_id,
		M.ANUNCIO_FECHA_PUBLICACION,
		I.inmueble_id,
		M.ANUNCIO_COSTO_ANUNCIO,
		M.ANUNCIO_PRECIO_PUBLICADO,
		O.tipo_operacion_id,
		P.tipo_periodo_id
	FROM gd_esquema.Maestra M
	JOIN [SQLITO].estado_anuncio AS E on (M.ANUNCIO_ESTADO = E.nombre)
	JOIN [SQLITO].moneda AS MO on (M.ANUNCIO_MONEDA = MO.tipo_moneda)
	JOIN [SQLITO].agente_inmobiliario AS A on (M.AGENTE_NOMBRE = A.nombre)
	JOIN [SQLITO].inmueble AS I on ( M.INMUEBLE_CODIGO = I.codigo_inmueble)
	JOIN [SQLITO].tipo_operacion AS O on (M.ANUNCIO_TIPO_OPERACION = O.nombre)
	JOIN [SQLITO].tipo_periodo AS P on (M.ANUNCIO_TIPO_PERIODO = P.nombre)
	WHERE
	M.AGENTE_NOMBRE IS NOT NULL AND
	M.ANUNCIO_MONEDA IS NOT NULL AND
    M.ANUNCIO_CODIGO IS NOT NULL AND
    M.ANUNCIO_ESTADO IS NOT NULL AND
    M.ANUNCIO_FECHA_FINALIZACION IS NOT NULL AND
    M.ANUNCIO_FECHA_PUBLICACION IS NOT NULL AND
    M.ANUNCIO_COSTO_ANUNCIO IS NOT NULL AND
    M.ANUNCIO_PRECIO_PUBLICADO IS NOT NULL 


INSERT INTO [SQLITO].[alquiler](anuncio,comision,fecha_fin,fecha_inicio,codigo_alquiler,gastos_de_averiguacion, deposito, estado_alquiler, inquilino)
	SELECT DISTINCT 
	A.anuncio_id,
	ALQUILER_COMISION,
	ALQUILER_FECHA_FIN,
	ALQUILER_FECHA_INICIO,
	ALQUILER_CODIGO,
	ALQUILER_GASTOS_AVERIGUA,
	ALQUILER_DEPOSITO,
	E.estado_alquiler_id,
	I.inquilino_id
	FROM gd_esquema.Maestra M
	INNER JOIN anuncio AS A ON A.codigo_anuncio = M.ANUNCIO_CODIGO 
	INNER JOIN estado_alquiler AS E ON M.ALQUILER_ESTADO = E.nombre
	INNER JOIN inquilino AS I ON (M.INQUILINO_NOMBRE = I.nombre 
								AND  M.INQUILINO_APELLIDO = I.apellido
								AND M.INQUILINO_DNI = i.dni
								AND M.INQUILINO_MAIL = I.mail)
	WHERE
    ALQUILER_COMISION IS NOT NULL AND
    ALQUILER_FECHA_FIN IS NOT NULL AND
    ALQUILER_FECHA_INICIO IS NOT NULL AND
    ALQUILER_CODIGO IS NOT NULL AND
    ALQUILER_GASTOS_AVERIGUA IS NOT NULL AND
    ALQUILER_DEPOSITO IS NOT NULL 

INSERT INTO [SQLITO].[detalle_importe_alquiler](alquiler, numero_periodo_inicio, numero_periodo_fin, precio)
	SELECT DISTINCT
		A.alquiler_id,
		M.DETALLE_ALQ_NRO_PERIODO_INI,
		M.DETALLE_ALQ_NRO_PERIODO_FIN,
		M.DETALLE_ALQ_PRECIO
		FROM gd_esquema.Maestra M
		INNER JOIN Alquiler A ON A.codigo_alquiler = M.ALQUILER_CODIGO
		WHERE ALQUILER_CODIGO IS NOT NULL AND M.DETALLE_ALQ_NRO_PERIODO_FIN IS NOT NULL 
		AND M.DETALLE_ALQ_NRO_PERIODO_INI IS NOT NULL AND DETALLE_ALQ_PRECIO IS NOT NULL;


INSERT INTO [SQLITO].[pago_alquiler](numero_periodo, fecha_inicio_periodo, fecha_fin_periodo, codigo_pago, descripcion_periodo, alquiler, fecha_pago, importe, medio_pago, fecha_vencimiento)
	SELECT DISTINCT
		M.PAGO_ALQUILER_NRO_PERIODO,
		M.PAGO_ALQUILER_FEC_INI,
		M.PAGO_ALQUILER_FEC_FIN,
		M.PAGO_ALQUILER_CODIGO,
		M.PAGO_ALQUILER_DESC,
		A.alquiler_id,
		M.PAGO_ALQUILER_FECHA,
		M.PAGO_ALQUILER_IMPORTE,
		MP.medio_pago_id,
		M.PAGO_ALQUILER_FECHA_VENCIMIENTO
	FROM gd_esquema.Maestra M
	JOIN alquiler AS A ON A.codigo_alquiler = M.ALQUILER_CODIGO 
	JOIN medio_pago AS MP ON M.PAGO_ALQUILER_MEDIO_PAGO = MP.nombre
	
	WHERE
    M.PAGO_ALQUILER_NRO_PERIODO IS NOT NULL AND
    M.PAGO_ALQUILER_FEC_INI IS NOT NULL AND
    M.PAGO_ALQUILER_FEC_FIN IS NOT NULL AND
    M.PAGO_ALQUILER_CODIGO IS NOT NULL AND
    M.PAGO_ALQUILER_DESC IS NOT NULL AND
    M.PAGO_ALQUILER_IMPORTE IS NOT NULL AND
    M.PAGO_ALQUILER_FECHA IS NOT NULL 


INSERT INTO [SQLITO].[caracteristica](nombre)
	SELECT DISTINCT
	CASE
	WHEN
	INMUEBLE_CARACTERISTICA_CABLE = 1 THEN 'CABLE' ELSE NULL END
	FROM gd_esquema.Maestra
	WHERE INMUEBLE_CARACTERISTICA_CABLE IS NOT NULL AND INMUEBLE_CARACTERISTICA_CABLE != '0'
	UNION
	SELECT DISTINCT
		CASE
		WHEN
		INMUEBLE_CARACTERISTICA_CALEFACCION = 1 THEN 'CALEFACCION' ELSE NULL END
		FROM gd_esquema.Maestra
		WHERE INMUEBLE_CARACTERISTICA_CALEFACCION IS NOT NULL AND INMUEBLE_CARACTERISTICA_CALEFACCION != '0'
	UNION 
	SELECT DISTINCT
	CASE
	WHEN
	INMUEBLE_CARACTERISTICA_GAS = 1 THEN 'GAS' ELSE NULL END
	FROM gd_esquema.Maestra
	WHERE INMUEBLE_CARACTERISTICA_GAS IS NOT NULL AND INMUEBLE_CARACTERISTICA_GAS != '0'
	UNION 
	SELECT DISTINCT
	CASE
	WHEN
	INMUEBLE_CARACTERISTICA_WIFI = 1 THEN 'WIFI' ELSE NULL END
	FROM gd_esquema.Maestra
	WHERE INMUEBLE_CARACTERISTICA_WIFI IS NOT NULL AND INMUEBLE_CARACTERISTICA_WIFI != '0'


INSERT INTO [SQLITO].[caracteristica_X_inmueble] (inmueble, caracteristica)
	SELECT DISTINCT
	    I.inmueble_id, 
	    C.caracteristica_id
	FROM [SQLITO].[inmueble] AS I
	CROSS JOIN [SQLITO].[caracteristica] AS C
	WHERE 
	    (I.descripcion IS NOT NULL AND I.codigo_inmueble IS NOT NULL AND I.antiguedad IS NOT NULL 
	    AND I.direccion IS NOT NULL AND I.barrio IS NOT NULL AND I.disposicion IS NOT NULL 
	    AND I.estado_inmueble IS NOT NULL AND I.orientacion IS NOT NULL AND I.supericie_total IS NOT NULL 
	    AND I.expensas IS NOT NULL AND I.tipo_inmueble IS NOT NULL AND I.tipo_ambiente IS NOT NULL)
	    AND 
	    (C.nombre IN ('CABLE', 'CALEFACCION', 'GAS', 'WIFI'))
	    AND NOT EXISTS (
	        SELECT 1
	        FROM [SQLITO].[caracteristica_X_inmueble] AS IC
	        WHERE IC.inmueble = I.inmueble_id AND IC.caracteristica = C.caracteristica_id);


 INSERT INTO [SQLITO].[venta](codigo_venta, anuncio, fecha_venta, precio_venta, comision_inmobiliaria, comprador)
	SELECT DISTINCT
		M.VENTA_CODIGO,
		A.anuncio_id,
		M.VENTA_FECHA,
		M.VENTA_PRECIO_VENTA,
		M.VENTA_COMISION,
		C.comprador_id
	FROM gd_esquema.Maestra M
	JOIN anuncio AS A ON M.ANUNCIO_CODIGO = A.codigo_anuncio
	JOIN comprador AS C ON (M.COMPRADOR_DNI = C.dni AND M.COMPRADOR_NOMBRE = C.nombre)
	WHERE 
	M.VENTA_CODIGO IS NOT NULL AND
	M.VENTA_FECHA IS NOT NULL AND
	M.VENTA_PRECIO_VENTA IS NOT NULL AND
	M.VENTA_COMISION IS NOT NULL


INSERT INTO [SQLITO].[pago_compra_inmueble](cotizacion, medio_pago, moneda, venta)
	SELECT  DISTINCT
		M.PAGO_VENTA_COTIZACION,
		P.medio_pago_id,
		MO.moneda_id,
		V.venta_id
	FROM gd_esquema.Maestra M
	JOIN medio_pago AS P ON M.PAGO_VENTA_MEDIO_PAGO = P.nombre
	JOIN moneda AS MO ON M.PAGO_VENTA_MONEDA = MO.tipo_moneda
	JOIN venta AS V ON M.VENTA_CODIGO = V.codigo_venta
	WHERE 
	M.PAGO_VENTA_COTIZACION IS NOT NULL

COMMIT
END
GO
 
EXEC SQLITO.MIGRACION;
GO
