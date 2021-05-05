<?php
include "config.php";
use PHPMailer\PHPMailer\PHPMailer;
require 'C:\xampp\composer\vendor\autoload.php';
$response = array();

if($_POST['email'] && $_POST['password'] && $_POST['conf_password'] && $_POST['username'] &&$_POST['gender'] ){
	if ($_POST['password'] != $_POST['conf_password']){
	$response['error'] = true;
	$response['message'] = "Passwords not matching! Try again";
}
	else{
	$email = $_POST['email'];
	$password = password_hash($_POST['password'], PASSWORD_DEFAULT);
	$username = $_POST['username'];
	$gender= $_POST['gender'];
	$phone=$_POST['phone'];

	$sql = $conn->prepare("SELECT * FROM users WHERE email = ?");
	$sql->bind_param("s",$email);
	$sql->execute();
	$sql->store_result();
	if($sql->num_rows > 0){
		$response['error'] = true;
		$response['message'] = "User already registered!\nTry with another email";
	} else{
		$stmt = $conn->prepare("INSERT INTO `users` (`username`, `email`, `password` , `gender` , `phone`, `role`) VALUES(?,?,?,?,?,'C')");
		$stmt->bind_param("sssss", $username, $email, $password, $gender, $phone);
		$result = $stmt->execute();
		if($result){
			$response['error'] = false;
			$response['message'] = "User Registered Successfully";
			$response['email'] = $email;
			
$code = mt_rand(1000,9999);
$messag = '<html><head>
           <title>Email Verification</title>
           </head>
           <body>';
$messag .= '<h1>Hi ' . $username . ' ! </h1>';
$messag .= '<p><a>You have tried to register using this account. To validate your registration, here is your confirmation code : <br></a><a style="font-weight:bold;font-size:1.2em">' . $code . '</a></p>';
$messag .= "</body></html>";
// php mailer code starts
$mail = new PHPMailer(true);
// telling the class to use SMTP
$mail->IsSMTP();
// enable SMTP authentication
$mail->SMTPAuth = true;   
// sets the prefix to the server
$mail->SMTPSecure = "ssl"; 
// sets GMAIL as the SMTP server
$mail->Host = "smtp.gmail.com"; 
// set the SMTP port for the GMAIL server
$mail->Port = 465; 
// set your username here
$mail->Username = 'replyandroapp@gmail.com';
$mail->Password = 'androidapp';
// set your subject
$mail->Subject = trim("Email Verifcation");
// sending mail from
$mail->SetFrom('replyandroapp@gmail.com', 'Confirmation');
// sending to
$mail->AddAddress($email);
// set the message
$mail->MsgHTML($messag);
try {
  $mail->send();
  $stmt = $conn->prepare("UPDATE `users` SET `code` =  ? WHERE `email`= ? ");
		$stmt->bind_param("ss",$code, $email);
		$result = $stmt->execute();
} catch (Exception $ex) {
echo $ex->getMessage();}

		} else {
			$response['error'] = true;
			$response['message'] = "Cannot complete user registration";
		}
	}
}
}
 else{
	$response['error'] = true;
	$response['message'] = "Insufficient Parameters";
}

echo json_encode($response);	
?>
