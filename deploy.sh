#!/bin/bash

# Variables
TOMCAT_HOME="/home/livai/apache-tomcat-10.1.28" # Utilisation de la variable globale
PROJECT_DIR="$(pwd)"          # Répertoire du projet
BUILD_DIR="$PROJECT_DIR/build"
WAR_FILE="$BUILD_DIR/ETU003184.war"
DEPLOY_DIR="$TOMCAT_HOME/webapps"

# Couleurs pour les logs
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # Pas de couleur

echo -e "${GREEN}Début du déploiement...${NC}"

# Étape 1 : Nettoyer l'ancien build
echo "Nettoyage des anciens fichiers..."
rm -rf "$BUILD_DIR"
mkdir -p "$BUILD_DIR/WEB-INF/classes"

# Étape 2 : Compiler les fichiers Java
echo "Compilation des fichiers Java..."
find "$PROJECT_DIR/src" -name "*.java" > sources.txt
javac -d "$BUILD_DIR/WEB-INF/classes" -cp "$PROJECT_DIR/lib/servlet-api.jar" @sources.txt
if [ $? -ne 0 ]; then
  echo -e "${RED}Échec de la compilation.${NC}"
  exit 1
fi
rm sources.txt

# Étape 3 : Copier les fichiers HTML, CSS, web.xml, et autres ressources
echo "Copie des fichiers statiques et du fichier web.xml..."
# cp -r "$PROJECT_DIR/web"/* "$BUILD_DIR/"
cp -r "$PROJECT_DIR/src/main/webapp"/* "$BUILD_DIR/"

# Étape 4 : Créer le fichier WAR
echo "Création du fichier WAR..."
cd "$BUILD_DIR"
jar -cvf "$WAR_FILE" .
cd "$PROJECT_DIR"

# Étape 5 : Déployer sur Tomcat
echo "Déploiement sur Tomcat..."
rm -rf "$DEPLOY_DIR/ETU003184" "$DEPLOY_DIR/ETU003184.war"
cp "$WAR_FILE" "$DEPLOY_DIR/"

# Étape 6 : Redémarrer Tomcat
# echo "Redémarrage de Tomcat..."
# "$TOMCAT_HOME/bin/shutdown.sh"
# "$TOMCAT_HOME/bin/startup.sh"

echo -e "${GREEN}Déploiement terminé avec succès.${NC}"
