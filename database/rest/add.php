<?php

include 'koneksi.php');

$nid = $_POST['nid'];
$nama = $_POST['nama'];
$nid = $_POST['telepon'];

if (isset($nid) && isset($nama) && isset($telepon)) {
  
  $add_dosen = mysqli_query($koneksi, "INSERT INTO dosen VALUES('$nid','$nama','$telepon')");
  
  if ($add_dosen) {
	
	  $pesan = 'data berhasil disimpan';
  }else {
	  $pesan = 'data gagal disimpan';
}
echo json_encode(array(
'status' => $pesan
));
}