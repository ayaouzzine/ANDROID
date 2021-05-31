<?php
include "config.php";
use PHPMailer\PHPMailer\PHPMailer;
require 'C:\xampp\composer\vendor\autoload.php';
$response = array();

if($_POST['email'] && $_POST['code'] && $_POST['password'] && $_POST['confirmpassword']  ){
	$email = $_POST['email'];
    $code = $_POST['code'];
    $password = $_POST['password'];
    $hash = password_hash($_POST['password'], PASSWORD_DEFAULT);
    $confirmpassword = $_POST['confirmpassword'];
	$stmt = $conn->prepare("SELECT * FROM `users` WHERE `email` = ? and `code` = ?");
		$stmt->bind_param("ss", $email, $code);
		$stmt->execute();
		$stmt->store_result();
		if($stmt->num_rows > 0){
            if ($_POST['password'] != $_POST['confirmpassword']){
                $response['error'] = true;
                $response['message'] = "Passwords";
            }else{
			$sql = $conn->prepare("UPDATE `users` SET `validation`= ? , `password`= ?   WHERE `email` = ?");
			$sql->bind_param("sss", $code, $hash, $email);
			$result = $sql->execute();
			$response['error'] = false;
			$response['message'] = "Success";
        }
		}else {
			$response['error'] = false;
			$response['message'] = "Fail";			
			
		}
}

echo json_encode($response);	
?>