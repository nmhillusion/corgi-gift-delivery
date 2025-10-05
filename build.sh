#!/bin/bash

. ./.env

currDir=$(pwd)

echo "Current dir: $currDir"
echo "App name: $app_name"
echo "Frontend base href: $frontend_base_href"

cd frontend

if [[ "$OSTYPE" == "linux-gnu"* ]]; then
    echo "Build on Linux"
    var__frontend_base_href=$frontend_base_href npm run build:unix
elif [[ "$OSTYPE" == "darwin"* ]]; then
    echo "Build on macOS"
    var__frontend_base_href=$frontend_base_href npm run build:unix
elif [[ "$OSTYPE" == "cygwin" ]] || [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]]; then
    echo "Build on Windows"
    var__frontend_base_href=$frontend_base_href npm run build:win
else
    echo "ERROR: Unknown OS"
fi

cd ../backend

sed -i "s|app.name: .* # var__app_name|app.name: $app_name # var__app_name|g" ./src/main/resources/application.yml
sed -i "s|app.frontend_base_href: .* # var__frontend_base_href|app.frontend_base_href: $frontend_base_href # var__frontend_base_href|g" ./src/main/resources/application.yml

mvn clean compile package -Dmaven.test.skip=true