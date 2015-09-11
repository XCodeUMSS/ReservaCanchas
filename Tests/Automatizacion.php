<?php
/****************************************************************
**  Proposito:  Ejecutar los scripts de automatizacion
**				* ejecutar Scripts
*****************************************************************/
        $baseDir = getcwd();
	echo     "\r\n**********************"
           . "\r\n**  Iniciando SAHI  **"
           . "\r\n**********************\r\n\r\n";
        chdir("C:\\sahi\\userdata\\bin\\");
        $sahi = "taskkill /f /fi \"WINDOWTITLE eq C:\Windows\system32\cmd.exe - start_dashboard.bat\""
			  . "& taskkill /f /fi \"WINDOWTITLE eq Start Sahi\""
			  . "& taskkill /f /fi \"WINDOWTITLE eq Sahi Dashboard\""
              . " & start /I /MIN start_dashboard.bat";
	try{
		echo "COMMAND: \r\n\t{$sahi}\r\n";
		system($sahi,$retVal);
	}
	catch(Exception $exception){
		throw new Exception ('Falló la ejecucion de SAHI', 0,$exception);
		echo "Excepción: {$exception->getMessage()}\r\n";
		exit($exception);
	}
        chdir($baseDir);
	echo     "\r\n************************"
           . "\r\n**  Iniciando Autoit  **"
           . "\r\n************************\r\n\r\n";
        system("regsvr32.exe /s AutoItX3.dll",$retVal);
        echo "\t* Return value: {$retVal}\r\n\r\n";
        system("regsvr32.exe /s AutoItX3_x64.dll",$retVal);
        echo "\t* Return value: {$retVal}\r\n\r\n";
        
	echo     "\r\n********************************"
           . "\r\n**  Compilando Proyecto SAHI  **"
           . "\r\n********************************\r\n\r\n";
        system("ant -Dnb.internal.action.name=rebuild clean jar",$retVal);
        echo "\t* Return value: {$retVal}\r\n\r\n";
        
	echo     "\r\n******************************"
           . "\r\n**  Iniciando scripts SAHI  **"
           . "\r\n******************************\r\n\r\n";
        chdir($baseDir);
	$classPath = ".";
	$classPath = $classPath . ";.\\build\\classes\\";
	$classPath = $classPath . ";.\\lib\\*";

	$run = "java -cp {$classPath} reservaCanchas.runner.TSRunner";
	try{
		$retVal = -1;
		echo "COMMAND: \r\n\t{$run}\r\n";
		system($run,$retVal);
	}
	catch(Exception $exception){
		throw new Exception ('Falló la ejecucion de los tests SAHI', 0,$exception);
		echo "Excepción: {$exception->getMessage()}\r\n";
		exit($exception);
	}
        chdir($baseDir);
	echo     "\r\n*******************************************************"
           . "\r\n**  Abriendo resultados en el navegador por defecto  **"
           . "\r\n*******************************************************\r\n\r\n";
        system("start " . $baseDir . "\\test-output\\index.html",$retVal);
        echo "\t* Return value: {$retVal}\r\n\r\n";
        
?>