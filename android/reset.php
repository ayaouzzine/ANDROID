<?php
include "config.php";
use PHPMailer\PHPMailer\PHPMailer;
require 'C:\xampp\composer\vendor\autoload.php';
$response = array();

	$email = $_POST['email'];
	$stmt = $conn->prepare("SELECT * FROM `users` WHERE `email` = ?");
		$stmt->bind_param("s", $email);
		$stmt->execute();
		$stmt->store_result();
		if($stmt->num_rows > 0){
			$response['error'] = false;
		$response['message'] = "Success";
        $code = mt_rand(1000,9999);
        $messag = '<html><head>
                   <title>Password Reset</title>
                   </head>
                   <body>';
        $messag .= '<h1>Welcome to password reset ! </h1>';
        $messag .= '<p><a>You have tried to change your password using this account. To validate your choice, here is your confirmation code : <br></a><a style="font-weight:bold;font-size:1.2em">' . $code . '</a></p>';
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
        $mail->Subject = trim("Password Reset");
        // sending mail from
        $mail->SetFrom('replyandroapp@gmail.com', 'Confirmation');
        // sending to
        $mail->AddAddress($email);
        // set the message
        $mail->MsgHTML($messag);
        try {
          $mail->send();
          $sql = $conn->prepare("UPDATE `users` SET `code` =  ?, `validation` = ? WHERE `email`= ? ");
                $sql->bind_param("sss",$code,$code, $email);
                $result = $sql->execute();
               
        } catch (Exception $ex) {
        echo $ex->getMessage();}

		}else {
			$response['error'] = false;
			$response['message'] = "Fail";			
			
		}
echo json_encode($response);	
?>