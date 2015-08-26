<?php
/****************************************************************
**  Proposito:  Ejecutar los scripts de automatizacion
**				* ejecutar Scripts
*****************************************************************/
	echo "\r\n*****************\r\n**  Iniciando scripts SAHI \r\n**********************\r\n\r\n";
	$classPath = ".";
	$classPath = $classPath . ";..\\Tests\\build\\classes\\";
	$classPath = $classPath . ";..\\Tests\\lib\\*";

	$run = "java -cp {$classPath} reservaCanchas.runner.TSRunner";
	try{
		$retVal = -1;
		echo "COMMAND: \r\n\t{$run}\r\n";
		system($run,$retVal);
		exit($retVal);
	}
	catch(Exception $exception){
		throw new Exception ('Falló la ejecucion de los tests SAHI', 0,$exception);
		echo "Excepción: {$exception->getMessage()}\r\n";
		exit($exception);
	}
?>