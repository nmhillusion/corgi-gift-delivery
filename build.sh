. ./.env

currDir=$(pwd)

echo "Current dir: $currDir"
echo "App name: $app_name"
echo "Frontend base href: $frontend_base_href"

cd frontend

OLD_PATTERN="var__frontend_base_href"
sed "s|$OLD_PATTERN|$frontend_base_href|g" "./package.template.json" > "./package.json"
npm run build