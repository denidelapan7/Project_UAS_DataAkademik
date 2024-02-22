<?php

if ($_SERVER['REQUES_METHOD'] == 'POST') {
	
	include 'koneksi.php';
	
	if ($_POST['action'] == 'tampil_dosen') {
		
		$a = mysqli_query ($koneksi, "SELECT * FROM dosen ORDER BY nid ASC");
		$result = [];
		while ($row = mysqli_fetch_array($a)) {
			
			array_push ($result, [
			'nid' => $row['nid'],
			'nama' => $row['nama'],
			'telepon' => $row['telepon'],
			]);
		}
		
		echo json_encode([
		   'DataDosen' => $result
		]);
	}
	
	mysqli_close ($koneksi);
}
