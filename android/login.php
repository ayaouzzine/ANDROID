<?php
include "config.php";
 
$response = array();
if($_POST['email'] && $_POST['password']){
	$email = $_POST['email'];
	$post_password = $_POST['password'];
	$stmt = $conn->prepare("SELECT username, password,role,code,validation FROM users WHERE email = ?");
	$stmt->bind_param("s",$email);
	$stmt->execute();
	$stmt->bind_result($username, $db_password,$role,$code,$validation);
	$stmt->store_result();
	
	$stmt->fetch();
	if(password_verify($post_password, $db_password)){
		$response['error'] = false;
		$response['message'] = "Login Successful!";
		$response['email'] = $email;
		$response['username'] = $username;
		$response['role']=$role;
		if($code=="0"||$code!=$validation)
			$response['nv']="nv";
	}else if($stmt->num_rows == 0){
		$response['error'] = true;
		$response['message'] = "Invalid Email";
	}
	 else{
		$response['error'] = true;
		$response['message'] = "Invalid Password";
	}
} else {
	$response['error'] = true;
	$response['message'] = "Insufficient Parameters";
}
echo json_encode($response);	
?>