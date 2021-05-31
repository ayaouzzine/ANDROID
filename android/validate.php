<?php
include "config.php";
$response = array();

if($_POST['email'] && $_POST['code']){
	$email = $_POST['email'];
	$code = $_POST['code'];
	$stmt = $conn->prepare("SELECT * FROM `users` WHERE `email` = ? and `code` = ?");
		$stmt->bind_param("ss", $email, $code);
		$stmt->execute();
		$stmt->store_result();
		if($stmt->num_rows > 0){
			$stmt = $conn->prepare("UPDATE `users` SET `validation`= ?  WHERE `email` = ?");
			$stmt->bind_param("ss", $code, $email);
			$result = $stmt->execute();
			$response['error'] = false;
			$response['message'] = "Success";
			
		}else {
			$response['error'] = false;
			$response['message'] = "Fail";			
			
		}
}
echo json_encode($response);	
?>