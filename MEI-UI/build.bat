@echo off
chcp 65001 >nul
echo.
echo ========================================
echo    MEI 前端项目打包脚本
echo ========================================
echo.

cd /d %~dp0

echo [1/3] 清理旧的打包文件...
if exist "dist" rmdir /s /q "dist"

echo [2/3] 开始打包前端项目...
call npm run build

if %errorlevel% neq 0 (
    echo.
    echo ❌ 前端打包失败！
    echo.
    pause
    exit /b 1
)

echo.
echo [3/3] 打包完成！
echo.
echo ✅ 前端打包产物位置: %~dp0dist
echo.
echo ========================================
echo    前端打包成功！
echo ========================================
echo.

pause
