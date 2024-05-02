USE GD2C2023;
GO


IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_DURACION_PROMEDIO_ANUNCIOS'))
    DROP VIEW [SQLITO].VW_DURACION_PROMEDIO_ANUNCIOS;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PROMEDIO_PRECIO_INMUEBLES'))
    DROP VIEW [SQLITO].VW_PROMEDIO_PRECIO_INMUEBLES;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].[BI_VW_TOP_BARRIOS_POR_RANGO_ETARIO]'))
    DROP VIEW [SQLITO].[BI_VW_TOP_BARRIOS_POR_RANGO_ETARIO];
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_TOTAL_CIERRE_CONTRATOS'))
    DROP VIEW [SQLITO].VW_TOTAL_CIERRE_CONTRATOS;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PORCENTAJE_INCUMPLIMIENTO_ALQUILER'))
    DROP VIEW [SQLITO].VW_PORCENTAJE_INCUMPLIMIENTO_ALQUILER;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PROMEDIO_INCREMENTO_ALQUILER'))
    DROP VIEW [SQLITO].VW_PROMEDIO_INCREMENTO_ALQUILER;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PRECIO_PROMEDIO_M2_VENTAS'))
    DROP VIEW [SQLITO].VW_PRECIO_PROMEDIO_M2_VENTAS;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PROMEDIO_COMISION_TIPO_OPERACION'))
    DROP VIEW [SQLITO].VW_PROMEDIO_COMISION_TIPO_OPERACION;
GO
IF EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID('[SQLITO].VW_PORCENTAJE_OPERACIONES_CONCRETADAS'))
    DROP VIEW [SQLITO].VW_PORCENTAJE_OPERACIONES_CONCRETADAS;
GO

IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_HECHOS_ANUNCIO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_HECHOS_ANUNCIO;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_HECHOS_ALQUILER' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_HECHOS_ALQUILER;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_HECHOS_VENTA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_HECHOS_VENTA;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_HECHOS_PAGO_ALQUILER' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_HECHOS_PAGO_ALQUILER;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_AGENCIA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_AGENCIA;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_UBICACION' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_UBICACION;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_TIPO_MONEDA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_TIPO_MONEDA;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_TIPO_OPERACION' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_TIPO_OPERACION;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_RANGO_METROS' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_RANGO_METROS;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_AMBIENTES' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_AMBIENTES;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_TIPO_INMUEBLE' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_TIPO_INMUEBLE;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_RANGO_ETARIO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_RANGO_ETARIO;
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'BI_TIEMPO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP TABLE [SQLITO].BI_TIEMPO;


IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_RANGO_ETARIO_EMPLEADO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_RANGO_ETARIO_EMPLEADO;
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_RANGO_ETARIO_INQUILINO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_RANGO_ETARIO_INQUILINO;
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_CUATRIMESTRE' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_CUATRIMESTRE;
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_RANGO_ETARIO_COMPRADOR' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_RANGO_ETARIO_COMPRADOR;
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_RANGO_ETARIO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_RANGO_ETARIO;
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'CALCULAR_RANGO_METROS' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].CALCULAR_RANGO_METROS;
GO
IF EXISTS (SELECT * FROM sys.objects WHERE type IN ('FN', 'IF', 'TF') AND name = 'OBTENER_TIEMPO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP FUNCTION [SQLITO].OBTENER_TIEMPO;
GO


IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_AMBIENTES' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_AMBIENTES;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_TIPO_MONEDA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_TIPO_MONEDA;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_TIPO_OPERACION' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_TIPO_OPERACION;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_AGENCIA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_AGENCIA;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_UBICACION' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_UBICACION;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_RANGO_ETARIO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_RANGO_ETARIO;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_RANGO_METROS' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_RANGO_METROS;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_TIPO_INMUEBLE' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_TIPO_INMUEBLE;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_TIEMPO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_TIEMPO;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_HECHO_ANUNCIO' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_HECHO_ANUNCIO;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_HECHO_ALQUILER' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_HECHO_ALQUILER;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_HECHO_PAGO_ALQUILER' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_HECHO_PAGO_ALQUILER;
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'BI_MIGRAR_HECHO_VENTA' AND schema_id = SCHEMA_ID('SQLITO'))
    DROP PROCEDURE [SQLITO].BI_MIGRAR_HECHO_VENTA;	


CREATE TABLE [SQLITO].BI_TIEMPO(
	BI_TIEMPO_ID NUMERIC(18,0) IDENTITY(1, 1) PRIMARY KEY,
	BI_TIEMPO_ANIO INT,
	BI_TIEMPO_CUATRIMESTRE INT,
	BI_TIEMPO_MES INT
);
GO

CREATE TABLE [SQLITO].BI_UBICACION (
    BI_UBICACION_ID numeric PRIMARY KEY IDENTITY(1,1),
    BI_PROVINCIA nvarchar(100), 
    BI_LOCALIDAD nvarchar(100), 
    BI_BARRIO NVARCHAR(100)
);

CREATE TABLE [SQLITO].BI_AGENCIA(
	BI_AGENCIA_ID NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_AGENCIA_NOMBRE nvarchar(100)
);
GO

CREATE TABLE [SQLITO].BI_RANGO_ETARIO(
	BI_RANGO_ETARIO_ID NUMERIC(18,0) IDENTITY(1, 1) PRIMARY KEY,
	BI_RANGO_INICIO NUMERIC,
	BI_RANGO_FIN NUMERIC,
);
GO

CREATE TABLE [SQLITO].BI_TIPO_INMUEBLE(
	BI_TIPO_INMUEBLE_ID NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_TIPO_INMUEBLE nvarchar(100)
);
GO

CREATE TABLE [SQLITO].BI_AMBIENTES(
	BI_AMBIENTES_ID NUMERIC(18,0) IDENTITY(1,1) PRIMARY KEY,
	BI_AMBIENTES_CANTIDAD nvarchar(100)
);
GO

CREATE TABLE [SQLITO].BI_RANGO_METROS(
	BI_RANGO_METROS_ID NUMERIC(18,0) IDENTITY(1, 1) PRIMARY KEY,
	BI_RANGO_METROS_INICIO NUMERIC,
	BI_RANGO_METROS_FIN NUMERIC,
);
GO

CREATE TABLE [SQLITO].BI_TIPO_OPERACION(
	BI_TIPO_OPERACION_ID NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_TIPO_OPERACION_DESCRIPCION nvarchar(100)
);
GO

CREATE TABLE [SQLITO].BI_TIPO_MONEDA(
	BI_TIPO_MONEDA_ID NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_TIPO_MONEDA nvarchar(100)
);
GO

CREATE TABLE [SQLITO].BI_HECHOS_ANUNCIO(
	BI_hechos_anuncio_id NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_tiempo NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_TIEMPO] ([BI_TIEMPO_ID]),							
	BI_tipo_operacion NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_OPERACION] ([BI_TIPO_OPERACION_ID]),	
	BI_ubicacion NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_UBICACION] ([BI_UBICACION_ID]),					
	BI_ambientes NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_AMBIENTES] ([BI_AMBIENTES_ID]),					
	BI_tipo_inmueble NUMERIC(18,0)FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_INMUEBLE] ([BI_TIPO_INMUEBLE_ID]),			
	BI_rango_metros NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_METROS] ([BI_RANGO_METROS_ID]),				
	BI_tipo_moneda NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_MONEDA] ([BI_TIPO_MONEDA_ID]),				
	BI_agencia NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_AGENCIA] ([BI_AGENCIA_ID]),							
	BI_rango_etario_empleado NUMERIC(18,0)  FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_ETARIO] ([BI_RANGO_ETARIO_ID]),	
	BI_promedio_publicacion NUMERIC(18,0),																				
	BI_precio_promedio NUMERIC(18,2),
	BI_cantidad_de_anuncios NUMERIC(18,0),
	BI_cantidad_de_operaciones_concretadas NUMERIC(18,0),
	BI_monto_total NUMERIC(18,2),
	BI_promedio_comision NUMERIC(18,2)
);
GO


CREATE TABLE [SQLITO].BI_HECHOS_ALQUILER(
	BI_hechos_alquiler_id NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_rango_etario_inquilino NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_ETARIO] ([BI_RANGO_ETARIO_ID]),
	BI_rango_etario_empleado NUMERIC(18,0)  FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_ETARIO] ([BI_RANGO_ETARIO_ID]),
	BI_tiempo NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_TIEMPO] ([BI_TIEMPO_ID]),
	BI_tipo_inmueble NUMERIC(18,0)FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_INMUEBLE] ([BI_TIPO_INMUEBLE_ID]),
	BI_agencia NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_AGENCIA] ([BI_AGENCIA_ID]),
	BI_ubicacion NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_UBICACION] ([BI_UBICACION_ID]),
	BI_ambientes NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_AMBIENTES] ([BI_AMBIENTES_ID]),
	BI_rango_metros NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_METROS] ([BI_RANGO_METROS_ID]),
	BI_tipo_moneda NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_MONEDA] ([BI_TIPO_MONEDA_ID]),
	BI_cantidad_alquileres NUMERIC(18,0),
	BI_porcentaje_incumplimiento_pagos NUMERIC(18,2),
	BI_cantidad_de_pagos NUMERIC(18,0)
);
GO

CREATE TABLE [SQLITO].BI_HECHOS_VENTA(
	BI_hechos_venta_id NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_tiempo NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_TIEMPO] ([BI_TIEMPO_ID]),
	BI_rango_etario_compradores NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_ETARIO] ([BI_RANGO_ETARIO_ID]),
	BI_rango_etario_empleado NUMERIC(18,0)  FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_ETARIO] ([BI_RANGO_ETARIO_ID]),
	BI_tipo_inmueble NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_INMUEBLE] ([BI_TIPO_INMUEBLE_ID]),
	BI_tipo_moneda NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_TIPO_MONEDA] ([BI_TIPO_MONEDA_ID]),
	BI_ubicacion NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_UBICACION] ([BI_UBICACION_ID]),
	BI_ambientes NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_AMBIENTES] ([BI_AMBIENTES_ID]),
	BI_agencia NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_AGENCIA] ([BI_AGENCIA_ID]),
	BI_rango_metros NUMERIC(18,0) FOREIGN KEY REFERENCES [SQLITO].[BI_RANGO_METROS] ([BI_RANGO_METROS_ID]),
	BI_cantidad_de_ventas numeric(18,0),
	BI_precio_promedio_X_metro_cuadrado numeric(18, 2)
);
GO

CREATE TABLE [SQLITO].BI_HECHOS_PAGO_ALQUILER(
	BI_hechos_pago_id NUMERIC(18,0) IDENTITY PRIMARY KEY,
	BI_tiempo NUMERIC(18,0) NOT NULL FOREIGN KEY REFERENCES [SQLITO].[BI_TIEMPO] ([BI_TIEMPO_ID]),
	BI_cantidad_de_pagos numeric(18,0),
	BI_promedio_aumento numeric(18, 2)
);
GO

-- FUNCIONES

CREATE FUNCTION [SQLITO].CALCULAR_RANGO_ETARIO (@fechaNacimiento DATE)
RETURNS INT
AS
BEGIN
    DECLARE @edad INT
    DECLARE @rangoEtarioID INT

    
    SET @edad = DATEDIFF(YEAR, @fechaNacimiento, GETDATE())

    
    IF @edad <= 25
        SET @rangoEtarioID = 1;
    ELSE IF @edad <= 35
        SET @rangoEtarioID = 2;
    ELSE IF @edad <= 50
        SET @rangoEtarioID = 3;
    ELSE
        SET @rangoEtarioID = 4;

    RETURN @rangoEtarioID;
END;
GO


CREATE FUNCTION [SQLITO].CALCULAR_CUATRIMESTRE
(
    @fecha DATETIME
)
RETURNS INT
AS
BEGIN
    DECLARE @mes INT;
    DECLARE @cuatrimestre INT;

    SET @mes = MONTH(@fecha);

    SET @cuatrimestre = 
        CASE 
            WHEN @mes BETWEEN 1 AND 4 THEN 1
            WHEN @mes BETWEEN 5 AND 8 THEN 2
            WHEN @mes BETWEEN 9 AND 12 THEN 3
            ELSE 0
        END;

    RETURN @cuatrimestre;
END
GO


CREATE FUNCTION [SQLITO].CALCULAR_RANGO_METROS (@metrosCuadrados NUMERIC)
RETURNS INT
AS
BEGIN
    DECLARE @rangoM2ID INT

   
    SELECT @rangoM2ID = BI_RANGO_METROS_ID
    FROM [SQLITO].BI_RANGO_METROS
    WHERE @metrosCuadrados >= BI_RANGO_METROS_INICIO AND @metrosCuadrados <= BI_RANGO_METROS_FIN ;

    RETURN @rangoM2ID;
END;
GO


CREATE FUNCTION [SQLITO].OBTENER_TIEMPO(@fecha DATETIME)
RETURNS INT
AS
BEGIN
    DECLARE @anio INT
    DECLARE @mes INT
    DECLARE @cuatrimestre INT
    DECLARE @id_tiempo INT

    SET @anio = YEAR(@fecha)
    SET @mes = MONTH(@fecha)
    SET @cuatrimestre = [SQLITO].CALCULAR_CUATRIMESTRE(@fecha)

    SELECT @id_tiempo = BI_TIEMPO_ID
    FROM [SQLITO].BI_TIEMPO
    WHERE BI_TIEMPO_ANIO = @anio AND BI_TIEMPO_MES = @mes AND BI_TIEMPO_CUATRIMESTRE = @cuatrimestre

    RETURN @id_tiempo
END;
GO

-- MIGRACIONES


CREATE PROCEDURE [SQLITO].BI_MIGRAR_AMBIENTES
AS
BEGIN
INSERT INTO [SQLITO].BI_AMBIENTES(BI_AMBIENTES_CANTIDAD)
SELECT
	t.nombre
FROM [SQLITO].tipo_ambiente t
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_TIEMPO
AS
BEGIN
    INSERT INTO [SQLITO].BI_tiempo (BI_TIEMPO_ANIO, BI_TIEMPO_CUATRIMESTRE, BI_TIEMPO_MES)
    SELECT DISTINCT YEAR(fecha_publicacion), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_publicacion), MONTH(fecha_publicacion) FROM [SQLITO].Anuncio
    UNION 
    SELECT DISTINCT YEAR(fecha_finalizacion), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_finalizacion), MONTH(fecha_finalizacion) FROM [SQLITO].Anuncio
    UNION
    SELECT DISTINCT YEAR(fecha_inicio), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_inicio), MONTH(fecha_inicio) FROM [SQLITO].Alquiler
    UNION
    SELECT DISTINCT YEAR(fecha_fin), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_fin), MONTH(fecha_fin) FROM [SQLITO].Alquiler
    UNION
    SELECT DISTINCT YEAR(fecha_venta), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_venta), MONTH(fecha_venta) FROM [SQLITO].Venta
    UNION
    SELECT DISTINCT YEAR(fecha_pago), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_pago), MONTH(fecha_pago) FROM [SQLITO].pago_alquiler
    UNION
    SELECT DISTINCT YEAR(fecha_inicio_periodo), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_inicio_periodo), MONTH(fecha_inicio_periodo) FROM [SQLITO].pago_alquiler
    UNION
    SELECT DISTINCT YEAR(fecha_fin_periodo), [SQLITO].CALCULAR_CUATRIMESTRE(fecha_fin_periodo), MONTH(fecha_fin_periodo) FROM [SQLITO].pago_alquiler
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_TIPO_MONEDA
AS
BEGIN
INSERT INTO BI_TIPO_MONEDA
SELECT
	m.tipo_moneda
FROM [SQLITO].moneda m
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_TIPO_OPERACION
AS
BEGIN
INSERT INTO BI_TIPO_OPERACION
SELECT
	t.nombre
FROM [SQLITO].tipo_operacion t
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_AGENCIA
AS
BEGIN
INSERT INTO BI_AGENCIA
SELECT
	s.nombre
FROM [SQLITO].agencia s
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_UBICACION
AS
BEGIN
    INSERT INTO [SQLITO].BI_ubicacion (BI_PROVINCIA, BI_LOCALIDAD, BI_BARRIO)
    SELECT DISTINCT
        p.nombre,
        l.nombre,
        b.nombre 
    FROM [SQLITO].Barrio b
    JOIN [SQLITO].Localidad l ON b.localidad = l.localidad_id
    JOIN [SQLITO].Provincia p ON l.provincia = p.provincia_id
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_RANGO_ETARIO
AS
BEGIN 
    INSERT INTO [SQLITO].BI_RANGO_ETARIO(BI_RANGO_INICIO, BI_RANGO_FIN)
    VALUES (0, 25);
    INSERT INTO [SQLITO].BI_RANGO_ETARIO(BI_RANGO_INICIO, BI_RANGO_FIN)
    VALUES (25, 35);
    INSERT INTO [SQLITO].BI_RANGO_ETARIO(BI_RANGO_INICIO, BI_RANGO_FIN)
    VALUES (35, 50);
    INSERT INTO [SQLITO].BI_RANGO_ETARIO(BI_RANGO_INICIO, BI_RANGO_FIN)
    VALUES (50, 100);
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_RANGO_METROS
AS
BEGIN
    INSERT INTO [SQLITO].BI_RANGO_METROS(BI_RANGO_METROS_INICIO, BI_RANGO_METROS_FIN)
    VALUES 
        (0, 35),
        (35, 55),
        (55, 75),
        (75, 100),
        (100, 100000);
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_TIPO_INMUEBLE
AS
BEGIN
INSERT INTO [SQLITO].BI_TIPO_INMUEBLE
SELECT
	ti.nombre
FROM [SQLITO].tipo_inmueble ti
END
GO



CREATE PROCEDURE [SQLITO].BI_MIGRAR_HECHO_ANUNCIO
AS
BEGIN
    INSERT INTO [SQLITO].BI_HECHOS_ANUNCIO(BI_tiempo, BI_ubicacion, BI_agencia, BI_rango_etario_empleado, BI_tipo_inmueble, BI_ambientes, BI_rango_metros, BI_tipo_operacion, BI_tipo_moneda, BI_promedio_publicacion, BI_precio_promedio, BI_cantidad_de_anuncios, BI_cantidad_de_operaciones_concretadas, BI_monto_total, BI_promedio_comision)
    SELECT 
    [SQLITO].OBTENER_TIEMPO(a.fecha_publicacion),
    u.BI_UBICACION_ID,
    agenciacita.BI_AGENCIA_ID,
    [SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
    tipoInm.BI_TIPO_INMUEBLE_ID,
    inmueble.tipo_ambiente,
    [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
    tipoOp.BI_TIPO_OPERACION_ID,
    tipoMoneda.BI_TIPO_MONEDA_ID,
    AVG(DATEDIFF(DAY, a.fecha_publicacion, a.fecha_finalizacion)) AS promedioTiempoPublicado,
    AVG(a.precio_publicado_inmueble) AS precioPromedio,
    COUNT(DISTINCT a.anuncio_id) AS cantAnuncios,
    SUM(CASE WHEN alq.alquiler_id IS NOT NULL OR v.venta_id IS NOT NULL THEN 1 ELSE 0 END) AS cantOperacionesConcretadas,
    SUM(CASE WHEN alq.alquiler_id IS NOT NULL OR v.venta_id IS NOT NULL THEN a.precio_publicado_inmueble ELSE 0 END) AS montoTotalOperacionesConcretadas,
    AVG(CASE WHEN tipoOp.BI_TIPO_OPERACION_DESCRIPCION = 'Tipo Operacion Alquiler Contrato'  OR tipoOp.BI_TIPO_OPERACION_DESCRIPCION = 'Tipo Operacion Alquiler Temporario' THEN alq.comision WHEN tipoOp.BI_TIPO_OPERACION_DESCRIPCION = 'Tipo Operacion Venta' THEN v.comision_inmobiliaria ELSE 0 END) AS promedioComision
    FROM [SQLITO].anuncio a
    JOIN [SQLITO].Inmueble inmueble ON a.inmueble = inmueble.inmueble_id
    JOIN [SQLITO].agente_inmobiliario ag ON ag.agente_inmobiliario_id = a.agente
    JOIN [SQLITO].agencia s ON s.agencia_id = ag.agencia
    JOIN [SQLITO].BI_AGENCIA agenciacita ON s.agencia_id = agenciacita.BI_AGENCIA_ID
    JOIN [SQLITO].BI_tipo_operacion tipoOp ON a.tipo_operacion = tipoOp.BI_TIPO_OPERACION_ID
    JOIN [SQLITO].moneda tm ON a.moneda = tm.moneda_id
    JOIN [SQLITO].BI_tipo_moneda tipoMoneda ON tm.tipo_moneda = tipoMoneda.BI_TIPO_MONEDA
    JOIN [SQLITO].tipo_inmueble ti ON inmueble.tipo_inmueble = ti.tipo_inmueble_id
    JOIN [SQLITO].BI_TIPO_INMUEBLE tipoInm ON ti.tipo_inmueble_id = tipoInm.BI_TIPO_INMUEBLE_ID
    JOIN [SQLITO].barrio barrio ON inmueble.barrio = barrio.barrio_id
    JOIN [SQLITO].Localidad localidad ON barrio.localidad = localidad.localidad_id
    JOIN [SQLITO].Provincia provincia ON localidad.provincia = provincia.provincia_id
    JOIN [SQLITO].BI_ubicacion u ON provincia.nombre = u.BI_PROVINCIA AND localidad.nombre = u.BI_LOCALIDAD AND barrio.nombre = u.BI_BARRIO
    LEFT JOIN [SQLITO].venta v ON a.anuncio_id = v.anuncio
    LEFT JOIN [SQLITO].alquiler alq ON alq.anuncio = a.anuncio_id
    WHERE a.fecha_publicacion IS NOT NULL and ag.fecha_nacimiento IS NOT NULL
    GROUP BY
        [SQLITO].OBTENER_TIEMPO(a.fecha_publicacion),
        u.BI_UBICACION_ID,
        agenciacita.BI_AGENCIA_ID,
		[SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
        tipoInm.BI_TIPO_INMUEBLE_ID,
        inmueble.tipo_ambiente,
        [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
        tipoOp.BI_TIPO_OPERACION_ID,
        tipoMoneda.BI_TIPO_MONEDA_ID;
END
GO


CREATE PROCEDURE [SQLITO].BI_MIGRAR_HECHO_ALQUILER
AS
BEGIN
    INSERT INTO [SQLITO].BI_HECHOS_ALQUILER (BI_tiempo, BI_ubicacion, BI_agencia, BI_rango_etario_inquilino, BI_rango_etario_empleado, BI_tipo_inmueble, BI_ambientes, BI_rango_metros, BI_tipo_moneda, BI_cantidad_alquileres, BI_porcentaje_incumplimiento_pagos, BI_cantidad_de_pagos)
    SELECT DISTINCT
        [SQLITO].OBTENER_TIEMPO(a.fecha_inicio),
        u.BI_UBICACION_ID,
        s.agencia_id,
        [SQLITO].CALCULAR_RANGO_ETARIO(inquilino.fecha_nacimiento),
        [SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
        tipoInm.BI_TIPO_INMUEBLE_ID,
        inmueble.tipo_ambiente,
        [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
        tipoMoneda.BI_TIPO_MONEDA_ID,
        COUNT(DISTINCT a.alquiler_id) AS BI_cantAlquileres,
        SUM(CASE WHEN (DATEDIFF(DAY, pa.fecha_pago, pa.fecha_vencimiento) > 0) THEN 1 ELSE 0 END) / COUNT(*) * 100 AS BI_porcentajeIncumplimientoPagos,
        COUNT(DISTINCT pa.pago_alquiler_id) AS BI_cantPagos
    FROM [SQLITO].Alquiler a
    JOIN [SQLITO].Anuncio anuncio ON a.anuncio = anuncio.anuncio_id
    JOIN [SQLITO].Inmueble inmueble ON anuncio.inmueble = inmueble.inmueble_id
    JOIN [SQLITO].agente_inmobiliario ag ON ag.agente_inmobiliario_id = anuncio.agente
    JOIN [SQLITO].agencia s ON s.agencia_id = ag.agencia
    JOIN [SQLITO].moneda tm ON anuncio.moneda = tm.moneda_id
    JOIN [SQLITO].BI_TIPO_MONEDA tipoMoneda ON tm.tipo_moneda = tipoMoneda.BI_TIPO_MONEDA
    JOIN [SQLITO].Inquilino inquilino ON a.inquilino = inquilino.inquilino_id
    JOIN [SQLITO].tipo_inmueble ti ON inmueble.tipo_inmueble = ti.tipo_inmueble_id
    JOIN [SQLITO].BI_TIPO_INMUEBLE tipoInm ON ti.tipo_inmueble_id = tipoInm.BI_TIPO_INMUEBLE_ID
    JOIN [SQLITO].barrio barrio ON inmueble.barrio = barrio.barrio_id
    JOIN [SQLITO].Localidad localidad ON barrio.localidad = localidad.localidad_id
    JOIN [SQLITO].Provincia provincia ON localidad.provincia = provincia.provincia_id
    JOIN [SQLITO].BI_UBICACION u ON provincia.nombre = u.BI_PROVINCIA AND localidad.nombre = u.BI_LOCALIDAD AND barrio.nombre = u.BI_BARRIO
    LEFT JOIN [SQLITO].pago_alquiler pa ON a.alquiler_id = pa.alquiler
    WHERE a.fecha_inicio IS NOT NULL
    GROUP BY
        [SQLITO].OBTENER_TIEMPO(a.fecha_inicio),
        u.BI_UBICACION_ID,
        s.agencia_id,
        [SQLITO].CALCULAR_RANGO_ETARIO(inquilino.fecha_nacimiento),
        [SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
        tipoInm.BI_TIPO_INMUEBLE_ID,
        inmueble.tipo_ambiente,
        [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
        tipoMoneda.BI_TIPO_MONEDA_ID
END
GO
			 
CREATE PROCEDURE [SQLITO].BI_MIGRAR_HECHO_PAGO_ALQUILER
AS
BEGIN
    INSERT INTO [SQLITO].BI_HECHOS_PAGO_ALQUILER(BI_tiempo, BI_promedio_aumento, BI_cantidad_de_pagos)
    SELECT DISTINCT
        [SQLITO].OBTENER_TIEMPO(pa.fecha_pago),
        SUM((pa.importe - pago_anterior.importe)/pago_anterior.importe*100)/ COUNT(*) AS promedioPorcentajeAumento,
        COUNT(*) AS cantPagos
        FROM [SQLITO].Alquiler a
        JOIN [SQLITO].estado_alquiler ea ON a.estado_alquiler = ea.estado_alquiler_id --AND ea.nombre = 'Activo'
        JOIN [SQLITO].pago_alquiler pa ON a.alquiler_id = pa.alquiler
        JOIN [SQLITO].Pago_Alquiler pago_anterior ON pago_anterior.alquiler = pa.alquiler 
												AND YEAR(pago_anterior.fecha_pago) * 12 + MONTH(pago_anterior.fecha_pago) = YEAR(pa.fecha_pago) * 12 + MONTH(pa.fecha_pago) - 1 
												AND pago_anterior.importe != pa.importe
        WHERE pa.fecha_pago IS NOT NULL
        GROUP BY
        [SQLITO].OBTENER_TIEMPO(pa.fecha_pago)
END
GO

CREATE PROCEDURE [SQLITO].BI_MIGRAR_HECHO_VENTA
AS
BEGIN
    INSERT INTO [SQLITO].BI_HECHOS_VENTA (BI_tiempo, BI_ubicacion, BI_agencia, BI_rango_etario_empleado, BI_rango_etario_compradores, BI_tipo_inmueble, BI_ambientes, BI_rango_metros, BI_tipo_moneda, BI_cantidad_de_ventas, BI_precio_promedio_X_metro_cuadrado)
    SELECT DISTINCT
  
        [SQLITO].OBTENER_TIEMPO(v.fecha_venta),
        u.BI_UBICACION_ID,
        s.agencia_id,
        [SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
        [SQLITO].CALCULAR_RANGO_ETARIO(comprador.fecha_nacimiento),
        tipoInm.BI_TIPO_INMUEBLE_ID,
        inmueble.tipo_ambiente,
        [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
        tipoMoneda.BI_TIPO_MONEDA_ID,
        COUNT(DISTINCT v.venta_id) AS cantVentas,
        AVG(v.precio_venta / inmueble.supericie_total) AS precioPromedioPorM2
        FROM [SQLITO].Venta v
        JOIN [SQLITO].Anuncio anuncio ON v.anuncio = anuncio.anuncio_id
        JOIN [SQLITO].agente_inmobiliario ag ON ag.agente_inmobiliario_id = anuncio.agente
        JOIN [SQLITO].agencia s ON s.agencia_id = ag.agencia
        JOIN [SQLITO].moneda tm ON anuncio.moneda = tm.moneda_id
        JOIN [SQLITO].BI_TIPO_MONEDA tipoMoneda ON tm.tipo_moneda = tipoMoneda.BI_TIPO_MONEDA
        JOIN [SQLITO].Comprador comprador ON v.comprador = comprador.comprador_id
        JOIN [SQLITO].Inmueble inmueble ON anuncio.inmueble = inmueble.inmueble_id
        JOIN [SQLITO].tipo_inmueble ti ON inmueble.tipo_inmueble = ti.tipo_inmueble_id
        JOIN [SQLITO].BI_tipo_inmueble tipoInm ON ti.tipo_inmueble_id = tipoInm.BI_TIPO_INMUEBLE_ID
        JOIN [SQLITO].Barrio barrio ON inmueble.barrio = barrio.barrio_id
        JOIN [SQLITO].Localidad localidad ON barrio.localidad = localidad.localidad_id
        JOIN [SQLITO].Provincia provincia ON localidad.provincia = provincia.provincia_id
        JOIN [SQLITO].BI_ubicacion u ON provincia.nombre = u.BI_PROVINCIA AND localidad.nombre = u.BI_LOCALIDAD AND barrio.nombre = u.BI_BARRIO
        WHERE v.fecha_venta IS NOT NULL
        GROUP BY
        [SQLITO].Obtener_Tiempo(v.fecha_venta),
        u.BI_UBICACION_ID,      
        s.agencia_id,
        [SQLITO].CALCULAR_RANGO_ETARIO(ag.fecha_nacimiento),
        [SQLITO].CALCULAR_RANGO_ETARIO(comprador.fecha_nacimiento),
        tipoInm.BI_TIPO_INMUEBLE_ID,
		inmueble.tipo_ambiente,
        [SQLITO].CALCULAR_RANGO_METROS(inmueble.supericie_total),
        tipoMoneda.BI_TIPO_MONEDA_ID
END
GO


BEGIN TRANSACTION
EXECUTE [SQLITO].BI_MIGRAR_AGENCIA
EXECUTE [SQLITO].BI_MIGRAR_TIEMPO
EXECUTE [SQLITO].BI_MIGRAR_RANGO_ETARIO
EXECUTE [SQLITO].BI_MIGRAR_RANGO_METROS
EXECUTE [SQLITO].BI_MIGRAR_TIPO_MONEDA
EXECUTE [SQLITO].BI_MIGRAR_TIPO_OPERACION
EXECUTE [SQLITO].BI_MIGRAR_TIPO_INMUEBLE
EXECUTE [SQLITO].BI_MIGRAR_AMBIENTES
EXECUTE [SQLITO].BI_MIGRAR_UBICACION

commit;

BEGIN TRANSACTION
EXECUTE [SQLITO].BI_MIGRAR_HECHO_ANUNCIO
EXECUTE [SQLITO].BI_MIGRAR_HECHO_ALQUILER
EXECUTE [SQLITO].BI_MIGRAR_HECHO_VENTA
EXECUTE [SQLITO].BI_MIGRAR_HECHO_PAGO_ALQUILER
COMMIT;



-- Vistas

GO
CREATE VIEW [SQLITO].VW_DURACION_PROMEDIO_ANUNCIOS 
AS
SELECT
    t.BI_TIEMPO_ANIO AS Anio,
    t.BI_TIEMPO_CUATRIMESTRE AS Cuatrimestre,
    ubi.BI_barrio AS Barrio,
    amb.BI_AMBIENTES_CANTIDAD AS ambientes,
    tipo.BI_TIPO_OPERACION_DESCRIPCION AS tipoOperacion,
    SUM(a.BI_tiempo * a.BI_cantidad_de_anuncios) / sum(a.BI_cantidad_de_anuncios) AS DuracionPromedio
FROM [SQLITO].BI_HECHOS_ANUNCIO a
    JOIN [SQLITO].BI_TIEMPO t ON t.BI_TIEMPO_ID = a.BI_tiempo
    JOIN [SQLITO].BI_TIPO_OPERACION tipo ON tipo.BI_TIPO_OPERACION_ID = a.BI_tipo_operacion
    JOIN [SQLITO].BI_UBICACION ubi on ubi.BI_UBICACION_ID = A.BI_ubicacion
    JOIN [SQLITO].BI_AMBIENTES amb ON amb.BI_AMBIENTES_ID = a.BI_ambientes
GROUP BY
    t.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
    tipo.BI_TIPO_OPERACION_DESCRIPCION,
    ubi.BI_BARRIO,
    amb.BI_AMBIENTES_CANTIDAD;
GO

--VISTA 2
GO
CREATE VIEW [SQLITO].VW_PROMEDIO_PRECIO_INMUEBLES AS
SELECT 
    t.BI_TIEMPO_ANIO as anio,
    t.BI_TIEMPO_CUATRIMESTRE as cuatrimestre,
    op.BI_TIPO_OPERACION_DESCRIPCION as tipoOperacion,
    inm.BI_TIPO_INMUEBLE as tipoInmueble,
    rm.BI_RANGO_METROS_INICIO,
	rm.BI_RANGO_METROS_FIN,
	tm.BI_TIPO_MONEDA,
    SUM(ha.BI_precio_promedio * ha.BI_cantidad_de_anuncios) / SUM(ha.BI_cantidad_de_anuncios) AS Precio_Promedio
FROM 
    [SQLITO].BI_HECHOS_ANUNCIO ha
INNER JOIN 
    [SQLITO].BI_TIEMPO t ON ha.BI_tiempo = t.BI_TIEMPO_ID
INNER JOIN 
    [SQLITO].BI_TIPO_OPERACION op ON ha.BI_tipo_operacion = op.BI_TIPO_OPERACION_ID
INNER JOIN 
    [SQLITO].BI_TIPO_INMUEBLE inm ON ha.BI_tipo_inmueble = inm.BI_TIPO_INMUEBLE_ID
INNER JOIN 
    [SQLITO].BI_RANGO_METROS rm ON ha.BI_rango_metros = rm.BI_RANGO_METROS_ID
INNER JOIN 
    [SQLITO].BI_TIPO_MONEDA tm ON ha.BI_tipo_moneda = tm.BI_TIPO_MONEDA_ID
GROUP BY 
    t.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
    op.BI_TIPO_OPERACION_DESCRIPCION,
    inm.BI_TIPO_INMUEBLE,
    rm.BI_RANGO_METROS_INICIO,
	rm.BI_RANGO_METROS_FIN,
    tm.BI_TIPO_MONEDA;
GO

--VISTA 3
GO

CREATE VIEW [SQLITO].[BI_VW_TOP_BARRIOS_POR_RANGO_ETARIO]
AS
SELECT TOP(5)
	
    t.BI_TIEMPO_ANIO as anio,
	t.BI_TIEMPO_CUATRIMESTRE as cuatrimestre,
	re.BI_RANGO_INICIO,
	re.BI_RANGO_FIN,
    u.BI_BARRIO as barrio,
    COUNT(*) AS [CantidadAlquileres]
FROM
    [SQLITO].BI_HECHOS_ALQUILER a
INNER JOIN [SQLITO].BI_TIEMPO t ON a.BI_tiempo = t.BI_TIEMPO_ID
INNER JOIN [SQLITO].BI_RANGO_ETARIO re ON a.BI_rango_etario_inquilino = re.BI_RANGO_ETARIO_ID
INNER JOIN [SQLITO].BI_UBICACION u ON a.BI_ubicacion = u.BI_UBICACION_ID
GROUP BY
	t.BI_TIEMPO_ANIO,
	t.BI_TIEMPO_CUATRIMESTRE,
	re.BI_RANGO_INICIO,
	re.BI_RANGO_FIN,
	u.BI_BARRIO
ORDER BY
	CantidadAlquileres DESC
GO

--VISTA 4
GO
CREATE VIEW [SQLITO].VW_PORCENTAJE_INCUMPLIMIENTO_ALQUILER AS
SELECT
    T.BI_TIEMPO_ANIO AS Anio,
    T.BI_TIEMPO_CUATRIMESTRE AS Cuatrimestre,
    T.BI_TIEMPO_MES AS Mes,
    SUM( A.BI_porcentaje_incumplimiento_pagos + A.BI_cantidad_de_pagos) / SUM(A.BI_cantidad_de_pagos) AS PorcentajeIncumplimiento
FROM [SQLITO].BI_HECHOS_ALQUILER A
    JOIN [SQLITO].BI_TIEMPO T ON T.BI_TIEMPO_ID = A.BI_tiempo
GROUP BY
    T.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
    T.BI_TIEMPO_MES;
GO

--Vista 5
GO
CREATE VIEW [SQLITO].VW_PROMEDIO_INCREMENTO_ALQUILER AS
SELECT
    t.BI_TIEMPO_ANIO as Anio,
    t.BI_TIEMPO_CUATRIMESTRE as Cuatrimestre,
    t.BI_TIEMPO_MES as Mes,
    SUM(a.BI_promedio_aumento * a.BI_cantidad_de_pagos) / SUM(a.BI_cantidad_de_pagos) as porcentajePromedioIncrementoAlquileres
    
FROM [SQLITO].BI_HECHOS_PAGO_ALQUILER a
    JOIN [SQLITO].BI_TIEMPO t ON t.BI_TIEMPO_ID = a.bi_tiempo
GROUP BY
    t.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
    t.BI_TIEMPO_MES;
GO

--Vista 6
GO
CREATE VIEW [SQLITO].VW_PRECIO_PROMEDIO_M2_VENTAS AS
SELECT
    tInm.BI_TIPO_INMUEBLE AS TipoInmueble,
    u.BI_LOCALIDAD AS Localidad,
    t.BI_TIEMPO_ANIO AS Anio,
    t.BI_TIEMPO_CUATRIMESTRE AS Cuatrimestre,
	m.BI_TIPO_MONEDA,
    SUM(v.BI_precio_promedio_X_metro_cuadrado * v.BI_cantidad_de_ventas)/Sum(v.BI_cantidad_de_ventas) AS PrecioPromedioM2
FROM [SQLITO].BI_HECHOS_VENTA v
JOIN [SQLITO].BI_TIPO_INMUEBLE tInm ON v.BI_tipo_inmueble = tInm.BI_TIPO_INMUEBLE_ID
JOIN [SQLITO].BI_UBICACION u ON v.BI_ubicacion = u.BI_UBICACION_ID
JOIN [SQLITO].BI_TIEMPO t ON v.BI_tiempo = t.BI_TIEMPO_ID
JOIN [SQLITO].BI_TIPO_MONEDA m ON v.BI_tipo_moneda = m.BI_TIPO_MONEDA_ID
GROUP BY
    tInm.BI_TIPO_INMUEBLE,
    u.BI_LOCALIDAD,
    t.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
	m.BI_TIPO_MONEDA
GO

--Vista 7
GO
CREATE VIEW [SQLITO].VW_PROMEDIO_COMISION_TIPO_OPERACION AS
SELECT
    t.BI_TIEMPO_ANIO as anio,
    t.BI_TIEMPO_CUATRIMESTRE as Cuatrimestre,
    op.BI_TIPO_OPERACION_DESCRIPCION as tipoOperacion,
    ag.BI_AGENCIA_NOMBRE as agencia,
    SUM(AN.BI_promedio_comision * AN.BI_cantidad_de_anuncios) / SUM(AN.BI_cantidad_de_anuncios) as valorPromedioComision
FROM [SQLITO].BI_HECHOS_ANUNCIO an
    JOIN [SQLITO].bi_tiempo t on t.BI_TIEMPO_ID = an.bi_tiempo
    JOIN [SQLITO].BI_tipo_operacion op on an.BI_tipo_operacion = op.BI_TIPO_OPERACION_ID
    JOIN [SQLITO].BI_agencia ag on an.BI_AGENCIA = ag.BI_AGENCIA_ID
GROUP BY  
    t.BI_TIEMPO_CUATRIMESTRE, 
    t.BI_TIEMPO_ANIO,
    op.BI_TIPO_OPERACION_DESCRIPCION, 
    ag.BI_AGENCIA_NOMBRE;
GO




--Vista 8
GO
CREATE VIEW [SQLITO].VW_PORCENTAJE_OPERACIONES_CONCRETADAS 
AS
SELECT
        t.BI_TIEMPO_ANIO as anio,
        t.BI_TIEMPO_CUATRIMESTRE,
        agen.BI_AGENCIA_NOMBRE as sucursal,
        r.BI_RANGO_INICIO,
        r.BI_RANGO_FIN,
        SUM( a.BI_cantidad_de_operaciones_concretadas ) / SUM( a.BI_cantidad_de_anuncios ) as porcentajeOperacionesConcretadas
    FROM
        [SQLITO].BI_HECHOS_ANUNCIO a
    JOIN [SQLITO].BI_tiempo t on t.BI_TIEMPO_ID = a.bi_tiempo
    JOIN [SQLITO].BI_agencia agen on agen.BI_AGENCIA_ID = a.BI_agencia
    JOIN [SQLITO].BI_RANGO_ETARIO r on r.BI_RANGO_ETARIO_ID = a.BI_rango_etario_empleado
    GROUP BY
        t.BI_TIEMPO_ANIO, 
        t.BI_TIEMPO_CUATRIMESTRE,
        agen.BI_AGENCIA_NOMBRE,
        r.BI_RANGO_INICIO,
        r.BI_RANGO_FIN
GO


--Vista 9
GO
CREATE VIEW [SQLITO].VW_TOTAL_CIERRE_CONTRATOS AS
SELECT
    a.BI_AGENCIA_ID,
    t.BI_TIEMPO_ANIO AS Anio,
    t.BI_TIEMPO_CUATRIMESTRE AS Cuatrimestre,
	tm.BI_TIPO_MONEDA AS Moneda,
	tope.BI_TIPO_OPERACION_DESCRIPCION as Operacion,
	SUM(v.BI_monto_total) as TotalCierreContratos
FROM [SQLITO].BI_HECHOS_ANUNCIO v
JOIN [SQLITO].BI_AGENCIA a ON v.BI_agencia = a.BI_AGENCIA_ID
JOIN [SQLITO].BI_TIPO_MONEDA tm ON v.BI_tipo_moneda = tm.BI_TIPO_MONEDA_ID
JOIN [SQLITO].BI_TIEMPO t ON v.BI_tiempo = t.BI_TIEMPO_ID
JOIN [SQLITO].BI_TIPO_OPERACION tope ON v.BI_tipo_operacion = tope.BI_TIPO_OPERACION_ID
WHERE
	v.BI_tiempo IS NOT NULL
GROUP BY
    a.BI_AGENCIA_ID,
    tope.BI_TIPO_OPERACION_DESCRIPCION,
    t.BI_TIEMPO_ANIO,
    t.BI_TIEMPO_CUATRIMESTRE,
	tm.BI_TIPO_MONEDA
GO