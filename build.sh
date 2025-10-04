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
    echo "Unknown OS"
fi