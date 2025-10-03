. .env

currDir=$(pwd)

echo "Current dir: $currDir"
echo "App name: $app_name"
echo "Frontend base href: $frontend_base_href"

cd frontend
frontend_base_href=$frontend_base_href npm run build