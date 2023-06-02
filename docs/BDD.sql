-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : ven. 27 jan. 2023 à 13:01
-- Version du serveur :  5.7.34
-- Version de PHP : 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `AtelierPro`
--

-- --------------------------------------------------------

--
-- Structure de la table `Employé`
--

CREATE TABLE `Employé` (
  `id` int(5) NOT NULL,
  `nom` varchar(10) NOT NULL,
  `prenom` varchar(10) NOT NULL,
  `mail` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL,
  `date_arr` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `date_dep` datetime DEFAULT NULL,
  `id_ligue` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Employé`
--

INSERT INTO `Employé` (`id`, `nom`, `prenom`, `mail`, `password`, `date_arr`, `date_dep`, `id_ligue`) VALUES
(1, 'root', '', '', 'toor', '2023-01-13 16:03:31', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `Ligue`
--

CREATE TABLE `Ligue` (
  `id` int(5) NOT NULL,
  `nom` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `Ligue`
--

INSERT INTO `Ligue` (`id`, `nom`) VALUES
(1, 'Fléchettes');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Employé`
--
ALTER TABLE `Employé`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Ligue`
--
ALTER TABLE `Ligue`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `Employé`
--
ALTER TABLE `Employé`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `Ligue`
--
ALTER TABLE `Ligue`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
