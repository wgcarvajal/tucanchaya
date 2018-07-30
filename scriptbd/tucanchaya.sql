-- phpMyAdmin SQL Dump
-- version 4.6.5.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 30-07-2018 a las 15:52:44
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
-- Estructura de tabla para la tabla `centrodeportivo`
--

CREATE TABLE `centrodeportivo` (
  `cenId` bigint(20) NOT NULL,
  `ciuId` bigint(20) NOT NULL,
  `colorId` int(11) DEFAULT NULL,
  `cenNombre` varchar(100) NOT NULL,
  `cenDireccion` varchar(200) DEFAULT NULL,
  `cenTelefonos` varchar(200) DEFAULT NULL,
  `cenUbicacion` varchar(200) DEFAULT NULL,
  `cenDescripcion` longtext,
  `cenAlto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `centrodeportivofotos`
--

CREATE TABLE `centrodeportivofotos` (
  `cenfotId` bigint(20) NOT NULL,
  `cenfoNombre` varchar(40) NOT NULL,
  `cenId` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `ciuId` bigint(20) NOT NULL,
  `ciunombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`ciuId`, `ciunombre`) VALUES
(1, 'Popayán'),
(2, 'Neiva'),
(3, 'Bogota'),
(4, 'Pitalito'),
(5, 'Florencia'),
(6, 'Cali'),
(7, 'Medellin'),
(8, 'Armenia'),
(9, 'Barranquilla');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `color`
--

CREATE TABLE `color` (
  `colorId` int(11) NOT NULL,
  `colorHexa` varchar(10) NOT NULL,
  `colorNombre` varchar(50) NOT NULL
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
('superAdmin', NULL),
('user', NULL);

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
-- Indices de la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  ADD PRIMARY KEY (`cenId`),
  ADD KEY `fk_centrodeportivo_ciudad` (`ciuId`),
  ADD KEY `fk_centrodeportivo_color` (`colorId`);

--
-- Indices de la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  ADD PRIMARY KEY (`cenfotId`),
  ADD KEY `fk_centrodeportivofotos_centrodeportivo` (`cenId`);

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
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`gruId`);

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
-- AUTO_INCREMENT de la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  MODIFY `cenId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  MODIFY `cenfotId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `ciuId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT de la tabla `color`
--
ALTER TABLE `color`
  MODIFY `colorId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `usuId` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `centrodeportivo`
--
ALTER TABLE `centrodeportivo`
  ADD CONSTRAINT `fk_centrodeportivo_ciudad` FOREIGN KEY (`ciuId`) REFERENCES `ciudad` (`ciuId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_centrodeportivo_color` FOREIGN KEY (`colorId`) REFERENCES `color` (`colorId`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `centrodeportivofotos`
--
ALTER TABLE `centrodeportivofotos`
  ADD CONSTRAINT `fk_centrodeportivofotos_centrodeportivo` FOREIGN KEY (`cenId`) REFERENCES `centrodeportivo` (`cenId`) ON DELETE NO ACTION ON UPDATE NO ACTION;

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
