<?php

if ($_SERVER['REQUES_METHOD'] == 'POST') {
	
	include 'koneksi.php';
	
	if ($_POST['action'] == 'EditeData') {
		$nid = $_POST['nid'];
		
		$hapus = mysqli_query ($koneksi, "UPDATE * FROM dosen WHERE nid =  '$nid'");
		
		if ($hapus) {
		
		$response['status'] = 'data berhasil diupdate';
	}else {
		$response['status'] = 'gagal';
	}
	echo json_decode($response);
	}
	
	mysqli_close ($koneksi);
}