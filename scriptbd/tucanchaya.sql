-- phpMyAdmin SQL Dump
-- version 4.6.5.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 12-02-2019 a las 02:31:31
-- Versión del servidor: 5.7.16
-- Versión de PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tucanchaya`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrio`
--

CREATE TABLE `barrio` (
  `barId` bigint(20) NOT NULL,
  `ciuId` bigint(20) NOT NULL,
  `barNombre` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `catId` bigint(20) NOT NULL,
  `catNombre` varchar(100) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `centrodeportivo`
--

CREATE TABLE `centrodeportivo` (
  `cenId` bigint(20) NOT NULL,
  `barId` bigint(20) NOT NULL,
  `colorId` int(11) DEFAULT NULL,
  `cenNit` varchar(30) DEFAULT NULL,
  `cenNombre` varchar(100) NOT NULL,
  `cenDireccion` varchar(200) DEFAULT NULL,
  `cenUbicacion` varchar(200) DEFAULT NULL,
  `cenDescripcion` longtext,
  `cenAlto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `centrodeportivofotos`
--

CREATE TABLE `centrodeportivofotos` (
  `cenFotId` bigint(20) NOT NULL,
  `cenId` bigint(20) NOT NULL,
  `cenFoNombre` varchar(200) DEFAULT NULL,
  `cenFotPrincipal` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `centrodeportivotelefono`
--

CREATE TABLE `centrodeportivotelefono` (
  `cenTelId` int(11) NOT NULL,
  `cenId` bigint(20) NOT NULL,
  `cenTelNumero` varchar(20) NOT NULL,
  `cenTelTipo` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `ciuId` bigint(20) NOT NULL,
  `ciuNombre` varchar(100) NOT NULL,
  `ciuPorDefecto` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`ciuId`, `ciuNombre`, `ciuPorDefecto`) VALUES
(1, 'Popayán', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `color`
--

CREATE TABLE `color` (
  `colorId` int(11) NOT NULL,
  `colorHexadecimal` varchar(10) NOT NULL,
  `colorNombre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `color`
--

INSERT INTO `color` (`colorId`, `colorHexadecimal`, `colorNombre`) VALUES
(1, '#0000FF', 'Azul'),
(2, '#FF0000', 'Rojo'),
(3, '#FF00FF', 'Violeta');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `escenario`
--

CREATE TABLE `escenario` (
  `escId` bigint(20) NOT NULL,
  `cenId` bigint(20) NOT NULL,
  `escNombre` int(11) NOT NULL,
  `escMedidaRealAncho` float DEFAULT NULL,
  `escMedidaRealLargo` float DEFAULT NULL,
  `escImagenAncho` float DEFAULT NULL,
  `escImagenLargo` float DEFAULT NULL,
  `escImagenAngulo` float DEFAULT NULL,
  `escImagen` varchar(200) DEFAULT NULL,
  `escEstado` int(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `gruId` varchar(20) NOT NULL,
  `gruDescripcion` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `grupo`
--

INSERT INTO `grupo` (`gruId`, `gruDescripcion`) VALUES
('admin', NULL),
('employee', NULL),
('superAdmin', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `precio`
--

CREATE TABLE `precio` (
  `precId` bigint(20) NOT NULL,
  `precValor` bigint(20) NOT NULL,
  `precioFechaInicial` varchar(20) NOT NULL,
  `precioFechaFinal` varchar(20) DEFAULT NULL,
  `prodId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `prodId` bigint(20) NOT NULL,
  `prodNombre` varchar(100) CHARACTER SET latin1 NOT NULL,
  `prodImagen` varchar(200) DEFAULT NULL,
  `catId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `usuId` bigint(20) NOT NULL,
  `usuIdentificacion` varchar(30) NOT NULL,
  `usuNombre` varchar(200) NOT NULL,
  `usuNombreUsuario` varchar(75) NOT NULL,
  `usuContrasena` varchar(256) NOT NULL,
  `usuEmail` varchar(200) NOT NULL,
  `usuDireccion` varchar(200) DEFAULT NULL,
  `usuTelefono` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuariogrupo`
--

CREATE TABLE `usuariogrupo` (
  `usuNombreUsuario` varchar(75) NOT NULL,
  `gruId` varchar(20) NOT NULL,
  `usuId` bigint(20) NOT NULL,
  `cenId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `barrio`
--
ALTER TABLE `barrio`
  ADD PRIMARY KEY (`barId`),
  ADD KEY `fl_ciudad_barrio` (`ciuId`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`catId`);

--
-- Indices de la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  ADD PRIMARY KEY (`cenId`),
  ADD KEY `fk_centrodeportivo_color` (`colorId`),
  ADD KEY `fk_centrodeportivo_barrio` (`barId`);

--
-- Indices de la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  ADD PRIMARY KEY (`cenFotId`),
  ADD KEY `fk_centrodeportivofotos_centrodeportivo` (`cenId`);

--
-- Indices de la tabla `centrodeportivotelefono`
--
ALTER TABLE `centrodeportivotelefono`
  ADD PRIMARY KEY (`cenTelId`),
  ADD KEY `fk_centrodeportivo_centrodeportivotelefono` (`cenId`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`ciuId`);

--
-- Indices de la tabla `color`
--
ALTER TABLE `color`
  ADD PRIMARY KEY (`colorId`);

--
-- Indices de la tabla `escenario`
--
ALTER TABLE `escenario`
  ADD PRIMARY KEY (`escId`),
  ADD KEY `fk_centrodeportivo_escenario` (`cenId`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`gruId`);

--
-- Indices de la tabla `precio`
--
ALTER TABLE `precio`
  ADD PRIMARY KEY (`precId`),
  ADD KEY `fk_producto_precio` (`prodId`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`prodId`),
  ADD KEY `fk_categoria_producto` (`catId`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`usuId`),
  ADD UNIQUE KEY `unique_usuNombreUsuario` (`usuNombreUsuario`);

--
-- Indices de la tabla `usuariogrupo`
--
ALTER TABLE `usuariogrupo`
  ADD PRIMARY KEY (`usuNombreUsuario`),
  ADD KEY `fk_usuariogrupo_grupo` (`gruId`),
  ADD KEY `fk_usuariogrupo_usuario` (`usuId`),
  ADD KEY `fk_usuariogrupo_centrodeportivo` (`cenId`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `barrio`
--
ALTER TABLE `barrio`
  MODIFY `barId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `catId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  MODIFY `cenId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  MODIFY `cenFotId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `centrodeportivotelefono`
--
ALTER TABLE `centrodeportivotelefono`
  MODIFY `cenTelId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `ciuId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `color`
--
ALTER TABLE `color`
  MODIFY `colorId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `escenario`
--
ALTER TABLE `escenario`
  MODIFY `escId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `precio`
--
ALTER TABLE `precio`
  MODIFY `precId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `prodId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `barrio`
--
ALTER TABLE `barrio`
  ADD CONSTRAINT `fl_ciudad_barrio` FOREIGN KEY (`ciuId`) REFERENCES `ciudad` (`ciuId`);

--
-- Filtros para la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  ADD CONSTRAINT `fk_centrodeportivo_barrio` FOREIGN KEY (`barId`) REFERENCES `barrio` (`barId`),
  ADD CONSTRAINT `fk_centrodeportivo_color` FOREIGN KEY (`colorId`) REFERENCES `color` (`colorId`);

--
-- Filtros para la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  ADD CONSTRAINT `fk_centrodeportivofotos_centrodeportivo` FOREIGN KEY (`cenId`) REFERENCES `centrodeportivo` (`cenId`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `centrodeportivotelefono`
--
ALTER TABLE `centrodeportivotelefono`
  ADD CONSTRAINT `fk_centrodeportivo_centrodeportivotelefono` FOREIGN KEY (`cenId`) REFERENCES `centrodeportivo` (`cenId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `escenario`
--
ALTER TABLE `escenario`
  ADD CONSTRAINT `fk_centrodeportivo_escenario` FOREIGN KEY (`cenId`) REFERENCES `centrodeportivo` (`cenId`);

--
-- Filtros para la tabla `precio`
--
ALTER TABLE `precio`
  ADD CONSTRAINT `fk_producto_precio` FOREIGN KEY (`prodId`) REFERENCES `producto` (`prodId`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `fk_categoria_producto` FOREIGN KEY (`catId`) REFERENCES `categoria` (`catId`);

--
-- Filtros para la tabla `usuariogrupo`
--
ALTER TABLE `usuariogrupo`
  ADD CONSTRAINT `fk_usuariogrupo_centrodeportivo` FOREIGN KEY (`cenId`) REFERENCES `centrodeportivo` (`cenId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuariogrupo_grupo` FOREIGN KEY (`gruId`) REFERENCES `grupo` (`gruId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuariogrupo_usuario` FOREIGN KEY (`usuId`) REFERENCES `usuario` (`usuId`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
